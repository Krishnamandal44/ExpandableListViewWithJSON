package com.example.mandal.krishna.expandablelistviewwithjson;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class Category extends AppCompatActivity {
    private ExpandableListView expandableListView;
    private ArrayList<CategoryPojo> categoryPojos = new ArrayList<CategoryPojo>();
    private List<SubCategoryPojo> subCategoryPojos = new ArrayList<SubCategoryPojo>();
    private ExpandableListAdapter expandableAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
//        loadSubCategoryFromServer();
        loadSubCategoryFromServerUseJSONvolley(); ///******* if you use JSON volley  for load JSON url **********//////
        loadCategoryFromServer();

        initializeView();
        setListener();
    }


    private void initializeView() {
        expandableListView = (ExpandableListView) findViewById(R.id.expandable_list_view);
        expandableAdapter = new CategoryAdapter(this, categoryPojos);
        expandableListView.setAdapter(expandableAdapter);
    }
    private void setListener() {
        // This listener will expand one group at one time
        // You can remove this listener for expanding all groups
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if ((previousGroup != -1) && (groupPosition != previousGroup)) {
                    expandableListView.collapseGroup(previousGroup);
                }
                previousGroup = groupPosition;
            }
        });
        // This listener will show toast on child click
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView listview, View view,
                                        int groupPos, int childPos, long id) {
                //get the group header
                CategoryPojo headerInfo = categoryPojos.get(groupPos);
                //get the child info
                SubCategoryPojo detailInfo =  headerInfo.getSubCategoryPojos().get(childPos);
                //display it or do something with it
                Toast.makeText(getBaseContext(), "" + headerInfo.getTitle()
                        + "/" + detailInfo.getTitle(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        // This listener will show toast on group click
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView listview, View view,
                                        int group_pos, long id) {
                //get the group header
                CategoryPojo headerInfo = categoryPojos.get(group_pos);
                //display it or do something with it
                Toast.makeText(getBaseContext(), "" + headerInfo.getTitle(),
                        Toast.LENGTH_SHORT).show();

                return false;
            }
        });

    }

    private void loadCategoryFromServer() {
        IonConnector ionConnector = IonConnector.getInstance();
        try {
            JsonObject result = ionConnector.getJson(this,"https://api.myjson.com/bins/cm5i9");
            JSONObject resultJSONAsString = new JSONObject(result.toString());
            if (resultJSONAsString.getString("result").equals("success")){
//                JSONArray navBarJSONArray = resultJSONAsString.getJSONArray("top_nav_bar");
                Gson gson = new Gson();
                ArrayList<JsonObject> jsonObjects = gson.fromJson(result.get("catgory"),
                        new TypeToken<ArrayList<JsonObject>>(){}.getType());

                for (JsonObject jsonObject: jsonObjects) {
                    JSONObject categoryJSONObject = new JSONObject(jsonObject.toString());
                    String title = categoryJSONObject.getString("title");
                    String id = categoryJSONObject.getString("id");
                    CategoryPojo categoryName=new CategoryPojo(id,title,subCategoryPojos);
                    categoryPojos.add(categoryName);
                }
            }
        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadSubCategoryFromServer() {
        IonConnector ionConnector = IonConnector.getInstance();
        try {
            JsonObject result = ionConnector.getJson(this,"https://api.myjson.com/bins/a8f35");
            JSONObject resultJSONAsString = new JSONObject(result.toString());
            if (resultJSONAsString.getString("result").equals("success")){
//                JSONArray navBarJSONArray = resultJSONAsString.getJSONArray("top_nav_bar");
                Gson gson = new Gson();
                ArrayList<JsonObject> jsonObjects = gson.fromJson(result.get("sub_catgory"),
                        new TypeToken<ArrayList<JsonObject>>(){}.getType());
                for (JsonObject jsonObject: jsonObjects) {
                    JSONObject subCategoryJSONObject = new JSONObject(jsonObject.toString());
                    String title = subCategoryJSONObject.getString("title");
                    String id = subCategoryJSONObject.getString("id");
                    subCategoryPojos.add(new SubCategoryPojo(title,id));
                }
            }
        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
        }
    }
    ////////////******* if you use JSON volley  for load JSON url **********//////////
    private void loadSubCategoryFromServerUseJSONvolley(){
        String url="https://api.myjson.com/bins/a8f35";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("krishna", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("result").equals("success")) {
                        JSONArray jsonArray = jsonObject.optJSONArray("sub_catgory");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObjectSubCategory = jsonArray.optJSONObject(i);
                            String title = jsonObjectSubCategory.getString("title");
                            String id = jsonObjectSubCategory.getString("id");
                            SubCategoryPojo subCategoryPojo = new SubCategoryPojo(title, id);
                            subCategoryPojos.add(subCategoryPojo);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(Category.this);
        requestQueue.add(stringRequest);
    }
}
