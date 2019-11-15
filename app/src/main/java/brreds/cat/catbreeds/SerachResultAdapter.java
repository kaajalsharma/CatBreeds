package brreds.cat.catbreeds;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SerachResultAdapter extends RecyclerView.Adapter<SerachResultAdapter.SearchResultViewHolder> {

    private List<Cat> data;

    public SerachResultAdapter(List<Cat> data){
        this.data = data;
    }

    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view  = inflater.inflate( R.layout.search_result_item,parent,false);

        return new SearchResultViewHolder( view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHolder  holder, final int position) {
        String title = data.get(position).getName();
        holder.txttitle.setText(title);

        String url = "https://api.thecatapi.com/v1/images/search?breed_id="+data.get(position).getId()+"&size=small";
//        final Context context = holder.imgicon.getContext();
        final SearchResultViewHolder  hold = holder;
        StringRequest request =  new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        //TODO:it has a hell lot of warnings
                        Log.d("adapter response",response);
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        final CatDatum[] catData = gson.fromJson(response,CatDatum[].class);
                        if(catData.length != 0){
                        Glide.with(hold.imgicon.getContext()).load(catData[0].getUrl()).into(hold.imgicon);
                        Log.i("imageUrl",catData[0].getUrl());
                        }
                        hold.parent_layout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(hold.imgicon.getContext(),CatDetails.class);
                                Gson gson  = new Gson();

                                intent.putExtra("data",gson.toJson(data.get(position)));
                                intent.putExtra("imgurl",catData.length==0?"no":catData[0].getUrl());
                                hold.imgicon.getContext().startActivity(intent);
                            }
                        });
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ERROR","error => "+error.toString());
                        Toast.makeText(hold.imgicon.getContext(), "Something went Wrong", Toast.LENGTH_SHORT).show();
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
        RequestQueue queue = Volley.newRequestQueue(hold.imgicon.getContext());
        queue.add(request);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class SearchResultViewHolder extends RecyclerView.ViewHolder{
        ImageView imgicon;
        TextView txttitle;
        LinearLayout parent_layout;
        public  SearchResultViewHolder(View itemview){
            super(itemview);
            imgicon = itemview.findViewById(R.id.imgIcon);
            txttitle= itemview.findViewById((R.id.title ));
            parent_layout = itemview.findViewById(R.id.parent_layout);
        }
    }
}
