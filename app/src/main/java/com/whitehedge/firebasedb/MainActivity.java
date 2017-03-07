package com.whitehedge.firebasedb;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements ChildEventListener {
    private EditText editTextFirstName;
    private EditText editTextMiddleName;
    private EditText editTextLastName;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private RecyclerView recyclerViewList;
    private ListAdapter mListAdapter;
    private LinkedList<User> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextMiddleName = (EditText) findViewById(R.id.editTextMiddleName);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        recyclerViewList = (RecyclerView) findViewById(R.id.recyclerViewList);
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager();
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        recyclerViewList.setLayoutManager(flexboxLayoutManager);
        mList = new LinkedList<>();
        mListAdapter = new ListAdapter(MainActivity.this, mList);
        recyclerViewList.setAdapter(mListAdapter);
        database = FirebaseDatabase.getInstance();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        myRef = database.getReference("Users");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                User user = dataSnapshot.getValue(User.class);
                Log.v("MainACtivity", "Users:-" + user.toString());
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("MainACtivity", "loadPost:onCancelled", databaseError.toException());
            }

        };
        //loadind data one by one
        database.getReference("Users").addChildEventListener(MainActivity.this);

    }

    public void handleSubmitData(View view) {
        DatabaseReference myRef = database.getReference("Users");
        String userId = myRef.push().getKey();
        User user = new User(editTextFirstName.getText().toString(), editTextMiddleName.getText().toString(), editTextLastName.getText().toString());
        myRef.child(userId).setValue(user);
        editTextFirstName.setText("");
        editTextMiddleName.setText("");
        editTextLastName.setText("");
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Log.v("MainActivity", "Child Added:-" + dataSnapshot.toString());
        mList.add(dataSnapshot.getValue(User.class));
//        mListAdapter.notifyItemInserted(mList.size() - 1);
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        Log.v("MainActivity", "Child Changed:-" + dataSnapshot.toString());
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        Log.v("MainActivity", "Child Remove" + dataSnapshot.toString());
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        Log.v("MainActivity", "Child Moved:-" + dataSnapshot.toString());
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
