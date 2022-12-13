package com.example.netflix;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
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

public class MainActivity extends AppCompatActivity implements Adapter.onItemClickListener {

    public static final String EXTRA_URLSEASON="urlSeason";

    private RecyclerView mRecyclerView;
    private Adapter mAdapter;
    private ArrayList<Item> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView=findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        LeerApi("","");
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        //accion de busqueda
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        //en caso de seleccionar el pais
        if(item.getGroupId()==R.id.paises || item.getItemId()==R.id.Pais){
            LeerApi(String.valueOf(item.getTitle()),"Pais");
        }
        //en caso de seleccionar el genero
        if(item.getGroupId()==R.id.generos || item.getItemId()==R.id.Genero){
            LeerApi(String.valueOf(item.getTitle()),"Genero");
        }
        //en caso de seleccionar el corazon

        return(super.onOptionsItemSelected(item));
    }

    //metodo de leer la api
    public void LeerApi(String filtro, String tipoFiltro) {
        //request
        String url="https://api.tvmaze.com/shows";
        StringRequest postResquest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //obtener el array con los valores
                    JSONArray jsonarray = new JSONArray(response);
                    mList=new ArrayList<>();
                    for (int i = 0; i < jsonarray.length(); i++) {
                        try {
                            JSONObject jsonObject = jsonarray.getJSONObject(i);
                            //filtros de busqueda de pais y de genero
                            if(tipoFiltro.equals("Pais")){
                                String pais = jsonObject.getJSONObject("network").getJSONObject("country").getString("name");
                                if(filtro.equals(pais) || filtro.equals("Pais")){
                                    String nombre = jsonObject.getString("name");
                                    String urlSeason = jsonObject.getJSONObject("_links").getJSONObject("self").getString("href");
                                    String imagen = jsonObject.getJSONObject("image").getString("original");
                                    mList.add(new Item(imagen,nombre,urlSeason));
                                }
                            }else if(tipoFiltro.equals("Genero")){
                                if(filtro.equals("Genero")){
                                    String nombre = jsonObject.getString("name");
                                    String urlSeason = jsonObject.getJSONObject("_links").getJSONObject("self").getString("href");
                                    String imagen = jsonObject.getJSONObject("image").getString("original");
                                    mList.add(new Item(imagen,nombre,urlSeason));
                                }else{
                                    JSONArray listaGeneros=jsonObject.getJSONArray("genres");
                                    for(int j=0;j<listaGeneros.length();j++){
                                        if(filtro.equals(listaGeneros.get(j).toString())){
                                            String nombre = jsonObject.getString("name");
                                            String urlSeason = jsonObject.getJSONObject("_links").getJSONObject("self").getString("href");
                                            String imagen = jsonObject.getJSONObject("image").getString("original");
                                            mList.add(new Item(imagen,nombre,urlSeason));
                                            break;
                                        }
                                    }
                                }
                            }else{
                                //si no hay filtro, mostrar todos
                                String nombre = jsonObject.getString("name");
                                String urlSeason = jsonObject.getJSONObject("_links").getJSONObject("self").getString("href");
                                String imagen = jsonObject.getJSONObject("image").getString("original");
                                mList.add(new Item(imagen,nombre,urlSeason));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    mAdapter=new Adapter(MainActivity.this,mList);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.setOnItemClickListener(MainActivity.this);
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


    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, SeasonActivity.class);
        Item clickedItem=mList.get(position);
        detailIntent.putExtra(EXTRA_URLSEASON,clickedItem.getmUrlSeason());
        startActivity(detailIntent);
    }



}