package com.picture.gesturelock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 手势密码图案提示
 *
 */
public class LockIndicator extends View {
	private int numRow = 3;	// 行
	private int numColum = 3; // 列
	private int patternWidth = 40;
	private int patternHeight = 40;
	private int f = 5;
	private int g = 5;
	private int strokeWidth = 5;
	private Paint paint = null;
	private Drawable patternNoraml = null;
	private Drawable patternPressed = null;
	private String lockPassStr; // 手势密码
    private Path   mPath;

	public LockIndicator(Context paramContext) {
		super(paramContext);
	}

	public LockIndicator(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet, 0);
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStrokeWidth(strokeWidth);
		paint.setStyle(Style.STROKE);
		patternNoraml = getResources().getDrawable(R.drawable.lock_pattern_node_normal);
		patternPressed = getResources().getDrawable(R.drawable.lock_pattern_node_pressed);
		if (patternPressed != null) {
			patternWidth = patternPressed.getIntrinsicWidth();
			patternHeight = patternPressed.getIntrinsicHeight();
			this.f = (patternWidth / 2);
			this.g = (patternHeight / 2);
			patternPressed.setBounds(0, 0, patternWidth, patternHeight);
			patternNoraml.setBounds(0, 0, patternWidth, patternHeight);
		}
        mPath = new Path();
    }

	@Override
	protected void onDraw(Canvas canvas) {
		if ((patternPressed == null) || (patternNoraml == null)) {
			return;
		}

		// 绘制3*3的图标
		for (int i = 0; i < numRow; i++) {
			for (int j = 0; j < numColum; j++) {
				paint.setColor(getResources().getColor(R.color.lock_hint_bg));
				int i1 = j * patternHeight + j * this.g;
				int i2 = i * patternWidth + i * this.f;
//				if (i == 0 && j == 0) {// 当前添加为第一个
//					mPath.moveTo(lastPathX, lastPathY);
//				} else {
//					// 非第一个，将两者使用线连上
//					mPath.lineTo(lastPathX, lastPathY);
//				}
//				paint.setColor(getResources().getColor(R.color.lock_hint_bg));
//				paint.setAlpha(50);
//				canvas.drawPath(mPath, paint);
				canvas.save();
				canvas.translate(i1, i2);
				String curNum = String.valueOf(numColum * i + (j + 1));
				if (!TextUtils.isEmpty(lockPassStr)) {
					if (lockPassStr.indexOf(curNum) == -1) {
						// 未选中
						patternNoraml.draw(canvas);
					} else {
						// 被选中
						patternPressed.draw(canvas);
					}
				} else {
					// 重置状态
					patternNoraml.draw(canvas);
				}
				canvas.restore();
			}
		}
		for (int i = 0; i < list.size(); i++) {
			String coordinate = getCoordinate(list.get(i));
			if (!TextUtils.isEmpty(coordinate)) {
				String[] split = coordinate.split(",");
				int x = Integer.parseInt(split[0]);
				int y = Integer.parseInt(split[1]);
				if (i == 0) {
					// 当前添加为第一个
					mPath.moveTo(x, y);
				} else {
					// 非第一个，将两者使用线连上
					mPath.lineTo(x, y);
				}
			}
		}
		paint.setColor(getResources().getColor(R.color.lock_hint_bg));
		//						paint.setAlpha(50);
		canvas.drawPath(mPath, paint);
	}

	private String getCoordinate(int i) {
		int halfWidth = patternWidth / 2;
		int halfHeight = patternHeight / 2;
		switch (i) {
		    case 1:
				int x1 = halfWidth;
				int y1 = halfHeight;
				return x1 + "," + y1;
			case 2:
				int x2 = (patternWidth + this.f) / 2 + (patternWidth * 2 + this.f) / 2;
				int y2 = halfHeight;
				return x2 + "," + y2;
			case 3:
				int x3 = patternWidth + this.f + (patternWidth * 3 + this.f * 2 )/ 2;
				int y3 = halfHeight;
				return x3 + "," + y3;
			case 4:
				int x4 = halfWidth;
				int y4 = (patternHeight + this.g) / 2 + (patternHeight * 2 + this.g) / 2;
				return x4 + "," + y4;
			case 5:
				int x5 = (patternWidth + this.f) / 2 + (patternWidth * 2 + this.f) / 2;
				int y5 = (patternHeight + this.g) / 2 + (patternHeight * 2 + this.g) / 2;
				return x5 + "," + y5;
			case 6:
				int x6 = patternWidth + this.f + (patternWidth * 3 + this.f * 2 )/ 2;
				int y6 = (patternHeight + this.g) / 2 + (patternHeight * 2 + this.g) / 2;
				return x6 + "," + y6;
			case 7:
				int x7 = halfWidth;
				int y7 = patternHeight + this.g + (patternHeight * 3 + this.g * 2 )/ 2;
				return x7 + "," + y7;
			case 8:
				int x8 = (patternWidth + this.f) / 2 + (patternWidth * 2 + this.f) / 2;
				int y8 = patternHeight + this.g + (patternHeight * 3 + this.g * 2 )/ 2;
				return x8 + "," + y8;
			case 9:
				int x9 = patternWidth + this.f + (patternWidth * 3 + this.f * 2 )/ 2;
				int y9 = patternHeight + this.g + (patternHeight * 3 + this.g * 2 )/ 2;
				return x9 + "," + y9;
		}
		return null;
	}

	@Override
	protected void onMeasure(int paramInt1, int paramInt2) {
		if (patternPressed != null)
			setMeasuredDimension(numColum * patternHeight + this.g
					* (-1 + numColum), numRow * patternWidth + this.f
					* (-1 + numRow));
	}

	private List<Integer> list = new ArrayList<>();
	/**
	 * 请求重新绘制
	 * @param paramString 手势密码字符序列
	 */
	public void setPath(String paramString) {
		lockPassStr = paramString;
		for (int i = 0; i < paramString.length(); i++) {
			list.add(Integer.parseInt(paramString.substring(i, i + 1)));
		}
		invalidate();
	}

}