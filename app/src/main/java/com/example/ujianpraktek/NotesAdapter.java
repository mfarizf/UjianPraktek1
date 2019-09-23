package com.example.ujianpraktek;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.SimpleTimeZone;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
    private Context context;
    private OnNotesClickListener listener;
    private List<Notes> listNotesInfo;

    public NotesAdapter(Context context, List<Notes> listNotesInfo,OnNotesClickListener listener){
        this.context = context;
        this.listNotesInfo = listNotesInfo;
        this.listener = listener;
    }

    public interface OnNotesClickListener{
        void onNotesClick(Notes notes, String action);
    }
    @NonNull
    @Override
    public NotesAdapter.NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data,parent,false);
        NotesViewHolder notesViewHolder=new NotesViewHolder(view);
        return notesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.NotesViewHolder holder, int position) {
        final Notes notes = listNotesInfo.get(position);
        holder.judul.setText(notes.getJudul());
        holder.deskripsi.setText(notes.getDeskripsi());
        holder.tanggal.setText(notes.getTanggal());
        holder.line1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String action = null;
                listener.onNotesClick(notes,action);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listNotesInfo.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {
        TextView judul,tanggal,deskripsi;
        LinearLayout line1;
        NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.judul);
            tanggal = itemView.findViewById(R.id.tanggal);
            deskripsi = itemView.findViewById(R.id.deskripsi);
            line1 = itemView.findViewById(R.id.line1);
        }
    }

}
