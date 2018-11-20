package com.redread.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.redread.MyApplication;
import com.redread.model.entity.DownLoad;
import com.redread.model.gen.DownLoadDao;
import com.redread.net.Api;

import org.greenrobot.greendao.query.WhereCondition;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhangshexin on 2018/11/7.
 */

public class DownLoadThread {

    /**
     * 将封面的url转在本地存储路径
     * @param coverUrl 从数据库取出
     * @return
     */
    public static String converCoverDir(String coverUrl){
        String cu=coverUrl.substring(coverUrl.lastIndexOf("/")==-1?0:coverUrl.lastIndexOf("/"));
        return Constant.picture+cu;
    }


    /**
     * 将书的url转在本地存储路径
     * @param bookUrl 从数据库取出
     * @return
     */
    public static String converBookDir(String bookUrl){
        String cu=bookUrl.substring(bookUrl.lastIndexOf("/")==-1?0:bookUrl.lastIndexOf("/"));
        return Constant.bookPath+cu;
    }


    private String TAG = getClass().getName();
    private static DownLoadThread instance;

    public static synchronized DownLoadThread getInstanc() {
        if (instance == null) {
            instance = new DownLoadThread();
        }
        return instance;
    }

    /**
     * 是否有任务在进行，如果有则不用管，没有需要启动
     */
    private boolean ing = false;
    private DownLoadDao dao;

    public DownLoadThread() {
        dao = MyApplication.getInstances().getDaoSession().getDownLoadDao();
    }

    /**
     * 执行下载
     */
    public void downLoad(@NonNull DownLoad task) {
        insertOneTask(task);
        if (ing)
            return;
        ing = true;
        new Thread() {
            @Override
            public void run() {
                DownLoad task = getLastedTask();
                do {

                    try {
                        //1下载图片
                        try {
                            String coverUrlStr = Api.downUrl + task.getCoverUrl();
                            URL coverUrl = new URL(coverUrlStr);
                            URLConnection connection = coverUrl.openConnection();
                            connection.addRequestProperty("Accept-Encoding", "identity");
                            connection.connect();
                            InputStream coverIns = connection.getInputStream();
                            long coverLongth = connection.getContentLength();
                            //下图片
                            File picDir = new File(Constant.picture);
                            if (!picDir.isDirectory()) {
                                picDir.mkdirs();
                            }
                            File coverFile = new File(picDir, task.getCoverUrl());
                            //如果存在判断一下大小是不是一样，一样就不下了
                            if (coverFile.exists() && coverFile.length() != coverLongth) {
                                coverFile.delete();
                            }
                            if (!coverFile.exists()) {
                                FileOutputStream coverFos = new FileOutputStream(coverFile);
                                byte buf[] = new byte[1024];
                                int len = 0;
                                while ((len = coverIns.read(buf)) != -1) {
                                    coverFos.write(buf, 0, len);
                                }
                                coverFos.flush();
                                coverFos.close();
                            }
                            coverIns.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //2、下载图书
                        String bookUrlStr = Api.downUrl + task.getUrl();
                        File bookDir = new File(Constant.bookPath);
                        if (!bookDir.isDirectory()) {
                            bookDir.mkdirs();
                        }
                        File bookFile = new File(bookDir, task.getUrl());
//                        RandomAccessFile bookRWDFile = new RandomAccessFile(bookFile, "rwd");
                        long totalLength ;//文件长度
                        long currentLength = 0;//已下载的长度
                        if (bookFile.exists()) {
                            bookFile.delete();
                        }
                        FileOutputStream fos=new FileOutputStream(bookFile);
                        URL bookUrl = new URL(bookUrlStr);
                        HttpURLConnection bookUrlConnection = (HttpURLConnection) bookUrl.openConnection();
                        bookUrlConnection.addRequestProperty("Accept-Encoding", "identity");
                        //后台不支持
//                        bookUrlConnection.setRequestProperty("Range", "bytes=" + currentLength + "-" + totalLength);//设置下载范围
                        bookUrlConnection.connect();
//                        bookRWDFile.seek(currentLength);//指向上次的位置
                        InputStream bookIns = bookUrlConnection.getInputStream();
                        totalLength = bookUrlConnection.getContentLength();
                        if (totalLength > 0) {
                            byte[] buffer = new byte[1024];
                            int len;
                            boolean isInterrupt = false;//是否打断了当前的循环,对在下载过程中用户手动操作处理
                            while ((len = bookIns.read(buffer)) != -1 && !isInterrupt) {
                                //写入文件
//                                bookRWDFile.write(buffer, 0, len);
                                fos.write(buffer, 0, len);
                                currentLength += len;
                                task.setDownProgress(currentLength);
                                //查一下状态是不是用户给暂停了或着删除了，进行相应的处理
                                Log.e(TAG, task.getBookName() + " 任务下载进度： " + task.getDownProgress() + "/" + task.getDataLongth());
                                DownLoad resTask = dao.load(task.getId());//查找到表中的记录
                                resTask.setDownProgress(currentLength);
                                if (resTask.getDataLongth() == 0)//这个只设置一次，因为用了断点下载不能重复设置
                                    resTask.setDataLongth(totalLength);
                                resTask.setBookDir(converCoverDir(resTask.getUrl()));//写入封面的本地路径
                                resTask.setCoverDir(converBookDir(resTask.getCoverUrl()));//写入文件的本地路径

                                switch (resTask.getStatus()) {
                                    case Constant.DOWN_STATUS_WAIT://等待下载
                                    case Constant.DOWN_STATUS_ING://下载中
                                        resTask.setStatus(Constant.DOWN_STATUS_ING);
                                        //状态正常需更新进度
                                        if (resTask.getDataLongth() == currentLength) {
                                            //下完了，更新状态
                                            resTask.setStatus(Constant.DOWN_STATUS_SUCCESS);
                                        }
                                        //更新
                                        dao.update(resTask);
                                        break;
                                    case Constant.DOWN_STATUS_PAUS://暂停了
                                        isInterrupt = true;
                                        //更新
                                        dao.update(resTask);
                                        break;
                                    case Constant.DOWN_STATUS_FAILE://失败了
                                        //理论上这种状态不用处理，需要用户手动处理
                                        break;
                                    case Constant.DOWN_STATUS_CLEAR://删除了
                                        //用户在书架用了删除，当时可能正在下载中所以在这里要作删除
                                        isInterrupt = true;
                                        dao.deleteByKey(task.getId());
//                                        bookRWDFile.close();
                                        bookIns.close();
                                        bookFile.delete();
                                        break;
                                }

                            }
//                            //更新状态
                            DownLoad resTask = dao.load(task.getId());
                            //结束时可能是因为用户暂停。所以要判断一下当前的进度和文件长度是否一致
                            if(resTask.getDownProgress()>=resTask.getDataLongth())
                                resTask.setStatus(Constant.DOWN_STATUS_SUCCESS);
                            else
                                resTask.setStatus(Constant.DOWN_STATUS_PAUS);
                            dao.update(resTask);
                        } else {
                            //更新状态
                            DownLoad resTask = dao.load(task.getId());
                            resTask.setStatus(Constant.DOWN_STATUS_FAILE);
                            dao.update(resTask);
                        }
                        //下载完成
//                        bookRWDFile.close();
                        bookIns.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(TAG, "任务下载失败： " + task.getBookName() + "\n" + e.toString());
                        //更新为下载失败，并更新状态
                        DownLoad resTask = dao.load(task.getId());
                        resTask.setStatus(Constant.DOWN_STATUS_FAILE);
                        dao.update(resTask);
                    }

                } while ((task = getLastedTask()) != null);
                ing = false;
            }
        }.start();
    }

    /**
     * 如果有这本书的任务就不管了，没有就插入
     *
     * @param task
     * @return
     */
    private void insertOneTask(DownLoad task) {
        List<DownLoad> searchResult = dao.queryBuilder().where(new WhereCondition.StringCondition("url ='" + task.getUrl() + "'")).list();
        if (searchResult.size() == 0)
            dao.insert(task);
    }

    /**
     * 取最早的一条登待下载任务
     *
     * @return
     */
    private DownLoad getLastedTask() {
        List<DownLoad> temp = dao.queryBuilder().where(new WhereCondition.StringCondition("status = " + Constant.DOWN_STATUS_WAIT)).orderAsc(DownLoadDao.Properties.UpDate).limit(1).list();
        if (temp.size() != 0)
            return temp.get(0);
        return null;
    }
}
