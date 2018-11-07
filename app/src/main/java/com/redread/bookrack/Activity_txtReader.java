//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.redread.bookrack;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import com.bifan.txtreaderlib.R.color;
import com.bifan.txtreaderlib.R.drawable;
import com.bifan.txtreaderlib.R.id;
import com.bifan.txtreaderlib.R.layout;
import com.bifan.txtreaderlib.bean.TxtMsg;
import com.bifan.txtreaderlib.interfaces.ICenterAreaClickListener;
import com.bifan.txtreaderlib.interfaces.IChapter;
import com.bifan.txtreaderlib.interfaces.ILoadListener;
import com.bifan.txtreaderlib.interfaces.IPageChangeListener;
import com.bifan.txtreaderlib.interfaces.ISliderListener;
import com.bifan.txtreaderlib.interfaces.ITextSelectListener;
import com.bifan.txtreaderlib.main.TxtConfig;
import com.bifan.txtreaderlib.main.TxtReaderView;
import com.bifan.txtreaderlib.ui.ChapterList;
import com.redread.MyApplication;
import com.redread.R;
import com.redread.model.entity.DownLoad;
import com.redread.model.gen.DownLoadDao;

import java.io.File;

public class Activity_txtReader extends AppCompatActivity {
    protected Handler mHandler;
    protected View mTopDecoration;
    protected View mBottomDecoration;
    protected TextView mChapterNameText;
    protected TextView mChapterMenuText;
    protected TextView mProgressText;
    protected TextView mSettingText;
    protected TextView mSelectedText;
    protected TxtReaderView mTxtReaderView;
    protected View mTopMenu;
    protected View mBottomMenu;
    protected View mCoverView;
    protected View ClipboardView;
    protected String CurrentSelectedText;
    protected ChapterList mChapterListPop;
    protected Activity_txtReader.MenuHolder mMenuHolder = new Activity_txtReader.MenuHolder();
    private final int[] StyleTextColors = new int[]{Color.parseColor("#4a453a"), Color.parseColor("#505550"), Color.parseColor("#453e33"), Color.parseColor("#8f8e88"), Color.parseColor("#27576c")};
    protected String FilePath = null;
    protected String FileName = null;
    private long id;
    private Toast t;
    boolean hasExisted = false;

    private DownLoadDao dao;
    public Activity_txtReader() {
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(this.getContentViewLayout());
        dao = MyApplication.getInstances().getDaoSession().getDownLoadDao();
        this.getIntentData();
        this.init();
        this.loadFile();
        this.registerListener();
    }

    protected int getContentViewLayout() {
        return com.bifan.txtreaderlib.R.layout.activity_hwtxtpaly;
    }

    protected void getIntentData() {
        this.FilePath = this.getIntent().getStringExtra("FilePath");
        this.FileName = this.getIntent().getStringExtra("FileName");
        this.id=getIntent().getLongExtra("id",0);
    }

    public static void loadTxtFile(Context context, String FilePath,long id) {
        loadTxtFile(context, FilePath, (String)null,id);
    }

    public static void loadTxtFile(Context context, String FilePath, String FileName,long id) {
        Intent intent = new Intent();
        intent.putExtra("FilePath", FilePath);
        intent.putExtra("FileName", FileName);
        intent.putExtra("id", id);
        intent.setClass(context, Activity_txtReader.class);
        context.startActivity(intent);
    }

    protected void init() {
        this.mHandler = new Handler();
        this.mTopDecoration = this.findViewById(R.id.activity_hwtxtplay_top);
        this.mBottomDecoration = this.findViewById(R.id.activity_hwtxtplay_bottom);
        this.mTxtReaderView = (TxtReaderView)this.findViewById(R.id.activity_hwtxtplay_readerView);
        this.mChapterNameText = (TextView)this.findViewById(R.id.activity_hwtxtplay_chaptername);
        this.mChapterMenuText = (TextView)this.findViewById(R.id.activity_hwtxtplay_chapter_menutext);
        this.mProgressText = (TextView)this.findViewById(R.id.activity_hwtxtplay_progress_text);
        this.mSettingText = (TextView)this.findViewById(R.id.activity_hwtxtplay_setting_text);
        this.mTopMenu = this.findViewById(R.id.activity_hwtxtplay_menu_top);
        this.mBottomMenu = this.findViewById(R.id.activity_hwtxtplay_menu_bottom);
        this.mCoverView = this.findViewById(R.id.activity_hwtxtplay_cover);
        this.ClipboardView = this.findViewById(R.id.activity_hwtxtplay_Clipboar);
        this.mSelectedText = (TextView)this.findViewById(R.id.activity_hwtxtplay_selected_text);
        this.mMenuHolder.mTitle = (TextView)this.findViewById(R.id.txtreadr_menu_title);
        this.mMenuHolder.mPreChapter = (TextView)this.findViewById(R.id.txtreadr_menu_chapter_pre);
        this.mMenuHolder.mNextChapter = (TextView)this.findViewById(R.id.txtreadr_menu_chapter_next);
        this.mMenuHolder.mSeekBar = (SeekBar)this.findViewById(R.id.txtreadr_menu_seekbar);
        this.mMenuHolder.mTextSizeDel = (TextView)this.findViewById(R.id.txtreadr_menu_textsize_del);
        this.mMenuHolder.mTextSize = (TextView)this.findViewById(R.id.txtreadr_menu_textsize);
        this.mMenuHolder.mTextSizeAdd = (TextView)this.findViewById(R.id.txtreadr_menu_textsize_add);
        this.mMenuHolder.mBoldSelectedLayout = this.findViewById(R.id.txtreadr_menu_textsetting1_bold);
        this.mMenuHolder.mBoldSelectedPic = (ImageView)this.findViewById(R.id.txtreadr_menu_textsetting1_boldpic);
        this.mMenuHolder.mNormalSelectedLayout = this.findViewById(R.id.txtreadr_menu_textsetting1_normal);
        this.mMenuHolder.mNormalSelectedPic = (ImageView)this.findViewById(R.id.txtreadr_menu_textsetting1_normalpic);
        this.mMenuHolder.mCoverSelectedLayout = this.findViewById(R.id.txtreadr_menu_textsetting2_cover);
        this.mMenuHolder.mCoverSelectedPic = (ImageView)this.findViewById(R.id.txtreadr_menu_textsetting2_coverpic);
        this.mMenuHolder.mTranslateSelectedLayout = this.findViewById(R.id.txtreadr_menu_textsetting2_translate);
        this.mMenuHolder.mTranslateSelectedPc = (ImageView)this.findViewById(R.id.txtreadr_menu_textsetting2_translatepic);
        this.mMenuHolder.mTextSize = (TextView)this.findViewById(R.id.txtreadr_menu_textsize);
        this.mMenuHolder.mStyle1 = this.findViewById(R.id.hwtxtreader_menu_style1);
        this.mMenuHolder.mStyle2 = this.findViewById(R.id.hwtxtreader_menu_style2);
        this.mMenuHolder.mStyle3 = this.findViewById(R.id.hwtxtreader_menu_style3);
        this.mMenuHolder.mStyle4 = this.findViewById(R.id.hwtxtreader_menu_style4);
        this.mMenuHolder.mStyle5 = this.findViewById(R.id.hwtxtreader_menu_style5);
    }

    protected void loadFile() {
        TxtConfig.savePageSwitchDuration(this, 400);
        if(!TextUtils.isEmpty(this.FilePath) && (new File(this.FilePath)).exists()) {
            this.mHandler.postDelayed(new Runnable() {
                public void run() {
                    Activity_txtReader.this.loadOurFile();
                }
            }, 300L);
        } else {
            this.toast("文件不存在");
        }
    }

    protected void loadOurFile() {
        this.mTxtReaderView.loadTxtFile(this.FilePath, new ILoadListener() {
            public void onSuccess() {
                Activity_txtReader.this.onLoadDataSuccess();
            }

            public void onFail(final TxtMsg txtMsg) {
                Activity_txtReader.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Activity_txtReader.this.onLoadDataFail(txtMsg);
                    }
                });
            }

            public void onMessage(String message) {
            }
        });
    }

    protected void onLoadDataFail(TxtMsg txtMsg) {
        this.toast(txtMsg + "");
    }

    protected void onLoadDataSuccess() {
        if(TextUtils.isEmpty(this.FileName)) {
            this.FileName = this.mTxtReaderView.getTxtReaderContext().getFileMsg().FileName;
        }

        this.setBookName(this.FileName);
        this.initWhenLoadDone();
    }

    private void loadStr() {
        String testText = "滚滚长江东逝水，浪花淘尽英雄。是非成败转头空。    青山依旧在，几度夕阳红。白发渔樵江渚上，惯    看秋月春风。一壶浊酒喜相逢。古今多少事，都付    笑谈中。    ——调寄《临江仙》    话说天下大势，分久必合，合久必分。周末七国分争，并入于秦。及秦灭之后，楚、汉分争，又并入于汉。汉朝自高祖斩白蛇而起义，一统天下，后来光武中兴，传至献帝，遂分为三国。推其致乱之由，殆始于桓、灵二帝。桓帝禁锢善类，崇信宦官。及桓帝崩，灵帝即位，大将军窦武、太傅陈蕃共相辅佐。时有宦官曹节等弄权，窦武、陈蕃谋诛之，机事不密反为所害，中涓自此愈横。    建宁二年四月望日，帝御温德殿。方升座，殿角狂风骤起。只见一条大青蛇，从梁上飞将下来，蟠于椅上。帝惊倒，左右急救入宫，百官俱奔避。须臾，蛇不见了。忽然大雷大雨，加以冰雹，落到半夜方止，坏却房屋无数。建宁四年二月，洛阳地震；又海水泛溢，沿海居民，尽被大浪卷入海中。光和元年，雌鸡化雄。六月朔，黑气十余丈，飞入温德殿中。秋七月，有虹现于玉堂；五原山岸，尽皆崩裂。种种不祥，";
        this.mTxtReaderView.loadText(testText, new ILoadListener() {
            public void onSuccess() {
                Activity_txtReader.this.setBookName("test with str");
                Activity_txtReader.this.initWhenLoadDone();
            }

            public void onFail(TxtMsg txtMsg) {
                Activity_txtReader.this.toast(txtMsg + "");
            }

            public void onMessage(String message) {
            }
        });
    }

    protected void initWhenLoadDone() {
        if(this.mTxtReaderView.getTxtReaderContext().getFileMsg() != null) {
            this.FileName = this.mTxtReaderView.getTxtReaderContext().getFileMsg().FileName;
        }

        this.mMenuHolder.mTextSize.setText(this.mTxtReaderView.getTextSize() + "");
        this.mTopDecoration.setBackgroundColor(this.mTxtReaderView.getBackgroundColor());
        this.mBottomDecoration.setBackgroundColor(this.mTxtReaderView.getBackgroundColor());
        this.onTextSettingUi(this.mTxtReaderView.getTxtReaderContext().getTxtConfig().Bold);
        this.onPageSwitchSettingUi(this.mTxtReaderView.getTxtReaderContext().getTxtConfig().SwitchByTranslate);
        if(this.mTxtReaderView.getTxtReaderContext().getTxtConfig().SwitchByTranslate.booleanValue()) {
            this.mTxtReaderView.setPageSwitchByTranslate();
        } else {
            this.mTxtReaderView.setPageSwitchByCover();
        }

        if(this.mTxtReaderView.getChapters() != null && this.mTxtReaderView.getChapters().size() > 0) {
            WindowManager m = (WindowManager)this.getSystemService(WINDOW_SERVICE);
            DisplayMetrics metrics = new DisplayMetrics();
            m.getDefaultDisplay().getMetrics(metrics);
            int ViewHeight = metrics.heightPixels - this.mTopDecoration.getHeight();
            this.mChapterListPop = new ChapterList(this, ViewHeight, this.mTxtReaderView.getChapters(), this.mTxtReaderView.getTxtReaderContext().getParagraphData().getCharNum());
            this.mChapterListPop.getListView().setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    IChapter chapter = (IChapter)Activity_txtReader.this.mChapterListPop.getAdapter().getItem(i);
                    Activity_txtReader.this.mChapterListPop.dismiss();
                    Activity_txtReader.this.mTxtReaderView.loadFromProgress(chapter.getStartParagraphIndex(), 0);
                }
            });
            this.mChapterListPop.setBackGroundColor(this.mTxtReaderView.getBackgroundColor());
        } else {
            this.Gone(new View[]{this.mChapterMenuText});
        }

    }

    protected void registerListener() {
        this.mTopMenu.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        this.mBottomMenu.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        this.mCoverView.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Activity_txtReader.this.Gone(new View[]{Activity_txtReader.this.mTopMenu, Activity_txtReader.this.mBottomMenu, Activity_txtReader.this.mCoverView});
                return true;
            }
        });
        this.mSettingText.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Activity_txtReader.this.Show(new View[]{Activity_txtReader.this.mTopMenu, Activity_txtReader.this.mBottomMenu, Activity_txtReader.this.mCoverView});
            }
        });
        this.mChapterMenuText.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if(!Activity_txtReader.this.mChapterListPop.isShowing()) {
                    Activity_txtReader.this.mChapterListPop.showAsDropDown(Activity_txtReader.this.mTopDecoration);
                    Activity_txtReader.this.mHandler.postDelayed(new Runnable() {
                        public void run() {
                            IChapter currentChapter = Activity_txtReader.this.mTxtReaderView.getCurrentChapter();
                            if(currentChapter != null) {
                                Activity_txtReader.this.mChapterListPop.setCurrentIndex(currentChapter.getIndex());
                                Activity_txtReader.this.mChapterListPop.notifyDataSetChanged();
                            }

                        }
                    }, 300L);
                } else {
                    Activity_txtReader.this.mChapterListPop.dismiss();
                }

            }
        });
        this.mMenuHolder.mSeekBar.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == 1) {
                    Activity_txtReader.this.mTxtReaderView.loadFromProgress((float)Activity_txtReader.this.mMenuHolder.mSeekBar.getProgress());
                }

                return false;
            }
        });
        this.mTxtReaderView.setOnCenterAreaClickListener(new ICenterAreaClickListener() {
            public boolean onCenterClick(float widthPercentInView) {
                Activity_txtReader.this.mSettingText.performClick();
                return true;
            }

            public boolean onOutSideCenterClick(float widthPercentInView) {
                if(Activity_txtReader.this.mBottomMenu.getVisibility() == View.VISIBLE) {
                    Activity_txtReader.this.mSettingText.performClick();
                    return true;
                } else {
                    return false;
                }
            }
        });
        this.mTxtReaderView.setPageChangeListener(new IPageChangeListener() {
            public void onCurrentPage(float progress) {
                int p = (int)(progress * 1000.0F);
                Activity_txtReader.this.mProgressText.setText((float)p / 10.0F + "%");
                //进度写入我的数据库，哈哈
                DownLoad d=dao.load(id);
                d.setReadProgress(((int)((float)p / 10.0)));
                dao.update(d);

                Activity_txtReader.this.mMenuHolder.mSeekBar.setProgress((int)(progress * 100.0F));
                IChapter currentChapter = Activity_txtReader.this.mTxtReaderView.getCurrentChapter();
                if(currentChapter != null) {
                    Activity_txtReader.this.mChapterNameText.setText((currentChapter.getTitle() + "").trim());
                } else {
                    Activity_txtReader.this.mChapterNameText.setText("无章节");
                }

            }
        });
        this.mTxtReaderView.setOnTextSelectListener(new ITextSelectListener() {
            public void onTextChanging(String selectText) {
                Activity_txtReader.this.onCurrentSelectedText(selectText);
            }

            public void onTextSelected(String selectText) {
                Activity_txtReader.this.onCurrentSelectedText(selectText);
            }
        });
        this.mTxtReaderView.setOnSliderListener(new ISliderListener() {
            public void onShowSlider(String currentSelectedText) {
                Activity_txtReader.this.onCurrentSelectedText(currentSelectedText);
                Activity_txtReader.this.Show(new View[]{Activity_txtReader.this.ClipboardView});
            }

            public void onReleaseSlider() {
                Activity_txtReader.this.Gone(new View[]{Activity_txtReader.this.ClipboardView});
            }
        });
        this.mTopMenu.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if(Activity_txtReader.this.mChapterListPop.isShowing()) {
                    Activity_txtReader.this.mChapterListPop.dismiss();
                }

            }
        });
        this.mMenuHolder.mPreChapter.setOnClickListener(new Activity_txtReader.ChapterChangeClickListener(Boolean.valueOf(true)));
        this.mMenuHolder.mNextChapter.setOnClickListener(new Activity_txtReader.ChapterChangeClickListener(Boolean.valueOf(false)));
        this.mMenuHolder.mTextSizeAdd.setOnClickListener(new Activity_txtReader.TextChangeClickListener(Boolean.valueOf(true)));
        this.mMenuHolder.mTextSizeDel.setOnClickListener(new Activity_txtReader.TextChangeClickListener(Boolean.valueOf(false)));
        this.mMenuHolder.mStyle1.setOnClickListener(new Activity_txtReader.StyleChangeClickListener(ContextCompat.getColor(this, color.hwtxtreader_styleclor1), this.StyleTextColors[0]));
        this.mMenuHolder.mStyle2.setOnClickListener(new Activity_txtReader.StyleChangeClickListener(ContextCompat.getColor(this, color.hwtxtreader_styleclor2), this.StyleTextColors[1]));
        this.mMenuHolder.mStyle3.setOnClickListener(new Activity_txtReader.StyleChangeClickListener(ContextCompat.getColor(this, color.hwtxtreader_styleclor3), this.StyleTextColors[2]));
        this.mMenuHolder.mStyle4.setOnClickListener(new Activity_txtReader.StyleChangeClickListener(ContextCompat.getColor(this, color.hwtxtreader_styleclor4), this.StyleTextColors[3]));
        this.mMenuHolder.mStyle5.setOnClickListener(new Activity_txtReader.StyleChangeClickListener(ContextCompat.getColor(this, color.hwtxtreader_styleclor5), this.StyleTextColors[4]));
        this.mMenuHolder.mBoldSelectedLayout.setOnClickListener(new Activity_txtReader.TextSettingClickListener(Boolean.valueOf(true)));
        this.mMenuHolder.mNormalSelectedLayout.setOnClickListener(new Activity_txtReader.TextSettingClickListener(Boolean.valueOf(false)));
        this.mMenuHolder.mTranslateSelectedLayout.setOnClickListener(new Activity_txtReader.SwitchSettingClickListener(Boolean.valueOf(true)));
        this.mMenuHolder.mCoverSelectedLayout.setOnClickListener(new Activity_txtReader.SwitchSettingClickListener(Boolean.valueOf(false)));
    }

    private void onCurrentSelectedText(String SelectedText) {
        this.mSelectedText.setText("选中" + (SelectedText + "").length() + "个文字");
        this.CurrentSelectedText = SelectedText;
    }

    private void onTextSettingUi(Boolean isBold) {
        if(isBold.booleanValue()) {
            this.mMenuHolder.mBoldSelectedPic.setBackgroundResource(drawable.ic_selected);
            this.mMenuHolder.mNormalSelectedPic.setBackgroundResource(drawable.ic_unselected);
        } else {
            this.mMenuHolder.mBoldSelectedPic.setBackgroundResource(drawable.ic_unselected);
            this.mMenuHolder.mNormalSelectedPic.setBackgroundResource(drawable.ic_selected);
        }

    }

    private void onPageSwitchSettingUi(Boolean isTranslate) {
        if(isTranslate.booleanValue()) {
            this.mMenuHolder.mTranslateSelectedPc.setBackgroundResource(drawable.ic_selected);
            this.mMenuHolder.mCoverSelectedPic.setBackgroundResource(drawable.ic_unselected);
        } else {
            this.mMenuHolder.mTranslateSelectedPc.setBackgroundResource(drawable.ic_unselected);
            this.mMenuHolder.mCoverSelectedPic.setBackgroundResource(drawable.ic_selected);
        }

    }

    protected void setBookName(String name) {
        this.mMenuHolder.mTitle.setText(name + "");
    }

    protected void Show(View... views) {
        View[] var2 = views;
        int var3 = views.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            View v = var2[var4];
            v.setVisibility(View.VISIBLE);
        }

    }

    protected void Gone(View... views) {
        View[] var2 = views;
        int var3 = views.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            View v = var2[var4];
            v.setVisibility(View.GONE);
        }

    }

    protected void toast(String msg) {
        if(this.t != null) {
            this.t.cancel();
        }

        this.t = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        this.t.show();
    }

    protected void onDestroy() {
        super.onDestroy();
        this.exist();
    }

    public void BackClick(View view) {
        this.finish();
    }

    public void onCopyText(View view) {
        if(!TextUtils.isEmpty(this.CurrentSelectedText)) {
            this.toast("已经复制到粘贴板");
            ClipboardManager cm = (ClipboardManager)this.getSystemService(CLIPBOARD_SERVICE);
            cm.setText(this.CurrentSelectedText + "");
        }

        this.onCurrentSelectedText("");
        this.mTxtReaderView.releaseSelectedState();
        this.Gone(new View[]{this.ClipboardView});
    }

    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    public void finish() {
        super.finish();
        this.exist();
    }

    protected void exist() {
        if(!this.hasExisted) {
            this.hasExisted = true;
            if(this.mTxtReaderView != null) {
                this.mTxtReaderView.saveCurrentProgress();
            }

            this.mHandler.postDelayed(new Runnable() {
                public void run() {
                    if(Activity_txtReader.this.mTxtReaderView != null) {
                        Activity_txtReader.this.mTxtReaderView.getTxtReaderContext().Clear();
                        Activity_txtReader.this.mTxtReaderView = null;
                    }

                    if(Activity_txtReader.this.mHandler != null) {
                        Activity_txtReader.this.mHandler.removeCallbacksAndMessages((Object)null);
                        Activity_txtReader.this.mHandler = null;
                    }

                    if(Activity_txtReader.this.mChapterListPop != null) {
                        if(Activity_txtReader.this.mChapterListPop.isShowing()) {
                            Activity_txtReader.this.mChapterListPop.dismiss();
                        }

                        Activity_txtReader.this.mChapterListPop.onDestroy();
                        Activity_txtReader.this.mChapterListPop = null;
                    }

                    Activity_txtReader.this.mMenuHolder = null;
                }
            }, 300L);
        }

    }

    protected class MenuHolder {
        public TextView mTitle;
        public TextView mPreChapter;
        public TextView mNextChapter;
        public SeekBar mSeekBar;
        public TextView mTextSizeDel;
        public TextView mTextSizeAdd;
        public TextView mTextSize;
        public View mBoldSelectedLayout;
        public ImageView mBoldSelectedPic;
        public View mNormalSelectedLayout;
        public ImageView mNormalSelectedPic;
        public View mCoverSelectedLayout;
        public ImageView mCoverSelectedPic;
        public View mTranslateSelectedLayout;
        public ImageView mTranslateSelectedPc;
        public View mStyle1;
        public View mStyle2;
        public View mStyle3;
        public View mStyle4;
        public View mStyle5;

        protected MenuHolder() {
        }
    }

    private class StyleChangeClickListener implements OnClickListener {
        private int BgColor;
        private int TextColor;

        public StyleChangeClickListener(int bgColor, int textColor) {
            this.BgColor = bgColor;
            this.TextColor = textColor;
        }

        public void onClick(View view) {
            Activity_txtReader.this.mTxtReaderView.setStyle(this.BgColor, this.TextColor);
            Activity_txtReader.this.mTopDecoration.setBackgroundColor(this.BgColor);
            Activity_txtReader.this.mBottomDecoration.setBackgroundColor(this.BgColor);
            if(Activity_txtReader.this.mChapterListPop != null) {
                Activity_txtReader.this.mChapterListPop.setBackGroundColor(this.BgColor);
            }

        }
    }

    private class TextChangeClickListener implements OnClickListener {
        private Boolean Add = Boolean.valueOf(false);

        public TextChangeClickListener(Boolean pre) {
            this.Add = pre;
        }

        public void onClick(View view) {
            int textSize = Activity_txtReader.this.mTxtReaderView.getTextSize();
            if(this.Add.booleanValue()) {
                if(textSize + 2 <= TxtConfig.MAX_TEXT_SIZE) {
                    Activity_txtReader.this.mTxtReaderView.setTextSize(textSize + 2);
                    Activity_txtReader.this.mMenuHolder.mTextSize.setText(textSize + 2 + "");
                }
            } else if(textSize - 2 >= TxtConfig.MIN_TEXT_SIZE) {
                Activity_txtReader.this.mTxtReaderView.setTextSize(textSize - 2);
                Activity_txtReader.this.mMenuHolder.mTextSize.setText(textSize - 2 + "");
            }

        }
    }

    private class ChapterChangeClickListener implements OnClickListener {
        private Boolean Pre;

        public ChapterChangeClickListener(Boolean pre) {
            this.Pre = pre;
        }

        public void onClick(View view) {
            if(this.Pre.booleanValue()) {
                Activity_txtReader.this.mTxtReaderView.jumpToPreChapter();
            } else {
                Activity_txtReader.this.mTxtReaderView.jumpToNextChapter();
            }

        }
    }

    private class SwitchSettingClickListener implements OnClickListener {
        private Boolean isSwitchTranslate = Boolean.valueOf(false);

        public SwitchSettingClickListener(Boolean pre) {
            this.isSwitchTranslate = pre;
        }

        public void onClick(View view) {
            if(!this.isSwitchTranslate.booleanValue()) {
                Activity_txtReader.this.mTxtReaderView.setPageSwitchByCover();
            } else {
                Activity_txtReader.this.mTxtReaderView.setPageSwitchByTranslate();
            }

            Activity_txtReader.this.onPageSwitchSettingUi(this.isSwitchTranslate);
        }
    }

    private class TextSettingClickListener implements OnClickListener {
        private Boolean Bold = Boolean.valueOf(false);

        public TextSettingClickListener(Boolean bold) {
            this.Bold = bold;
        }

        public void onClick(View view) {
            Activity_txtReader.this.mTxtReaderView.setTextBold(this.Bold.booleanValue());
            Activity_txtReader.this.onTextSettingUi(this.Bold);
        }
    }
}
