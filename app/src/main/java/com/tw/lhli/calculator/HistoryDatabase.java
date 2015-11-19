package com.tw.lhli.calculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public final class HistoryDatabase {
    public static class HistoryTable implements BaseColumns {
        public static final String TABLE_NAME = "History";
        public static final String COLUMN_FORMULA = "formula";
        public static final String COLUMN_RESULT = "title";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + HistoryTable.TABLE_NAME + " (" +
                    HistoryTable._ID + " INTEGER PRIMARY KEY," +
                    HistoryTable.COLUMN_FORMULA + TEXT_TYPE + COMMA_SEP +
                    HistoryTable.COLUMN_RESULT + TEXT_TYPE + COMMA_SEP +
                    " )";

    private static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + HistoryTable.TABLE_NAME;

    public class HistoryDbHelper extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "History.db";

        public HistoryDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_TABLE);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_TABLE);
            onCreate(db);
        }

        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }

    public void insertToDb(String formula, String result) {
//        HistoryDbHelper mDbHelper = new HistoryDbHelper(getContext());
//        SQLiteDatabase db = mDbHelper.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(HistoryDatabase.HistoryTable.COLUMN_FORMULA, formula);
//        values.put(HistoryDatabase.HistoryTable.COLUMN_RESULT, result);
//
//        long newRowId = db.insert(
//                HistoryDatabase.HistoryTable.TABLE_NAME,
//                null,
//                values);
    }
}

