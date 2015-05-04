package com.example.bitmapdemo;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import android.widget.RelativeLayout;

/**
 * @ClassName: CloudScreenLinearSelector
 * @Description: 背景的选择器
 * 仅仅用于横版的容器
 * @author: zhangpengzhan
 * @date 2015年4月20日 下午12:00:06
 * 
 */
@SuppressLint("NewApi")
public class CloudScreenLinearSelector extends RelativeLayout {
	static String TAG = CloudScreenLinearSelector.class.getSimpleName();
	/**
	 * activity 上下文环境
	 * 
	 * @Fields mContext
	 */
	protected Context mContext;

	/**
	 * 当前View的宽 高
	 * 
	 * @Fields cloudHeight
	 */
	private int cloudWidth, cloudHeight;

	/**
	 * item view的宽高
	 * 
	 * @Fields itemWidth
	 */
	private int itemWidth = 1, itemHeight = 1;

	/**
	 * 适配器
	 * 
	 * @Fields baseAdapter
	 */
	private BaseAdapter baseAdapter;


	/**
	 * item 总数
	 * 
	 * @Fields allItems
	 */
	private int allItems;

	/**
	 * 当前View的index
	 * 
	 * @Fields viewIndex
	 */
	private int viewIndex = 0;

	/**
	 * 第一次加载应该获取focus的View的索引
	 * 
	 * @Fields viewFirstIndex
	 */
	private int viewFirstIndex = 0;

	/**
	 * 整体View 开始滑动的标志位
	 * 
	 * @Fields viewStandIndex
	 */
	private int viewStandIndex = 0;

	protected ViewGroup viewGroup;

	/**
	 * @Fields cloudViewScoller
	 */
	private OverScroller cloudViewScoller;

	/**
	 * 在動畫的狀態
	 *
	 * @Fields isAimStatus
	 */
	private boolean isScrollStatus = false;

	/**
	 * 焦點在變化的狀態
	 * 
	 * @Fields isFocusChangeStatus
	 */
	private boolean isFocusChangeStatus = false;

	/**
	 * 焦点view 与 实际要获取焦点view的大小的偏差
	 * 
	 * @Fields deviationX
	 */
	private int deviationW, deviationH;


	/**
	 * focus view attribute
	 * 
	 * @Fields theFocusViewWidth
	 */
	private int theFocusViewWidth;
	private int theFocusViewHeight;
	private float theFocusViewX;
	private float theFocusViewY;
	private View theFocusView;
	private int theFocusViewContinue = 300;
	/**
	 * x 和 y 轴上的偏差
	 * @Fields deviationX
	 */
	private float deviationX = 40,deviationY = 20;
	/**
	 * 焦点view
	 * 
	 * @Fields focusImageView
	 */
	private FocusView focusImageView;
	/**
	 * @Fields liearLayout
	 */
	private CloudScreenLinearLayout liearLayout;
	
	/**
	 * content the child
	 * @Fields mViewGroupLinearLayout
	 */
	private LinearLayout mViewGroupLinearLayout;
	/**
	 * focus view move tag
	 * 
	 * @Fields FOCUS_VIEW_MOVE
	 */
	protected final static int FOCUS_VIEW_MOVE = 0x000189;

	/**
	 * 为了保证setAdapter 线程安全 在handler中刷新view
	 * 
	 * @Fields SCREENINVALIDATE
	 */
	protected final static int SCREEN_INVALIDATE = 0x001245;

	/**
	 * 选择监听
	 * 
	 * @Fields mItemSelectLinstener
	 */
	private ItemSelectLinstener mItemSelectLinstener;
	
	/**
	 * 同上
	 * @Fields theItemSelectListener
	 */
	private TheItemSelectListener theItemSelectListener;
	/**
	 * 光标选中设置的第一个view
	 * @Fields SELECT_FIRST_VIEW
	 */
	protected final static int SELECT_FIRST_VIEW = 0x000598;
	
	/**
	 * 向左还是向右的状态
	 * @Fields mDispathKayStatus
	 */
	private DispathKayStatus mDispathKayStatus;
	
	/**
	 * view group click listener
	 * @Fields mLinearSelectorOnClickListener
	 */
	private LinearSelectorOnClickListener linearSelectorOnClickListener;
	
	/**
	 * 焦点图能走到屏幕最右侧的位置与屏幕宽之间的差值
	 * @Fields theFocusViewDeviatonRight
	 */
	private float theFocusViewDeviatonRight = 0;
	
	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description: 
	 * @param theFocusViewDeviatonRight
	 */
	public void setTheFocusViewDeviatonRight(float theFocusViewDeviatonRight) {
		this.theFocusViewDeviatonRight = theFocusViewDeviatonRight;
	}
	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description: 
	 * @return
	 */
	public float getTheFocusViewDeviatonRight() {
		return theFocusViewDeviatonRight;
	}
	
	/**
	 * 焦点图的初始化偏移量
	 * @Fields theFocusViewDeviationX
	 */
	private float theFocusViewDeviationX = 0;
	
	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description: 焦点图的初始化偏移量
	 * @param theFocusViewDeviationX
	 */
	public void setTheFocusViewDeviationX(float theFocusViewDeviationX) {
		this.theFocusViewDeviationX = theFocusViewDeviationX;
	}
	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description: 焦点图的初始化偏移量
	 * @return
	 */
	public float getTheFocusViewDeviationX() {
		return theFocusViewDeviationX;
	}

	public CloudScreenLinearSelector(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
		init();
	}

	public CloudScreenLinearSelector(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		init();
	}

	public CloudScreenLinearSelector(Context context) {
		super(context);
		this.mContext = context;
		init();
	}

	/*
	 * 计算View的宽高 (non-Javadoc)
	 * 
	 * @see android.widget.LinearLayout#onMeasure(int, int)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setCloudHeight(getHeight());
		cloudWidth = getWidth();
		//Log.d(TAG, "cloudHeight::" + cloudHeight + "|cloudWidth::" + cloudWidth);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	/*
	 * View的绘制 
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		// The layout has actually already been performed and the positions
		// cached. Apply the cached values to the children.
		if (viewStandIndex == 0) {
			int count = mViewGroupLinearLayout.getChildCount();
			Log.d(TAG, "count::" + count);
			int itemAllInScreen = 0;
			for (int i = 0; i < count; i++) {
				View child = mViewGroupLinearLayout.getChildAt(i);
				if (child.getVisibility() != GONE) {
					setItemHeight(child.getHeight());
					itemWidth = child.getWidth();
					itemAllInScreen += itemWidth;
					if (itemAllInScreen <= cloudWidth)
						viewStandIndex++;
					Log.d(TAG, "viewStandIndex::" + viewStandIndex);
				}
			}
		}
	}

	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description: 焦点view 动画持续时间
	 * @param theFocusViewContinue
	 */
	public void setTheFocusViewContinue(int theFocusViewContinue) {
		this.theFocusViewContinue = theFocusViewContinue;
	}

	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description: 焦点view 动画持续的时间
	 * @return
	 */
	public int getTheFocusViewContinue() {
		return theFocusViewContinue;
	}
	
	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description: 焦点图的x轴偏差
	 * @param deviationX
	 */
	public void setDeviationX(float deviationX) {
		this.deviationX = deviationX;
	}
	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description: 焦点图的x轴偏差
	 * @return
	 */
	public float getDeviationX() {
		return deviationX;
	}
	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description: 焦点图的y轴偏差
	 * @param deviationY
	 */
	public void setDeviationY(float deviationY) {
		this.deviationY = deviationY;
	}
	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description: 焦点图的y轴偏差
	 * @return
	 */
	public float getDeviationY() {
		return deviationY;
	}

	/**
	 * @ClassName: ItemFocusListener
	 * @Description: item view the focus listener
	 * @author: zhangpengzhan
	 * @date 2015年4月21日 上午10:28:10
	 * 
	 */
	class ItemFocusListener implements View.OnFocusChangeListener {

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if (hasFocus) {
				isFocusChangeStatus = false;
				theFocusViewHeight = v.getHeight();
				theFocusViewWidth = v.getWidth();
				theFocusViewX = v.getX();
				setTheFocusViewY(v.getY());
				mHandler.sendEmptyMessage(FOCUS_VIEW_MOVE);
			} else {

			}
		}

	}
	
	class TheItemSelectListener implements ItemSelectLinstener{

		@Override
		public void select(int itemIndex, View v) {
			isFocusChangeStatus = false;
			theFocusViewHeight = v.getHeight();
			theFocusViewWidth = v.getWidth();
			theFocusViewX = v.getX();
			setTheFocusViewY(v.getY());
			mHandler.sendEmptyMessage(FOCUS_VIEW_MOVE);
			
		}
		
	}

	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description: 初始化相关信息
	 */
	private void init() {
		try {
			viewGroup = this;
			cloudViewScoller = new OverScroller(mContext, new OvershootInterpolator());
			focusImageView = new FocusView(mContext);
			focusImageView.setBackgroundResource(R.drawable.focus);
			liearLayout = new CloudScreenLinearLayout(mContext);
			liearLayout.setHorizontalScrollBarEnabled(false);
			mViewGroupLinearLayout = new LinearLayout(mContext);
			theItemSelectListener = new TheItemSelectListener();
			mDispathKayStatus = DispathKayStatus.RIGHT;
			this.setOnClickListener( new CSLinearListener());
			setFocusable(true);
			setFocusableInTouchMode(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	class CSLinearListener implements  OnClickListener {

		@Override
		public void onClick(View v) {
			if(null != linearSelectorOnClickListener){
				linearSelectorOnClickListener.onClick(theFocusView);
			}
		}
		
	}
	

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
	
		Log.d(TAG, "keyCode==dispatchKeyEvent" + event.getKeyCode() + "|" + KeyEvent.KEYCODE_DPAD_RIGHT);
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			if (chackFocusMoveStatus()) {
				return true;
			}
			// 把消息发送到handler里边
			mHandler.sendEmptyMessage(event.getKeyCode());
		}
		return super.dispatchKeyEvent(event);
	}


	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description: 檢查焦點的活動狀態 是否是按下按鍵需要改變的狀態
	 * @return
	 */
	public boolean chackFocusMoveStatus() {
		Log.d(TAG, "	isFocusChangeStatus::" + isFocusChangeStatus);
		Log.d(TAG, "	isScrollStatus::" + isScrollStatus);
		return (isScrollStatus ||isFocusChangeStatus);
	}

	/**
	 * 事件处理
	 * 
	 * @Fields mHandler
	 */
	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
			super.dispatchMessage(msg);
			switch (msg.what) {
			case KeyEvent.KEYCODE_DPAD_LEFT:
				viewIndex--;
				if (viewIndex < 0) {
					viewIndex = 0;
					break;
				}
				mDispathKayStatus = DispathKayStatus.LEFT;
				try {
					setItemRequestFocus(viewIndex);
				} catch (Exception e) {
					e.printStackTrace();
					viewIndex++;
				}
				break;
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				viewIndex++;
				Log.d(TAG, "1::viewIndex::" + viewIndex);
				if (viewIndex >= allItems) {
					viewIndex = allItems - 1;
					break;
				}
				mDispathKayStatus = DispathKayStatus.RIGHT;
				try {
					setItemRequestFocus(viewIndex);
				} catch (Exception e) {
					e.printStackTrace();
					viewIndex--;
				}
				Log.d(TAG, "2::viewIndex::" + viewIndex);
				break;
			case KeyEvent.KEYCODE_DPAD_UP:

				break;
			case KeyEvent.KEYCODE_DPAD_DOWN:

				break;
			case SCREEN_INVALIDATE:
				// 添加child 重绘屏幕
				if (null != baseAdapter) {
					removeAllViews();
				}
				
				for (int i = 0; i < allItems; i++) {
					View view = baseAdapter.getView(i, null, null);
					//所有view的select都不是child 真正的获取焦点,
					//所以不存在焦点的机制
					view.setFocusable(false);
					view.setFocusableInTouchMode(false);
					view.setOnFocusChangeListener(new ItemFocusListener());
					LinearLayout.LayoutParams linear = new LinearLayout.LayoutParams(view.getWidth(), view.getHeight());
					linear.weight = 1;
					/*Log.d(TAG, "==" + i);
					Log.d(TAG, view.getWidth() + "|" + view.getHeight());*/
					mViewGroupLinearLayout.addView(view, i);
					itemWidth = view.getWidth();
					setItemHeight(view.getHeight());

				}
				liearLayout.addView(mViewGroupLinearLayout);
				addView(liearLayout);
				addView(focusImageView);
				requestLayout();
				mHandler.sendEmptyMessage(SELECT_FIRST_VIEW);
				break;
			case FOCUS_VIEW_MOVE:
				Log.d(TAG, "FOCUS_VIEW_MOVE==>theFocusViewX::" + theFocusViewX);
				startAnimaction();
				break;
			case SELECT_FIRST_VIEW :
				setSelectFirstView();
				break;
			}
		}
	};

	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展 
	 * @Description:  focus view animator or linear animator move
	 */
	private void startAnimaction() {
		switch (mDispathKayStatus) {
		case RIGHT:
			if (isViewStandIndex()) {
				setTheFocusViewMove(theFocusViewWidth, theFocusViewHeight, theFocusViewX, 0, deviationX, deviationY);
				Log.d(TAG, "startAnimaction::1");
			}
			if (!isViewStandIndex() && !isLastOne()) {
				setTheLinearViewGroupMove(theFocusViewWidth, 0);
				Log.d(TAG, "startAnimaction::2");
			}
			Log.d(TAG, "RIGHT+++>" + isTheFocusViewAtConfusion());
			if (isLastOne()) {
				if (isTheFocusViewAtConfusion()) {
					setTheFocusViewMove(theFocusViewWidth, theFocusViewHeight, focusImageView.getX()+itemWidth-getViewDeviationOutScreen()+deviationX/2, 0, deviationX,
							deviationY);
					setTheLinearViewGroupMove(getViewDeviationOutScreen(), 0);
				} else {
					setTheLinearViewGroupMove(theFocusViewWidth - getViewDeviationOutScreen(), 0);
				}
				Log.d(TAG, "startAnimaction::3");
			}
			break;
		case LEFT:
			Log.d(TAG, "LEFT+++>" + isTheFocusViewAtConfusion());
			if (isViewStandIndex() && !isFirstOne()) {
				setTheFocusViewMove(theFocusViewWidth, theFocusViewHeight, focusImageView.getX() - theFocusViewWidth + deviationX / 2, 0, deviationX,
						deviationY);
				Log.d(TAG, "startAnimaction::1");
			}
			if (!isViewStandIndex() && !isLastOne()) {
				setTheLinearViewGroupMove(-theFocusViewWidth, 0);
				Log.d(TAG, "startAnimaction::2");
			}
			if (isFirstOne()) {
				if (isTheFocusViewAtConfusion()) {
					Log.d(TAG, "startAnimaction::4");
					setTheFocusViewMove(theFocusViewWidth, theFocusViewHeight, theFocusViewDeviationX, 0, deviationX, deviationY);
					setTheLinearViewGroupMove(-getViewDeviationOutScreen(), 0);
				} else {
					Log.d(TAG, "startAnimaction::5");
					setTheLinearViewGroupMove(-theFocusViewWidth, 0);
				}
			}
			break;
		case RIGHT_1:
			Log.d(TAG, "liearLayout.getsx"+liearLayout.getScrollX());
			if (isViewStandIndex()) {
				setTheFocusViewMove(theFocusViewWidth, theFocusViewHeight, theFocusViewX, 0, deviationX, deviationY);
			}
			if (!isViewStandIndex()) {
				if (isViewStandPosition() && isTheFocusViewAtConfusion()) {
					setTheFocusViewMove(theFocusViewWidth, theFocusViewHeight, focusImageView.getX() + itemWidth - getViewDeviationOutScreen()
							+ deviationX / 2, 0, deviationX, deviationY);
					setTheLinearViewGroupMove(getViewDeviationOutScreen(), 0);
				} else {
					setTheLinearViewGroupMove(theFocusViewWidth, 0);
				}
			}
			break;
		case LEFT_1:
			if (isLinearScollerXMove()) {
				if (isTheFocusViewAtConfusion()&&!isTheFocusViewAtRight()) {
					Log.d(TAG, "LEFT_1::1");
					setTheFocusViewMove(theFocusViewWidth, theFocusViewHeight, theFocusViewDeviationX, 0, deviationX, deviationY);
					setTheLinearViewGroupMove(-getViewDeviationOutScreen(), 0);
				} else {
					Log.d(TAG, "LEFT_1::2");
					if (!isFocusViewAtLeft()) {
						Log.d(TAG, "LEFT_1::3");
						setTheFocusViewMove(theFocusViewWidth, theFocusViewHeight, focusImageView.getX() - theFocusViewWidth + deviationX / 2, 0,
								deviationX, deviationY);
					} else {
						Log.d(TAG, "LEFT_1::4");
						setTheLinearViewGroupMove(-theFocusViewWidth, 0);
					}
				}
			}
			if (!isLinearScollerXMove()) {
				Log.d(TAG, "LEFT_1::5");
				setTheFocusViewMove(theFocusViewWidth, theFocusViewHeight, focusImageView.getX() - theFocusViewWidth + deviationX / 2, 0, deviationX,
						deviationY);
			}
			break;
		default:
			break;
		}

	}
	
	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description: 判断焦点图是不是在一个混乱的位置状态
	 * 所谓的边界状态
	 * @return
	 */
	private boolean isTheFocusViewAtConfusion() {
		Log.d(TAG, "focusImageView.getX()::"+focusImageView.getX()+"|focusImageView.getWidth()::"+focusImageView.getWidth()+"|itemWidth:"+itemWidth);
		float focusViewX = focusImageView.getX() +deviationX/2 ;
		if (focusViewX == theFocusViewDeviationX || focusViewX== (cloudWidth - focusImageView.getWidth())) {
			return false;
		} 
		Log.d(TAG, "Right:"+(focusViewX + itemWidth > (cloudWidth - focusImageView.getWidth()))+"|cloudWidth:"+cloudWidth);
		return (focusViewX - itemWidth <= theFocusViewDeviationX)
				|| (focusViewX + itemWidth > (cloudWidth - focusImageView.getWidth()+deviationX));
	}
	
	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description: 判断焦点图是否在屏幕
	 * 最左侧
	 * @return
	 */
	private boolean isFocusViewAtLeft(){
		Log.d(TAG, "isFocusViewAtLeft()==>X::"+focusImageView.getX());
		return focusImageView.getX() <= theFocusViewDeviationX;
	}
	
	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description: focus view at right in the screen
	 * @return
	 */
	private boolean isTheFocusViewAtRight() {
		float focusViewX = focusImageView.getX() + deviationX/2;
		return (focusViewX >= (cloudWidth - focusImageView.getWidth() - theFocusViewDeviatonRight));
	}

	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description: the linear move
	 */
	private void setTheLinearViewGroupMove(int theFocusViewWidth,int deviationX) {
		Log.d(TAG, "setTheLinearViewGroupMove()::");
		liearLayout.smoothScrollBySlow((int) (theFocusViewWidth - deviationX), 0,theFocusViewContinue);
	}
	
	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description: 计算出最后一个view 在屏幕外的宽
	 * @return 
	 */
	private int getViewDeviationOutScreen(){
		if(viewStandIndex*itemWidth != cloudWidth){
			int deviation = (viewStandIndex+1)*itemWidth - cloudWidth;
			Log.d(TAG, "deviation::"+deviation+"|itemWidth::"+itemWidth+"|viewStandIndex::"+viewStandIndex+"|cloudWidth::"+cloudWidth);
			return deviation;
		}
		return 0;
	}
	
	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description: 是否有移动过
	 * @return
	 */
	private boolean isLinearScollerXMove() {
		Log.d(TAG, "isLinearScollerXMove:::"+ liearLayout.getScrollX() );
		return liearLayout.getScrollX() > 0;
	}

	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description: the focus view move
	 * @param move
	 */
	private void setTheFocusViewMove(int focusWidth, int focusHeight ,float theFocusViewX,float theFocusViewY,float deviationX , float deviationY) {
		Log.d(TAG, "1==" + (theFocusViewWidth / focusImageView.getWidth()) + "||w:" + theFocusViewWidth + "|" + focusImageView.getWidth());
		Log.d(TAG, "2==" + (theFocusViewHeight / focusImageView.getHeight()) + "||H:" + theFocusViewHeight + "|" + focusImageView.getHeight());
		AnimatorSet animatorSet = new AnimatorSet();
		// 初始化多属性的动画对象，用于改变焦点框View的宽和高
		PropertyValuesHolder pvhWidth = PropertyValuesHolder.ofInt("width1", (int)(focusWidth + deviationX));
		PropertyValuesHolder pvhHeight = PropertyValuesHolder.ofInt("height1",(int) (focusHeight - deviationY));
		ObjectAnimator animator1 = ObjectAnimator.ofPropertyValuesHolder(focusImageView, pvhWidth, pvhHeight);
		ObjectAnimator animator2 = null, animator3 = null;
		// focus view move
		if (isOrientation()) {
			animator2 = ObjectAnimator.ofFloat(focusImageView, "translationY", deviationY/2);
			animator3 = ObjectAnimator.ofFloat(focusImageView, "translationX",theFocusViewX - deviationX/2);
		} else {
			animator3 = ObjectAnimator.ofFloat(focusImageView, "translationY", theFocusViewY);
		}
		animatorSet.playTogether(animator1, animator2, animator3);
		animatorSet.setDuration(theFocusViewContinue);
		animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
		animatorSet.addListener(new AnimatorListener() {

			public void onAnimationStart(Animator animation) {
				isFocusChangeStatus = true;
			}

			public void onAnimationRepeat(Animator animation) {
				
			}

			public void onAnimationEnd(Animator animation) {
				isFocusChangeStatus = false;
			}

			public void onAnimationCancel(Animator animation) {
				isFocusChangeStatus = false;
			}
		});
		animatorSet.start();
	}

	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description: select the view request focus
	 * @param index
	 * @return if the view request focus return true other way return false;
	 */
	private void setItemRequestFocus(int index) throws Exception {
		View v = (View) baseAdapter.getItem(index);
		if (null == v) {
			return;
		}
		theFocusView = v;
		if (null != mItemSelectLinstener) {
			mItemSelectLinstener.select(viewIndex,v);
		}
		theItemSelectListener.select(viewIndex, v);
		/*focusView.requestFocus();
		Log.d(TAG, "==" + focusView.requestFocus());*/
		isFocusChangeStatus = false;
	}

	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description: 设置view的适配器
	 * @param baseAdapter
	 */
	public void setAdapter(BaseAdapter baseAdapter) {
		this.baseAdapter = baseAdapter;
		if (null == baseAdapter) {
			return;
		}
		allItems = baseAdapter.getCount();
		mHandler.sendEmptyMessage(SCREEN_INVALIDATE);
	}

	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description:得到当前View的索引值
	 * @return
	 */
	public int getViewIndex() {
		return viewIndex;
	}

	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description: 设置View的默认默认获取焦点的index
	 * @param viewFirstIndex
	 */
	public void setViewFirstIndex(int viewFirstIndex) {
		this.viewFirstIndex = viewFirstIndex;
		mHandler.sendEmptyMessage(SELECT_FIRST_VIEW);
	}

	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description: 得到默认的View的获取焦点的索引
	 * @return
	 */
	public int getViewFirstIndex() {
		return viewFirstIndex;
	}
	
	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description: 初始化的第一个
	 */
	private void setSelectFirstView(){
		if(null != baseAdapter){
			try {
				setItemRequestFocus(getViewFirstIndex());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description: get the views size in the screen for example one two three
	 *               and so on
	 * @return
	 */
	@SuppressLint("UseValueOf")
	private int getViewStandIndex() {
		Log.d(TAG, "getViewStandIndex::"+viewStandIndex);
		return viewStandIndex;
	}

	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description: return true view index is the last one other way return
	 *               false
	 * @return
	 */
	private boolean isLastOne() {
		return viewIndex == baseAdapter.getCount() - 1;
	}
	
	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description: 
	 * @return if the select view is the first return true
	 * other way return false;
	 */
	private boolean isFirstOne(){
		return viewIndex == 0;
	}

	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description: if linear begin move return true other way return false
	 * @return
	 */
	private boolean isViewStandIndex() {
		Log.d(TAG, "viewIndex::"+viewIndex);
		return viewIndex < getViewStandIndex();
	}
	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description: 是否处于边界位置
	 * @return
	 */
	private boolean isViewStandPosition(){
		return viewIndex == getViewStandIndex();
	}

	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description: get the view group orientation
	 * @return
	 */
	private boolean isOrientation() {
		// 判断方向
		/*
		 * if (liearLayout.getOrientation() == LinearLayout.HORIZONTAL) { return
		 * true; }
		 */
		return true;
	}

	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description: 得到当前的适配器
	 * @return
	 */
	public BaseAdapter getBaseAdapter() {
		return baseAdapter;
	}

	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description:
	 * @return
	 */
	public int getDeviationW() {
		return deviationW;
	}

	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description:
	 * @param deviationX
	 */
	public void setDeviationW(int deviationX) {
		this.deviationW = deviationX;
	}

	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description:
	 * @return
	 */
	public int getDeviationH() {
		return deviationH;
	}

	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description:
	 * @param deviationY
	 */
	public void setDeviationH(int deviationY) {
		this.deviationH = deviationY;
	}

	/**
	 * @ClassName: FocusView
	 * @Description: 焦点的view
	 * @author: zhangpengzhan
	 * @date 2015年4月21日 下午7:03:28
	 * 
	 */
	class FocusView extends LinearLayout {

		public FocusView(Context context) {
			super(context);
		}

		public int getWidth1() {
			return this.getLayoutParams().width;
		}

		public void setWidth1(int width) {
			this.getLayoutParams().width = width;
			this.requestLayout();
		}

		public int getHeight1() {
			return this.getLayoutParams().height;
		}

		public void setHeight1(int height) {
			this.getLayoutParams().height = height;
			this.requestLayout();
		}
	}

	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description: 设置选择监听
	 * @param mItemSelectLinstener
	 */
	public void setmItemSelectLinstener(ItemSelectLinstener mItemSelectLinstener) {
		this.mItemSelectLinstener = mItemSelectLinstener;
	}

	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description: 得到监听器
	 * @return
	 */
	public ItemSelectLinstener getmItemSelectLinstener() {
		return mItemSelectLinstener;
	}
	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description: view group click listener
	 * @param linearSelectorOnClickListener
	 */
	public void setLinearSelectorOnClickListener(LinearSelectorOnClickListener linearSelectorOnClickListener) {
		this.linearSelectorOnClickListener = linearSelectorOnClickListener;
	}
	/**
	 * @Title: CloudScreenLinearSelector
	 * @author:张鹏展
	 * @Description: view group click listener
	 * @return
	 */
	public LinearSelectorOnClickListener getLinearSelectorOnClickListener() {
		return linearSelectorOnClickListener;
	}

	public int getCloudHeight() {
		return cloudHeight;
	}
	public void setCloudHeight(int cloudHeight) {
		this.cloudHeight = cloudHeight;
	}

	public int getItemHeight() {
		return itemHeight;
	}
	public void setItemHeight(int itemHeight) {
		this.itemHeight = itemHeight;
	}

	public float getTheFocusViewY() {
		return theFocusViewY;
	}
	public void setTheFocusViewY(float theFocusViewY) {
		this.theFocusViewY = theFocusViewY;
	}

	/**
	 * @ClassName: ItemSelectLinstener
	 * @Description: item 被选中的接口
	 * @author: zhangpengzhan
	 * @date 2015年4月24日 下午4:35:08
	 * 
	 */
	public interface ItemSelectLinstener {
		public void select(int itemIndex,View v);
	}
	
	/**
	 * @ClassName: LinearSelectorOnClickListener
	 * @Description: 背景选择器点击事件
	 * @author: zhangpengzhan
	 * @date 2015年4月28日 下午3:15:19
	 * 
	 */
	public interface LinearSelectorOnClickListener{
		public void onClick(View v);
	}
	
	class CloudScreenLinearLayout extends HorizontalScrollView{

		public CloudScreenLinearLayout(Context context) {
			super(context);
			init();
		}

		public CloudScreenLinearLayout(Context context, AttributeSet attrs) {
			super(context, attrs);
			init();
		}

		public CloudScreenLinearLayout(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			 init();
		}
		
		/**
		 * @Title: CloudScreenLinearLayout
		 * @author:张鹏展
		 * @Description:  初始化方法
		 */
		private void init(){
			setHorizontalScrollBarEnabled(false);//设置原有的滚动无效
			setFocusable(false);
			setFocusableInTouchMode(false);
		}
		
		@Override
		public void computeScroll() {
			// 先判断mScroller滚动是否完成
			if (cloudViewScoller.computeScrollOffset()) {// 滚动没有完成
				// 这里调用View的scrollTo()完成实际的滚动
				scrollTo(cloudViewScoller.getCurrX(), cloudViewScoller.getCurrY());
				// 必须调用该方法，否则不一定能看到滚动效果
				isScrollStatus = true;
				postInvalidate();
			}else{
				isScrollStatus = false;
				Log.d(TAG, "computeScroll:: done");
			}
			super.computeScroll();
			
		}
		// 调用此方法设置滚动的相对偏移
		public void smoothScrollBySlow(int dx, int dy, int duration) {
			// 设置mScroller的滚动偏移量
			cloudViewScoller.startScroll(getScrollX(), getScrollY(), dx, dy, duration);// scrollView使用的方法（因为可以触摸拖动）
			// mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(),
			// dx, dy, duration); //普通view使用的方法
			invalidate();// 这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
		}
	}
	
	/**
	 * @ClassName: DispathKayStatus
	 * @Description: 按键状态
	 * @author: zhangpengzhan
	 * @date 2015年4月28日 下午12:07:02
	 * 
	 */
	enum DispathKayStatus{
		LEFT
		,RIGHT
		,LEFT_1
		,RIGHT_1
	}

}
