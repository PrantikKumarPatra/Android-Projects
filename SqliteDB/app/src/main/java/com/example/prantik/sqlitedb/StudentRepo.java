package com.example.prantik.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class StudentRepo {
    private DBHelper dbHelper;

    public  StudentRepo(Context context)
    {
        dbHelper=new DBHelper(context);
    }


    public int insert(Student student)
    {
        //Open connection to write data

        SQLiteDatabase sqLiteDatabase=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Student.KEY_age,student.age);
        values.put(Student.KEY_email,student.email);
        values.put(Student.KEY_name,student.name);

        // Inserting Row
        long student_Id=sqLiteDatabase.insert(Student.TABLE,null,values);
        sqLiteDatabase.close(); //Closing database connection
        return(int)student_Id;

    }


    public  void delete(int student_Id)
    {
        SQLiteDatabase sqLiteDatabase=dbHelper.getWritableDatabase();
        // instead of concatenate string
        sqLiteDatabase.delete(Student.TABLE,Student.KEY_ID +"=?",new  String[]{ String.valueOf(student_Id)});
        sqLiteDatabase.close(); //Closing database connection
    }

    public void update(Student student)
    {
        SQLiteDatabase sqLiteDatabase=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(Student.KEY_age,student.age);
        values.put(Student.KEY_email,student.email);
        values.put(Student.KEY_name,student.name);

        // instead of concatenate string
        sqLiteDatabase.update(Student.TABLE,values,Student.KEY_ID +"=?",new String[]{String.valueOf(student.student_ID)});
        sqLiteDatabase.close();  //Closing database connection
    }
    public ArrayList<HashMap<String,String>> getStudentList()
    {
        //Open connection to read only
        SQLiteDatabase sqLiteDatabase=dbHelper.getReadableDatabase();
        String selectQuery=" SELECT "+ Student.KEY_ID +","+ Student.KEY_email +","+ Student.KEY_age +" FROM " + Student.TABLE ;
        ArrayList<HashMap<String, String>> studentList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst())
        {
            do {
                HashMap<String,String>student= new HashMap<String,String>();
                student.put("id",cursor.getString(cursor.getColumnIndex(Student.KEY_ID)));
                student.put("name",cursor.getString(cursor.getColumnIndex(Student.KEY_name)));
                studentList.add(student);
            }while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return studentList;


    }
    public Student getStudentById(int Id)
    {
        SQLiteDatabase sqLiteDatabase=dbHelper.getReadableDatabase();
        String selectQuery=" SELECT "+ Student.KEY_ID +","+ Student.KEY_email +","+ Student.KEY_age +" FROM " + Student.TABLE +" WHERE "+ Student.KEY_ID + "=?";


        int iCount =0;
        Student student=new Student();

        Cursor cursor=sqLiteDatabase.rawQuery(selectQuery,new String[] { String.valueOf(Id)});

        if (cursor.moveToFirst())
        {
            do {
                student.student_ID =cursor.getInt(cursor.getColumnIndex(Student.KEY_ID));
                student.name =cursor.getString(cursor.getColumnIndex(Student.KEY_name));
                student.email  =cursor.getString(cursor.getColumnIndex(Student.KEY_email));
                student.age =cursor.getInt(cursor.getColumnIndex(Student.KEY_age));

            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return  student;

    }
}
