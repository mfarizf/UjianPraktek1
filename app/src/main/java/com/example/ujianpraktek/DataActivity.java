package com.example.ujianpraktek;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class DataActivity extends AppCompatActivity implements NotesAdapter.OnNotesClickListener{
    RecyclerView recyclerView;
    FloatingActionButton actionButton;
    Context context;
    RecyclerView.LayoutManager layoutManager;
    List<Notes> listNotesInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        context = getApplicationContext();

        recyclerView = findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        actionButton = findViewById(R.id.fab);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DataActivity.this,ManageData.class);
                startActivity(intent);
            }
        });
        setupRecyclerView();
    }

    private void setupRecyclerView(){
        DatabaseHelper db = new DatabaseHelper(context);
        listNotesInfo = db.selectNotes();

        NotesAdapter adapter = new NotesAdapter(context,listNotesInfo,this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onNotesClick(final Notes notes , String action) {
        dialog(notes, action);
    }

    public void dialog(final Notes notes, final String action){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Option");

        String[] pilihan = {"EDIT","DELETE"};
        builder.setItems(pilihan, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i){
                    case 0 :
                        Intent intent = new Intent(DataActivity.this,ManageData.class);
                        startActivity(intent);
                        break;
                    case 1 :
                        DatabaseHelper db = new DatabaseHelper(context);
                        db.delete(notes.getJudul());
                        setupRecyclerView();
                        break;
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
