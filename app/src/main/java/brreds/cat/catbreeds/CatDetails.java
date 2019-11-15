package brreds.cat.catbreeds;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CatDetails extends AppCompatActivity {


    TextView Name;
    TextView description;
    TextView weight;
    TextView Temprament;
    TextView Origin;
    TextView LifeSpan;
    TextView WikipediaLink;
    TextView DogFriendnessLevel;
    ImageView image;
    SharedPreferences mPrefs ;
    ToggleButton btn;
    Cat cat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_details);
        Name = findViewById(R.id.name_of_cat);
        description = findViewById(R.id.description_of_cat);
        weight = findViewById(R.id.weight_of_cat);
        Temprament = findViewById(R.id.temprament_of_cat);
        Origin = findViewById(R.id.origin_of_cat);
        LifeSpan = findViewById(R.id.lifespan_of_cat);
        WikipediaLink = findViewById(R.id.Wikipedia_of_cat);
        DogFriendnessLevel = findViewById(R.id.Dog_friendness_of_cat);
        image = findViewById(R.id.cat_image);
        btn = findViewById(R.id.isfav);
        mPrefs= getSharedPreferences("favdata",MODE_PRIVATE);
        getIncomingIntent();


    }
    public void toggleSwitch(View view){

        if(view ==btn){
//            MyObject myObject = new MyObject;
//set variables of 'myObject', etc.
            Log.i("hii i am called","toogle switch");
            Gson gson = new Gson();
            String json = mPrefs.getString("data", "");
            Type type = new TypeToken<List<Cat>>(){}.getType();
            List<Cat> cats = gson.fromJson(json, type);

            if(btn.isChecked()){
                if(cats == null){
                    cats = new ArrayList<Cat>();
                }
                Log.i("arraylist",cats.toString());
                cats.add(cat);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                json = gson.toJson(cats);
                prefsEditor.putString("data", json);
                prefsEditor.commit();
            }

        }

    }
    private void getIncomingIntent(){
        if(getIntent().hasExtra("data")){
            Gson gson = new Gson();
            String data = getIntent().getStringExtra("data");
            cat = gson.fromJson(data,Cat.class);
            Name.setText(cat.getName());
            description.setText(cat.getDescription());
            Weight w = cat.getWeight();
            weight.setText(w.getImperial() +" impirial Units");
            Temprament.setText(cat.getTemperament());
            Origin.setText(cat.getOrigin());
            LifeSpan.setText(cat.getLifeSpan());
            WikipediaLink.setText(cat.getWikipediaUrl());
            DogFriendnessLevel.setText(cat.getDogFriendly().toString());
        }
        if(getIntent().hasExtra("imgurl")){
           String url =  getIntent().getStringExtra("imgurl");
            Glide.with(this).load(url).into(image);
        }
    }
}
