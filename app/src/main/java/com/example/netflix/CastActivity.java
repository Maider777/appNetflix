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

public class CastActivity extends AppCompatActivity {

    String url;
    private RecyclerView mRecyclerViewCast;
    private ArrayList<ItemCast> mListCast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast);
        Intent intent = getIntent();
        url = intent.getStringExtra("EXTRA_URLSEASON");
        mRecyclerViewCast=findViewById(R.id.recycler_view_cast);
        mRecyclerViewCast.setHasFixedSize(true);
        mRecyclerViewCast.setLayoutManager(new LinearLayoutManager(this));
        TextView textViewSeason=(TextView) findViewById(R.id.text_view_cast);
        textViewSeason.setText(url);
        mListCast=new ArrayList<>();
        LeerApi();
    }

    //metodo de leer la api
    public void LeerApi() {
        //request
        url=url.replace("seasons","cast");
        StringRequest postResquest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //obtener el array con los valores
                    JSONArray jsonarray = new JSONArray(response);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        try {
                            JSONObject jsonObject = jsonarray.getJSONObject(i);
                            //pasar el nombre, la foto de la temporada, creando las cartas
                            JSONObject persona = jsonObject.getJSONObject("person");
                            String name = persona.getString("name");
                            String image = persona.getJSONObject("image").getString("original");
                            mListCast.add(new ItemCast(name,image));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    AdapterCast mAdapterCast=new AdapterCast(CastActivity.this,mListCast);
                    mRecyclerViewCast.setAdapter(mAdapterCast);
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
