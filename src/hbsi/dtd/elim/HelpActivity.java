package hbsi.dtd.elim;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import android.widget.ImageSwitcher;
import android.widget.ViewSwitcher.ViewFactory;

public class HelpActivity extends Activity implements OnItemSelectedListener,ViewFactory,OnItemClickListener   {

	int[] ImageRes = {
			R.drawable.help1,
			R.drawable.help2,
			R.drawable.help3,
			R.drawable.help4,
	};
	 private ImageSwitcher is;
	 private Gallery gallery;  
	 private ImageAdapter imageAdapter;  
	 private int mCurrentPos = -1;// 当前的item  
	 private HashMap<Integer, ImageView> mViewMap; 
	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.help_layout);
	    gallery = (Gallery) findViewById(R.id.gallery);
	
		 imageAdapter = new ImageAdapter(this, ImageRes.length);  
	     gallery.setAdapter(imageAdapter);  
	     gallery.setOnItemSelectedListener(this);  
	     gallery.setSelection(1);// 设置一加载Activity就显示的图片为第二张  
	  
	     gallery.setOnItemClickListener(this); 


	 	is = (ImageSwitcher) findViewById(R.id.imageSwitcher);
		is.setFactory(this);
		is.setInAnimation(AnimationUtils.loadAnimation(this,
				    android.R.anim.fade_in));
		is.setOutAnimation(AnimationUtils.loadAnimation(this,
				    android.R.anim.fade_out));

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public View makeView() {
		ImageView imageView = new ImageView(this);  
        imageView.setBackgroundColor(0xFF000000);  
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);  
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);  
        imageView.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));  


        return imageView;  
	}
	
	public class ImageAdapter extends BaseAdapter  
    {  
        int mGalleryItemBackground;  
        private Context mContext;  
        private int mCount;// 一共多少个item  
  
        public ImageAdapter(Context context, int count)  
        {  
            mContext = context;  
            mCount = count;  
            mViewMap = new HashMap<Integer, ImageView>(count);  
          //  TypedArray typedArray = obtainStyledAttributes(R.styleable.Gallery);  
            // 设置边框的样式  
          //  mGalleryItemBackground = typedArray.getResourceId(R.styleable.Gallery_android_galleryItemBackground, 0);  
        }  
  
        public int getCount()  
        {  
            return Integer.MAX_VALUE;  
        }  
  
        public Object getItem(int position)  
        {  
            return position;  
        }  
  
        public long getItemId(int position)  
        {  
            return position;  
        }  
  
        public View getView(int position, View convertView, ViewGroup parent)  
        {  
            ImageView imageview = mViewMap.get(position % mCount);  
            if (imageview == null)  
            {  
                imageview = new ImageView(mContext);  
                imageview.setImageResource(ImageRes[position % ImageRes.length]);  
                imageview.setScaleType(ImageView.ScaleType.FIT_XY);  
                imageview.setLayoutParams(new Gallery.LayoutParams(136, 88));  
                imageview.setBackgroundResource(mGalleryItemBackground);  
            }  
            return imageview;  
        }  
    }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		 if (mCurrentPos == position)  
	        {  
	            // 如果在显示当前图片，再点击，就不再加载。  
	            return;  
	        }  
	        mCurrentPos = position;  
	        is.setImageResource(ImageRes[position % ImageRes.length]);  
		
	}
	

}
