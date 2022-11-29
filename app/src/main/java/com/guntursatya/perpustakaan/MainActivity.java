package com.guntursatya.perpustakaan;


import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.guntursatya.perpustakaan.adapters.CustomCursorAdapter;
import com.guntursatya.perpustakaan.adapters.DBHelper;
import com.guntursatya.perpustakaan.adapters.User;
import com.guntursatya.perpustakaan.sessions.SessionManager;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView Is;
    DBHelper dbHelper;
    Context context;

    SharedPreferences pref;

    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "session";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getSharedPreferences(PREF_NAME, PRIVATE_MODE);
//        String Email= pref.getString("key_email", "");

//        String Email= getIntent().getExtras().getString("emailnya");


        FloatingActionButton fab = findViewById(R.id.fab);
        FloatingActionButton fablogout = findViewById(R.id.fabuser);

        fablogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, AddActivity.class);
//                intent.putExtra("emailn",Email);
                startActivity(intent);
            }
        });

        dbHelper = new DBHelper(this);

        Is = (ListView)findViewById(R.id.list_pinjam);
        Is.setOnItemClickListener(this);

        setupListView();
    }

    private void setupListView() {
//        String Email= getIntent().getExtras().getString("emailnya");
        pref = getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        String Email= pref.getString("key_email", "");

        Cursor cursor = dbHelper.Datain(Email);
        CustomCursorAdapter customCursorAdapter = new CustomCursorAdapter(this, cursor, 1);
        Is.setAdapter(customCursorAdapter);
    }

//    private void setupListView() {
//        Cursor cursor = dbHelper.allData();
//        CustomCursorAdapter customCursorAdapter = new CustomCursorAdapter(this, cursor, 1);
//        Is.setAdapter(customCursorAdapter);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long I) {
        TextView getID = (TextView)view.findViewById(R.id.listID);
        final long id = Long.parseLong(getID.getText().toString());
        Cursor cur = dbHelper.oneData(id);
        cur.moveToFirst();

        Intent idpinjam = new Intent(MainActivity.this, AddActivity.class);
        idpinjam.putExtra(DBHelper.row_id, id);
        startActivity(idpinjam);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupListView();
    }
}