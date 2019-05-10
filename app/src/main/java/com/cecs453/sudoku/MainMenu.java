package com.cecs453.sudoku;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MyRecyclerViewAdapter.ItemClickListener, View.OnClickListener {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private ImageView sudokuLogo;
    private Chronometer timer;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter mAdapter;
    public int[][] sudokuBoard = new int[9][9];
    int[][] solution;
    public ArrayList<String> data;
    public ArrayList<String> dataOriginal;
    private LinearLayout b1,b2,b3,b4,b5,b6,b7,b8,b9,b0;
    private ImageView undo;
    public int holdValue = 0;
    @Override
    protected void onStart() {
        super.onStart();
        timer.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        sudokuLogo = findViewById(R.id.logo);
        sudokuLogo.setImageResource(R.drawable.sudoku_horizontal);
        timer = findViewById(R.id.timer);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        b4 = findViewById(R.id.b4);
        b5 = findViewById(R.id.b5);
        b6 = findViewById(R.id.b6);
        b7 = findViewById(R.id.b7);
        b8 = findViewById(R.id.b8);
        b9 = findViewById(R.id.b9);
        b0 = findViewById(R.id.b0);
        undo = findViewById(R.id.imageViewUndo);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        b7.setOnClickListener(this);
        b8.setOnClickListener(this);
        b9.setOnClickListener(this);
        b0.setOnClickListener(this);
        undo.setOnClickListener(this);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        ImageView headerImageView = headerView.findViewById(R.id.headerimageView);
        TextView headerDisplayName = headerView.findViewById(R.id.headerDisplayName);
        TextView headerEmail = headerView.findViewById(R.id.headerEmail);
        headerDisplayName.setText(currentUser.getDisplayName());
        headerEmail.setText(currentUser.getEmail());
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 9));
        dataOriginal = new ArrayList<>();
        data = new ArrayList<>();
        newGame();
        mAdapter = new MyRecyclerViewAdapter(this,data,dataOriginal);
        mAdapter.setClickListener(this);
        recyclerView.setAdapter(mAdapter);
}

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            this.moveTaskToBack(true);
            //super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_newgame){
            newGame();
        }
        else if (id == R.id.nav_changePass) {
            startActivity(new Intent(getApplicationContext(),ChangePassword.class));
        } else if (id == R.id.nav_changeProfile) {
            startActivity(new Intent(getApplicationContext(),ChangeProfile.class));
        } else if (id == R.id.nav_help) {
            startActivity(new Intent(getApplicationContext(),Help.class));
        } else if (id == R.id.nav_about){
            startActivity(new Intent(getApplicationContext(),About.class));
        }
        else if (id == R.id.nav_logout) {
            firebaseAuth.signOut();
            startActivity(new Intent(this, Login.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void newGame(){
        //timer.setBase((SystemClock.elapsedRealtime()));
        SudokuGenerator sudokuGenerator = new SudokuGenerator();
        solution = sudokuGenerator.generateGrid();
        sudokuBoard = sudokuGenerator.removeElements(solution);
        for (int i = 0; i < sudokuBoard.length; i++) {
            for (int j = 0; j < sudokuBoard[i].length; j++) {
                System.out.print(sudokuBoard[i][j] + " ");
                dataOriginal.add(Integer.toString(sudokuBoard[i][j]));
                data.add(Integer.toString(sudokuBoard[i][j]));
            }
            System.out.println();
        }
    }
    public void checkWin(){
        
    }

    @Override
    public void onItemClick(View view, int position) {
        if(dataOriginal.get(position).equals("0")){
            data.set(position,Integer.toString(holdValue));
            mAdapter.notifyDataSetChanged();
        }
        checkWin();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.b1:
                holdValue = 1;
                break;
            case R.id.b2:
                holdValue = 2;
                break;
            case R.id.b3:
                holdValue = 3;
                break;
            case R.id.b4:
                holdValue = 4;
                break;
            case R.id.b5:
                holdValue = 5;
                break;
            case R.id.b6:
                holdValue = 6;
                break;
            case R.id.b7:
                holdValue = 7;
                break;
            case R.id.b8:
                holdValue = 8;
                break;
            case R.id.b9:
                holdValue = 9;
                break;
            case R.id.b0:
                holdValue = 0;
                break;

        }
    }
}

