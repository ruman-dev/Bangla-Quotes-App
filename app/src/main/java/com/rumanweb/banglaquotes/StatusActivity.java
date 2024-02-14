package com.rumanweb.banglaquotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class StatusActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    HashMap<String, String> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.primaryColor));

        listView = findViewById(R.id.statusListView);


        String url = "https://dummyjson.com/quotes";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("quotes");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String quote = jsonObject.getString("quote");
                        String author = jsonObject.getString("author");
                        hashMap = new HashMap<>();
                        hashMap.put("quote", quote);
                        hashMap.put("author", author);
                        arrayList.add(hashMap);
                    }


                    CustomAdapter adapter = new CustomAdapter();
                    listView.setAdapter(adapter);

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

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    public class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.status_list_item, parent, false);
            }

            TextView statusText = convertView.findViewById(R.id.statusText);
            TextView authorText = convertView.findViewById(R.id.author);
            LinearLayout shareLayout = convertView.findViewById(R.id.shareLayout);
            LinearLayout copyLayout = convertView.findViewById(R.id.copyLayout);

            HashMap<String, String> hashMap = arrayList.get(position);
            String quote = hashMap.get("quote");
            String author = hashMap.get("author");

            statusText.setText(quote);
            authorText.setText("- "+author);

            shareLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, quote + "\n\n- " + author);
                    startActivity(Intent.createChooser(shareIntent, "Share Via"));
                }
            });

            copyLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("quote", quote + "\n\n- " + author);
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(StatusActivity.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
                }
            });
            return convertView;
        }
    }
}