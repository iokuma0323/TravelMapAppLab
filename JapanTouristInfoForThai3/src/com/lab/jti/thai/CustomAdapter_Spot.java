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
 * �J�X�^���A�_�v�^�[ �N���X
 */
public class CustomAdapter_Spot extends ArrayAdapter<String> {

	private LayoutInflater mLayoutInflater;	// LayoutInflater

	/**
	 * �R���X�g���N�^
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

		if (v == null) { // View���ė��p���Ă���ꍇ�͐V����View�����Ȃ�
			mLayoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = mLayoutInflater.inflate(R.layout.list_item, null);
			TextView label = (TextView) v.findViewById(R.id.tv);
			vh = new ViewHolder();
			vh.labelText = label;
			v.setTag(vh);
		} else {
			vh = (ViewHolder) v.getTag();
		}
		String str = getItem(position); // ����̍s�̃f�[�^���擾

		if (!TextUtils.isEmpty(str)) {
			vh.labelText.setText(str); // �e�L�X�g�r���[�Ƀ��x�����Z�b�g
		}

		if (position % 2 == 0) { // �s���ɔw�i�F��ς���
			vh.labelText.setBackgroundColor(Color.parseColor("#87cefa"));
		} else {
			vh.labelText.setBackgroundColor(Color.parseColor("#00bfff"));
		}
		Animation anim = AnimationUtils.loadAnimation(getContext(),R.anim.list_item_animation); // XML�Œ�`�����A�j���[�V������ǂݍ���
		v.startAnimation(anim); // ���X�g�A�C�e���̃A�j���[�V�������J�n
		return v;
	}
// ----------------------------------------------------------------------------------------------------
	static class ViewHolder {
		TextView labelText;
	}
}