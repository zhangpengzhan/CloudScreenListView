package com.example.bitmapdemo;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;

/**
 * @ClassName: CloudScreenBitmapFactory
 * @Description: 默认使用native memory create bitmap
 * @author: zhangpengzhan
 * @date 2015年4月13日 下午4:38:40
 * 
 */
public class CloudScreenBitmapFactory extends BitmapFactory {
	/**
	 * 默认的bitmap加载配置信息
	 * @Fields CloudScreenOoptions
	 */
	static BitmapFactory.Options CloudScreenOptions;
	//初始化配置信息
	static {
		CloudScreenOptions = new BitmapFactory.Options();
		CloudScreenOptions.inPreferredConfig = Config.ARGB_8888;
		CloudScreenOptions.inPurgeable = true;// 允许可清除
		CloudScreenOptions.inInputShareable = true;// 以上options的两个属性必须联合使用才会有效果
	}
	/**
	 * @Title: CloudScreenBitmapFactory
	 * @author:张鹏展
	 * @Description: 设置加载bitmap的配置项
	 * @param cloudScreenOoptions
	 */
	public void setCloudScreenOoptions(BitmapFactory.Options cloudScreenOoptions) {
		CloudScreenOptions = cloudScreenOoptions;
	}
	/**
	 * @Title: CloudScreenBitmapFactory
	 * @author:张鹏展
	 * @Description: 加载bitmap到内存
	 * @param cloudScreenOoptions
	 */
	 public static Bitmap decodeStreamFromColudSvreenOptions(InputStream is) {
		return decodeStream(is, null, CloudScreenOptions);
	 }
}
