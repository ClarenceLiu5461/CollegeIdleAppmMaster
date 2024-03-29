package com.leaf.collegeidleapp.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.leaf.collegeidleapp.bean.Commodity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 商品數據庫類
 */
public class CommodityDbHelper extends SQLiteOpenHelper {

    //定義商品表
    public static final String DB_NAME = "tb_commodity";

    /**創建商品表*/
    private static final String CREATE_COMMODITY_DB = "create table tb_commodity(" +
            "id integer primary key autoincrement," +
            "title text," +
            "category text," +
            "price float," +
            "phone text," +
            "description text," +
            "picture blob," +
            "stuId text)";

    public CommodityDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_COMMODITY_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 添加物品方法
     */
    public boolean AddCommodity(Commodity commodity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title",commodity.getTitle());
        values.put("category",commodity.getCategory());
        values.put("price",commodity.getPrice());
        values.put("phone",commodity.getPhone());
        values.put("description",commodity.getDescription());
        values.put("picture",commodity.getPicture());
        values.put("stuId",commodity.getStuId());
        db.insert(DB_NAME,null,values);
        values.clear();
        return true;
    }

    /**
     * 通過學號查找我的發布物品信息
     */
    public List<Commodity> readMyCommodities(String stuId) {
        List<Commodity> myCommodities = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_commodity where stuId=?",new String[]{stuId});
        if(cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String category = cursor.getString(cursor.getColumnIndex("category"));
                float price = cursor.getFloat(cursor.getColumnIndex("price"));
                String phone = cursor.getString(cursor.getColumnIndex("phone"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                byte[] picture = cursor.getBlob(cursor.getColumnIndex("picture"));
                Commodity commodity = new Commodity();
                commodity.setTitle(title);
                commodity.setCategory(category);
                commodity.setPrice(price);
                commodity.setDescription(description);
                commodity.setPhone(phone);
                commodity.setPicture(picture);
                myCommodities.add(commodity);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return myCommodities;
    }

    /**
     * 獲取所有的商品信息
     */
    public List<Commodity> readAllCommodities() {
        List<Commodity> allCommodities = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_commodity order by price",null);
        if(cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String category = cursor.getString(cursor.getColumnIndex("category"));
                float price = cursor.getFloat(cursor.getColumnIndex("price"));
                String phone = cursor.getString(cursor.getColumnIndex("phone"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                byte[] picture = cursor.getBlob(cursor.getColumnIndex("picture"));
                String stuId = cursor.getString(cursor.getColumnIndex("stuId"));
                Commodity commodity = new Commodity();
                commodity.setTitle(title);
                commodity.setCategory(category);
                commodity.setPrice(price);
                commodity.setDescription(description);
                commodity.setPhone(phone);
                commodity.setPicture(picture);
                commodity.setStuId(stuId);
                allCommodities.add(commodity);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return allCommodities;
    }

    /**
     * 根據商品名稱刪除商品
     */
    public void deleteMyCommodity(String title,String description,float price) {
        SQLiteDatabase db = this.getWritableDatabase();
        if(db.isOpen()) {
            db.delete(DB_NAME,"title=? and description=? and price=?",new String[]{title,description,String.valueOf(price)});
            db.close();
        }
    }

    /**
     * 讀取不同類別的商品信息
     */
    public List<Commodity> readCommodityType(String category) {
        List<Commodity> differentTypes = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_commodity where category=?",new String[]{category});
        if(cursor.moveToFirst()) {
            do{
                String title = cursor.getString(cursor.getColumnIndex("title"));
                float price = cursor.getFloat(cursor.getColumnIndex("price"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                byte[] picture = cursor.getBlob(cursor.getColumnIndex("picture"));
                Commodity commodity = new Commodity();
                commodity.setTitle(title);
                commodity.setPrice(price);
                commodity.setCategory(category);
                commodity.setDescription(description);
                commodity.setPicture(picture);
                differentTypes.add(commodity);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return differentTypes;
    }

}
