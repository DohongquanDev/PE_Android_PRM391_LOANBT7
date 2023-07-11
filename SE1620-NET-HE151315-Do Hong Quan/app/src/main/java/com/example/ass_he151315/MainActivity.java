package com.example.ass_he151315;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText edt_id, edt_firstname, edt_lastname, edt_age;
    private Button btn_add, btn_update, btn_delete, btn_list;
    private RecyclerView recyclerView;

    private DBHelper DB;

    private List<User> userList;

    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindingView();

        DB = new DBHelper(this);
        userList = new ArrayList<>();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    userList.clear();

                    int id = Integer.parseInt(edt_id.getText().toString());
                    String first_name = edt_firstname.getText().toString();
                    String last_name = edt_lastname.getText().toString();
                    String age = edt_age.getText().toString();

                    if (!DB.checkExistUserInDB(id)) {
                        User user = new User(id, first_name, last_name, Integer.parseInt(age));
                        boolean checkInsertData = DB.insertUser(user);
                        if (checkInsertData) {
                            Toast.makeText(MainActivity.this, "Add successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Add failed!", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        User user = new User();
                        user.setId(id);
                        user.setFirst_name(first_name);
                        user.setLast_name(last_name);
                        user.setAge(Integer.parseInt(age));
                        int i = DB.updateUser(user);
                        if (i >= 1) {
                            Toast.makeText(MainActivity.this, "Update successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Update failed!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    userList = DB.getAllUsers();
                    userAdapter = new UserAdapter(MainActivity.this, userList);
                 recyclerView.setAdapter(userAdapter);
                  recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userList.clear();
                String id = edt_id.getText().toString().trim();
                String firstname = edt_firstname.getText().toString().trim();
                String lastname = edt_lastname.getText().toString().trim();
                String age = edt_age.getText().toString().trim();

                ArrayList<String> arrSearch = new ArrayList<>();
                arrSearch.add(id);
                arrSearch.add(firstname);
                arrSearch.add(lastname);
                arrSearch.add(age);

                List<User> userList = DB.searchUsers(arrSearch);

                userAdapter = new UserAdapter(MainActivity.this, userList);
                recyclerView.setAdapter(userAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }
        });


        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    userList.clear();
                    int id = Integer.parseInt(edt_id.getText().toString().trim());
                    User user = DB.getUser(id);
                    DB.deleteUser(user);
                    Toast.makeText(MainActivity.this, "Delete successfully!", Toast.LENGTH_SHORT).show();

                    userList = DB.getAllUsers();
                    userAdapter = new UserAdapter(MainActivity.this, userList);
                    recyclerView.setAdapter(userAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    userList.clear();
                    int id = Integer.parseInt(edt_id.getText().toString().trim());
                    String first_name = edt_firstname.getText().toString().trim();
                    String last_name = edt_lastname.getText().toString().trim();
                    String age = edt_age.getText().toString().trim();
                    User user = DB.getUser(id);
                    user.setFirst_name(first_name);
                    user.setLast_name(last_name);
                    user.setAge(Integer.parseInt(age));
                    DB.updateUser(user);
                    Toast.makeText(MainActivity.this, "Update successfully!", Toast.LENGTH_SHORT).show();

                    userList = DB.getAllUsers();
                    userAdapter = new UserAdapter(MainActivity.this, userList);
                    recyclerView.setAdapter(userAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        userList = DB.getAllUsers();
        userAdapter = new UserAdapter(MainActivity.this, userList);
        recyclerView.setAdapter(userAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    private void bindingView() {
        edt_id = findViewById(R.id.edt_id);
        edt_firstname = findViewById(R.id.edt_firstname);
        edt_lastname = findViewById(R.id.edt_lastname);
        edt_age = findViewById(R.id.edt_age);
        btn_add = findViewById(R.id.btn_add);
        btn_update = findViewById(R.id.btn_update);
        btn_delete = findViewById(R.id.btn_delete);
        btn_list = findViewById(R.id.btn_list);
        recyclerView = findViewById(R.id.recyclerView);
    }
}