package edu.jiangxin.droiddemo.graphics.opengles.ball;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import android.opengl.GLES20;

import static edu.jiangxin.droiddemo.graphics.opengles.ball.Constant.UNIT_SIZE;

public class Ball {
	private String mVertexShader;
	private String mFragmentShader;
	private FloatBuffer mVertexBuffer;

	private int mProgram;
	private int muMVPMatrixHandle;
	private int maPositionHandle;
	private int muRHandle;

	private int vCount = 0;
	public float yAngle = 0;
	public float xAngle = 0;
	private float zAngle = 0;
	private float r = 0.8f;

	public Ball(MySurfaceView mv) {
		this.initVertexData();
		this.initShader(mv);
	}

	// 初始化顶点数据的方法
	public void initVertexData() {
		// 顶点坐标数据的初始化================begin============================
		ArrayList<Float> alVertix = new ArrayList<Float>();// 存放顶点坐标的ArrayList
		final int angleSpan = 10;// 将球进行单位切分的角度
		for (int vAngle = -90; vAngle < 90; vAngle = vAngle + angleSpan)// 垂直方向angleSpan度一份
		{
			for (int hAngle = 0; hAngle <= 360; hAngle = hAngle + angleSpan)// 水平方向angleSpan度一份
			{// 纵向横向各到一个角度后计算对应的此点在球面上的坐标
				float x0 = (float) (r * UNIT_SIZE
						* Math.cos(Math.toRadians(vAngle)) * Math.cos(Math
						.toRadians(hAngle)));
				float y0 = (float) (r * UNIT_SIZE
						* Math.cos(Math.toRadians(vAngle)) * Math.sin(Math
						.toRadians(hAngle)));
				float z0 = (float) (r * UNIT_SIZE * Math.sin(Math
						.toRadians(vAngle)));

				float x1 = (float) (r * UNIT_SIZE
						* Math.cos(Math.toRadians(vAngle)) * Math.cos(Math
						.toRadians(hAngle + angleSpan)));
				float y1 = (float) (r * UNIT_SIZE
						* Math.cos(Math.toRadians(vAngle)) * Math.sin(Math
						.toRadians(hAngle + angleSpan)));
				float z1 = (float) (r * UNIT_SIZE * Math.sin(Math
						.toRadians(vAngle)));

				float x2 = (float) (r * UNIT_SIZE
						* Math.cos(Math.toRadians(vAngle + angleSpan)) * Math
						.cos(Math.toRadians(hAngle + angleSpan)));
				float y2 = (float) (r * UNIT_SIZE
						* Math.cos(Math.toRadians(vAngle + angleSpan)) * Math
						.sin(Math.toRadians(hAngle + angleSpan)));
				float z2 = (float) (r * UNIT_SIZE * Math.sin(Math
						.toRadians(vAngle + angleSpan)));

				float x3 = (float) (r * UNIT_SIZE
						* Math.cos(Math.toRadians(vAngle + angleSpan)) * Math
						.cos(Math.toRadians(hAngle)));
				float y3 = (float) (r * UNIT_SIZE
						* Math.cos(Math.toRadians(vAngle + angleSpan)) * Math
						.sin(Math.toRadians(hAngle)));
				float z3 = (float) (r * UNIT_SIZE * Math.sin(Math
						.toRadians(vAngle + angleSpan)));

				// 将计算出来的XYZ坐标加入存放顶点坐标的ArrayList
				alVertix.add(x1);
				alVertix.add(y1);
				alVertix.add(z1);
				alVertix.add(x3);
				alVertix.add(y3);
				alVertix.add(z3);
				alVertix.add(x0);
				alVertix.add(y0);
				alVertix.add(z0);

				alVertix.add(x1);
				alVertix.add(y1);
				alVertix.add(z1);
				alVertix.add(x2);
				alVertix.add(y2);
				alVertix.add(z2);
				alVertix.add(x3);
				alVertix.add(y3);
				alVertix.add(z3);
			}
		}
		vCount = alVertix.size() / 3;// 顶点的数量为坐标值数量的1/3，因为一个顶点有3个坐标
		// 将alVertix中的坐标值转存到一个float数组中
		float vertices[] = new float[vCount * 3];
		for (int i = 0; i < alVertix.size(); i++) {
			vertices[i] = alVertix.get(i);
		}

		// 创建顶点坐标数据缓冲
		// vertices.length*4是因为一个整数四个字节
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());// 设置字节顺序
		mVertexBuffer = vbb.asFloatBuffer();// 转换为float型缓冲
		mVertexBuffer.put(vertices);// 向缓冲区中放入顶点坐标数据
		mVertexBuffer.position(0);// 设置缓冲区起始位置
		// 特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
		// 转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
	}


	private void initShader(MySurfaceView mv) {
		this.mVertexShader = ShaderUtil.loadFromAssetsFile("vertex.sh",
				mv.getResources());
		this.mFragmentShader = ShaderUtil.loadFromAssetsFile("frag.sh",
				mv.getResources());

		this.mProgram = ShaderUtil.createProgram(this.mVertexShader,
				this.mFragmentShader);
		this.maPositionHandle = GLES20.glGetAttribLocation(this.mProgram,
				"aPosition");
		this.muMVPMatrixHandle = GLES20.glGetUniformLocation(this.mProgram,
				"uMVPMatrix");
		this.muRHandle = GLES20.glGetUniformLocation(this.mProgram, "uR");
	}

	public void drawSelf() {
		MatrixState.rotate(xAngle, 1, 0, 0);
		MatrixState.rotate(yAngle, 0, 1, 0);
		MatrixState.rotate(zAngle, 0, 0, 1);

		GLES20.glUseProgram(this.mProgram);
		GLES20.glUniformMatrix4fv(this.muMVPMatrixHandle, 1, false,
				MatrixState.getFinalMatrix(), 0);
		GLES20.glUniform1f(this.muRHandle, this.r * UNIT_SIZE);
		GLES20.glVertexAttribPointer(this.maPositionHandle, 3, GLES20.GL_FLOAT,
				false, 3 * 4, this.mVertexBuffer);
		GLES20.glEnableVertexAttribArray(this.maPositionHandle);
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount);
	}
}
