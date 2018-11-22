package com.redread.libary;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.URLUtil;

import com.redread.MyApplication;
import com.redread.R;
import com.redread.base.BaseActivity;
import com.redread.bookrack.Activity_pdfReader;
import com.redread.bookrack.Activity_txtReader;
import com.redread.databinding.LayoutBookdetailBinding;
import com.redread.model.bean.Book;
import com.redread.model.entity.DownLoad;
import com.redread.model.gen.DownLoadDao;
import com.redread.net.Api;
import com.redread.net.netbean.NetBeanBook;
import com.redread.rxbus.RxBus;
import com.redread.rxbus.bean.RXRefreshBooktract;
import com.redread.utils.Constant;
import com.redread.utils.DownLoadThread;
import com.redread.utils.GlideUtils;
import com.redread.widget.ArcProgress2;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangshexin on 2018/9/20.
 * 图书详情页
 */

public class Activity_bookDetail extends BaseActivity implements View.OnClickListener {
    private String TAG = getClass().getName();
    private LayoutBookdetailBinding binding;

    public static String EXTR_BOOK = "book";

    private DownLoadDao dao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.layout_bookdetail);
        binding.bookDetailBorrow.setOnClickListener(this);
        dao = MyApplication.getInstances().getDaoSession().getDownLoadDao();
        initView();
    }

    // 图书下载地址
    private String downUrl = null;
    private NetBeanBook book;
    private int progess = 0;
    private Handler taskHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (book != null) {

                //根据url查找
                List<DownLoad> searchResult = dao.queryBuilder().where(new WhereCondition.StringCondition("url ='" + book.getBrowsPath() + "'")).list();
                if (searchResult.size() > 0) {
                    DownLoad task = searchResult.get(0);
                    if (task.getStatus() != Constant.DOWN_STATUS_SUCCESS)
                        binding.circleIndicator.setVisibility(View.VISIBLE);
                    else {
                        binding.circleIndicator.setVisibility(View.GONE);
                    }

                    if (task.getDataLongth() != 0) {
                        binding.circleIndicator.setMax((int) task.getDataLongth());
                        binding.circleIndicator.setProgress((int) task.getDownProgress());
                    }
                    switch (task.getStatus()) {
                        case Constant.DOWN_STATUS_FAILE:
                            binding.bookDetailRed.setText(R.string.down_text_faile);
                            break;
                        case Constant.DOWN_STATUS_SUCCESS:
                            //成功直接跳转阅读
                            binding.bookDetailRed.performClick();
                            break;
                        case Constant.DOWN_STATUS_PAUS:
                            binding.bookDetailRed.setText(R.string.down_text_pause);
                            break;
                        case Constant.DOWN_STATUS_WAIT:
                            binding.bookDetailRed.setText(R.string.down_text_wait);
                            break;
                        case Constant.DOWN_STATUS_ING:
                            binding.bookDetailRed.setText(R.string.down_text_ing);
                            break;
                        default:
                            binding.bookDetailRed.setText("立即阅读");
                            break;
                    }

                }
            }
            sendEmptyMessageDelayed(1, 1000);
        }
    };


    private void initView() {
        book = (NetBeanBook) getIntent().getSerializableExtra(EXTR_BOOK);
        List<DownLoad> searchResult = dao.queryBuilder().where(new WhereCondition.StringCondition("url ='" + book.getBrowsPath() + "'")).list();
        DownLoad data=null;
        if (searchResult.size() > 0)
            if ((data = searchResult.get(0)).getStatus() == Constant.DOWN_STATUS_SUCCESS) {
                binding.circleIndicator.setVisibility(View.GONE);
            } else {
                binding.circleIndicator.setVisibility(View.VISIBLE);
                if (data != null && data.getDataLongth() > 0)
                    binding.circleIndicator.setMax((int) data.getDataLongth());
                if (data != null)
                    binding.circleIndicator.setProgress((int) data.getDownProgress());
            }
        binding.circleIndicator.setOnCenterDraw(new ArcProgress2.OnCenterDraw() {
            @Override
            public void draw(Canvas canvas, RectF rectF, float x, float y, float storkeWidth, int progress) {
                Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                textPaint.setStrokeWidth(35);
                textPaint.setTextSize(20);
                textPaint.setColor(getResources().getColor(R.color.red));
                float p = progress / (float)binding.circleIndicator.getMax();
                String progressStr = String.valueOf(p * 100F);
                if(progressStr.length()>3){
                    progressStr=progressStr.substring(0,3)+"%";
                }
                float textX = x - (textPaint.measureText(progressStr) / 2);
                float textY = y - ((textPaint.descent() + textPaint.ascent()) / 2);
                canvas.drawText(progressStr, textX, textY, textPaint);
            }
        });
        if (book == null)
            return;
        GlideUtils.LoadImageRandomColor(this, Api.downUrl + book.getCoverPath(), binding.bookDetailCover);
        binding.bookDetailBookName.setText(book.getName());
        binding.introduction.setText(book.getIntroduction());
        downUrl = Api.downUrl + book.getBrowsPath();

        binding.bookDetailRed.setOnClickListener(this);
        binding.bookDetailBorrow.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //启一个handler不停的查状态，对下载状态进行表显
        taskHandler.sendEmptyMessageDelayed(1, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        taskHandler.removeMessages(1);
    }

    @Override
    public void onClick(View v) {
        if (book == null)
            return;
        switch (v.getId()) {
            case R.id.bookDetail_borrow://如果没有借阅地址则跳转到推荐馆茂
                if (!URLUtil.isHttpUrl(book.getBorrowAddress())) {
                    Bundle bundle = new Bundle();
                    bundle.putString("bookName", book.getName());
                    bundle.putString("bookId", book.getId());//TODO id要重处理
                    startActivity(Activity_recommend.class, bundle);
                } else {
                    WebView_borrow.goMe(this,"借阅",book.getBorrowAddress());
                }
                break;
            case R.id.bookDetail_red:
                //判断是否已下载，没下载先下，下了直接看
                doAction();
                break;
        }
    }


    /**
     * 执行下载或打开
     */
    private void doAction() {
        lockBtn(true);
        //根据url查找
        List<DownLoad> searchResult = dao.queryBuilder().where(new WhereCondition.StringCondition("url ='" + book.getBrowsPath() + "'")).list();
        if (searchResult.size() > 0 && searchResult.get(0).getStatus() == Constant.DOWN_STATUS_SUCCESS) {
            //太好了，找到了，不用下了
            Book book = Book.conver2Book(searchResult.get(0));
            //去阅读
            if (book.getBookType().equals(Constant.BOOK_TYPE_TXT))
                Activity_txtReader.loadTxtFile(this, book.getBookDir(), book.getBookName(), book.getId());
            else if (book.getBookType().equals(Constant.BOOK_TYPE_PDF)) {
                Intent intent = new Intent(this, Activity_pdfReader.class);
                intent.putExtra("book", book);
                startActivity(intent);
            }
            lockBtn(false);
        } else if (searchResult.size() == 0) {
            //没有记录
            DownLoad task = new DownLoad();
            task.setStatus(Constant.DOWN_STATUS_WAIT);
            task.setUrl(book.getBrowsPath());
            task.setUpDate(new Date(System.currentTimeMillis()));
            task.setCoverUrl(book.getCoverPath());
            task.setBookType(book.getType().toUpperCase());
            task.setAuthor(book.getAuthor());
            task.setBookName(book.getName());
            //嗥，没找着，下吧
            DownLoadThread.getInstanc().downLoad(task);
            //删完了更新一下页面展示
            RxBus.getDefault().post(new RXRefreshBooktract(RXRefreshBooktract.STATUE_REFRESH));
        } else {
            //有但需要改状态和时间
            DownLoad taskOld = searchResult.get(0);
            taskOld.setStatus(Constant.DOWN_STATUS_WAIT);
            taskOld.setUpDate(new Date(System.currentTimeMillis()));
            dao.update(taskOld);
            DownLoadThread.getInstanc().downLoad(taskOld);
        }
    }

    private void lockBtn(boolean lock) {
        binding.bookDetailRed.setClickable(!lock);
    }
    // overridePendingTransition(R.anim.bottom_in,R.anim.bottom_out);
}
