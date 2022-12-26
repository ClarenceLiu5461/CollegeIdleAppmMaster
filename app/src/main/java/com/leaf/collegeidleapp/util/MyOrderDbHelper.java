package com.leaf.collegeidleapp.util;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.leaf.collegeidleapp.bean.Collection;
import com.leaf.collegeidleapp.bean.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单数据库连接类
 */
public class MyOrderDbHelper extends SQLiteOpenHelper {



    //定义数据库表名
    public static final String DB_NAME = "tb_order";
    /** 创建数据库连接类 **/
    private static final String CREATE_COLLECTION_DB = "create table tb_order (" +
            "id integer primary key autoincrement," +//买方id
            "userId integer," +   //卖方ID
            "picture blob," +      //照片
            "title text," +
            "description text," +
            "price float," +
            "buyId text," +//买方id
            "phone text )";

    public MyOrderDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
     * 添加我的订单
     */
    public void addMyOrder(Order Order) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userId",Order.getUserId());
        values.put("picture",Order.getPicture());
        values.put("title",Order.getTitle());
        values.put("description",Order.getDescription());
        values.put("price",Order.getPrice());
        values.put("phone",Order.getPhone());
        values.put("buyId",Order.getBuyId());
        db.insert(DB_NAME,null,values);
        values.clear();
    }

    /**
     * 通过学号获取我的订单信息
     */
    public List<Order> readMyOrder(String userId) {
        List<Order> orders = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_order where userId=?",new String[]{userId});
        if(cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndex("title"));
                float price = cursor.getFloat(cursor.getColumnIndex("price"));
                String phone = cursor.getString(cursor.getColumnIndex("phone"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                byte[] picture = cursor.getBlob(cursor.getColumnIndex("picture"));
                Order order = new Order();
                order.setPicture(picture);
                order.setTitle(title);
                order.setDescription(description);
                order.setPrice(price);
                order.setPhone(phone);
                orders.add(order);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return orders;
    }

    /**
     * 根据买方id查询(查询我的订单)
     */
    @SuppressLint("LongLogTag")
    public List<Order> readMyBuy(String buyid) {

        Log.d("MyBuyA测试根据买方id查询记录，看看买方id是多少",buyid);

        List<Order> orders = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_order where buyId=?",new String[]{buyid});
        //Cursor cursor4 = db.rawQuery("select * from tb_order",null);
        //Log.d("测试一共有多少条数据：", String.valueOf(cursor4.getCount()));
        Log.d("测试根据id查询有多少条数据：", String.valueOf(cursor.getCount()));

//        cursor4.moveToFirst();
//        if(cursor4.moveToFirst()){
//            do {
//                String title = cursor4.getString(cursor4.getColumnIndex("title"));
//                Log.d("测试MyOrder的title", String.valueOf(title));
//                int id = cursor4.getInt(cursor.getColumnIndex("userId"));
//                Log.d("测试MyOrder的id", String.valueOf(id));
//
//            }while (cursor4.moveToNext());
//        }
        if(cursor.moveToFirst()) {
            do {
                Log.d("提示：：", "进来了");
                Log.d("测试id查询：", cursor.getString(cursor.getColumnIndex("buyId")));

                String title = cursor.getString(cursor.getColumnIndex("title"));
                float price = cursor.getFloat(cursor.getColumnIndex("price"));
                String phone = cursor.getString(cursor.getColumnIndex("phone"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                byte[] picture = cursor.getBlob(cursor.getColumnIndex("picture"));
                Order order = new Order();
                order.setPicture(picture);
                order.setTitle(title);
                order.setDescription(description);
                order.setPrice(price);
                order.setPhone(phone);
                orders.add(order);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return orders;
    }

    /**
     * 删除订单商品项
     */
    public void deleteMyOrder(String title,String description,float price) {
        SQLiteDatabase db = this.getWritableDatabase();
        if(db.isOpen()) {
            db.delete(DB_NAME,"title=? and description=? and price=?",new String[]{title,description,String.valueOf(price)});
            db.close();
        }
    }

}
