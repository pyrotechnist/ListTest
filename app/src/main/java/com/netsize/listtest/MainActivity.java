package com.netsize.listtest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String[] values = new String[] { "Android", "iPhone", "WindowsMobile"};
               /* "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile" };*/

        final ListView listView = (ListView) findViewById(R.id.listview);

        final List<String> list = new ArrayList<>();

        Collections.addAll(list,values);


        final ArrayAdapter adapter =  new MySimpleArrayAdapter(this,values); //new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                view.animate().setDuration(2000).alpha(0)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                list.remove(item);
                                adapter.notifyDataSetChanged();
                                view.setAlpha(1);
                            }
                        });
            }

        });


    }

    private class MySimpleArrayAdapter  extends ArrayAdapter<String> {

        private final Context context;
        private final String[] values;


        public MySimpleArrayAdapter (Context context,
                                     String[] objects) {
            super(context, -1, objects);
            this.context = context;
            this.values = objects;

        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            View rowView = convertView;
            if (rowView == null) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                rowView = inflater.inflate(R.layout.item, viewGroup, false);
            }

            ImageView image = (ImageView) rowView.findViewById(R.id.pic);

            TextView text = (TextView) rowView.findViewById(R.id.desc);

            text.setText(values[position]);


            String s = values[position];
            if (s.startsWith("iPhone")) {
                image.setImageResource(android.R.drawable.arrow_down_float);
            } else {
                image.setImageResource(android.R.drawable.arrow_up_float);
            }

            return rowView;
        }
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
}
