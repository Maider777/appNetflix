package com.example.netflix;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class SeasonActivity extends AppCompatActivity {

    public static final String EXTRA_URLSEASON="urlSeason";
    private RecyclerView mRecyclerViewSeason;
    String urlSeason;
    Button botonEpisodio;
    Button botonReparto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season);
        Intent intent = getIntent();
        urlSeason = intent.getStringExtra(EXTRA_URLSEASON);
        botonEpisodio=(Button) findViewById(R.id.botonEpisodio);
        botonReparto=(Button) findViewById(R.id.botonReparto);
        mRecyclerViewSeason=findViewById(R.id.recycler_view_season);
        mRecyclerViewSeason.setHasFixedSize(true);
        mRecyclerViewSeason.setLayoutManager(new LinearLayoutManager(this));
        TextView textViewSeason=(TextView) findViewById(R.id.text_view_number);
        textViewSeason.setText(urlSeason);
        LeerApi();
    }

    //metodo de leer la api
    public void LeerApi() {
        //request
        urlSeason=urlSeason+"/seasons";
        StringRequest postResquest = new StringRequest(Request.Method.GET, urlSeason, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //obtener el array con los valores
                    JSONArray jsonarray = new JSONArray(response);
                    ArrayList<ItemSeason> mListSeason=new ArrayList<>();
                    for (int i = 0; i < jsonarray.length(); i++) {
                        try {
                            JSONObject jsonObject = jsonarray.getJSONObject(i);
                            //pasar el nombre, la foto de la temporada, creando las cartas
                            String image = jsonObject.getJSONObject("image").getString("original");
                            String season = jsonObject.getString("number");
                            mListSeason.add(new ItemSeason(image,season,urlSeason));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    AdapterSeason mExampleAdapterSeason=new AdapterSeason(SeasonActivity.this,mListSeason);
                    mRecyclerViewSeason.setAdapter(mExampleAdapterSeason);
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

    public void onClickEpisodio(View view) {
        Intent intent = new Intent(SeasonActivity.this, EpisodeActivity.class);
        intent.putExtra("EXTRA_URLSEASON", urlSeason);
        String textoBoton= (String) ((Button)view).getText();
        String[] tokens = textoBoton.split("T");
        String season=tokens[1];
        intent.putExtra("EXTRA_NUMEROTEMPORADA", season);
        startActivity(intent);
    }

    public void onClickReparto(View view) {
        Intent intent = new Intent(SeasonActivity.this, CastActivity.class);
        intent.putExtra("EXTRA_URLSEASON", urlSeason);
        startActivity(intent);
    }

}