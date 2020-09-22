package com.pond.myapplication.ui.recyclerview;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pond.myapplication.R;

public class BookViewHolder extends RecyclerView.ViewHolder {
    private TextView tvBookId;
    private TextView tvBookTitle;
    private TextView tvBookAuthor;
    public Button btnUpdate;
    public Button btnRemove;

    public BookViewHolder(@NonNull View itemView) {
        super(itemView);
        tvBookId = itemView.findViewById(R.id.tv_book_id);
        tvBookTitle = itemView.findViewById(R.id.tv_book_title);
        tvBookAuthor = itemView.findViewById(R.id.tv_book_author);
        btnUpdate = itemView.findViewById(R.id.btn_update);
        btnRemove = itemView.findViewById(R.id.btn_remove);
    }

    public void setTvBookId(String bookId) {
        tvBookId.setText(bookId);
    }

    public void setTvBookTitle(String bookTitle) {
        tvBookTitle.setText(bookTitle);
    }

    public void setTvBookAuthor(String bookAuthor) {
        tvBookAuthor.setText(bookAuthor);
    }

}
