package com.threabba.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ETRI LSAR Project Team on 2018-01-16.
 */

public class DBAdapter extends SQLiteOpenHelper {
    private static final String DB_NAME = "promise4.db";
    private static final int VERSION = 1;
    private static final String ID = "_id";
    private static final String TITLE = "meeting";
    private static final String CONTENT = "sodyd";
    private static final String DATE = "date";

    private static final String TABLE_NAME = "promise3";
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    ID + " integer primary key autoincrement, " +
                    TITLE + " not null, " +
                    CONTENT + " not null, " +
                    DATE + " not null )";

    private SQLiteDatabase db;

    public DBAdapter(Context context) {
        super(context, DB_NAME, null, VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public synchronized void close() {
        db.close();
        super.close();
    }

    @Override
    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // create
    public boolean insertMemo(String title,String note, String date) {
        ContentValues cv = new ContentValues();
        cv.put(TITLE, title);
        cv.put(CONTENT,note);
        cv.put(DATE, date);
        return db.insert(TABLE_NAME, null, cv) != -1;
    }
    public boolean insertMemo(Memo memo) {
        ContentValues cv = new ContentValues();
        cv.put(TITLE, memo.getTitle());
        cv.put(CONTENT, memo.getNote());
        cv.put(DATE, memo.getDate());
        return db.insert(TABLE_NAME, null, cv) != -1;
    }

    // read
    public ArrayList<Memo> getAllMemo() {
        ArrayList<Memo> memo = new ArrayList<Memo>();
        Cursor c = db.query(TABLE_NAME, new String[] {ID, TITLE,CONTENT, DATE}, null, null, null, null, ID + " DESC");

        if (c.moveToFirst()) {
            final int indexId = c.getColumnIndex(ID);
            final int indexTitle = c.getColumnIndex(TITLE);
            final int indexNote = c.getColumnIndex(CONTENT);
            final int indexDate = c.getColumnIndex(DATE);

            do {
                int id = c.getInt(indexId);
                String title = c.getString(indexTitle);
                String note = c.getString(indexNote);
                String date = c.getString(indexDate);
                memo.add(new Memo(id, title,note, date));
            } while (c.moveToNext());
        }
        c.close();

        return memo;
    }
    public String[] getAllMemoByStringArray() {
        Cursor c = db.query(TABLE_NAME, new String[] {ID, TITLE, CONTENT, DATE}, null, null, null, null, ID + " DESC");
        int count = c.getCount();
        Log.e("인투", "인투 : "+c.getCount());
        if(count == 0){
            return null;
        }
        String[] memoList = new String[count];

        if (c.moveToFirst()) {
            final int indexId = c.getColumnIndex(ID);
            final int indexTitle = c.getColumnIndex(TITLE);
            final int indexNote = c.getColumnIndex(CONTENT);
            final int indexDate = c.getColumnIndex(DATE);
            int i =0;
            do {
                int id = c.getInt(indexId);
                String title = c.getString(indexTitle);
                String note = c.getString(indexNote);
                String date = c.getString(indexDate);
                memoList[i++] = id+", "+title+", "+note+", "+date;
            } while (c.moveToNext());
        }
        c.close();

        return memoList;
    }

    // update
    public boolean updateMemo(long id,Memo i) {
        ContentValues cv = new ContentValues();
        cv.put(TITLE, i.getTitle());
        cv.put(CONTENT,i.getNote());
        cv.put(DATE, i.getDate());
        String[] params = new String[] { Long.toString(id) };
        int result = db.update(TABLE_NAME, cv, ID + "=?", params);
        return result > 0;
    }
    public boolean updateMemo(Memo i) {
        ContentValues cv = new ContentValues();
        cv.put(TITLE, i.getTitle());
        cv.put(CONTENT,i.getNote());
        cv.put(DATE, i.getDate());
        String[] params = new String[] { Long.toString(i.getId()) };
        int result = db.update(TABLE_NAME, cv, ID + "=?", params);
        return result > 0;
    }

    // delete
    public boolean deleteMemo(int id) {
        String[] params = new String[] { Integer.toString(id) };
        int result = db.delete(TABLE_NAME, ID + "=?", params);
        return result > 0;
    }

    public boolean deleteAll() {
        int result=db.delete(TABLE_NAME, null, null);
        return result > 0;
    }

}
