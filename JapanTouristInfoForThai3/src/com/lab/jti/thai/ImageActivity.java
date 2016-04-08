package com.lab.jti.thai;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ImageView;

@SuppressWarnings("deprecation")
public class ImageActivity extends Activity {
	private ImageView mImageView;
	private Integer[] mThumbIds_Dummy = { R.drawable.train_mapt1,
			R.drawable.train_mapt2 };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image);
		mImageView = (ImageView) findViewById(R.id.image_view);
		setGallery();
		mImageView.setBackgroundResource(mThumbIds_Dummy[0]);
	}

	public void setGallery() {
		Gallery g = (Gallery) findViewById(R.id.gallery);
		g.setAdapter(new ImageAdapter(this, mThumbIds_Dummy));

		g.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				// Toast.makeText(ImageActivity.this, "" + arg2, Toast.LENGTH_SHORT).show();
				mImageView.setBackgroundResource(mThumbIds_Dummy[arg2]);
			}
		});
	}

	public void setImageBitmap(Bitmap bmp) {
		mImageView.setImageBitmap(bmp);
		mImageView.setBackgroundResource(mThumbIds_Dummy[0]);
	}
}