package com.example.onlinebookstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.onlinebookstore.Fragment.CartFragment;
import com.example.onlinebookstore.Fragment.ChatFragment;
import com.example.onlinebookstore.Fragment.HomeFragment;
import com.example.onlinebookstore.Fragment.PersonFragment;
import com.example.onlinebookstore.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new HomeFragment());//default display when run app

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

//            switch (itemId){
//                case R.id.navigation_home://home
//                    replaceFragment(new HomeFragment());
//                    break;
//                case  R.id.navigation_person://person
//                    replaceFragment(new PersonFragment());
//                    break;
//               case R.id.navigation_chat://chat
//                   replaceFragment(new ChatFragment());
//                    break;
//                case  R.id.navigation_cart://cart
//                    replaceFragment(new CartFragment());
//                    break;
//            }
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
            else if(itemId == R.id.navigation_chat){
                System.out.println("CC");
                replaceFragment(new ChatFragment());
                return true;

            }
            else{
                System.out.println("DD");
                replaceFragment(new CartFragment());
                return true;

            }
        });
        System.out.println("EEE");
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}