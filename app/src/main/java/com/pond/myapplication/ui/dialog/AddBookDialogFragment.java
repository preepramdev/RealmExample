package com.pond.myapplication.ui.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.pond.myapplication.R;
import com.pond.myapplication.model.BookModel;


public class AddBookDialogFragment extends DialogFragment {
    private EditText edtBookTitle;
    private EditText edtBookAuthor;
    private Button btnAdd;
    private Button btnCancel;

    public interface OnDialogListener {
        void onAddBookAddClick(BookModel bookModel);

        void onAddBookCancelClick();
    }

    public static AddBookDialogFragment newInstance() {
        AddBookDialogFragment fragment = new AddBookDialogFragment();
        Bundle bundle = new Bundle();
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
        return inflater.inflate(R.layout.dialog_add_book, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
        setupView();
    }

    private void bindView(View view) {
        edtBookTitle = view.findViewById(R.id.edt_book_title);
        edtBookAuthor = view.findViewById(R.id.edt_book_author);
        btnAdd = view.findViewById(R.id.btn_add);
        btnCancel = view.findViewById(R.id.btn_cancel);
    }

    private void setupView() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnDialogListener listener = AddBookDialogFragment.this.getOnDialogListener();
                if (listener != null) {
                    String bookTitle = edtBookTitle.getText().toString();
                    String bookAuthor = edtBookAuthor.getText().toString();
                    BookModel bookModel = new BookModel(bookTitle, bookAuthor);
                    listener.onAddBookAddClick(bookModel);
                }
                AddBookDialogFragment.this.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnDialogListener listener = AddBookDialogFragment.this.getOnDialogListener();
                if (listener != null) {
                    listener.onAddBookCancelClick();
                }
                AddBookDialogFragment.this.dismiss();
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
    }

    private void restoreInstanceState(Bundle bundle) {
    }

    private void restoreArguments(Bundle bundle) {
    }

    public static class Builder {
        private BookModel bookModel;

        public Builder() {
        }

        public AddBookDialogFragment build() {
            return AddBookDialogFragment.newInstance();
        }
    }
}
