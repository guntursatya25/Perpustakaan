package com.guntursatya.perpustakaan.adapters;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.CursorAdapter;

public class DBHelper extends SQLiteOpenHelper {

    public static final String database_name = "db_perpus";

    //    table perpus
    public static final String tabel_name = "tabel_perpus";

    public static final String row_id = "_id";
    public static final String row_nama = "Nama";
    public static final String row_judul = "Judul";
    public static final String row_pinjam = "TglPinjam";
    public static final String row_kembali = "TglKembali";
    public static final String row_email = "emailn";
    public static final String row_status = "Status";


    //TABLE NAME
    public static final String TABLE_USERS = "users";

    public static final String KEY_ID = "id";

    //COLUMN user name
    public static final String KEY_USER_NAME = "username";

    //COLUMN email
    public static final String KEY_EMAIL = "email";

    //COLUMN password
    public static final String KEY_PASSWORD = "password";

    //SQL for creating users table
    public static final String SQL_TABLE_USERS = " CREATE TABLE " + TABLE_USERS
            + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_USER_NAME + " TEXT, "
            + KEY_EMAIL + " TEXT, "
            + KEY_PASSWORD + " TEXT"
            + " ) ";


    private SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, database_name, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + tabel_name + "(" + row_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + row_nama + " TEXT," + row_judul + " TEXT," + row_pinjam + " TEXT," + row_kembali + " TEXT," + row_email + " TEXT, " + row_status + " TEXT)";
        db.execSQL(SQL_TABLE_USERS);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tabel_name);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_USERS);

        onCreate(db);

    }

    public Cursor checkUser(String user, String passw){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cur = db.rawQuery("SELECT * FROM " + SQL_TABLE_USERS + " WHERE " + KEY_USER_NAME+ " = " + user + " AND " + KEY_PASSWORD + " = " + passw, null);

        return cur;
    }
    //Get All SQLite Data
    public Cursor allData(){
        db = getWritableDatabase();

        Cursor cur = db.rawQuery("SELECT * FROM " + tabel_name + " ORDER BY " + row_id + " DESC ", null);
        return cur;
    }


    //GET 1 DATA BY ID
    public Cursor oneData(long id){
        db = getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM " + tabel_name + " WHERE " + row_id + "=" + id, null);
        return cur;
    }

    public Cursor Datain(String emaili){
        db = getWritableDatabase();
        Cursor cur = db.rawQuery("select * from "+ tabel_name +" where "+row_email+"= ?", new String[]{emaili});
        return cur;
    }

//    Insert User
//    public void insertUser(ContentValues values){
//        db.insert(tabel_user, null, values);
//    }

    //Insert Data
    public void insertData(ContentValues values){

        db = getWritableDatabase();

        db.insert(tabel_name, null, values);
    }

    //Update Data
    public void updateData(ContentValues values, long id){
        db = getWritableDatabase();

        db.update(tabel_name, values, row_id + "=" + id, null);
    }

    //Delete Data
    public void deleteData(long id){

        db = getWritableDatabase();
        db.delete(tabel_name, row_id + "=" + id, null);
    }


    //using this method we can add users to user table
    public void addUser(User user) {

        //get writable database
        SQLiteDatabase db = getWritableDatabase();

        //create content values to insert
        ContentValues values = new ContentValues();

        //Put username in  @values
        values.put(KEY_USER_NAME, user.userName);

        //Put email in  @values
        values.put(KEY_EMAIL, user.email);

        //Put password in  @values
        values.put(KEY_PASSWORD, user.password);

        // insert row
        long todo_id = db.insert(TABLE_USERS, null, values);
    }

    public User Authenticate(User user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,// Selecting Table
                new String[]{KEY_ID, KEY_USER_NAME, KEY_EMAIL, KEY_PASSWORD},//Selecting columns want to query
                KEY_EMAIL + "=?",
                new String[]{user.email},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            //if cursor has value then in user database there is user associated with this given email
            User user1 = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));

            //Match both passwords check they are same or not
            if (user.password.equalsIgnoreCase(user1.password)) {
                return user1;
            }
        }

        //if user password does not matches or there is no record with that email then return @false
        return null;
    }

    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,// Selecting Table
                new String[]{KEY_ID, KEY_USER_NAME, KEY_EMAIL, KEY_PASSWORD},//Selecting columns want to query
                KEY_EMAIL + "=?",
                new String[]{email},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            //if cursor has value then in user database there is user associated with this given email so return true
            return true;
        }

        //if email does not exist return false
        return false;
    }
}

