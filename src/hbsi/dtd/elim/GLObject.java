package hbsi.dtd.elim;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.opengl.GLUtils;

public class GLObject {
	private int width;
	private int height;
	private Buffer texture;
	private Buffer vertex;
	Bitmap bitmap;
	private GL10 gl;
	public GLObject(GL10 gl,Bitmap bitmap){
		this.gl = gl;
		this.bitmap = bitmap;

		width = bitmap.getWidth();
		height = bitmap.getHeight();
		float[] position = new float[]{
				-width/2,-height/2,				//LB
				width/2,-height/2,				//RB	
				width/2,height/2,					//RT
				-width/2,height/2,				//LT	
		};
		short[] textureVer =new short[]{
				0,1,
				1,1,
				1,0,
				0,0,			
		};
		vertex = ByteBuffer.allocateDirect(position.length * 4).
				order(ByteOrder.nativeOrder()).
				asFloatBuffer().put(position).
				flip();
		texture = ByteBuffer.allocateDirect(textureVer.length * 2).
				order(ByteOrder.nativeOrder()).
				asShortBuffer().put(textureVer).
				flip();
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, this.bitmap, 0);//3--÷∆∂®Œ∆¿Ì
		
	}
	public void toBlind(){
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		vertex.position(0);
		gl.glVertexPointer(2, GL10.GL_FLOAT, 2*4, vertex);
		
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		texture.position(0);
		gl.glVertexPointer(2, GL10.GL_SHORT, 2*2, texture);
		
		
	}
	public void noBlind(){
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	}
	public void paint(){
		toBlind();
		gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);
		noBlind();
	}
}
