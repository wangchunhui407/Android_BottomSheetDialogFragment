package com.lzw.bottomsheetdialogdemo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.lzw.bottomsheetdialogdemo.swipetoloadlayout.SwipeToLoadLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class BottomRecyclerFragment extends BottomSheetDialogFragment {

    private BottomSheetBehavior mBehavior;
    private RecyclerView mRecyclerView;
    private DataAdapter mAdapater;
    private SwipeToLoadLayout mLayout;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what) {
                case 1:
                    mLayout.setRefreshing(false);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.layout_bottom_recyclerview, null);
        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
//        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View view, int i) {
//
//            }
//
//            @Override
//            public void onSlide(@NonNull View view, float v) {
//
//            }
//        });
//        mBehavior.setPeekHeight(200);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        mRecyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapater = new DataAdapter();
        mAdapater.setmContext(getActivity());

        ArrayList<String> data = new ArrayList<>();

        for (int i = 0; i < 100; ++i) {
            StringBuffer buff = new StringBuffer();
            for (int j = 0;j < 50;++j) {
                buff.append(i);
            }
            data.add(buff.toString());
        }

        mRecyclerView.setAdapter(mAdapater);
        mAdapater.setData(data);
        mAdapater.notifyDataSetChanged();
        mLayout = (SwipeToLoadLayout)view.findViewById(R.id.refresh_view);


        Button button = (Button)view.findViewById(R.id.refresh);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLayout.setRefreshing(true);
                Message msg = Message.obtain();
                msg.what = 1;
                mHandler.sendMessageDelayed(msg, 2000);
            }
        });

        return dialog;
    }

    @Override
    public int getTheme() {
        return R.style.dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);//全屏展开
    }

    /**
     * 点击布局里的ImageView，触发的点击事件
     * @param v
     */
    public void doclick(View v) {
        mBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    private class DataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private ArrayList<String> mData;
        Context mContext;

        public void setmContext(Context context) {
            mContext = context;
        }

        public void setData(ArrayList<String> data) {
            mData = data;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.layout_recycler_item, parent, false);
            DataAdapterViewHolder holder = new DataAdapterViewHolder(itemView);

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            String title = mData.get(position);
            DataAdapterViewHolder viewHolder = (DataAdapterViewHolder)holder;
            viewHolder.mTitleView.setText(title);
        }

        @Override
        public int getItemCount() {
            if (mData == null) return 0;
            return mData.size();
        }
    }

    private class DataAdapterViewHolder extends RecyclerView.ViewHolder {
        public View mItemView;
        public TextView mTitleView;

        public DataAdapterViewHolder(View itemView) {
            super(itemView);
            setItemView(itemView);
        }

        public void setItemView(View view) {
            mItemView = view;
            mTitleView = mItemView.findViewById(R.id.recycler_item_title);
        }
    }
}
