package com.example.aplikasinote;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class CustomCursorAdapter extends CursorAdapter {
    private LayoutInflater ly;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public CustomCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        ly = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = ly.inflate(R.layout.row_notes, viewGroup, false);
        MyHolder holder = new MyHolder();
        holder.listID = (TextView)v.findViewById(R.id.listID);
        holder.listNama = (TextView)v.findViewById(R.id.listNama);
        holder.listTanggal = (TextView)v.findViewById(R.id.listKeterangan);
        holder.listKeterangan = (TextView)v.findViewById(R.id.listTanggal);
        v.setTag(holder);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        MyHolder holder = (MyHolder)view.getTag();

        holder.listID.setText(cursor.getString(cursor.getColumnIndex(DBHandler.row_id)));
        holder.listNama.setText(cursor.getString(cursor.getColumnIndex(DBHandler.row_nama_notes)));
        holder.listTanggal.setText(cursor.getString(cursor.getColumnIndex(DBHandler.row_tanggal_notes)));
        holder.listKeterangan.setText(cursor.getString(cursor.getColumnIndex(DBHandler.row_keterangan)));
    }

    class MyHolder {
        TextView listID;
        TextView listNama;
        TextView listTanggal;
        TextView listKeterangan;
    }
}
