package com.redread;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bifan.txtreaderlib.bean.TxtMsg;
import com.bifan.txtreaderlib.interfaces.ILoadListener;
import com.redread.databinding.LayoutTxtBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by zhangshexin on 2018/8/31.
 *
 * txt类型的阅读
 */

public class Activity_txt extends Activity implements ILoadListener {
    private String TAG=this.getClass().getName();
    private LayoutTxtBinding binding;
    private String assetFile="yuanzun.txt";
    private String sdFile=Environment.getExternalStorageDirectory().getPath()+"/read";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        putAssetsToSDCard(this,assetFile,sdFile);
        Activity_txtReader.loadTxtFile(this, Environment.getExternalStorageDirectory().getPath()+"/read/yuanzun.txt");
//        binding= DataBindingUtil.setContentView(this,R.layout.layout_txt);
//        binding.activityTxtReader.loadTxtFile(Environment.getExternalStorageDirectory().getPath()+"/read/yuanzun.txt",this);
    }

    @Override
    public void onSuccess() {
        Log.e(TAG, "----------onSuccess: ");
    }

    @Override
    public void onFail(TxtMsg txtMsg) {
        Log.e(TAG, "------------onFail: " );
    }

    @Override
    public void onMessage(String s) {
        Log.e(TAG, "-------------onMessage: ");
    }


    /**
     * 将assets下的文件放到sd指定目录下
     *
     * @param context    上下文
     * @param assetsPath assets下的路径
     * @param sdCardPath sd卡的路径
     */
    public static void putAssetsToSDCard(Context context, String assetsPath,
                                         String sdCardPath) {
        try {
            String mString[] = context.getAssets().list(assetsPath);
            if (mString.length == 0) { // 说明assetsPath为空,或者assetsPath是一个文件
                InputStream mIs = context.getAssets().open(assetsPath); // 读取流
                byte[] mByte = new byte[1024];
                int bt = 0;
                File file = new File(sdCardPath );
                if(!file.exists()){
                    file.mkdirs();
                }
                file=new File(file,assetsPath);
                if (!file.exists()) {
                    file.createNewFile(); // 创建文件
                } else {
                    return;//已经存在直接退出
                }
                FileOutputStream fos = new FileOutputStream(file); // 写入流
                while ((bt = mIs.read(mByte)) != -1) { // assets为文件,从文件中读取流
                    fos.write(mByte, 0, bt);// 写入流到文件中
                }
                fos.flush();// 刷新缓冲区
                mIs.close();// 关闭读取流
                fos.close();// 关闭写入流

            } else { // 当mString长度大于0,说明其为文件夹
                sdCardPath = sdCardPath + File.separator + assetsPath;
                File file = new File(sdCardPath);
                if (!file.exists())
                    file.mkdirs(); // 在sd下创建目录
                for (String stringFile : mString) { // 进行递归
                    putAssetsToSDCard(context, assetsPath + File.separator
                            + stringFile, sdCardPath);
                }
            }
        } catch (
                Exception e
                )

        {
            e.printStackTrace();
        }
    }

}
