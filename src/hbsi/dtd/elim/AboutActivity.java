package hbsi.dtd.elim;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class AboutActivity extends Activity implements  OnItemClickListener  {

	ListView lv;
	String[] person = new String[]{
			"程序员:Ly",
			"策划:Hyf",
			"美工:ZQ",
			"测试:Tjj",
			"Dota Allstar",
			"Dota!Dota!"
	};

	String[] info = new String[]{
			"组长大人,萌萌哒",
			"小峰峰~~负责小组策划,喜欢篮球",
			"张强是一个帅比,负责了小组的所有美工",
			"→_→需要解释鸡哥吗?强悍的人生不需要解释,小牛的眼神",
			"We are Dotaer,that's enjoy!Every Dotaer is star",
			"No one can beat us,because we are the team of dota !",
	};
	int[] id = new int[]{
			R.drawable.dota_1,
			R.drawable.dota_5,
			R.drawable.dota_3,
			R.drawable.dota_6,
			R.drawable.dota_7,
			R.drawable.dota_8,
	};
	Bitmap bitmap;
	Bitmap[] cutBitmap = new Bitmap[4];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.about);
		
		bitmapCut();
		lv = (ListView) this.findViewById(R.id.about_list);
		SimpleAdapter adapter = new SimpleAdapter(this, getData(), R.layout.about_title, 
				new String[]{"img","title","info"},
				new int[]{R.id.about_image,R.id.about_title,R.id.about_text});
		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,person);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);
		
	}



	private void bitmapCut() {
		try {
			bitmap = BitmapFactory.decodeStream(this.getAssets().open("a1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(int i=0;i<4;i++){
			cutBitmap[i] = Bitmap.createBitmap(bitmap, i*128, 0, 128, 128);
		}
		
		
	}
	private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
        for(int i=0;i<person.length;i++){
        	map  = new HashMap<String, Object>();
            map.put("img",id[i] );
            map.put("title", person[i]);
            map.put("info", info[i]);
            list.add(map);
        }
        return list;
    }
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}





	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}



	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}



	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}



	public Object getItem(int position) {

		return null;
	}


	public long getItemId(int position) {
		switch(position){
		case 0:
			Toast.makeText(AboutActivity.this, "position="+position,
					Toast.LENGTH_LONG).show();
			break;
		case 1:
			Log.d("", "Ly Two");
			break;
		case 2:
			Log.d("", "Ly Three");
			break;
		case 3:
			Log.d("", "Ly Four");
			break;
			
		}
		return 0;
	}



	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return 0;
	}


	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 0;
	}


	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean areAllItemsEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		return false;
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Log.d("", "position"+position+"id"+id);
		Toast.makeText(AboutActivity.this, "你点击的是="+person[position],
				Toast.LENGTH_SHORT).show();
	}

}
