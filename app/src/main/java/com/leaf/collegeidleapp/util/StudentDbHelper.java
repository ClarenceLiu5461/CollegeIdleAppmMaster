package com.leaf.collegeidleapp.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.leaf.collegeidleapp.bean.Student;

import java.util.LinkedList;

/**
 * 學生數據庫連接類
 */
public class StudentDbHelper extends SQLiteOpenHelper {

    //定義學生表
    public static final String DB_NAME = "tb_student";
    /**創建學生表*/
    private static final String CREATE_STUDENT_DB = "create table tb_student(" +
            "id integer primary key autoincrement," +
            "stuNumber text," +
            "stuName text," +
            "stuMajor text," +
            "stuPhone text," +
            "stuQq text," +
            "stuAddress text)";

    public StudentDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STUDENT_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 保存學生信息
     */
    public void saveStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("stuNumber",student.getStuNumber());
        values.put("stuName",student.getStuName());
        values.put("stuMajor",student.getStuMajor());
        values.put("stuPhone",student.getStuPhone());
        values.put("stuQq",student.getStuQq());
        values.put("stuAddress",student.getStuAddress());
        db.insert(DB_NAME,null,values);
        values.clear();
    }

    /**
     * 通過學號讀取學生相關信息
     */
    public LinkedList<Student> readStudents(String stuNumber) {
        LinkedList<Student> students = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_student where stuNumber=?",new String[]{stuNumber});
        if(cursor.moveToFirst()) {
            do {
                //String stuNum = cursor.getString(cursor.getColumnIndex("stuNumber"));
                String stuName = cursor.getString(cursor.getColumnIndex("stuName"));
                String stuMajor = cursor.getString(cursor.getColumnIndex("stuMajor"));
                String stuPhone = cursor.getString(cursor.getColumnIndex("stuPhone"));
                String stuQq = cursor.getString(cursor.getColumnIndex("stuQq"));
                String stuAddress = cursor.getString(cursor.getColumnIndex("stuAddress"));
                Student student = new Student();
                //student.setStuNumber(stuNum);
                student.setStuName(stuName);
                student.setStuMajor(stuMajor);
                student.setStuPhone(stuPhone);
                student.setStuQq(stuQq);
                student.setStuAddress(stuAddress);
                students.add(student);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return students;
    }

}
