package com.lzw.bottomsheetdialogdemo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class BottomRecyclerFragment extends BottomSheetDialogFragment {

    private BottomSheetBehavior mBehavior;
    private RecyclerView mRecyclerView;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.layout_bottom_recyclerview, null);
        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());

        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
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
