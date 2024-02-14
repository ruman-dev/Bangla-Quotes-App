package com.rumanweb.banglaquotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ImageView headerImage;
    CardView cardView;
    GridView gridView;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    HashMap<String, String> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        headerImage = findViewById(R.id.headerImage);
        gridView = findViewById(R.id.gridView);

        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.primaryColor));

        InsertData();

        MyAdapter adapter = new MyAdapter();
        gridView.setAdapter(adapter);
    }

    public class MyAdapter extends BaseAdapter {

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

                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.layout_item, parent, false);
            }
            ImageView itemImage = convertView.findViewById(R.id.ItemImage);
            TextView itemText = convertView.findViewById(R.id.ItemText);
            CardView cardView = convertView.findViewById(R.id.cardView);

            HashMap<String, String> hashMap = arrayList.get(position);
            String title = hashMap.get("title");
            String imageView = hashMap.get("imageUrl");

            itemText.setText(title);
            assert imageView != null;
            itemImage.setImageResource(Integer.parseInt(imageView));
            Picasso.get().load("https://is1-ssl.mzstatic.com/image/thumb/Purple116/v4/2a/43/2a/2a432a43-ac93-7437-d2db-6ba735f7a5fa/AppIcon-0-0-1x_U007emarketing-0-0-0-10-0-0-sRGB-0-0-0-GLES2_U002c0-512MB-85-220-0-0.png/256x256bb.jpg"
            ).into(headerImage);


            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, StatusActivity.class);
                    startActivity(intent);
                    //Toast.makeText(MainActivity.this, title, Toast.LENGTH_SHORT).show();
                }
            });
            return convertView;
        }
    }

    private void InsertData() {
        hashMap = new HashMap<>();
        hashMap.put("imageUrl", String.valueOf(R.drawable.positive_thinking));
        hashMap.put("title", "এটিটিউড স্ট্যাটাস");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("imageUrl", String.valueOf(R.drawable.sad));
        hashMap.put("title", "দুঃখ-কষ্ট স্ট্যাটাস");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("imageUrl", String.valueOf(R.drawable.surprised));
        hashMap.put("title", "অসাধারণ স্ট্যাটাস");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("imageUrl", String.valueOf(R.drawable.child));
        hashMap.put("title", "একাকিত্ব স্ট্যাটাস");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("imageUrl", String.valueOf(R.drawable.heart));
        hashMap.put("title", "ভালোবাসার স্ট্যাটাস");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("imageUrl", String.valueOf(R.drawable.education));
        hashMap.put("title", "শিক্ষামূলক স্ট্যাটাস");
        arrayList.add(hashMap);
    }
}