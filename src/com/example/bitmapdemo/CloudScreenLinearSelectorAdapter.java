package com.example.bitmapdemo;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
	public CloudScreenLinearSelectorAdapter(Context context) {
		this.mContext = context;
		itemList = new ArrayList<>();
	}

	@Override
	public int getCount() {
		return 15;
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
		TextView tv = (TextView)view.findViewById(R.id.textView1);
		tv.setText(String.valueOf(position));
		itemList.add(position,view);
		return view;
	}
}
