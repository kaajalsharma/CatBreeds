package brreds.cat.catbreeds;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchFragment extends Fragment {
    String url = "https://api.thecatapi.com/v1/breeds/search?q=";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_search, container, false);

        Button btn = view.findViewById(R.id.search_button);
        btn.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Log.i("isclicked","yes");
                        EditText search_input = view.findViewById(R.id.search_input);
                        String query = search_input.getText().toString();
                        Log.i("text filed value",query);
                        final  RecyclerView search_results = view.findViewById(R.id.search_results);
                        search_results.setLayoutManager(new LinearLayoutManager(getContext()));
                        Log.d("url",url+query);
                        StringRequest request =  new StringRequest(Request.Method.GET, url+query,
                                new Response.Listener<String>()
                                {
                                    @Override
                                    public void onResponse(String response) {
                                        // response
                                        Log.d("Response", response);
                                        GsonBuilder gsonBuilder = new GsonBuilder();
                                        Gson gson = gsonBuilder.create();
                                        Type type = new TypeToken<List<Cat>>(){}.getType();
                                        List<Cat> cats = gson.fromJson(response,type);

                                        search_results.setAdapter( new SerachResultAdapter(cats));
                                    }
                                },
                                new Response.ErrorListener()
                                {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        // TODO Auto-generated method stub
                                        Log.d("ERROR","error => "+error.toString());
                                        Toast.makeText(getContext(), "Something went Wrong", Toast.LENGTH_SHORT).show();
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Log.d("setting params","i am setting them bro");
                                Map<String, String>  params = new HashMap<String, String>();
                                params.put("x-api-key", "ca03767c-cb88-4552-8caa-801fa9d8a0e8");
                                return params;
                            }
                        };
                        RequestQueue queue = Volley.newRequestQueue(getContext());
                        queue.add(request);
                    }
                }
        );
        return view;
    }

    void buttonClicked(View v){
        Log.i("button click","i am clicked");
    }



}
