package com.example.recyclerviewproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ExampleItem> exampleList;

    private RecyclerView recyclerView;

    //for better performance if more data is passed
    // private RecyclerView.Adapter adapter;
    //change RecyclerView.Adapter to ExampleAdapter
    private ExampleAdapter adapter;

    //aligning of single item passed through this manager
    private RecyclerView.LayoutManager layoutManager;

    private Button insertBtn;
    private Button removeBtn;
    private EditText textInsert, textRemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createExampleList();
        buildRecyclerView();
        setButtons();
    }

    public void insertItem(int position) {
        //position to where put new item
        exampleList.add(position, new ExampleItem(R.drawable.ic_android, "New Item At position " + position, "This is Line 2"));
        adapter.notifyItemInserted(position);
    }

    public void removeItem(int position) {
        exampleList.remove(position);
        adapter.notifyItemRemoved(position);
    }

    //passed "Clicked" text
    public void changeItem(int position, String text) {
        exampleList.get(position).changeText(text);
        adapter.notifyItemChanged(position);
    }

    public void createExampleList() {
        exampleList = new ArrayList<>();
        exampleList.add(new ExampleItem(R.drawable.ic_android, "Line 1", "Line 2"));
        exampleList.add(new ExampleItem(R.drawable.ic_library, "Line 3", "Line 4"));
        exampleList.add(new ExampleItem(R.drawable.ic_music, "Line 5", "Line 6"));
        exampleList.add(new ExampleItem(R.drawable.ic_android, "Line 1", "Line 2"));
        exampleList.add(new ExampleItem(R.drawable.ic_library, "Line 3", "Line 4"));
        exampleList.add(new ExampleItem(R.drawable.ic_music, "Line 5", "Line 6"));
        exampleList.add(new ExampleItem(R.drawable.ic_android, "Line 1", "Line 2"));
        exampleList.add(new ExampleItem(R.drawable.ic_library, "Line 3", "Line 4"));
        exampleList.add(new ExampleItem(R.drawable.ic_music, "Line 5", "Line 6"));
        exampleList.add(new ExampleItem(R.drawable.ic_android, "Line 1", "Line 2"));
        exampleList.add(new ExampleItem(R.drawable.ic_library, "Line 3", "Line 4"));
        exampleList.add(new ExampleItem(R.drawable.ic_music, "Line 5", "Line 6"));
    }

    public void buildRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);

        //fix recyclerView size and increase performance of app
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        adapter = new ExampleAdapter(exampleList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        //call OnItemClickListener
        adapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                changeItem(position, "Clicked");
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
    }

    private void setButtons() {
        insertBtn = findViewById(R.id.insertBtn);
        removeBtn = findViewById(R.id.removeBtn);
        textInsert = findViewById(R.id.textInsert);
        textRemove = findViewById(R.id.textRemove);

        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textInsert.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Select position", Toast.LENGTH_SHORT).show();
                } else {
                    //change content of edit text into int
                    int position = Integer.parseInt(textInsert.getText().toString());
                    insertItem(position);
                }
                textInsert.setText("");
            }
        });

        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textRemove.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Select position", Toast.LENGTH_SHORT).show();
                } else {
                    int position = Integer.parseInt(textRemove.getText().toString());
                    removeItem(position);
                }
                textRemove.setText("");
            }
        });
    }

    //for search menu option
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.actionSearch);
        SearchView searchView = (SearchView) searchItem.getActionView();

        //Change keyboard search action to appropriate one
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}
