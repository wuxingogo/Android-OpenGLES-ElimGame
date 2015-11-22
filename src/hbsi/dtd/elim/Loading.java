package hbsi.dtd.elim;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

public class Loading extends Activity implements Runnable{

	TextView loading;
	ImageView img;
	Bitmap bg;
	Thread time;
	private Handler mHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.load);
		try {
			bg = BitmapFactory.decodeStream(this.getAssets().open("aaa.jpg")) ;
		} catch (IOException e) {
			e.printStackTrace();
		}
		img = (ImageView)this.findViewById(R.id.load_bgimg);
		img.setImageBitmap(bg);
		loading =(TextView) this.findViewById(R.id.load_textview);
		loading.setTextColor(Color.WHITE);
		loading.setGravity(Gravity.CENTER);
		mHandler=new Handler(){
			 public void handleMessage(Message msg) {
				 loading.setText((String) msg.obj);
		          }
		};
		time = new Thread(this);
		time.start();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	int count = 0;
	boolean start = true;
	@Override
	public void run() {
		while(start){
			if(count<4){
				Message message = Message.obtain();
				message.obj = loading.getText()+".";
				mHandler.sendMessage(message);
				count++;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else{
				start = false;

				Intent intent = new Intent();
				intent.setClass(Loading.this, MenuActivity.class);
				this.startActivity(intent);
				Loading.this.finish();
				
			}
		}
		
	}


}
