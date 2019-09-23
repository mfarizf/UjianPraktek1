package com.example.ujianpraktek;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ManageData extends AppCompatActivity implements NotesAdapter.OnNotesClickListener {
    EditText edtJudul, edtDeskripsi;
    Button btnSubmit;
    Context context;
    String action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_data);
        context=getApplicationContext();

        edtJudul = findViewById(R.id.edtJudul);
        edtDeskripsi = findViewById(R.id.edtDesk);
        btnSubmit = findViewById(R.id.btnSubmit);
        String action = getIntent().getStringExtra("action");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy / MM / dd ");
                String strDate = dateFormat.format(calendar.getTime());
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                String strTime = timeFormat.format(calendar.getTime());

                DatabaseHelper db = new DatabaseHelper(context);
                Notes notes =new Notes();
                String btnStatus = btnSubmit.getText().toString();
                if (btnStatus.equals("Submit")){
                    notes.setJudul(edtJudul.getText().toString());
                    notes.setDeskripsi(edtDeskripsi.getText().toString());
                    notes.setTanggal("Tanggal : "+strDate+" "+strTime);

                    db.insert(notes);
                }else if (btnStatus.equals("EDIT")){
                    notes.setJudul(edtJudul.getText().toString());
                    notes.setDeskripsi(edtDeskripsi.getText().toString());
                    notes.setTanggal("Tanggal : "+strDate+" "+strTime);

                    db.update(notes);
                }
                Intent intent = new Intent(ManageData.this, DataActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onNotesClick(Notes notes, String action) {

    }
}
