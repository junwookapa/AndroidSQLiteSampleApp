package com.threabba.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //wdiget
    private ListView mListView;
    private ArrayAdapter<String> mListViewAdapter;
    private Button insert, update, delete, select;
    private EditText mEditText;
    // db
    private DBAdapter mDBAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Binding widget
        mListView = findViewById(R.id.listView);
        mEditText = findViewById(R.id.editText);
        insert = findViewById(R.id.insert);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);
        select = findViewById(R.id.select);

        // init object
        mDBAdapter = new DBAdapter(this);
        mListViewAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        mListView.setAdapter(mListViewAdapter);

        // init data
        String[] allMemoArray = mDBAdapter.getAllMemoByStringArray();
        if(mDBAdapter.getAllMemoByStringArray() != null){
            mListViewAdapter.addAll(allMemoArray);
            mListViewAdapter.notifyDataSetChanged();
        }

        // set Adapters
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Memo memo = Memo.fromStringWithoutNumber(mEditText.getText().toString());
                if(memo != null){
                    Log.e("로그으 ", mDBAdapter.insertMemo(memo)+"입니다");
                    String[] allMemoArray = mDBAdapter.getAllMemoByStringArray();
                    if(mDBAdapter.getAllMemoByStringArray() != null){
                        mListViewAdapter.clear();
                        mListViewAdapter.addAll(allMemoArray);
                        mListViewAdapter.notifyDataSetChanged();
                    }
                }

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Memo memo = Memo.fromString(mEditText.getText().toString());
                if(memo != null){
                    mDBAdapter.updateMemo(memo);
                    String[] allMemoArray = mDBAdapter.getAllMemoByStringArray();
                    if(mDBAdapter.getAllMemoByStringArray() != null){
                        mListViewAdapter.clear();
                        mListViewAdapter.addAll(allMemoArray);
                        mListViewAdapter.notifyDataSetChanged();
                    }
                }

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Memo memo = Memo.fromString(mEditText.getText().toString());
                if(memo != null){
                    mDBAdapter.deleteMemo(memo.getId());
                    String[] allMemoArray = mDBAdapter.getAllMemoByStringArray();
                    if(mDBAdapter.getAllMemoByStringArray() != null){
                        mListViewAdapter.clear();
                        mListViewAdapter.addAll(allMemoArray);
                        mListViewAdapter.notifyDataSetChanged();
                    }
                }

            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mEditText.setText(mListViewAdapter.getItem(position));
            }
        });
    }
}
