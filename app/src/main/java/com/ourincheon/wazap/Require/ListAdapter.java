package com.ourincheon.wazap.Require;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ourincheon.wazap.R;

/**
 * Created by Youngdo on 2016-02-18.
 */
public class ListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    TextView dday, title, cate, man;
    ImageView small;
    Button bt;

    public ListAdapter(Activity context, int resource) {
        super(context, resource);
        this.context = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.require_ltem, null, true);
        dday = (TextView) rowView.findViewById(R.id.dday);
        title = (TextView) rowView.findViewById(R.id.title);
        cate = (TextView) rowView.findViewById(R.id.cate);
        man = (TextView) rowView.findViewById(R.id.man);
        bt = (Button) rowView.findViewById(R.id.require);
        small = (ImageView) rowView.findViewById(R.id.imageView5);

        dday.setText("D-14");
        title.setText("[서울] 한화생명 보험 아이디어 공모전");
        cate.setText("영상/ucc/사진");
        man.setText("모집인원 5명");
        return rowView;
    }

    @Override
    public int getCount() {
        return 5;
    }
}
