package com.redread.bookrack.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.redread.MyApplication;
import com.redread.R;
import com.redread.base.BaseRecycelAdapter;
import com.redread.base.BaseViewHolder;
import com.redread.databinding.LayoutBooktrackCellBinding;
import com.redread.model.bean.Book;
import com.redread.model.gen.DownLoadDao;
import com.redread.rxbus.RxBus;
import com.redread.rxbus.bean.RXRefreshBooktract;
import com.redread.utils.AlertDialogUtil;
import com.redread.utils.GlideUtils;
import com.redread.utils.IOUtile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangshexin on 2018/9/17.
 */

public class Adapter_booktrack extends BaseRecycelAdapter<BaseViewHolder> {
    private String TAG = getClass().getName();
    private List<Book> books;
    private DownLoadDao dao;
    private final int WHAT_PROGRESS = 1;
    private final int WHAT_PROGRESS_FINISH = 2;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case WHAT_PROGRESS:
                    alertDialogUtil.getLayoutDialogProgressBinding().dialogProgressAccurate.setProgress(msg.arg1);
                    break;
                case WHAT_PROGRESS_FINISH:
                    alertDialogUtil.dismiss();
                    break;
            }
        }
    };

    public Adapter_booktrack(Context context, List<Book> books) {
        super(context);
        this.books = books;
        dao = MyApplication.getInstances().getDaoSession().getDownLoadDao();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutBooktrackCellBinding binding = DataBindingUtil.inflate(inflater, R.layout.layout_booktrack_cell, parent, false);
        return new BaseViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        LayoutBooktrackCellBinding binding = (LayoutBooktrackCellBinding) holder.getBinding();
        Book book = books.get(position);
        binding.booktrackCellName.setText(book.getBookName());
        //阅读进度
        binding.booktrackCellProgress.setText(book.getReadProgress() + "%");
        //如果有封面则显示
        GlideUtils.glideLoader(mContext, book.getCoverUrl(), R.drawable.side_nav_bar, R.drawable.side_nav_bar, binding.booktrackCellCover);
        //显示隐藏选中按钮
        binding.booktrackCellCheck.setVisibility(book.isShowCheckBtn() ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    private AlertDialogUtil alertDialogUtil;

    /**
     * 删除选中的图书
     */
    public void deleteSelectBooks() {
        alertDialogUtil = new AlertDialogUtil(mContext).showReentryDialog(null, null).setOnLeftBtnClickListener(new AlertDialogUtil.OnLeftBtnClickListener() {
            @Override
            public void leftClickListener(View v) {
                doDelete();
            }
        }).setOnRightBtnClickListener(new AlertDialogUtil.OnRightBtnClickListener() {
            @Override
            public void rightClickListener(View v, int statue) {
                if (statue == AlertDialogUtil.DIALOG_PROGRESS) {
                    //停止删除
                    Log.e(TAG, "停止删除了====== ");
                    canDelete = false;
                } else {
                    alertDialogUtil.dismiss();
                }
            }
        });
    }

    private boolean canDelete = true;

    /**
     * 执行删除
     */
    private void doDelete() {
        //过滤出所有已选中的
        List<Book> checkBooks = getCheckBooks();
        int max = checkBooks.size();
        //如果什么也没选，直接过
        if(max==0){
            alertDialogUtil.dismiss();
            //取消删除的状态
            RxBus.getDefault().post(new RXRefreshBooktract(RXRefreshBooktract.STATUE_DELETE_CANCEL));
            canDelete = true;
            return;
        }
        alertDialogUtil.showProgressDialog(max);
        new Thread(new Runnable() {
            @Override
            public void run() {
                int progress = 0;
                do {
                    dao.deleteByKey(checkBooks.get(progress).getId());
                    //先删数据库记录，再删书
                    IOUtile.deleteFile(checkBooks.get(progress).getBookDir());
                    progress++;
                    //更新进度
                    progress++;
                    sendHandler(WHAT_PROGRESS, progress,0);
                } while (canDelete && progress < max);
                canDelete = true;
                sendHandler(WHAT_PROGRESS_FINISH, 0,1000);//这么作的原因是如果数据少就会很快完成，看不到效果
                //删完了更新一下页面展示
                RxBus.getDefault().post(new RXRefreshBooktract(RXRefreshBooktract.STATUE_REFRESH));
            }
        }).start();
    }

    private void sendHandler(int what, int arg1, long delay) {
        Message msg = new Message();
        msg.what = what;
        if (arg1 > 0)
            msg.arg1 = arg1;
        if (delay > 0)
            mHandler.sendMessageDelayed(msg, delay);
        else
            mHandler.sendMessage(msg);
    }

    /**
     * 过滤出所有已选中的
     *
     * @return
     */
    private List<Book> getCheckBooks() {
        List<Book> checkBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.isChecked())
                checkBooks.add(book);
        }
        return checkBooks;
    }

    /**
     * 显示或隐藏选则按钮
     *
     * @param show
     */
    public void showCheckBtn(boolean show) {
        for (int i = 0; i < books.size(); i++) {
            books.get(i).setShowCheckBtn(show);
        }
        notifyDataSetChanged();
    }
}
