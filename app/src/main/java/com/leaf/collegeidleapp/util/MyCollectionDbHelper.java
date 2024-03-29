package com.leaf.collegeidleapp.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.leaf.collegeidleapp.bean.Collection;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的收藏數據庫連接類
 */
public class MyCollectionDbHelper extends SQLiteOpenHelper {

    //定義數據庫表名
    public static final String DB_NAME = "tb_collection";
    /** 創建收藏信息表 **/
    private static final String CREATE_COLLECTION_DB = "create table tb_collection (" +
            "id integer primary key autoincrement," +
            "stuId text," +
            "picture blob," +
            "title text," +
            "description text," +
            "price float," +
            "phone text )";

    public MyCollectionDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_COLLECTION_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 添加我的收藏商品
     */
    public void addMyCollection(Collection collection) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("stuId",collection.getStuId());
        values.put("picture",collection.getPicture());
        values.put("title",collection.getTitle());
        values.put("description",collection.getDescription());
        values.put("price",collection.getPrice());
        values.put("phone",collection.getPhone());
        db.insert(DB_NAME,null,values);
        values.clear();
    }

    /**
     * 通過學號獲取我的收藏商品信息
     */
    public List<Collection> readMyCollections(String stuId) {
        List<Collection> collections = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_collection where stuId=?",new String[]{stuId});
        if(cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndex("title"));
                float price = cursor.getFloat(cursor.getColumnIndex("price"));
                String phone = cursor.getString(cursor.getColumnIndex("phone"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                byte[] picture = cursor.getBlob(cursor.getColumnIndex("picture"));
                Collection collection = new Collection();
                collection.setPicture(picture);
                collection.setTitle(title);
                collection.setDescription(description);
                collection.setPrice(price);
                collection.setPhone(phone);
                collections.add(collection);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return collections;
    }

    /**
     * 刪除收藏的商品項
     */
    public void deleteMyCollection(String title,String description,float price) {
        SQLiteDatabase db = this.getWritableDatabase();
        if(db.isOpen()) {
            db.delete(DB_NAME,"title=? and description=? and price=?",new String[]{title,description,String.valueOf(price)});
            db.close();
        }
    }

}
