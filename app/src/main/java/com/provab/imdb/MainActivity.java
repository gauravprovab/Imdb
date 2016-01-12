package com.provab.imdb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.provab.imdb.adapter.MyAdapter;
import com.provab.imdb.controller.WebServiceController;
import com.provab.imdb.interfaces.WebInterface;
import com.provab.imdb.model.MovieDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements WebInterface {

    private EditText etSearch;
    private ImageView ivSearch;
    private ListView lv;
    ArrayList<MovieDetails> al;
    MyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
    }

    private void findViews() {
        etSearch = (EditText) findViewById(R.id.et_search);
        ivSearch = (ImageView) findViewById(R.id.iv_search);
        lv = (ListView) findViewById(R.id.lv);
        al=new ArrayList<>();
        adapter=new MyAdapter(getApplicationContext(),al);
        lv.setAdapter(adapter);

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = etSearch.getText().toString();
                if (!key.equals("")) {
                    String link = "http://www.omdbapi.com/";
                    RequestParams params = new RequestParams();
                    params.put("s", key);
                    WebServiceController controller = new WebServiceController(MainActivity.this, MainActivity.this);
                    controller.sendRequest(link, params, 0);
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter the keyword to search !!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void getResponse(String response, int flag) {
        al.clear();
//        "Title":"The Lord of the Rings: The Fellowship of the Ring",
//                "Year":"2001",
//                "imdbID":"tt0120737",
//                "Type":"movie",
//                "Poster":
//        "http://ia.media-imdb.com/images/M/MV5BNTEyMjAwMDU1OV5BMl5BanBnXkFtZTcwNDQyNTkxMw@@._V1_SX300.jpg"
        try {
            JSONObject jo = new JSONObject(response);
            JSONArray ja = jo.getJSONArray("Search");
            for (int i = 0; i < ja.length(); i++) {
                JSONObject index = ja.getJSONObject(i);
                MovieDetails bean = new MovieDetails();
                bean.setId(index.getString("imdbID"));
                bean.setTitle(index.getString("Title"));
                bean.setPoster(index.getString("Poster"));
                bean.setYear(index.getString("Year"));
                bean.setType(index.getString("Type"));
                al.add(bean);
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
