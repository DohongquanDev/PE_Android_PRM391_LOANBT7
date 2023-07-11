package com.example.ass_he151315;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String USER_TABLE = "User";
    public static final String ID_COLUMN = "id";
    public static final String FIRSTNAME_COLUMN = "firstname";
    public static final String LASTNAME_COLUMN = "lastname";
    public static final String AGE_COLUMN = "averageScore";

    public DBHelper(Context context) {
        super(context, USER_TABLE + "Data.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create table " + USER_TABLE + "(" + ID_COLUMN + " INTEGER primary key, " + FIRSTNAME_COLUMN + " TEXT, " + LASTNAME_COLUMN + " TEXT, " + AGE_COLUMN + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int ii) {
        DB.execSQL("drop table if exists " + USER_TABLE);
        onCreate(DB);
    }

    public boolean insertUser(User user) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_COLUMN, user.getId());
        contentValues.put(FIRSTNAME_COLUMN, user.getFirst_name());
        contentValues.put(LASTNAME_COLUMN, user.getLast_name());
        contentValues.put(AGE_COLUMN, user.getAge());
        long result = DB.insert(USER_TABLE, null, contentValues);
        DB.close();
        if (result == -1) {
            return false;
        }
        return true;
    }

    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(USER_TABLE, ID_COLUMN + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }


    public boolean checkExistUserInDB(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "select * from " + USER_TABLE + " where " + ID_COLUMN + " = " + id;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(USER_TABLE, new String[]{ID_COLUMN,
                        FIRSTNAME_COLUMN, LASTNAME_COLUMN, AGE_COLUMN}, ID_COLUMN + "= ?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        int anInt = cursor.getInt(0);
        String string = cursor.getString(1);
        String string1 = cursor.getString(2);
        String string2 = cursor.getString(3);

        User user = new User(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)));

        return user;
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + USER_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setFirst_name(cursor.getString(1));
                user.setLast_name(cursor.getString(2));
                user.setAge(Integer.parseInt(cursor.getString(3)));

                userList.add(user);
            } while (cursor.moveToNext());
        }


        return userList;
    }

    public List<User> searchUsers(ArrayList<String> arrSearch) {
        List<User> userList = new ArrayList<>();
        // Select All Query
        String selectQuery = "select * from " + USER_TABLE + " WHERE 1=1 ";

        selectQuery += " AND " + ID_COLUMN + " LIKE '%" + arrSearch.get(0) + "%'";
        selectQuery += " AND " + FIRSTNAME_COLUMN + " LIKE '%" + arrSearch.get(1) + "%'";
        selectQuery += " AND " + LASTNAME_COLUMN + " LIKE '%" + arrSearch.get(2) + "%'";
        selectQuery += " AND " + AGE_COLUMN + " LIKE '%" + arrSearch.get(3) + "%'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setFirst_name(cursor.getString(1));
                user.setLast_name(cursor.getString(2));
                user.setAge(Integer.parseInt(cursor.getString(3)));

                userList.add(user);
            } while (cursor.moveToNext());
        }


        return userList;
    }

    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIRSTNAME_COLUMN, user.getFirst_name());
        values.put(LASTNAME_COLUMN, user.getLast_name());
        values.put(AGE_COLUMN, user.getAge());

        // updating row
        return db.update(USER_TABLE, values, ID_COLUMN + " = ?",
                new String[]{String.valueOf(user.getId())});
    }


    public Cursor getData() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from " + USER_TABLE, null);
        return cursor;
    }
}