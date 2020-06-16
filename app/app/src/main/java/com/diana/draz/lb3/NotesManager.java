package com.diana.draz.lb3;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class NotesManager {

    private final DBHelper db;

    public NotesManager(DBHelper db) {
        this.db = db;
    }

    public List<Note> getItems() {
        Cursor cursor = getCursorForTable("items", "id desc", null);
        return fromCursor(cursor);
    }


    public Note getItem(int id) {
        Cursor cursor = db.getReadableDatabase().rawQuery(
                "select id, title from items where id="+String.valueOf(id), null);

        int idIdx = cursor.getColumnIndex("id");
        int titleIdx = cursor.getColumnIndex("title");

        if (cursor.moveToFirst()) {
            String title = cursor.getString(titleIdx);

            Note item = new Note(
                    id,
                    title
            );

            return item;
        }

        return null;
    }

    private Cursor getCursorForTable(String table, String orderBy, @Nullable String selection) {
        return db.getReadableDatabase()
                .query(
                        table,
                        null,
                        null,
                        null,
                        null,
                        null,
                        orderBy,
                        null);
    }

    public void delete(int id) {
        db.getWritableDatabase()
                .delete("items", "id =  " + String.valueOf(id), null);
    }

    public void upsert(Note note) {

        ContentValues cv = new ContentValues();
        cv.put("title", note.title);


        if (note.id == 0) {
            db.getWritableDatabase().insert("items", null, cv);
        } else {
            cv.put("id", note.id );
            db.getWritableDatabase().update(
                    "items",
                    cv,
                    "id = "+String.valueOf(note.id),
                    null);
        }
    }

    public List<Note> getItems(String searchText) {
        String query = "";


        if (!searchText.equals(null) && !searchText.equals("")) {
            query += "title LIKE '%"+searchText+"%'";
        }

        Cursor cursor = getCursorForTable(
                "items",
                null,
                query);

        return fromCursor(cursor);
    }

    private List<Note> fromCursor(Cursor cursor) {

        if (cursor.getCount() == 0)
            return new ArrayList<Note>();

        ArrayList<Note> result = new ArrayList<Note>();

        int idIdx = cursor.getColumnIndex("id");
        int titleIdx = cursor.getColumnIndex("title");

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(idIdx);
                String title = cursor.getString(titleIdx);

                Note item = new Note(
                        id,
                        title
                );

                result.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return result;
    }

}