package com.pond.myapplication.ui.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pond.myapplication.R;
import com.pond.myapplication.model.BookModel;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookViewHolder> {

    private List<BookModel> mModels;
    private OnItemClickListener mOnItemClickListener;

    public BookAdapter(List<BookModel> mModels) {
        this.mModels = mModels;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onUpdateClick(int position);

        void onRemoveClick(int position);
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        BookViewHolder bookViewHolder = new BookViewHolder(view);
        return bookViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, final int position) {
        BookModel model = mModels.get(position);

        holder.setTvBookId(model.getId().toString());
        holder.setTvBookTitle(model.getTitle());
        holder.setTvBookAuthor(model.getAuthor());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    if (position != RecyclerView.NO_POSITION) {
                        mOnItemClickListener.onItemClick(position);
                    }
                }
            }
        });

        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    if (position != RecyclerView.NO_POSITION) {
                        mOnItemClickListener.onUpdateClick(position);
                    }
                }
            }
        });

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    if (position != RecyclerView.NO_POSITION) {
                        mOnItemClickListener.onRemoveClick(position);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mModels.size();
    }
}
