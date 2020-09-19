package com.pond.myapplication.database;

import com.pond.myapplication.model.BookModel;

import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmResults;

// todo 9. create DatabaseManager as a Singleton
public class DatabaseManager {
    private final Realm realm;
    private static DatabaseManager instance;

    private DatabaseManager() {
        realm = Realm.getDefaultInstance();
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }

        return instance;
    }

    // todo 10. create CRUD functions

    public RealmResults<BookModel> getBooks() {
        return realm.where(BookModel.class).findAll();
    }

    public void createBook(BookModel bookModel) {
        bookModel.setId(nextId());
        realm.beginTransaction();
        realm.insert(bookModel);
        realm.commitTransaction();
    }

    private int nextId() {
        Number bookModelMaxId = realm.where(BookModel.class).max("id");
        if (bookModelMaxId == null)
            return 0;
        else
            return bookModelMaxId.intValue() + 1;
    }

    public void updateBook(BookModel bookModel) {
        realm.beginTransaction();
        realm.insertOrUpdate(bookModel);
        realm.commitTransaction();
    }

    public void removeBook(int bookId) {
        realm.beginTransaction();
        Objects.requireNonNull(realm.where(BookModel.class)
                .equalTo("id", bookId)
                .findFirst())
                .deleteFromRealm();
        realm.commitTransaction();
    }
}
