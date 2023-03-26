package com.myra.contact;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText name, email, phone_number , ville;
    Button update_button, delete_button;

    String id, namee,  emaill, phonee_number , villee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        name = findViewById(R.id.name2);
        email = findViewById(R.id.email2);
        phone_number = findViewById(R.id.phone_number2);
        ville = findViewById(R.id.ville2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);


        getAndSetIntentData();


        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(namee);
        }

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //And only then we call this
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                namee = name.getText().toString().trim();
                emaill = email.getText().toString().trim();
                phonee_number = phone_number.getText().toString().trim();
                villee = ville.getText().toString().trim();
                myDB.updateData(id, namee, emaill, phonee_number , villee);
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name") &&
                getIntent().hasExtra("email") && getIntent().hasExtra("phone_number") && getIntent().hasExtra("ville") ){
            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            namee = getIntent().getStringExtra("name");
            emaill = getIntent().getStringExtra("email");
            phonee_number = getIntent().getStringExtra("phone_number");
            villee = getIntent().getStringExtra("ville");

            //Setting Intent Data
            name.setText(namee);
            email.setText(emaill);
            phone_number.setText(phonee_number);
            ville.setText(villee);
            Log.d("stev", namee+" "+emaill+" "+phonee_number + " " + villee);
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + namee + " ?");
        builder.setMessage("Are you sure you want to delete " + namee + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}
