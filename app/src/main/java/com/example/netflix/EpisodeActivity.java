package com.example.netflix;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EpisodeActivity extends AppCompatActivity {

    String urlEpisode;
    String temporadaSeleccionada;
    private RecyclerView mRecyclerViewEpisode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode);
        Intent intent = getIntent();
        urlEpisode = intent.getStringExtra("EXTRA_URLSEASON");
        temporadaSeleccionada = intent.getStringExtra("EXTRA_NUMEROTEMPORADA");
        mRecyclerViewEpisode=findViewById(R.id.recycler_view_episode);
        mRecyclerViewEpisode.setHasFixedSize(true);
        mRecyclerViewEpisode.setLayoutManager(new LinearLayoutManager(this));
        TextView textViewSeason=(TextView) findViewById(R.id.text_view_episode);
        textViewSeason.setText(urlEpisode);
        TextView textViewCards=(TextView) findViewById(R.id.text_view_cards);
        textViewCards.setText(temporadaSeleccionada);
        LeerApi();
    }

    //metodo de leer la api
    public void LeerApi() {
        //request
        urlEpisode=urlEpisode.replace("seasons","episodes");
        StringRequest postResquest = new StringRequest(Request.Method.GET, urlEpisode, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //obtener el array con los valores
                    JSONArray jsonarray = new JSONArray(response);
                    ArrayList<ItemEpisode> mListEpisode=new ArrayList<>();
                    //recorrer los episodios
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonObject = jsonarray.getJSONObject(i);
                        String season = jsonObject.getString("season");
                        if(season.equals(temporadaSeleccionada)){
                            try {
                                String name = jsonObject.getString("name");
                                String imageEpisode = jsonObject.getJSONObject("image").getString("original");
                                String numeroEpisodio = jsonObject.getString("number");
                                String summary = jsonObject.getString("summary");
                                summary = summary.replace("<p>","");
                                summary = summary.replace("</p>","");
                                mListEpisode.add(new ItemEpisode("E"+numeroEpisodio+" - "+name,imageEpisode,"",summary));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    AdapterEpisode mAdapterEpisode=new AdapterEpisode(EpisodeActivity.this,mListEpisode);
                    mRecyclerViewEpisode.setAdapter(mAdapterEpisode);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
            }
        });
        Volley.newRequestQueue(this).add(postResquest);
    }

}
