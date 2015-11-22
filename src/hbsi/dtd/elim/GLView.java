package hbsi.dtd.elim;

import java.io.IOException;
import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;
import android.view.MotionEvent;

public class GLView extends GLSurfaceView {

	MyRenderer myRender;
	Context context;
	Bitmap bitmap;
	
	public GLView(Context context) {
		super(context);
		this.context = context;
		try {
			bitmap =BitmapFactory.decodeStream(context.getAssets().open("Over.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		myRender = new MyRenderer();
	
		this.setEGLConfigChooser(8,8,8,8,16,0);
		this.setRenderer(myRender);
		/*设置当前GL的FPS为1秒60*/
		myRender.FPS = (long) (1000/60.0);
		
	}
	
	private int firX		= -1;
	private int firY		= -1;
	private int secX	= -1;
	private int secY	= -1;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
//		if(event.getAction()==MotionEvent.ACTION_DOWN){
//		int firX		= -1;
//		int firY		= -1;
//		int secX	= -1;
//		int secY	= -1;
//		}
		
		if(event.getAction()==MotionEvent.ACTION_DOWN){
			
			if(myRender.choose!=null)
				myRender.choose.setChooseState(false);
			int chooseX = (int) (event.getX()/(myRender.shapeSize+2*myRender.spaceWidth));
			int chooseY = (int) ((myRender.height-event.getY())/(myRender.shapeSize+2*myRender.spaceHeight));
			
			if(chooseX<myRender.operate.length&&chooseY<myRender.operate[0].length)
			{
				myRender.choose=myRender.operate[chooseX][chooseY];
				myRender.choose.setChooseState(true);
			}
			if(chooseX<myRender.operate.length&&chooseY<myRender.operate[0].length&&firX==-1){
				firX = chooseX;
				firY = chooseY;
			}
			else if(chooseX<myRender.operate.length&&chooseY<myRender.operate[0].length&&secX==-1&&(firX!=chooseX||firY!=chooseY)){
				secX = chooseX;
				secY = chooseY;
			}
			if(firX!=-1&&secX!=-1){
				if(!myRender.operate[firX][firY].getMoveState()&&!myRender.operate[secX][secY].getMoveState()){
				myRender.swap(firX,firY,secX,secY);
				}
				Log.d("", "moveTarget"+secX+","+secY);
				Log.d("", "moveTarget"+firX+","+firY);
				firX =-1;
				firY =-1;
				secX =-1;
				secY =-1;
			}
			
		}
		
		return true;
	}


	class MyRenderer implements Renderer{
		Random rand;
		OperateGL choose;
		GLCube cube;
		int [][]intArray 				=	new int[5][5];
		OperateGL[][] operate	=	new OperateGL[intArray.length][intArray[0].length];
		public boolean[][] elimArray = new boolean[intArray.length][intArray[0].length];
		float EverySize;
		float shapeSize;
		float spaceWidth;
		float spaceHeight;
		int score = 0;
		long startTime;
		long stopTime;
		long spaceTime;
		public long FPS = 30;
		private Canvas scoreCanvas;
		private Bitmap scoreBitmap;

		private Paint scorePaint;
		private float width,height;
		private GLObject scoreGL;
		public boolean checkState = true;
		
		private float[][] color = new float[][]{
				{0.1f,0.06f,0.1f,1.0f	},
				{0.189f,0.161f,0.201f,1.0f},
				{0.4f,0.3f,0.2f,1.0f},
				{0.8f,0.5f,0.5f,1.0f}
		};
		public int colorIndex =0;
		@Override
		public void onDrawFrame(GL10 gl) {
			startTime = System.currentTimeMillis();
			gl.glClearColor(color[colorIndex][0],color[colorIndex][1],color[colorIndex][2],color[colorIndex][3]);
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			for(int i = 0;i<intArray.length;i++){
				for(int j = 0;j<intArray[0].length;j++){
					gl.glLoadIdentity(); 
					gl.glPushMatrix();
					operate[i][j].checkState();
					cube.toBlind();
					cube.setBitmap(intArray[i][j]);
					cube.paint();
					gl.glPopMatrix();
				}
			}
			gl.glLoadIdentity(); 
			gl.glPushMatrix();
			gl.glTranslatef(width/2, height/2, 0);
			scoreGL.paint();
			gl.glPopMatrix();
			
	
			stopTime = System.currentTimeMillis();	
			spaceTime = stopTime-startTime;

			try {
				if(spaceTime<FPS)
					Thread.sleep(FPS-spaceTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			/*数据更新*/
			update();		
				
		}
		private void update() {
			
			for(int i=0;i<operate.length;i++){
				for(int j=0;j<operate[i].length;j++){
					if(operate[i][j].getMoveState()){
						return;
					}		
				}
			}
			if(checkState)
				checkIntArray();
		
			
		}
	
		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			
			this.width = width;
			this.height = height;
			gl.glViewport(0, 0, width, height);
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glOrthof(0, width, 0, height, -5, 5);
			
			
			scorePaint = new Paint();
			scorePaint.setColor(Color.BLACK);
			scoreBitmap = Bitmap.createBitmap(width/2, height/4, Bitmap.Config.ARGB_8888);
			
			scoreCanvas = new Canvas(scoreBitmap);
			scoreCanvas.drawText(score+"分数", 0, 0, scorePaint);
			scoreCanvas.drawRect(0, 0, width/2, height/4, scorePaint);
			scoreGL = new GLObject(gl,scoreBitmap);
			
			EverySize = width>height?height/
					(intArray.length>intArray[0].length?intArray.length:intArray[0].length):
						width/(intArray.length>intArray[0].length?intArray.length:intArray[0].length);
		
			shapeSize = EverySize*3/4;
			spaceWidth =(width-shapeSize*intArray.length)/intArray.length/2;
			spaceHeight = (height-shapeSize*intArray[0].length)/intArray[0].length/2;

			
			cube =new GLCube(gl,shapeSize,bitmap,128,128);
			rand = new Random();
			for(int i=0;i<intArray.length;i++){
				for(int j=0;j<intArray[0].length;j++){
					intArray[i][j] = rand.nextInt(5)+5;
					operate[i][j]	=	new OperateGL(gl);				
					operate[i][j].init(shapeSize,spaceWidth,spaceHeight,i,j);
				}
			}
			initElim();
			
		}
		

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
			 // 启用阴影平滑
	        gl.glShadeModel(GL10.GL_SMOOTH);

	        // 黑色背景
	       
	        
	        // 设置深度缓存
	        gl.glClearDepthf(1.0f);                            
	        // 启用深度测试
	        gl.glEnable(GL10.GL_DEPTH_TEST);                        
	        // 所作深度测试的类型
	        gl.glDepthFunc(GL10.GL_LEQUAL);                            
	        
	        // 告诉系统对透视进行修正
	        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
		}
		public void initElim(){
			for(boolean[] i:elimArray){
				for(boolean j:i){
					j = false; 
				}
			}
		
		}
		public void checkIntArray(){
			Log.d("", "check");
//			initElim();	
			for(int i=0;i<intArray.length;i++){
				for(int j=0;j<intArray[i].length;j++)
				{
					checkOne(j,i);
				}
			}
			elimArray();
			checkState = false;
		
		}
		private void elimArray() {
			Log.d("", "elimArray");
			myRender.colorIndex=myRender.colorIndex==myRender.color.length-1?0:myRender.colorIndex+1;
			for(int i=0;i<elimArray.length;i++){
				for(int j=0;j<elimArray[i].length;j++){
					if(elimArray[i][j]){
						elimArray[i][j] = false;
						operate[i][j].setChooseState(true);
						operate[i][j].setXY(0, 0);
						operate[i][j].moveTarget(i, j);
						intArray[i][j] = rand.nextInt(5)+5;
//						for(int t = i;t<elimArray.length-1;t++)
//						{
//							swap(j,t,j,t+1);
////							swap(t+1,j,t,j);
// 						}
						/*	顶层元素重新生成	*/
//						operate[intArray.length-1][j].setXY(0, 0);
//						operate[intArray.length-1][j].moveTarget(j,intArray.length-1);
//						intArray[intArray.length-1][j] =rand.nextInt(5);
						
						score+=100;
						checkState = true;
					}
					else{
						operate[i][j].setChooseState(false);
					}
				}
			}
		}
		
		public void checkOne(int rowIndex,int colIndex){
			/*消除个数*/
			int rowNum	=	1;
			int colNum	=	1;
			/*横着检测*/
			/*三消游戏 最后两个元素的横行不检测*/
			if(colIndex<intArray.length-2){
				for(int i=colIndex+1;i<intArray.length;i++){
					if(intArray[rowIndex][colIndex]==intArray[rowIndex][i]){
						colNum++;
						Log.d("hengxiang","hengxiang");
					}
					else{
						break;
					}			
				}
				if(colNum>=3)
				{
					Log.d("hengxiang","");
					for(int i=0;i<colNum;i++)
					{
						Log.d("", colIndex+","+i);
						elimArray[rowIndex][colIndex+i] = true;
					}
				}
			}
				/*竖着检测*/
				/*三消游戏 最后两个元素的竖行不检测*/
			if(rowIndex<intArray[0].length-2){ 
				for(int i=rowIndex+1;i<intArray.length;i++){
					if(intArray[rowIndex][colIndex]==intArray[i][colIndex]){
						rowNum++;
					}
					else{
						break;
					}
				}
				if(rowNum>=3){
					for(int i=0;i<rowNum ;i++){				
						Log.d("zongxiang","rowIndex"+rowIndex+",,,i"+i);
						elimArray[rowIndex+i][colIndex] = true;
					}
				}
			}	
			
		}
		public void swap(int firX,int firY,int secX,int secY){
			
			
				operate[firX][firY].moveTarget(secX, secY);
				operate[secX][secY].moveTarget(firX, firY);
				OperateGL temp = operate[firX][firY];
				operate[firX][firY] = operate[secX][secY];
				operate[secX][secY] = temp;
				int intTemp = intArray[firX][firY];
				intArray[firX][firY] = intArray[secX][secY];
				intArray[secX][secY] = intTemp;
				checkState = true;
			}
		
	}
}
