package tabbardemo.com.materialdesigntabs_demo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import tabbardemo.com.materialdesigntabs_demo.pojo.Example;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "ListData_db";


    public static final String TABLE_NAME = "ListData";
    public static final String COLUMN_NO = "SlNo";
    public static final String COLUMN_ID = "Id";
    public static final String COLUMN_POST_ID = "PostId";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_EMAIL = "Email";
    public static final String COLUMN_BODY = "Body";




    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_NO + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_ID + " INTEGER UNIQUE,"
                    + COLUMN_POST_ID+ " INTEGER,"
                    + COLUMN_NAME+ " TEXT,"
                    + COLUMN_EMAIL+ " TEXT,"
                    + COLUMN_BODY+ " TEXT"
                    + ")";




    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertNote(Example note) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(COLUMN_ID, note.getId());
        values.put(COLUMN_POST_ID, note.getPostId());
        values.put(COLUMN_NAME, note.getName());
        values.put(COLUMN_EMAIL, note.getEmail());
        values.put(COLUMN_BODY, note.getBody());


        // insert row
        long id = db.insert(TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public Example getNote(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_NO, COLUMN_ID, COLUMN_POST_ID, COLUMN_NAME, COLUMN_EMAIL, COLUMN_BODY},
                COLUMN_NO + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Example note = new Example(

                cursor.getInt(cursor.getColumnIndex(COLUMN_NO)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_POST_ID)), cursor.getString(cursor.getColumnIndex(COLUMN_NAME)), cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)), cursor.getString(cursor.getColumnIndex(COLUMN_BODY)));

        // close the db connection
        cursor.close();

        return note;
    }
}