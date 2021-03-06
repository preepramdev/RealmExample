package com.pond.myapplication.ui.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.pond.myapplication.R;
import com.pond.myapplication.model.BookModel;


public class UpdateBookDialogFragment extends DialogFragment {
    private static final String KEY_BOOK = "key_book";

    private TextView tvBookId;
    private EditText edtBookTitle;
    private EditText edtBookAuthor;
    private Button btnUpdate;
    private Button btnCancel;

    private BookModel bookModel;

    public interface OnDialogListener {
        void onUpdateBookUpdateClick(BookModel bookModel);

        void onUpdateBookCancelClick();
    }

    public static UpdateBookDialogFragment newInstance(BookModel bookModel) {
        UpdateBookDialogFragment fragment = new UpdateBookDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_BOOK, bookModel);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            restoreArguments(getArguments());
        } else {
            restoreInstanceState(savedInstanceState);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_update_book, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
        setupView();
    }

    private void bindView(View view) {
        tvBookId = view.findViewById(R.id.tv_book_id);
        edtBookTitle = view.findViewById(R.id.edt_book_title);
        edtBookAuthor = view.findViewById(R.id.edt_book_author);
        btnUpdate = view.findViewById(R.id.btn_update);
        btnCancel = view.findViewById(R.id.btn_cancel);
    }

    private void setupView() {
        tvBookId.setText(bookModel.getId().toString());
        edtBookTitle.setText(bookModel.getTitle());
        edtBookAuthor.setText(bookModel.getAuthor());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnDialogListener listener = UpdateBookDialogFragment.this.getOnDialogListener();
                if (listener != null) {
                    String bookTitle = edtBookTitle.getText().toString();
                    String bookAuthor = edtBookAuthor.getText().toString();
                    BookModel updateBookModel = new BookModel(bookTitle, bookAuthor);
                    updateBookModel.setId(bookModel.getId());
                    listener.onUpdateBookUpdateClick(updateBookModel);
                }
                UpdateBookDialogFragment.this.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnDialogListener listener = UpdateBookDialogFragment.this.getOnDialogListener();
                if (listener != null) {
                    listener.onUpdateBookCancelClick();
                }
                UpdateBookDialogFragment.this.dismiss();
            }
        });
    }

    private OnDialogListener getOnDialogListener() {
        Fragment fragment = getParentFragment();
        try {
            if (fragment != null) {
                return (OnDialogListener) fragment;
            } else {
                return (OnDialogListener) getActivity();
            }
        } catch (ClassCastException ignored) {
        }
        return null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_BOOK, bookModel);
    }

    private void restoreInstanceState(Bundle bundle) {
        bookModel = bundle.getParcelable(KEY_BOOK);
    }

    private void restoreArguments(Bundle bundle) {
        bookModel = bundle.getParcelable(KEY_BOOK);
    }

    public static class Builder {
        private BookModel bookModel;

        public Builder() {
        }

        public Builder setBook(BookModel book) {
            this.bookModel = book;
            return this;
        }

        public UpdateBookDialogFragment build() {
            return UpdateBookDialogFragment.newInstance(bookModel);
        }
    }
}
