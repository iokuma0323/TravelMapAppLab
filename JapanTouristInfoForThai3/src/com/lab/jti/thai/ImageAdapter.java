package com.lab.jti.thai;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

/**
 * イメージアダプタークラス
 */
public class ImageAdapter extends ArrayAdapter<Integer> {

	private LayoutInflater mLayoutInflater; // LayoutInflater

	/**
	 * コンストラクタ
	 * @param context
	 * @param objects
	 */
	public ImageAdapter(Context context, Integer[] objects) {
		super(context, 0, objects);
		mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.gallery_item, parent, false);
		}
		imageView = (ImageView) convertView.findViewById(R.id.gallery_image_view);
		imageView.setImageResource(getItem(position));
		return convertView;
	}
}