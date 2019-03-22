package com.example.aplikasinote;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;

public class Notes extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView ls;
    DBHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_notes);

        FloatingActionButton btn = (FloatingActionButton)findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(Notes.this, MainActivity.class);
                startActivity(intent);
            }
        });

        handler = new DBHandler(this);
        ls = (ListView)findViewById(R.id.listNotes);
        ls.setOnItemClickListener(this);
        setupListView();
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Notes.this);
        builder.setMessage("Apakah Anda ingin keluar dari aplikasi?");
        builder.setCancelable(true);
        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void setupListView() {
        Cursor cursor = handler.semuaData();
        CustomCursorAdapter customCursorAdapter = new CustomCursorAdapter(this, cursor, 1);
        ls.setAdapter(customCursorAdapter);
        ls.setDividerHeight(0);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView getid = (TextView)view.findViewById(R.id.listID);
        final long id = Long.parseLong(getid.getText().toString());
        Cursor cursr = handler.satuData(id);
        cursr.moveToFirst();

        String ket = cursr.getString(cursr.getColumnIndexOrThrow(DBHandler.row_keterangan));
        final String namaNotes = cursr.getString(cursr.getColumnIndex(DBHandler.row_nama_notes));
        final AlertDialog.Builder builder = new AlertDialog.Builder(Notes.this);
        builder.setTitle("Daftar Notes");
        builder.setMessage(ket);
        builder.setPositiveButton("HAPUS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogHapusItem(id, namaNotes);
            }
        });
        builder.setNegativeButton("EDIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent tanggalNotes = new Intent(Notes.this, EditNotes.class);
                tanggalNotes.putExtra(DBHandler.row_id, id);
                startActivity(tanggalNotes);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void dialogHapusItem(final long id, String nama) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(Notes.this);
        builder1.setTitle("Hapus Catatan " + nama);
        builder1.setMessage("Apakah Anda yakin ingin menghapus catatan " + nama + "?");
        builder1.setPositiveButton("HAPUS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                handler.hapusData(id);
                setupListView();
            }
        });
        builder1.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //CANCEL
            }
        });
        AlertDialog dialog = builder1.create();
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupListView();
    }
}
