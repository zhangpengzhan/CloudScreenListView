package com.example.bitmapdemo;

import java.io.IOException;
import java.io.InputStream;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

/**
 * @ClassName: CouldScreenImageView
 * @Description: 用做luancher 北京更换的imageview
 * @author: zhangpengzhan
 * @date 2015年4月13日 下午4:33:31
 * 
 */
@SuppressLint("NewApi")
public class CouldScreenImageView extends ImageView {
	/**
	 * 淡入动画
	 * @Fields inAnimator
	 */
	ValueAnimator inAnimator;
	/**
	 * 淡出动画
	 * @Fields outAnimator
	 */
	ValueAnimator outAnimator;
	/**
	 * 上下文
	 * @Fields mContext
	 */
	private Context mContext;
	/**
	 * 动画持续时间
	 * @Fields Duration
	 */
	long Duration = 1000;
	/**
	 * 是否执行动画
	 * 默认不开启动画
	 * @Fields isAnim
	 */
	private boolean isAnim = false;
	/**
	 * @Fields am
	 */
	private AssetManager am;

	public CouldScreenImageView(Context context) {
		super(context);
		this.mContext = context;
		initAnimator();
	}

	public CouldScreenImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		initAnimator();
	}

	public CouldScreenImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
		initAnimator();
	}

	/**
	 * @Title: CouldScreenImageView
	 * @author:张鹏展
	 * @Description: 初始哈一下动画内容,同时设置一个黑色的背景
	 */
	private void initAnimator() {
		this.setBackgroundColor(Color.BLACK);
		inAnimator = ObjectAnimator.ofFloat(this, "alpha", 1, 0.5f);// 淡出效果
		inAnimator.setDuration(Duration);
		inAnimator.setInterpolator(new AccelerateInterpolator());
		outAnimator = ObjectAnimator.ofFloat(this, "alpha", 0, 1);// 淡出效果
		outAnimator.setDuration(Duration);
		outAnimator.setInterpolator(new AccelerateInterpolator());
	}

	/**
	 * @Title: CouldScreenImageView
	 * @author:张鹏展
	 * @Description: 设置淡入动画
	 * @param inAnimator
	 */
	public void setInAnimator(ValueAnimator inAnimator) {
		this.inAnimator = inAnimator;
	}

	/**
	 * @Title: CouldScreenImageView
	 * @author:张鹏展
	 * @Description: 得到淡入动画
	 * @return
	 */
	public ValueAnimator getInAnimator() {
		return inAnimator;
	}

	/**
	 * @Title: CouldScreenImageView
	 * @author:张鹏展
	 * @Description: 设置淡出动画
	 * @param outAnimator
	 */
	public void setOutAnimator(ValueAnimator outAnimator) {
		this.outAnimator = outAnimator;
	}

	/**
	 * @Title: CouldScreenImageView
	 * @author:张鹏展
	 * @Description: 设置淡出动画
	 * @return
	 */
	public ValueAnimator getOutAnimator() {
		return outAnimator;
	}

	/**
	 * @Title: CouldScreenImageView
	 * @author:张鹏展
	 * @Description: 设置动画持续时长
	 * @param duration
	 */
	public void setDuration(long duration) {
		Duration = duration;
		if (null != inAnimator) {
			inAnimator.setDuration(Duration);
		}
		if (null != outAnimator) {
			outAnimator.setDuration(Duration);
		}
	}

	/**
	 * @Title: CouldScreenImageView
	 * @author:张鹏展
	 * @Description: 是否开启动画结果
	 * @return
	 */
	public boolean isAnim() {
		return isAnim;
	}

	/**
	 * @Title: CouldScreenImageView
	 * @author:张鹏展
	 * @Description: 设置是否开启动画效果
	 * @param isAnim
	 */
	public void setIsAnim(boolean isAnim) {
		this.isAnim = isAnim;
	}
	
	/**
	 * @Title: CouldScreenImageView
	 * @author:张鹏展
	 * @Description: 加载asset 中的图片
	 * @param arg1
	 */
	public void setImage(String arg1) {
		try {
			if (null == am) {
				am = mContext.getAssets();
			}
			InputStream is = am.open(arg1);
			Bitmap bitmap = CloudScreenBitmapFactory.decodeStreamFromColudSvreenOptions(is);
			setImageBitmap(bitmap);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 在设置动画bitmap时候执行相关动画
	 * 
	 *  (non-Javadoc)
	 * @see android.widget.ImageView#setImageBitmap(android.graphics.Bitmap)
	 */
	@Override
	public void setImageBitmap(final Bitmap bm) {
		if (isAnim) {
			inAnimator.start();
		}
		postDelayed(new Runnable() {
			@Override
			public void run() {
				setImageDrawable(new BitmapDrawable(mContext.getResources(), bm));
				if (isAnim) {
					outAnimator.start();
				}
			}
		}, isAnim ? Duration : 1);

	}

}
