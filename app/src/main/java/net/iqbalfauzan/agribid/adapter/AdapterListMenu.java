package net.iqbalfauzan.agribid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.iqbalfauzan.agribid.R;

import java.util.List;

public class AdapterListMenu extends BaseAdapter {
    Context context;
    List<String> titles;
    List<Integer> gambars;

    public AdapterListMenu(Context context, List<String> titles, List<Integer> gambars) {
        this.context = context;
        this.titles = titles;
        this.gambars = gambars;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    private static LayoutInflater inflater = null;

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class Holder
    {
        TextView textTitle;
        ImageView imgTitle;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder = new Holder();
        final View rowView;
        rowView = inflater.inflate(R.layout.list_item_atur,null);
        holder.imgTitle = (ImageView)rowView.findViewById(R.id.gambarKiri);
        holder.textTitle = (TextView)rowView.findViewById(R.id.tvAtur);
        holder.imgTitle.setImageResource(gambars.get(i));
        holder.textTitle.setText(titles.get(i));
        return rowView;
    }
}
