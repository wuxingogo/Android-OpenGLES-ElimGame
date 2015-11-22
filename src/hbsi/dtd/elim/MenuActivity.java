package hbsi.dtd.elim;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public  class  MenuActivity extends Activity implements View.OnClickListener {

	TextView tv;
	TextView start_button;
	TextView help_button;
	TextView about_button;
	TextView set_button;
	TextView exit_button;
	TextView high_button;
	SQLiteDatabase db;
	static MediaPlayer mPlayer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		AssetManager am =this.getAssets();
		Typeface tf = Typeface.createFromAsset(am, "font/STHUPO.TTF");
		this.setContentView(R.layout.menu);
		
		tv = (TextView) this.findViewById(R.id.menu_title);
		start_button =(TextView) this.findViewById(R.id.start_button);
		
		help_button =(TextView)this.findViewById(R.id.help_button);
		about_button =(TextView) this.findViewById(R.id.about_button);
		set_button =(TextView) this.findViewById(R.id.set_button);
		exit_button =(TextView) this.findViewById(R.id.exit_button);
		high_button = (TextView) this.findViewById(R.id.high_score_button);

	
		Shader shader =new LinearGradient(0, 0, 0, 20, Color.WHITE, Color.GRAY, TileMode.CLAMP);
		
		tv.setTextColor(Color.BLUE);
		tv.setTypeface(tf);
		tv.setText("µ¶ËþÏûÏû¿´");
		tv.getPaint().setShader(shader);
		tv.setGravity(1);
		start_button.setTypeface(tf);
		help_button.setTypeface(tf);
		about_button.setTypeface(tf);
		set_button.setTypeface(tf);
		exit_button.setTypeface(tf);
		high_button.setTypeface(tf);
		
	
		addListener();
		
		createMusic(this);
	}


	public static void createMusic(final Context context) {
		mPlayer = MediaPlayer.create(context, R.raw.background);
		//mPlayer.reset();
		mPlayer.setLooping(true);
		
		mPlayer.start();
	}

	private void addListener() {
		start_button.setOnClickListener(this);
		help_button.setOnClickListener(this);
		about_button.setOnClickListener(this);
		exit_button.setOnClickListener(this);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(mPlayer!=null){
			mPlayer.stop();
			mPlayer.release();
			mPlayer = null;
		}
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.start_button:
			Intent intent = new Intent();
			intent.setClass(MenuActivity.this, MainActivity.class);
			startActivity(intent);
			
			break;
		case R.id.help_button:
			Intent intent1 = new Intent();
			intent1.setClass(MenuActivity.this, HelpActivity.class);
			startActivity(intent1);
			break;
		case R.id.about_button:
			Intent intent2 = new Intent();
			intent2.setClass(MenuActivity.this, AboutActivity.class);
			startActivity(intent2);
			break;
		case R.id.set_button:
			break;
		case R.id.high_score_button:
			break;
		case R.id.exit_button:
			MenuActivity.this.finish();
			break;
			
		}
		
	}
	
}
