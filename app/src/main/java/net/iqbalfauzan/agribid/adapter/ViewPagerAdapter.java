package net.iqbalfauzan.agribid.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import net.iqbalfauzan.agribid.R;

public class ViewPagerAdapter extends PagerAdapter {
    private Activity context;
    private String[] images;

    public ViewPagerAdapter(Activity context, String[] images) {
        this.context = context;
        this.images = images;
    }


    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater)context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.viewpageritem, container, false);
        ImageView imageView = view.findViewById(R.id.imageSlide);
        DisplayMetrics dis = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dis);
        int height = dis.heightPixels;
        int width = dis.widthPixels;
        imageView.setMinimumHeight(height);
        imageView.setMinimumWidth(width);

        Glide.with(context).load(images[position]).into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((View)object);
    }
}
