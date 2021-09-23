package com.example.sqlitetutorial;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CongViecAdapter extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private List<CongViec> list;

    public CongViecAdapter(MainActivity context, int layout, List<CongViec> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder{
        TextView txtTen;
        ImageView imgDelete, imgEdit;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView  = layoutInflater.inflate(layout,null);
            viewHolder.txtTen = convertView.findViewById(R.id.txtTen);
            viewHolder.imgDelete = convertView.findViewById(R.id.imgDelete);
            viewHolder.imgEdit = convertView.findViewById(R.id.imgEdit);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CongViec congViec = list.get(position);

        viewHolder.txtTen.setText(congViec.getTenCongViec());
        viewHolder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.showDialogSua(congViec.getTenCongViec(),congViec.getIdCv());
            }
        });

        viewHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.showDialogXoa(congViec.getTenCongViec(), congViec.getIdCv());
            }
        });
        return convertView;
    }
}
