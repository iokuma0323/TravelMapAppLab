package com.lab.jti.thai;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * カスタムビュー クラス
 */
public class CustomView extends View {

	private Paint mCirclePaint = new Paint();

	// コンストラクタ
	public CustomView(Context context) {
		super(context);
	}
	// コンストラクタ
	public CustomView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int width = getWidth();
		int height = getHeight();
		mCirclePaint.setARGB(255, 153, 204, 255);
		mCirclePaint.setStyle(Paint.Style.FILL);
		canvas.drawColor(Color.WHITE);
		canvas.drawCircle(width / 2, height / 2, 25, mCirclePaint);
	}
}