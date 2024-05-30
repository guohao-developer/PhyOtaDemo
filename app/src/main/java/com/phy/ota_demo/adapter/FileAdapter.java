package com.phy.ota_demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.phy.ota_demo.R;
import com.phy.ota_demo.bean.UpgradeFile;
import com.phy.ota_demo.databinding.ItemFileListBinding;

import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {

    private Context mContext;
    private List<UpgradeFile> mFiles;

    public FileAdapter(Context mContext, List<UpgradeFile> mFiles) {
        this.mContext = mContext;
        this.mFiles = mFiles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemFileListBinding.inflate(LayoutInflater.from(mContext)));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UpgradeFile upgradeFile = mFiles.get(position);
        holder.binding.tvUpgradefileName.setText(upgradeFile.getFile().getName());
        holder.binding.tvUpgradefilePath.setText(upgradeFile.getFile().getPath());

        boolean check = upgradeFile.isCheck();
        holder.binding.ivFile.setImageResource(check ? R.drawable.ic_upload_file_white : R.drawable.ic_upload_file);
        holder.binding.itemFileLay.setBackgroundColor(check ?
                ContextCompat.getColor(mContext, R.color.black) : ContextCompat.getColor(mContext, R.color.white));
        holder.binding.tvUpgradefileName.setTextColor(check ? ContextCompat.getColor(mContext, R.color.white) :
                ContextCompat.getColor(mContext, R.color.black));
        holder.binding.tvUpgradefilePath.setTextColor(check ? ContextCompat.getColor(mContext, R.color.white) :
                ContextCompat.getColor(mContext, R.color.black));

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(v -> {
                int position1 = holder.getLayoutPosition();
                mOnItemClickListener.onItemClick(holder.itemView, position1);
            });
        }
    }

    @Override
    public int getItemCount() {
        return mFiles.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemFileListBinding binding;

        public ViewHolder(ItemFileListBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
