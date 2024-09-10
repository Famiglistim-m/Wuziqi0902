package com.example.wuziqi0902;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserDbHelper extends SQLiteOpenHelper {

    private static UserDbHelper sHelper;
    private static final String DB_NAME = "user.db";   // 数据库名
    private static final int VERSION = 1;              // 版本号

    public UserDbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public synchronized static UserDbHelper getInstance(Context context) {
        if (sHelper == null) {
            sHelper = new UserDbHelper(context.getApplicationContext(), DB_NAME, null, VERSION);
        }
        return sHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建user_table表
        db.execSQL("CREATE TABLE user_table (" +
                "user_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "phone TEXT, " +
                "username TEXT, " +
                "password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 在此处理数据库升级逻辑
    }

    public UserInfo login(String phone, String password) {
        SQLiteDatabase db = getReadableDatabase();
        UserInfo userInfo = null;

        // 查询条件
        String sql = "SELECT user_id, phone, username, password FROM user_table WHERE phone = ? AND password = ?";
        String[] selectionArgs = {phone, password};

        Cursor cursor = db.rawQuery(sql, selectionArgs);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                int userIdColIndex = cursor.getColumnIndex("user_id");
                int phoneColIndex = cursor.getColumnIndex("phone");
                int usernameColIndex = cursor.getColumnIndex("username");
                int passwordColIndex = cursor.getColumnIndex("password");

                if (userIdColIndex >= 0 && phoneColIndex >= 0 && usernameColIndex >= 0 && passwordColIndex >= 0) {
                    int user_id = cursor.getInt(userIdColIndex);
                    String phoneValue = cursor.getString(phoneColIndex);
                    String username = cursor.getString(usernameColIndex);
                    String pass = cursor.getString(passwordColIndex);
                    userInfo = new UserInfo(user_id, phoneValue, username, pass);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return userInfo;
    }

    public int register(String phone, String username, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("phone", phone);
        values.put("username", username);
        values.put("password", password);
        long insertId = db.insert("user_table", null, values);
        db.close();
        return (int) insertId;
    }

    public UserInfo loginOrRegister(String phone, String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        UserInfo userInfo = null;

        // 先查询用户是否存在
        String checkSql = "SELECT COUNT(*) FROM user_table WHERE phone = ?";
        String[] checkSelectionArgs = {phone};
        Cursor cursor = db.rawQuery(checkSql, checkSelectionArgs);
        try {
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            cursor.close();

            if (count == 0) {
                // 如果用户不存在则注册
                register(phone, username, password);
            }

            // 再次尝试登录
            userInfo = login(phone, password);
        } finally {
            db.close();
        }
        return userInfo;
    }
}