package com.example.takenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.takenotes.R;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {


    ListView listView;
    static ArrayList<String> notes;
    static ArrayAdapter arrayAdapter;
    SharedPreferences sharedPreferences;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.listView);
        sharedPreferences=getApplicationContext().getSharedPreferences("com.example.takenotes",Context.MODE_PRIVATE);
         HashSet<String> prevNotes=(HashSet<String>)sharedPreferences.getStringSet("notes",null);
         if(prevNotes==null) {
             notes.add("add here");
         }
         else
         {
             notes=new ArrayList(prevNotes);
         }
         Log.i("message",notes.get(0));
         arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,notes);
        listView.setAdapter(arrayAdapter);
        Log.i("message1",notes.get(0));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("message2",notes.get(0));
                Intent intent=new Intent(getApplicationContext(),notesEditorActivity.class);
                intent.putExtra("notesValue",position);
                startActivity(intent);

            }

        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(MainActivity.this).setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure").setMessage("Do you want to delete this item")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                notes.remove(position);
                                arrayAdapter.notifyDataSetChanged();

                                 sharedPreferences=getApplicationContext().getSharedPreferences("com.example.takenotes", Context.MODE_PRIVATE);
                                HashSet<String> storeNotes=new HashSet<>(MainActivity.notes);
                                sharedPreferences.edit().putStringSet("notes",storeNotes).apply();


                            }
                        }).setNegativeButton("no", null).show();
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         super.onOptionsItemSelected(item);
         Intent intent=new Intent(getApplicationContext(),notesEditorActivity.class);
         startActivity(intent);
         return true;
    }

}
