package com.android.databaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

// MainActivity class is created automatically when you created project
public class MainActivity extends AppCompatActivity {
    EditText name,email;  // declare layout EditText name and email
    Button addData;         // declare layout Button Add data.
    DatabaseReference reference;  // declare database reference

    @Override
    // onCreate method is the main method under which we will write code, it is created automatically
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // calling layouts by their id
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        addData = findViewById(R.id.addData);

        // Students is the main node inside student there is child node
        reference = FirebaseDatabase.getInstance().getReference().child("Students");

        // onclick listener is set on add data button
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            // this method is auto created when you added setOnClickListener
            public void onClick(View view) {
                // getting name email from layout and converting it to string
                String fullName = name.getText().toString();
                String emailId = email.getText().toString();

                // checking if name is not given
                if(TextUtils.isEmpty(fullName)){
                    Toast.makeText(MainActivity.this, "Enter Your Name", Toast.LENGTH_SHORT).show();
                }
                // checking empty case for email
                else if(TextUtils.isEmpty(emailId)){
                    Toast.makeText(MainActivity.this, "Enter Your email Id", Toast.LENGTH_SHORT).show();
                }
                else{
                    Student student = new Student(fullName,emailId); // creating object of student class
                    String uniqueId = reference.push().getKey();  // generating id that is unique for each student

                    reference.child(Objects.requireNonNull(uniqueId)).setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){  // if task is complete display success message
                                Toast.makeText(MainActivity.this, "Data added successfully", Toast.LENGTH_SHORT).show();
                                name.setText("");  // after adding name field will be empty
                                email.setText("");  // after adding email field will be empty
                            }
                            else{  // if task is not completed then display fail to insert message.
                                Toast.makeText(MainActivity.this, "Fail to insert data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}