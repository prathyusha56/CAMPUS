package com.hardway.gnits.databaseoffline;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by DELL on 28-06-2016.
 */
public class CreateDatabase extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Cflash";

    // Contacts table name
    private static final String TABLE_DATA = "Data";
    private static final String TABLE_QUIZ = "Quiz";

    // Contacts Table Columns names
    private static final String KEY_ID = "indeces";
    private static final String KEY_TOPIC = "topic";
    private static final String KEY_KEYWORD = "keyword";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_SYNTAX = "syntax";
    private static final String KEY_EXAMPLE = "example";

    private static final String QUIZ_KEY_ID = "id";
    private static final String QUIZ_KEY_QUESTION = "question";
    private static final String QUIZ_KEY_OPTION1 = "option1";
    private static final String QUIZ_KEY_OPTION2 = "option2";
    private static final String QUIZ_KEY_OPTION3 = "option3";
    private static final String QUIZ_KEY_OPTION4 = "option4";
    private static final String QUIZ_KEY_ANSWER = "answer";
    private static final String QUIZ_KEY_COUNT = "count";
    private static final String QUIZ_KEY_EXPLANATION = "explanation";

    public CreateDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DATA_TABLE = "CREATE TABLE " + TABLE_DATA + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TOPIC + " TEXT,"
                + KEY_KEYWORD + " TEXT," + KEY_DESCRIPTION + " TEXT,"+ KEY_SYNTAX + " TEXT,"+ KEY_EXAMPLE + " TEXT"+ ")";


        String CREATE_QUIZ_TABLE = "CREATE TABLE " + TABLE_QUIZ + "("
                + QUIZ_KEY_ID + " INTEGER PRIMARY KEY," + QUIZ_KEY_QUESTION + " TEXT,"
                + QUIZ_KEY_OPTION1 + " TEXT," + QUIZ_KEY_OPTION2 + " TEXT,"+QUIZ_KEY_OPTION3 + " TEXT,"+ QUIZ_KEY_OPTION4 + " TEXT,"+QUIZ_KEY_ANSWER + " INTEGER,"+ QUIZ_KEY_COUNT + " INTEGER,"+ QUIZ_KEY_EXPLANATION + " TEXT"+ ")";

        Log.d("sf",CREATE_QUIZ_TABLE);

        db.execSQL(CREATE_DATA_TABLE);
        db.execSQL(CREATE_QUIZ_TABLE);
        db.close();

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZ);
        // Create tables again
        onCreate(db);
    }


}