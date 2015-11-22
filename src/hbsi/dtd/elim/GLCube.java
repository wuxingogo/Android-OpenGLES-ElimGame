package hbsi.dtd.elim;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.opengl.GLUtils;
import android.util.Log;

public class GLCube {
	GL10 gl;
	Buffer vertex;
	float [][] textureArray;
	Buffer[] texture;
	Bitmap bitmap;
	GLCube(GL10 gl,float size,Bitmap bitmap,float cutBitmapWidth,float cutBitmapHeight){
		this.gl = gl;
		this.bitmap = bitmap;
		float[] position = {
				-size/2,-size/2,				//LB
				size/2,-size/2,				//RB	
				size/2,size/2,					//RT
				-size/2,size/2,				//LT	
		};
		
		int cutWidthNum = (int) (bitmap.getWidth()/cutBitmapWidth);
		int cutHeightNum = (int) (bitmap.getHeight()/cutBitmapHeight);
		
		float width = cutBitmapWidth/bitmap.getWidth();
		float height =  cutBitmapHeight/bitmap.getHeight();
		
		texture = new Buffer[10];
		textureArray = new float[10][];
		for(int i=0;i<cutHeightNum;i++){
			for(int j=0;j<cutWidthNum;j++){
				textureArray[i] =new float[] 
				{
//						j%cutWidthNum*width,(int)(j/cutWidthNum)*height+height,				//LB
//						j%cutWidthNum*width+width,(int)(j/cutWidthNum)*height+height,	//RB
//						j%cutWidthNum*width+width,(int)(j/cutWidthNum)*height,				//RT
//						j%cutWidthNum*width,(int)(j/cutWidthNum)*height,							//LT
						j*width,i*height+height,				//LB
						j*width+width,i*height+height,	//RB
						j*width+width,i*height,				//RT
						j*width,i*height,							//LT
						
						
				};

				texture[i*cutWidthNum+j] = 
						ByteBuffer.allocateDirect(textureArray[i].length * 4).
						order(ByteOrder.nativeOrder()).
						asFloatBuffer().put(textureArray[i]).
						flip();
			}			
		}
		
		
		vertex =ByteBuffer.allocateDirect(position.length * 4).
				order(ByteOrder.nativeOrder()).
				asFloatBuffer().put(position).
				flip();
		gl.glEnable(GL10.GL_BLEND);//开启颜色混合功能
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, 
				GL10.GL_ONE_MINUS_SRC_ALPHA);//透明
		
		gl.glEnable(GL10.GL_TEXTURE_2D);//开启纹理
		
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, this.bitmap, 0);//3--制定纹理
		
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, 
				GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR_MIPMAP_NEAREST);//放大
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, 
				GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);//缩小
//		gl.glDisable(GL10.GL_TEXTURE_2D);
	}
	public void toBlind(){
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		vertex.position(0);
		gl.glVertexPointer(2, GL10.GL_FLOAT,0, vertex);
	}
	public void setBmp(Bitmap bitmap){
		this.bitmap = bitmap;
	}
	public void setBitmap(int index){
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		texture[index].position(0);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 2*4, texture[index]);
		
	}
	public void noBlind(){
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	}
	public void paint(){
		
		gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);
		this.noBlind();
	}
}
