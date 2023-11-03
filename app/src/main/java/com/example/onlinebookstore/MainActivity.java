package com.example.onlinebookstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinebookstore.Controller.Customer.ChatActivity;
import com.example.onlinebookstore.Fragment.CartFragment;
import com.example.onlinebookstore.Fragment.HomeFragment;
import com.example.onlinebookstore.Fragment.PersonFragment;
import com.example.onlinebookstore.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private int customerId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        customerId = intent.getIntExtra("accountId", 0);
        ChatActivity chatActivity = new ChatActivity();
        chatActivity.setCustomerId(customerId);
        // Tạo ChatFragment và truyền customerId vào đó
        replaceFragment(new HomeFragment());//default display when run app

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if(itemId == R.id.navigation_home){
                System.out.println("AA");
                replaceFragment(new HomeFragment());
                return true;
            }
            else if(itemId == R.id.navigation_person){
                System.out.println("BB");
                replaceFragment(new PersonFragment());
                return true;
            }
            else{
                System.out.println("DD");
                replaceFragment(new CartFragment());
                return true;

            }
        });
        System.out.println("EEE");
    }   @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.bottom_nav_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.navigation_chat){
            Intent intent = new Intent(MainActivity.this, ChatActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}