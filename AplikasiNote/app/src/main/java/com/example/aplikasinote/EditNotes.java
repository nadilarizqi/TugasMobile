package com.example.aplikasinote;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditNotes extends AppCompatActivity {

    DBHandler handler;
    EditText NamaNote, TanggalNote, Keterangan;
    Button Simpan;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_notes);

        handler = new DBHandler(this);

        id = getIntent().getLongExtra(DBHandler.row_id, 0);

        NamaNote = (EditText)findViewById(R.id.editJudul);
        TanggalNote = (EditText)findViewById(R.id.editTanggal);
        Keterangan = (EditText)findViewById(R.id.editKeterangan);

        getData();

        Simpan = (Button)findViewById(R.id.btnSimpan);
        Simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namaNotes = NamaNote.getText().toString().trim();
                String tanggalNotes = TanggalNote.getText().toString().trim();
                String keterangan = Keterangan.getText().toString().trim();

                if (namaNotes.equals("") || tanggalNotes.equals("") || keterangan.equals("")) {
                    Toast.makeText(EditNotes.this, "Pastikan Semua Form Data Terisi", Toast.LENGTH_SHORT).show();
                } else {
                    ContentValues values = new ContentValues();
                    values.put(DBHandler.row_nama_notes, namaNotes);
                    values.put(DBHandler.row_tanggal_notes, tanggalNotes);
                    values.put(DBHandler.row_keterangan, keterangan);
                    handler.editData(values, id);
                    finish();
                }
            }
        });
    }

    public void getData() {
        Cursor cur = handler.satuData(id);
        if (cur.moveToFirst()) {
            String namaNotes = cur.getString(cur.getColumnIndex(DBHandler.row_nama_notes));
            String tanggalNotes = cur.getString(cur.getColumnIndex(DBHandler.row_tanggal_notes));
            String keterangan = cur.getString(cur.getColumnIndex(DBHandler.row_keterangan));
            NamaNote.setText(namaNotes);
            TanggalNote.setText(tanggalNotes);
            Keterangan.setText(keterangan);
        }
    }
}
