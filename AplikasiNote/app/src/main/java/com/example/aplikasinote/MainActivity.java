package com.example.aplikasinote;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DBHandler handler;
    EditText NamaNote, TanggalNote, Keterangan;
    Button TambahData;
    CheckBox keepData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new DBHandler(this);

        NamaNote = (EditText)findViewById(R.id.editJudul);
        TanggalNote = (EditText)findViewById(R.id.editTanggal);
        Keterangan = (EditText)findViewById(R.id.editKeterangan);

        TambahData = (Button)findViewById(R.id.btnTambah);

        keepData = (CheckBox)findViewById(R.id.keepData);

        TambahData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaNotes = NamaNote.getText().toString().trim();
                String tanggalNotes = TanggalNote.getText().toString().trim();
                String keterangan = Keterangan.getText().toString().trim();

                if (namaNotes.equals("") || tanggalNotes.equals("") || keterangan.equals("")) {
                    Toast.makeText(MainActivity.this, "Pastikan Semua Form Data Terisi", Toast.LENGTH_SHORT).show();
                } else {
                    ContentValues values = new ContentValues();
                    values.put(DBHandler.row_nama_notes, namaNotes);
                    values.put(DBHandler.row_tanggal_notes, tanggalNotes);
                    values.put(DBHandler.row_keterangan, keterangan);
                    handler.tambahData(values);
                    if (!keepData.isChecked()) {
                        NamaNote.setText("");
                        TanggalNote.setText("");
                        Keterangan.setText("");
                        NamaNote.requestFocus();
                    }
                    Toast.makeText(MainActivity.this, "Berhasil Menambah Catatan", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
