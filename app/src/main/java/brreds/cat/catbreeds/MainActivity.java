package brreds.cat.catbreeds;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }



    public void ChangeFragment(View view){
        Log.i("isChange","yes");
        Fragment fragment;
        if(view == findViewById(R.id.search_button1)){
            fragment = new  SearchFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_place,fragment);
            ft.commit();
        }
        if(view == findViewById(R.id.favourite_button)){
            fragment = new FavouriteFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_place,fragment);
            ft.commit();
        }
    }
}
