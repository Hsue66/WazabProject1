package com.ourincheon.wazap.Require;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import com.ourincheon.wazap.R;

/**
 * Created by Youngdo on 2016-02-02.
 */
public class RequireList extends AppCompatActivity{
    ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.require_activity);
        ListAdapter listAdapter = new com.ourincheon.wazap.Require.ListAdapter(this, R.layout.require_ltem);
        ListView listView = (ListView) findViewById(R.id.listView);
        ListView listView1 = (ListView) findViewById(R.id.listView1);
        scrollView = (ScrollView) findViewById(R.id.scrollView1);
        listView.setAdapter(listAdapter);
        listView1.setAdapter(listAdapter);

        setListViewHeightBasedOnChildren(listView);
        setListViewHeightBasedOnChildren(listView1);

    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
