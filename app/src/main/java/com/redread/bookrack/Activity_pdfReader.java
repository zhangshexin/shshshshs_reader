package com.redread.bookrack;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeProvider;

import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.redread.R;
import com.redread.databinding.LayoutMainBinding;
import com.shockwave.pdfium.PdfDocument;

import java.util.List;

/**
 * pdf类型的阅读
 */
public class Activity_pdfReader extends AppCompatActivity implements OnLoadCompleteListener {

    private LayoutMainBinding binding;

    private long time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        time= System.currentTimeMillis();
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.layout_main);




        binding.pdfView.fromAsset("tanke.pdf")
//        .pages(0, 2, 1, 3, 3, 3) // all pages are displayed by default
        .enableSwipe(true) // allows to block changing pages using swipe
        .swipeHorizontal(true)
        .enableDoubletap(true)
        .defaultPage(0).onLoad(this)

        // allows to draw something on the current page, usually visible in the middle of the screen
        /**.onDraw(new OnDrawListener() {
            @Override
            public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

            }
        });
        // allows to draw something on all pages, separately for every page. Called only for visible pages
//        .onDrawAll(onDrawListener)
        .onLoad(new OnLoadCompleteListener() {
            @Override
            public void loadComplete(int nbPages) {

            }
        }) // called after document is loaded and starts to be rendered
        .onPageChange(new OnPageChangeListener() {
            @Override
            public void onPageChanged(int page, int pageCount) {

            }
        })
        .onPageScroll(new OnPageScrollListener() {
            @Override
            public void onPageScrolled(int page, float positionOffset) {

            }
        })
        .onError(new OnErrorListener() {
            @Override
            public void onError(Throwable t) {

            }
        })
        .onPageError(new OnPageErrorListener() {
            @Override
            public void onPageError(int page, Throwable t) {

            }
        })
        .onRender(new OnRenderListener() {
            @Override
            public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {

            }
        }) // called after document is rendered for the first time
        // called on single tap, return true if handled, false to toggle scroll handle visibility
        .onTap(new OnTapListener() {
            @Override
            public boolean onTap(MotionEvent e) {
                return false;
            }
        })
//        .onLongPress(onLongPressListener)
        .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
        .password(null)
        .scrollHandle(null)
        .enableAntialiasing(true) // improve rendering a little bit on low-res screens
        // spacing between pages in dp. To define spacing color, set view background
        .spacing(0)
//        .autoSpacing(false) // add dynamic spacing to fit each page on its own on the screen
//        .linkHandler(DefaultLinkHandler)
//        .pageFitPolicy(FitPolicy.WIDTH)**/
        .pageSnap(true) // snap pages to screen boundaries
        .pageFling(true) // make a fling change only a single page like ViewPager
        .nightMode(false) // toggle night mode
        .load();

    }

private String TAG="kdkdkdkdkdkddk";
    @Override
    public void loadComplete(int nbPages) {
       String payTime=(System.currentTimeMillis()-time)+"";
        Log.e(TAG, payTime+"     ========================loadComplete: ========================"+nbPages );


        PdfDocument.Meta meta=binding.pdfView.getDocumentMeta();
        List<PdfDocument.Bookmark> bookmarks=binding.pdfView.getTableOfContents();
       AccessibilityNodeProvider provider= binding.pdfView.getAccessibilityNodeProvider();

    }
}
