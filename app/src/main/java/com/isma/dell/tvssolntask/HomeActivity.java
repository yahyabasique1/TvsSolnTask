package com.isma.dell.tvssolntask;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.isma.dell.tvssolntask.Adapter.TableDataAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    RecyclerView rvTableData;
    TableDataAdapter tableDataAdapter;
    Context context;
    SearchView searchView;
    SharedPreferences sharedPreferences;
    String myPreferencess="mypref";
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context=this;
        rvTableData=findViewById(R.id.rvTableData);
        searchView=findViewById(R.id.toolbar_search);
        floatingActionButton=findViewById(R.id.fab);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,BarChartActivity.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });

        sharedPreferences=getSharedPreferences(myPreferencess, Context.MODE_PRIVATE);

        String tableData=sharedPreferences.getString("TableData","");
        JSONObject jsonObject= null;
        try {
            jsonObject = new JSONObject(tableData);
            String json=jsonObject.getString("TABLE_DATA");
            JSONObject jsonObject1=new JSONObject(json);
            JSONArray jsonArray=jsonObject1.getJSONArray("data");
            List<String> list=new ArrayList<>();
            CacheData.listList=new ArrayList<>();
            Gson gson = new Gson();
            String jsonOutput = String.valueOf(jsonArray);
            Type listType = new TypeToken<List<List<String>>>(){}.getType();
            List<List<String>> posts = gson.fromJson(jsonOutput, listType);
            CacheData.listList.addAll(posts);
            tableDataAdapter=new TableDataAdapter(context,posts);
            rvTableData.setLayoutManager(new LinearLayoutManager(context));
            rvTableData.setAdapter(tableDataAdapter);
            tableDataAdapter.notifyDataSetChanged();

            for (int i=0;i<posts.size();i++){
                list.addAll(posts.get(i));
            }
            Log.d("RVTABDATA",posts.size()+" "+list.get(0));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                tableDataAdapter.getFilter().filter(query);
                return false;
            }


            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                tableDataAdapter.getFilter().filter(query);
                return false;
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
