package com.example.p;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private String API_KEY = "2ea5d9cc64d24d58b2a55835232510";
    private RequestQueue mQueue;
    ImageView icon;
    TextView condition, temp, city;
    List<Weather> wea;
    WeatherAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQueue = Volley.newRequestQueue(this);
        icon = findViewById(R.id.icon);
        condition = findViewById(R.id.condition);
        temp = findViewById(R.id.temp);
        city = findViewById(R.id.city);
        recyclerView = findViewById(R.id.list);
        wea = new ArrayList<>();

        getData();
    }

    public void getData() {
        String URL = "https://api.weatherapi.com/v1/forecast.json?key=" + API_KEY + "&q=London&days=7&aqi=no&alerts=no";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("mylog222222", "success");
                            JSONObject location = response.getJSONObject("location");
                            String name = location.getString("name");
                            JSONObject current = response.getJSONObject("current");
                            JSONObject cond = current.getJSONObject("condition");
                            String text = cond.getString("text");
                            String url = "https:" + cond.getString("icon");
                            Picasso.get().load(url).fit().centerInside().into(icon);
                            condition.setText(text);
                            String temp_c = current.getString("temp_c");
                            temp.setText(temp_c);
                            city.setText(name);
                            JSONObject forecast = response.getJSONObject("forecast");
                            JSONArray forecastDay = forecast.getJSONArray("forecastday");

                            for (int i = 0; i < forecastDay.length(); i++) {
                                JSONObject forecastDayElement = forecastDay.getJSONObject(i);
                                String date = forecastDayElement.getString("date");
                                JSONObject day = forecastDayElement.getJSONObject("day");
                                JSONObject condition = day.getJSONObject("condition");
                                url = "https:" + condition.getString("icon");
                                String maxTempC = day.getString("maxtemp_c");
                                wea.add(new Weather(date, maxTempC, url));
                            }
                            adapter = new WeatherAdapter(MainActivity.this, wea);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            Log.d("mylog222222", "error");
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("mylog222222", error.getMessage());
                    }
                });

        mQueue.add(request);
    }
}
