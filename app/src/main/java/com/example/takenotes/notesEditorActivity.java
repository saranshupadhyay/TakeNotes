package com.example.takenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class notesEditorActivity extends AppCompatActivity {
    int notesValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_editor);
        EditText editText=findViewById(R.id.editText);
        Intent intent=getIntent();
          notesValue =intent.getIntExtra("notesValue",-1);
        if(notesValue!=-1)
        {
            editText.setText(MainActivity.notes.get(notesValue));
        }
        else
        {
            MainActivity.notes.add("");
            notesValue=MainActivity.notes.size()-1;

        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.notes.set(notesValue,String.valueOf(s));
                MainActivity.arrayAdapter.notifyDataSetChanged();
                SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("com.example.takenotes", Context.MODE_PRIVATE);
                HashSet<String> storeNotes=new HashSet<>(MainActivity.notes);
                sharedPreferences.edit().putStringSet("notes",storeNotes).apply();


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

}
}
