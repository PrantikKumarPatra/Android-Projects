package com.example.prantik.sqlitedb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StudentDetail extends Activity implements android.view.View.OnClickListener {
Button btnClose,btnSave,btnDelete;
EditText editTextName,editTextEmail,editTextAge;
private int _Student_Id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        btnSave=(Button)findViewById(R.id.btnSave);
        btnDelete=(Button)findViewById(R.id.btnDelete);
        btnClose=(Button)findViewById(R.id.btnClose);

        editTextName=(EditText)findViewById(R.id.editText);
        editTextEmail=(EditText)findViewById(R.id.editText2);
        editTextAge=(EditText)findViewById(R.id.editText3);

        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);

        _Student_Id=0;
        Intent intent=getIntent();
        _Student_Id=intent.getIntExtra("student_Id",0);
        StudentRepo repo=new StudentRepo(this);
        Student student=new Student();
        student=repo.getStudentById(_Student_Id);

        editTextAge.setText(String.valueOf(student.age));
        editTextName.setText(student.name);
        editTextEmail.setText(student.email);
    }

    @Override
    public void onClick(View view)
    {
        if (view==findViewById(R.id.btnSave))
        {
            StudentRepo repo = new StudentRepo(this);
            Student student = new Student();
            student.age = Integer.parseInt(editTextAge.getText().toString());
            student.email = editTextEmail.getText().toString();
            student.name = editTextName.getText().toString();
            student.student_ID = _Student_Id;

            if (_Student_Id == 0)
            {
                _Student_Id = repo.insert(student);
                Toast.makeText(this, "New Student Inserted", Toast.LENGTH_SHORT).show();
            }
            else
                {
                repo.update(student);
                Toast.makeText(this, "Student Record updated", Toast.LENGTH_SHORT).show();
                }
        }
        else if(view==findViewById(R.id.btnDelete))
          {
             StudentRepo repo = new StudentRepo(this);
            repo.delete(_Student_Id);
            Toast.makeText(this,"Student Record Deleted",Toast.LENGTH_SHORT).show();
            finish();
          }
        else  if(view==findViewById(R.id.btnClose))
        {
          finish();
        }

        }

    }

