package hbsi.dtd.elim;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Canvas;


public class OperateGL {
	private GL10 gl;
	private boolean moveState;
	private boolean chooseState;
	private int rotateAngle =0;
	private int col = -1;
	private int row = -1;
	private float size;
	private float spaceWidth;
	private float spaceHeight;
	private float thisX;
	private float thisY;
	private float moveWidth;
	private float moveHeight;
	private int moveStep;

	public  OperateGL(GL10 gl){
		this.gl = gl;
		chooseState = false;
	}
	public void init(float size,float spaceWidth,float spaceHeight,int col,int row){
	
		
		this.size = size;
		this.spaceWidth = spaceWidth;
		this.spaceHeight = spaceHeight;
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glPushMatrix();
		moveTarget(col,row);
		
	}
	public void setXY(float thisX,float thisY){
		this.thisX = thisX;
		this.thisY = thisY;
	}
	private float time;
	private float endtime; 
	public void checkState(){

			checkMoveState();
			checkChoose();
	}
	public void moveTarget(int x,int y){
			if(col!=x||row!=y){
				col = x;
				row = y;
				if(thisX!=(x*2+1)*size/2+(x*2+1)*y - thisX)
					moveWidth = ((x*2+1)*size/2+(x*2+1)*spaceWidth - thisX)/100;
				if(thisY!=(y*2+1)*size/2+(y*2+1)*spaceHeight)
					moveHeight =((y*2+1)*size/2+(y*2+1)*spaceHeight - thisY)/100;
				moveState = true;
				moveStep = 0;
			
		}
	}
	private void checkMoveState(){
		if(moveState){
			moveStep++;
			if(moveStep<=100){
				
				thisX += moveWidth;
				thisY += moveHeight;
			}
			else{
				col = -1;
				row = -1;
				
				moveState = false;
			}
		}
		gl.glTranslatef(thisX, thisY, 0);
	}
	private void checkChoose(){
		rotateAngle=rotateAngle>360?0:rotateAngle+5;
		if(chooseState&&!moveState){
			gl.glRotatef(rotateAngle, 0, 0, 1);
		}
	}
	public void setChooseState(boolean chooseState){
		this.chooseState = chooseState;
	}
	public boolean getMoveState(){
		return moveState;
	}
}
