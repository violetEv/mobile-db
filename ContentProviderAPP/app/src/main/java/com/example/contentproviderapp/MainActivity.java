// MainActivity.java
package com.example.contentproviderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    public void onClickAddDetails(View view) {
        ContentValues values = new ContentValues();
        values.put(DaftarMenu.name, ((EditText) findViewById(R.id.txtNamaMenu)).getText().toString());
        values.put(DaftarMenu.harga, ((EditText) findViewById(R.id.txtHargaMenu)).getText().toString());
        getContentResolver().insert(DaftarMenu.CONTENT_URI, values);
        Toast.makeText(getBaseContext(), "Data berhasil disimpan", Toast.LENGTH_LONG).show();
        // Bersihkan EditText setelah memasukkan data
        ((EditText) findViewById(R.id.txtNamaMenu)).getText().clear();
        ((EditText) findViewById(R.id.txtHargaMenu)).getText().clear();
    }

    @SuppressLint("Range")
    public void onClickShowDetails(View view) {
        // Retrieve menu records
        TextView resultView = findViewById(R.id.resNama);
        Cursor cursor = getContentResolver().query(Uri.parse("content://com.example.contentproviderapp.DaftarMenu/menu"), null, null, null, null);
        if (cursor.moveToFirst()) {
            StringBuilder strBuild = new StringBuilder();
            while (!cursor.isAfterLast()) {
                strBuild.append("\n" + cursor.getString(cursor.getColumnIndex("id")) + "-" + cursor.getString(cursor.getColumnIndex("nama_menu")) + "-" + cursor.getString(cursor.getColumnIndex("harga")));
                cursor.moveToNext();
            }
            resultView.setText(strBuild);
        } else {
            resultView.setText("No Records Found");
        }
    }
}
