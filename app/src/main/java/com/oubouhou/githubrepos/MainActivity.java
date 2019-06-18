package com.oubouhou.githubrepos;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.oubouhou.githubrepos.Adapter.RepoAdapter;
import com.oubouhou.githubrepos.Model.RepoItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private RecyclerView repoRecyclerView;
    private RepoAdapter repoAdapter;

    private ArrayList<RepoItem> repos;
    private RequestQueue requestQueue;

    private ProgressBar progressBar;
    int page = 1;
    boolean show = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Trending Repos");

        repoRecyclerView = findViewById(R.id.recycler_repo);
        repoRecyclerView.setHasFixedSize(true);
        repoRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressBar = findViewById(R.id.progressBar3);
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Log.d("date: ",date);
        repos = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);

        fetchingJsonFile("https://api.github.com/search/repositories?q=created:>"+getDate()+"&sort=stars&order=desc");

        repoRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) repoRecyclerView.getLayoutManager();
                if(layoutManager.findLastCompletelyVisibleItemPosition() == repoRecyclerView.getAdapter().getItemCount()-1){
                    progressBar.setVisibility(View.VISIBLE);
                    fetchingJsonFile("https://api.github.com/search/repositories?q=created:>"+getDate()+"&sort=stars&order=desc&page="+page);
                    page++;
                }
            }
        });
    }

    private void fetchingJsonFile(String gitHubJsonFileUrl){
        //String gitHubJsonFileUrl = "https://api.github.com/search/repositories?q=created:>2017-10-22&sort=stars&order=desc";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, gitHubJsonFileUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray repoItems =  response.getJSONArray("items");
                    for(int i=0; i<repoItems.length(); i++){
                        JSONObject repo = repoItems.getJSONObject(i);

                        String owner = repo.getJSONObject("owner").getString("login");
                        String avatar_url = repo.getJSONObject("owner").getString("avatar_url");
                        String name = repo.getString("name");
                        String description = repo.getString("description");
                        int rate = repo.getInt("stargazers_count");

                        //

                        repos.add(new RepoItem(owner, name, description, avatar_url, formatedNumber(rate)));
                    }
                    progressBar.setVisibility(View.GONE);
                    repoAdapter = new RepoAdapter(MainActivity.this, repos);
                    repoRecyclerView.setAdapter(repoAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(request);
    }

    private String getDate(){
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String wantedDate = "";
        String splitedDate[] = date.split("-");
        if(Integer.valueOf(splitedDate[1]) == 01) {
            int year = Integer.valueOf(splitedDate[0]) - 1;
            wantedDate = year + "-" + splitedDate[1] + "-" + splitedDate[2];
        }else{
            int month = Integer.valueOf(splitedDate[1]);
            if(month - 1 < 10)
                wantedDate = splitedDate[0]+"-0"+(Integer.valueOf(splitedDate[1])-1)+"-"+splitedDate[2];
            else
                wantedDate = splitedDate[0]+"-"+(Integer.valueOf(splitedDate[1])-1)+"-"+splitedDate[2];
        }
        return wantedDate;
    }

    private String formatedNumber(int number){
        if(number >= 1000){
            String formatedNumber = String.valueOf(number/1000);
            formatedNumber+= "."+((number%1000)/100)+"K";
            return formatedNumber;

        }
        return String.valueOf(number);
    }
}
