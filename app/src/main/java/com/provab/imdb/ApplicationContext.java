package com.provab.imdb;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ApplicationContext extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext())

		.build();
		ImageLoader.getInstance().init(config);
	}

}
