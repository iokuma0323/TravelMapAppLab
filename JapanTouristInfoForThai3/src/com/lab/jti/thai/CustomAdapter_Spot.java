package com.lab.jti.thai;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * カスタムアダプター クラス
 */
public class CustomAdapter_Spot extends ArrayAdapter<String> {

	private LayoutInflater mLayoutInflater;	// LayoutInflater

	/**
	 * コンストラクタ
	 * @param context
	 * @param textViewResourceId
	 * @param labelList
	 */
	public CustomAdapter_Spot(Context context, int textViewResourceId, ArrayList<String> labelList) {
		super(context, textViewResourceId, labelList);
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		
		ViewHolder vh;
		View v = view;

		if (v == null) { // Viewを再利用している場合は新たにViewを作らない
			mLayoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = mLayoutInflater.inflate(R.layout.list_item, null);
			TextView label = (TextView) v.findViewById(R.id.tv);
			vh = new ViewHolder();
			vh.labelText = label;
			v.setTag(vh);
		} else {
			vh = (ViewHolder) v.getTag();
		}
		String str = getItem(position); // 特定の行のデータを取得

		if (!TextUtils.isEmpty(str)) {
			vh.labelText.setText(str); // テキストビューにラベルをセット
		}

		if (position % 2 == 0) { // 行毎に背景色を変える
			vh.labelText.setBackgroundColor(Color.parseColor("#87cefa"));
		} else {
			vh.labelText.setBackgroundColor(Color.parseColor("#00bfff"));
		}
		Animation anim = AnimationUtils.loadAnimation(getContext(),R.anim.list_item_animation); // XMLで定義したアニメーションを読み込む
		v.startAnimation(anim); // リストアイテムのアニメーションを開始
		return v;
	}
// ----------------------------------------------------------------------------------------------------
	static class ViewHolder {
		TextView labelText;
	}
}