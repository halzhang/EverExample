package com.halzhang.android.example.databindingexample;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Hal on 2016/3/3.
 */
public class Bindings {


    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }

}
