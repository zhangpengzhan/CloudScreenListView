package com.example.bitmapdemo;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends ActionBarActivity {
	Bitmap bitmap;
	 AssetManager am;
	 CloudScreenLinearSelector imageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		imageView = new CloudScreenLinearSelector(getBaseContext());
		imageView.setAdapter(new CloudScreenLinearSelectorAdapter(getBaseContext()));
/*		try {
            am = getAssets();
            InputStream is = am.open("a.jpg");
            bitmap = CloudScreenBitmapFactory.decodeStreamFromColudSvreenOptions(is);
            imageView.setImageBitmap(bitmap);
            is.close();
           handler.sendEmptyMessageDelayed(2, 9000);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
		
		setContentView(imageView);
	}

	/*Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				
			    InputStream is;
				try {
					is = am.open("a.jpg");
					bitmap = CloudScreenBitmapFactory.decodeStreamFromColudSvreenOptions(is);
					imageView.setImageBitmap(bitmap);
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				handler.sendEmptyMessageDelayed(2, 5000);
				break;
			case 2:
				try {
					if(null != bitmap){
						bitmap.recycle();
					}
					is  = am.open("b.jpg");
					bitmap = CloudScreenBitmapFactory.decodeStreamFromColudSvreenOptions(is);
					imageView.setImageBitmap(bitmap);
					is .close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				handler.sendEmptyMessageDelayed(3, 5000);
				break;
			case 3:
				try {
					if(null != bitmap){
						bitmap.recycle();
					}
					is  = am.open("c.jpg");
					bitmap = CloudScreenBitmapFactory.decodeStreamFromColudSvreenOptions(is);
					imageView.setImageBitmap(bitmap);
					is .close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				handler.sendEmptyMessageDelayed(4, 5000);
				break;
			case 4:
				try {
					if(null != bitmap){
						bitmap.recycle();
					}
					is  = am.open("d.jpg");
					bitmap = CloudScreenBitmapFactory.decodeStreamFromColudSvreenOptions(is);
					imageView.setImageBitmap(bitmap);
					is .close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				handler.sendEmptyMessageDelayed(5, 5000);
				break;
			case 5:
				try {
					if(null != bitmap){
						bitmap.recycle();
					}
					is   = am.open("e.jpg");
					bitmap = CloudScreenBitmapFactory.decodeStreamFromColudSvreenOptions(is);
					imageView.setImageBitmap(bitmap);
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				handler.sendEmptyMessageDelayed(6, 5000);
				break;
			case 6:
				try {
					if(null != bitmap){
						bitmap.recycle();
					}
					is  = am.open("f.jpg");
					bitmap = CloudScreenBitmapFactory.decodeStreamFromColudSvreenOptions(is);
					imageView.setImageBitmap(bitmap);
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				handler.sendEmptyMessageDelayed(7, 5000);
				break;
			case 7:
				try {
					if(null != bitmap){
						bitmap.recycle();
					}
					is = am.open("g.jpg");
					bitmap = CloudScreenBitmapFactory.decodeStreamFromColudSvreenOptions(is);
					imageView.setImageBitmap(bitmap);
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				handler.sendEmptyMessageDelayed(8, 5000);
				break;
			case 8:
				try {
					if(null != bitmap){
						bitmap.recycle();
					}
					is = am.open("h.jpg");
					bitmap = CloudScreenBitmapFactory.decodeStreamFromColudSvreenOptions(is);
					imageView.setImageBitmap(bitmap);
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				handler.sendEmptyMessageDelayed(9, 5000);
				break;
			case 9:
				try {
					if(null != bitmap){
						bitmap.recycle();
					}
					is = am.open("i.jpg");
					bitmap = CloudScreenBitmapFactory.decodeStreamFromColudSvreenOptions(is);
					imageView.setImageBitmap(bitmap);
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				handler.sendEmptyMessageDelayed(10, 5000);
				break;
			case 10:
				try {
					if(null != bitmap){
						bitmap.recycle();
					}
					is = am.open("j.jpg");
					bitmap = CloudScreenBitmapFactory.decodeStreamFromColudSvreenOptions(is);
					imageView.setImageBitmap(bitmap);
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				handler.sendEmptyMessageDelayed(1, 5000);
				break;
			}
			super.handleMessage(msg);
		}
	};*/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
