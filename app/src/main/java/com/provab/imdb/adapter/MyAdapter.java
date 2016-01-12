package com.provab.imdb.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.provab.imdb.R;
import com.provab.imdb.model.MovieDetails;

import java.util.ArrayList;

/**
 * Created by gaurav.0012 on 1/12/2016.
 */
public class MyAdapter extends BaseAdapter {

    ArrayList<MovieDetails> al;
    Context con;
    LayoutInflater inflater;
    DisplayImageOptions displayImageOptions;

    public MyAdapter(Context con, ArrayList<MovieDetails> al) {
        this.con = con;
        this.al = al;
        inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        displayImageOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
    }

    @Override
    public int getCount() {
        return al.size();
    }

    @Override
    public Object getItem(int position) {
        return al.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_layout, parent, false);
        }
        TextView tv_title = (TextView) convertView.findViewById(R.id.tv_title);
        TextView tv_type = (TextView) convertView.findViewById(R.id.tv_type);
        TextView tv_year = (TextView) convertView.findViewById(R.id.tv_year);
        ImageView iv_poster = (ImageView) convertView.findViewById(R.id.iv_poster);

        tv_title.setText(al.get(position).getTitle());
        tv_type.setText(al.get(position).getType());
        tv_year.setText(al.get(position).getYear());
        showImage(al.get(position).getPoster(), iv_poster);
        return convertView;
    }

    public void showImage(String url, ImageView iv) {
        ImageLoader loader = ImageLoader.getInstance();
        loader.displayImage(url,
                iv, displayImageOptions, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri,
                                                 View view) {
                    }

                    @Override
                    public void onLoadingFailed(String imageUri,
                                                View view, FailReason failReason) {
                        String message = null;

                        switch (failReason.getType()) {
                            case IO_ERROR:
                                message = "Input/Output error";
                                break;
                            case DECODING_ERROR:
                                message = "Image can't be decoded";
                                break;
                            case NETWORK_DENIED:
                                message = "Downloads are denied";
                                break;
                            case OUT_OF_MEMORY:
                                message = "Out Of Memory error";
                                break;
                            case UNKNOWN:
                                message = "Unknown error";
                                break;
                        }
                    }

                    @Override
                    public void onLoadingComplete(String imageUri,
                                                  View view, Bitmap loadedImage) {

                    }
                });

    }


}
