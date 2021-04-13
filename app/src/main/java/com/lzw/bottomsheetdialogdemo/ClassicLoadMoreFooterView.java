package com.lzw.bottomsheetdialogdemo;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzw.bottomsheetdialogdemo.swipetoloadlayout.SwipeLoadMoreFooterLayout;


/**
 * Created by Aspsine on 2015/9/2.
 */
public class ClassicLoadMoreFooterView extends SwipeLoadMoreFooterLayout {
    private TextView tvLoadMore;
    private ImageView ivArrow;
    private ImageView ivRefresh;
    private AnimationDrawable animationDrawable;
    private int mFooterHeight;

    public ClassicLoadMoreFooterView(Context context) {
        this(context, null);
    }

    public ClassicLoadMoreFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        mFooterHeight = getResources().getDimensionPixelOffset(R.dimen.load_more_footer_height_classic);
    }

    public ClassicLoadMoreFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mFooterHeight = getResources().getDimensionPixelOffset(R.dimen.load_more_footer_height_classic);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tvLoadMore = (TextView) findViewById(R.id.tvLoadMore);
        ivArrow = (ImageView) findViewById(R.id.ivArrow);
        ivRefresh = (ImageView) findViewById(R.id.ivRefresh);
        animationDrawable = (AnimationDrawable) ivRefresh.getBackground();
    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onMove(int y, boolean isComplete, boolean automatic) {
        if (!isComplete) {
            animationDrawable.stop();
            ivArrow.setVisibility(VISIBLE);
            ivRefresh.setVisibility(GONE);
            if (-y >= mFooterHeight) {
                tvLoadMore.setText(R.string.release_load_more);
            } else {
                tvLoadMore.setText(R.string.pull_load_more);
            }
        }
    }

    @Override
    public void onLoadMore() {
        tvLoadMore.setText(R.string.loading);
        ivRefresh.setVisibility(VISIBLE);
        animationDrawable.start();
    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onComplete() {
        ivRefresh.setVisibility(GONE);
        animationDrawable.stop();
        ivArrow.setVisibility(VISIBLE);
    }

    @Override
    public void onReset() {
        animationDrawable.stop();
        ivRefresh.setVisibility(GONE);
    }
}
