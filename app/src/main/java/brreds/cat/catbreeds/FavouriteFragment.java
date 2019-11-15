package brreds.cat.catbreeds;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class FavouriteFragment extends Fragment {
    SharedPreferences mPrefs ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_favourite, container, false);
        final RecyclerView search_results = view.findViewById(R.id.fav_results);
        search_results.setLayoutManager(new LinearLayoutManager(getContext()));
        Gson gson = new Gson();
        mPrefs= getContext().getSharedPreferences("favdata",MODE_PRIVATE);
        String json = mPrefs.getString("data", "");
        Type type = new TypeToken<List<Cat>>(){}.getType();
        List<Cat> cats = gson.fromJson(json, type);
        if(cats == null){
            cats = new ArrayList<Cat>();
        }
//        Cat[] arr = new Cat[cats.size()];
//        arr  = cats.toArray(arr);
        // ArrayList to Array Conversion
//        for (int i =0; i < cats.size(); i++)
//            arr[i] = cats.get(i);
        search_results.setAdapter( new SerachResultAdapter(cats));
        return view;
    }

   }
