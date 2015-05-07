package com.example.bitmapdemo;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @ClassName: CloudScreenLinearSelectorAdapter
 * @Description:
 * @author: zhangpengzhan
 * @date 2015年4月20日 下午1:49:08
 * 
 */
public class CloudScreenLinearSelectorAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<View> itemList;
	private String[] path;
	public CloudScreenLinearSelectorAdapter(Context context,String[] path) {
		this.mContext = context;
		itemList = new ArrayList<>();
		this.path = path;
	}

	@Override
	public int getCount() {
		return path.length;
	}

	@Override
	public Object getItem(int position) {
		return itemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(mContext).inflate(R.layout.bg_item, null);
		CouldScreenImageView tv = (CouldScreenImageView)view.findViewById(R.id.imageView1);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(706, 540);
		tv.setImage("bgth/"+path[position]);
		itemList.add(position,view);
		view.setLayoutParams(lp);
		return view;
	}
}
