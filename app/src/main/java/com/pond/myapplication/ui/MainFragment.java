package com.pond.myapplication.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pond.myapplication.R;
import com.pond.myapplication.database.DatabaseManager;
import com.pond.myapplication.model.BookModel;
import com.pond.myapplication.ui.adapter.BookAdapter;
import com.pond.myapplication.ui.dialog.AddBookDialogFragment;
import com.pond.myapplication.ui.dialog.BookDetailDialogFragment;
import com.pond.myapplication.ui.dialog.RemoveBookDialogFragment;
import com.pond.myapplication.ui.dialog.UpdateBookDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements
        BookDetailDialogFragment.OnDialogListener,
        AddBookDialogFragment.OnDialogListener,
        UpdateBookDialogFragment.OnDialogListener,
        RemoveBookDialogFragment.OnDialogListener {
    private static final String TAG = "MainFragment";

    private DatabaseManager databaseManager; // todo 11. define databaseManager
    private View mRootView;

    private List<BookModel> mBookModels;
    private BookAdapter mBookAdapter;

    private RecyclerView rcBooks;
    private Button btnAddBook;

    public MainFragment() {
    }

    @Override
    public void onBookDetailDialogOkClick() {
        Log.e(TAG, "onBookDetailDialogOkClick: ");
    }

    @Override
    public void onAddBookAddClick(BookModel bookModel) {
        Log.e(TAG, "onAddBookAddClick: " + bookModel);

        addBook(bookModel);
    }

    @Override
    public void onAddBookCancelClick() {
        Log.e(TAG, "onAddBookCancelClick: ");
    }

    @Override
    public void onUpdateBookUpdateClick(BookModel bookModel) {
        Log.e(TAG, "onUpdateBookUpdateClick: " + bookModel);
        updateBook(bookModel);
    }

    @Override
    public void onUpdateBookCancelClick() {
        Log.e(TAG, "onUpdateBookCancelClick: ");
    }

    @Override
    public void onRemoveBookRemoveClick(BookModel bookModel) {
        Log.e(TAG, "onRemoveBookRemoveClick: " + bookModel);

        removeBook(bookModel);
    }

    @Override
    public void onRemoveBookCancelClick() {
        Log.e(TAG, "onRemoveBookCancelClick: ");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseManager = DatabaseManager.getInstance(); // todo 12. assign databaseManager from DatabaseManager singleton
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void initInstances(View rootView, Bundle savedInstanceState) {
        mRootView = rootView;

        mBookModels = new ArrayList<>();
        mBookAdapter = new BookAdapter(mBookModels);

        mBookAdapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.e(TAG, "onItemClick: " + position);
                callBookDetailDialog(mBookModels.get(position));
            }

            @Override
            public void onUpdateClick(int position) {
                Log.e(TAG, "onUpdateClick: " + position);
                callUpdateBookDialog(mBookModels.get(position));
            }

            @Override
            public void onRemoveClick(int position) {
                Log.e(TAG, "onRemoveClick: " + position);
                callRemoveBookDialog(mBookModels.get(position));
            }
        });

        bindViews();
    }

    private void bindViews() {
        rcBooks = mRootView.findViewById(R.id.rcv_books);
        btnAddBook = mRootView.findViewById(R.id.btn_add_book);

        rcBooks.setLayoutManager(new LinearLayoutManager(requireActivity()));
        rcBooks.setHasFixedSize(true);
        rcBooks.setAdapter(mBookAdapter);

        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callAddBookDialog();
            }
        });
    }

    private void fetchBooks() {
        mBookModels.clear();

        // mock data if empty
        List<BookModel> books = databaseManager.getBooks(); // todo 13. get all book from realm

        if (books.isEmpty()) {
            List<BookModel> presetBooks = new ArrayList<>();
            presetBooks.add(new BookModel("My Book", "Me"));
            presetBooks.add(new BookModel("Unix Basic", "W. John Snow"));

            for (BookModel presetBook : presetBooks) {
                databaseManager.createBook(presetBook);
            }
        }
        mBookModels.addAll(books);
        mBookAdapter.notifyDataSetChanged();
    }

    private void addBook(BookModel bookModel) {
        databaseManager.createBook(bookModel);
        fetchBooks();
    }

    private void updateBook(BookModel bookModel) {
        databaseManager.updateBook(bookModel);
        fetchBooks();
    }

    private void removeBook(BookModel bookModel) {
        databaseManager.removeBook(bookModel.getId());
        fetchBooks();
    }

    private void callBookDetailDialog(BookModel bookModel) {
        BookDetailDialogFragment fragment = new BookDetailDialogFragment.Builder()
                .setBook(bookModel)
                .build();
        fragment.show(getChildFragmentManager(), "BookDetailDialogFragment");
    }

    private void callAddBookDialog() {
        AddBookDialogFragment fragment = new AddBookDialogFragment.Builder()
                .build();
        fragment.show(getChildFragmentManager(), "AddBookDialogFragment");
    }

    private void callUpdateBookDialog(BookModel bookModel) {
        UpdateBookDialogFragment fragment = new UpdateBookDialogFragment.Builder()
                .setBook(bookModel)
                .build();
        fragment.show(getChildFragmentManager(), "UpdateBookDialogFragment");
    }

    private void callRemoveBookDialog(BookModel bookModel) {
        RemoveBookDialogFragment fragment = new RemoveBookDialogFragment.Builder()
                .setBook(bookModel)
                .build();
        fragment.show(getChildFragmentManager(), "RemoveBookDialogFragment");
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchBooks();
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}