package edu.jiangxin.droiddemo.opengl.blur;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.view.MotionEvent;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MySurfaceView extends GLSurfaceView {
    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;//角度缩放比例
    private final SceneRenderer mRenderer;//场景渲染器

    float cx = 0;
    float cy = 22;
    float cz = -90;
    //目标点
    float tx = 0;
    float ty = 22;
    float tz = 40;
    //方向向量
    float dx = 0;
    float dy = 1;
    float dz = 0;
    //线程循环的标志位
    boolean flag = true;
    //摄像机的位置角度
    float cr = 50;//摄像机半径
    float cAngle = 0;
    float xAngle = 0;
    float maxAngle = 30.0f;//最大角度
    float objScale = 0.0241f;//教室缩放系数
    float objBeiZi = 0.02f;//杯子缩放系数
    //投影矩阵系数
    float near = 11.0f;
    private float mPreviousY;//上次的触控位置Y坐标
    private float mPreviousX;//上次的触控位置X坐标

    public MySurfaceView(Context context) {
        super(context);
        this.setEGLContextClientVersion(3); //设置使用OPENGL ES3.0
        mRenderer = new SceneRenderer(context);    //创建场景渲染器
        setRenderer(mRenderer);                //设置渲染器
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float y = e.getY();
        float x = e.getX();
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dy = y - mPreviousY;//计算触控笔Y位移
                float dx = x - mPreviousX;//计算触控笔X位移
                cAngle += dx * TOUCH_SCALE_FACTOR;
                xAngle += dy * TOUCH_SCALE_FACTOR;
                if (cAngle <= -maxAngle) {//控制角度的大小
                    cAngle = -maxAngle;
                }
                if (cAngle >= maxAngle) {
                    cAngle = maxAngle;
                }
                tx = (float) (Math.sin(Math.toRadians(cAngle)) * cr) + cx;
                tz = (float) (Math.cos(Math.toRadians(cAngle)) * cr) + cz;
        }
        mPreviousY = y;//记录触控笔位置
        mPreviousX = x;//记录触控笔位置
        return true;
    }

    public int initTexture(Resources res, String fileName) {
        int[] textures = new int[1];
        GLES30.glGenTextures(1, textures, 0);
        int textureId = textures[0];
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureId);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_NEAREST);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_REPEAT);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_REPEAT);

        InputStream is = null;
        try {
            is = res.getAssets().open("pic/" + fileName);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Bitmap bitmapTmp;
        try {
            bitmapTmp = BitmapFactory.decodeStream(is);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, bitmapTmp, 0);
        bitmapTmp.recycle();
        return textureId;
    }

    private class SceneRenderer implements GLSurfaceView.Renderer {
        private static final int GEN_TEX_WIDTH = 1024;

        private static final int GEN_TEX_HEIGHT = 1024;

        private final Context mContext;

        private int mSurfaceWidth;

        private int mSurfaceHeight;

        //帧缓冲id
        private int mFrameBuffer;

        //渲染深度缓冲id
        private int mRenderBuffer;

        //最后生成的纹理id
        int mTexture;

        int textWhiteBan;//白色地板
        int texchuanghu;//窗户纹理
        int texfangding;//房顶纹理
        int texdiban;//地板纹理
        int texheiban;//黑板纹理
        int texthongqi;//红旗
        int texchair;//椅子
        int texbeizi;//杯子纹理

        LoadedObject diban;//地板
        LoadedObject fangding;//房顶
        LoadedObject qiang1;//墙壁
        LoadedObject chuanghu;//窗户
        LoadedObject men;//门
        LoadedObject menkuang;//门框
        LoadedObject huangzhu;//黄柱
        LoadedObject baizhu;//白柱
        LoadedObject jiangtai;//讲台
        LoadedObject heiban;//黑板
        LoadedObject hongqi;//红旗
        LoadedObject jiangzhuo;//红旗
        LoadedObject baiban;//白色黑板
        LoadedObject zhuoheyi;//桌子和椅子
        LoadedObject beizi;//杯子
        LoadedObject beiziba;//杯子把
        LoadedObject beizigai;//杯子盖

        public SceneRenderer(Context context) {
            mContext = context;
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //设置屏幕背景色RGBA
            GLES30.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            //打开深度检测
            GLES30.glEnable(GLES30.GL_DEPTH_TEST);
            //打开背面剪裁
            GLES30.glEnable(GLES30.GL_CULL_FACE);
            //初始化变换矩阵
            MatrixState.setInitStack();
            //加载纹理
            texheiban = initTexture(mContext.getResources(), "heiban.jpg");
            texdiban = initTexture(mContext.getResources(), "diban.jpg");
            texfangding = initTexture(mContext.getResources(), "fangding.jpg");
            texchuanghu = initTexture(mContext.getResources(), "chuanghu.png");
            textWhiteBan = initTexture(mContext.getResources(), "whiteban.jpg");
            texthongqi = initTexture(mContext.getResources(), "guoqi.jpg");
            texchair = initTexture(mContext.getResources(), "chair.jpg");
            texbeizi = initTexture(mContext.getResources(), "beizi.jpg");
            //加载模型
            diban = LoadUtil.loadFromFile(mContext.getResources(),"diban.obj");
            fangding = LoadUtil.loadFromFile( mContext.getResources(),"fangdingtest.obj");
            qiang1 = LoadUtil.loadFromFile(mContext.getResources(),"qiang1.obj");
            chuanghu = LoadUtil.loadFromFile(mContext.getResources(),"chuanghu.obj");
            men = LoadUtil.loadFromFile(mContext.getResources(),"men.obj");
            menkuang = LoadUtil.loadFromFile(mContext.getResources(),"menkuang.obj");
            huangzhu = LoadUtil.loadFromFile(mContext.getResources(),"huangzhu.obj");
            baizhu = LoadUtil.loadFromFile(mContext.getResources(),"baizhu.obj");
            jiangtai = LoadUtil.loadFromFile(mContext.getResources(),"jiangtai.obj");
            heiban = LoadUtil.loadFromFile(mContext.getResources(),"heiban.obj");
            hongqi = LoadUtil.loadFromFile(mContext.getResources(),"hongqi.obj");
            jiangzhuo = LoadUtil.loadFromFile(mContext.getResources(),"jiangzhuotest.obj");
            baiban = LoadUtil.loadFromFile(mContext.getResources(),"baiban.obj");
            zhuoheyi = LoadUtil.loadFromFile(mContext.getResources(),"zhuoheyi.obj");
            beizi = LoadUtil.loadFromFile(mContext.getResources(),"beizi.obj");
            beiziba = LoadUtil.loadFromFile(mContext.getResources(),"beiziba.obj");
            beizigai = LoadUtil.loadFromFile(mContext.getResources(),"hugai.obj");
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            mSurfaceWidth = width;
            mSurfaceHeight = height;
            initFrameBuffers();
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            //通过绘制产生矩形纹理
            generateTextImage();
            //绘制真实场景
            drawBlur();
        }

        private void initFrameBuffers() {
            int[] frameBuffers = new int[1];
            GLES30.glGenFramebuffers(1, frameBuffers, 0);
            mFrameBuffer = frameBuffers[0];
            GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, mFrameBuffer);

            int[] renderBuffers = new int[1];
            GLES30.glGenRenderbuffers(1, renderBuffers, 0);
            mRenderBuffer = renderBuffers[0];
            GLES30.glBindRenderbuffer(GLES30.GL_RENDERBUFFER, mRenderBuffer);
            GLES30.glRenderbufferStorage(GLES30.GL_RENDERBUFFER, GLES30.GL_DEPTH_COMPONENT16, GEN_TEX_WIDTH, GEN_TEX_HEIGHT);
            GLES30.glFramebufferRenderbuffer(GLES30.GL_FRAMEBUFFER, GLES30.GL_DEPTH_ATTACHMENT, GLES30.GL_RENDERBUFFER, mRenderBuffer);

            int[] textures = new int[1];
            GLES30.glGenTextures(1, textures, 0);
            mTexture = textures[0];
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, mTexture);
            GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR);
            GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
            GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_CLAMP_TO_EDGE);
            GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_CLAMP_TO_EDGE);
            GLES30.glTexImage2D(GLES30.GL_TEXTURE_2D, 0, GLES30.GL_RGBA, GEN_TEX_WIDTH, GEN_TEX_HEIGHT, 0, GLES30.GL_RGBA, GLES30.GL_UNSIGNED_BYTE, null);
            GLES30.glFramebufferTexture2D(GLES30.GL_FRAMEBUFFER, GLES30.GL_COLOR_ATTACHMENT0, GLES30.GL_TEXTURE_2D, mTexture, 0);
        }

        //第一次绘制纹理
        private void generateTextImage() {
            //设置视窗大小及位置
            GLES30.glViewport(0, 0, GEN_TEX_WIDTH, GEN_TEX_HEIGHT);
            //绑定帧缓冲id
            GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, mFrameBuffer);
            //清除深度缓冲与颜色缓冲
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
            //调用此方法计算产生透视投影矩阵
            float ratio = (float) mSurfaceWidth / mSurfaceHeight;
            MatrixState.setProjectFrustum(-ratio * 5, ratio * 5, -5, 5, near, 3000);
            //调用此方法产生摄像机9参数位置矩阵
            MatrixState.setCamera(cx, cy, cz, tx, ty, tz, dx, dy, dz);
            MatrixState.pushMatrix();
            MatrixState.setLightLocation(0, 18, -72);//设置灯光位置
            GLES30.glDisable(GLES30.GL_CULL_FACE);
            MatrixState.translate(-4.0f, 15, -66);
            MatrixState.scale(objBeiZi, objBeiZi, objBeiZi);
            if (beizi != null) {
                beizi.drawSelf(texbeizi);
            }
            if (beiziba != null) {
                beiziba.drawSelf(texfangding);
            }
            if (beizigai != null) {
                beizigai.drawSelf(texfangding);
            }
            //打开背面剪裁
            GLES30.glEnable(GLES30.GL_CULL_FACE);
            MatrixState.popMatrix();

            //绘制杯子
            MatrixState.pushMatrix();
            GLES30.glDisable(GLES30.GL_CULL_FACE);
            MatrixState.translate(5.0f, 15, -66);
            MatrixState.scale(objBeiZi, objBeiZi, objBeiZi);
            if (beizi != null) {
                beizi.drawSelf(texbeizi);
            }
            if (beiziba != null) {
                beiziba.drawSelf(texfangding);
            }
            if (beizigai != null) {
                beizigai.drawSelf(texfangding);
            }
            //打开背面剪裁
            GLES30.glEnable(GLES30.GL_CULL_FACE);
            MatrixState.popMatrix();
            //将灯光调回教室中间位置
            MatrixState.setLightLocation(0, 100, -70);

            MatrixState.pushMatrix();
            MatrixState.scale(objScale, objScale, objScale);
            if (diban != null)//绘制地板
            {
                diban.drawSelf(texdiban);
            }
            //开启混合
            GLES30.glEnable(GLES30.GL_BLEND);
            //设置混合因子,其中第一个为源因子，第二个为目标因子
            GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA, GLES30.GL_ONE_MINUS_SRC_ALPHA);
            if (fangding != null)//绘制房顶
            {
                fangding.drawSelf(texfangding);
            }
            if (chuanghu != null)//绘制窗户
            {
                chuanghu.drawSelf(texchuanghu);
            }
            //关闭混合
            GLES30.glDisable(GLES30.GL_BLEND);
            if (qiang1 != null)//绘制墙壁
            {
                qiang1.drawSelf(texdiban);
            }
            if (men != null)//绘制门
            {
                men.drawSelf(textWhiteBan);
            }
            if (menkuang != null)//绘制门框
            {
                menkuang.drawSelf(textWhiteBan);
            }
            if (huangzhu != null)//绘制黄色柱子
            {
                huangzhu.drawSelf(texdiban);
            }
            if (baizhu != null)//绘制白色柱子
            {
                baizhu.drawSelf(texfangding);
            }
            if (jiangtai != null)//绘制讲台
            {
                jiangtai.drawSelf(texdiban);
            }
            if (hongqi != null)//绘制红旗
            {
                hongqi.drawSelf(texthongqi);
            }
            if (jiangzhuo != null)//绘制讲桌
            {
                jiangzhuo.drawSelf(texchair);
            }
            if (baiban != null)//绘制白板
            {
                baiban.drawSelf(texheiban);
            }
            if (heiban != null)//绘制黑板
            {
                heiban.drawSelf(texheiban);
            }
            MatrixState.popMatrix();

            MatrixState.pushMatrix();//绘制椅子桌子
            MatrixState.scale(objScale, objScale, objScale);
            if (zhuoheyi != null)//绘制第一排
            {
                zhuoheyi.drawSelf(texchair);
            }
            MatrixState.translate(2000, 0, 0);
            if (zhuoheyi != null) {
                zhuoheyi.drawSelf(texchair);
            }
            MatrixState.translate(2000, 0, 0);
            if (zhuoheyi != null) {
                zhuoheyi.drawSelf(texchair);
            }
            MatrixState.translate(0, 0, -1200);//第二排
            if (zhuoheyi != null) {
                zhuoheyi.drawSelf(texchair);
            }
            MatrixState.translate(-2000, 0, 0);
            if (zhuoheyi != null) {
                zhuoheyi.drawSelf(texchair);
            }
            MatrixState.translate(-2000, 0, 0);
            if (zhuoheyi != null) {
                zhuoheyi.drawSelf(texchair);
            }
            MatrixState.translate(0, 0, -1200);//第三排
            if (zhuoheyi != null) {
                zhuoheyi.drawSelf(texchair);
            }
            MatrixState.translate(2000, 0, 0);
            if (zhuoheyi != null) {
                zhuoheyi.drawSelf(texchair);
            }
            MatrixState.translate(2000, 0, 0);
            if (zhuoheyi != null) {
                zhuoheyi.drawSelf(texchair);
            }
            MatrixState.translate(0, 0, -1200);//第四排
            if (zhuoheyi != null) {
                zhuoheyi.drawSelf(texchair);
            }
            MatrixState.translate(-2000, 0, 0);
            if (zhuoheyi != null) {
                zhuoheyi.drawSelf(texchair);
            }
            MatrixState.translate(-2000, 0, 0);
            if (zhuoheyi != null) {
                zhuoheyi.drawSelf(texchair);
            }
            MatrixState.translate(0, 0, -1200);//第五排
            if (zhuoheyi != null) {
                zhuoheyi.drawSelf(texchair);
            }
            MatrixState.translate(2000, 0, 0);
            if (zhuoheyi != null) {
                zhuoheyi.drawSelf(texchair);
            }
            MatrixState.translate(2000, 0, 0);
            if (zhuoheyi != null) {
                zhuoheyi.drawSelf(texchair);
            }
            MatrixState.popMatrix();
        }

        //第二次绘制通过距离绘制
        private void drawBlur() {
            //设置视窗大小及位置
            GLES30.glViewport(0, 0, mSurfaceWidth, mSurfaceHeight);
            GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, 0);//绑定帧缓冲id
            //清除深度缓冲与颜色缓冲
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
            //调用此方法计算产生透视投影矩阵
            float ratio = (float) mSurfaceWidth / mSurfaceHeight;
            MatrixState.setProjectFrustum(-ratio * 5, ratio * 5, -5, 5, near, 3000);
            //调用此方法产生摄像机9参数位置矩阵
            MatrixState.setCamera(cx, cy, cz, tx, ty, tz, dx, dy, dz);
            //绘制场景
            //绘制杯子
            MatrixState.pushMatrix();
            GLES30.glDisable(GLES30.GL_CULL_FACE);
            MatrixState.translate(-4.0f, 15, -66);
            MatrixState.scale(objBeiZi, objBeiZi, objBeiZi);
            if (beizi != null) {
                beizi.drawSelfTwo(mTexture, mSurfaceWidth, mSurfaceHeight);
            }
            if (beiziba != null) {
                beiziba.drawSelfTwo(mTexture, mSurfaceWidth, mSurfaceHeight);
            }
            if (beizigai != null) {
                beizigai.drawSelfTwo(mTexture, mSurfaceWidth, mSurfaceHeight);
            }
            //打开背面剪裁
            GLES30.glEnable(GLES30.GL_CULL_FACE);
            MatrixState.popMatrix();

            //绘制杯子
            MatrixState.pushMatrix();
            GLES30.glDisable(GLES30.GL_CULL_FACE);
            MatrixState.translate(5.0f, 15, -66);
            MatrixState.scale(objBeiZi, objBeiZi, objBeiZi);
            if (beizi != null) {
                beizi.drawSelfTwo(mTexture, mSurfaceWidth, mSurfaceHeight);
            }
            if (beiziba != null) {
                beiziba.drawSelfTwo(mTexture, mSurfaceWidth, mSurfaceHeight);
            }
            if (beizigai != null) {
                beizigai.drawSelfTwo(mTexture, mSurfaceWidth, mSurfaceHeight);
            }
            //打开背面剪裁
            GLES30.glEnable(GLES30.GL_CULL_FACE);
            MatrixState.popMatrix();

            MatrixState.pushMatrix();
            MatrixState.scale(objScale, objScale, objScale);
            if (diban != null)//绘制地板
            {
                diban.drawSelfTwo(mTexture, mSurfaceWidth, mSurfaceHeight);
            }
            if (qiang1 != null)//绘制墙壁
            {
                qiang1.drawSelfTwo(mTexture, mSurfaceWidth, mSurfaceHeight);
            }
            if (fangding != null)//绘制房顶
            {
                fangding.drawSelfTwo(mTexture, mSurfaceWidth, mSurfaceHeight);
            }
            if (chuanghu != null)//绘制窗户
            {
                chuanghu.drawSelfTwo(mTexture, mSurfaceWidth, mSurfaceHeight);
            }
            if (men != null)//绘制门
            {
                men.drawSelfTwo(mTexture, mSurfaceWidth, mSurfaceHeight);
            }
            if (menkuang != null)//绘制门框
            {
                menkuang.drawSelfTwo(mTexture, mSurfaceWidth, mSurfaceHeight);
            }
            if (huangzhu != null)//绘制黄色柱子
            {
                huangzhu.drawSelfTwo(mTexture, mSurfaceWidth, mSurfaceHeight);
            }
            if (baizhu != null)//绘制白色柱子
            {
                baizhu.drawSelfTwo(mTexture, mSurfaceWidth, mSurfaceHeight);
            }
            if (jiangtai != null)//绘制讲台
            {
                jiangtai.drawSelfTwo(mTexture, mSurfaceWidth, mSurfaceHeight);
            }
            if (hongqi != null)//绘制红旗
            {
                hongqi.drawSelfTwo(mTexture, mSurfaceWidth, mSurfaceHeight);
            }
            if (jiangzhuo != null)//绘制讲桌
            {
                jiangzhuo.drawSelfTwo(mTexture, mSurfaceWidth, mSurfaceHeight);
            }
            if (baiban != null)//绘制白板
            {
                baiban.drawSelfTwo(mTexture, mSurfaceWidth, mSurfaceHeight);
            }
            if (heiban != null)//绘制黑板
            {
                heiban.drawSelfTwo(mTexture, mSurfaceWidth, mSurfaceHeight);
            }
            MatrixState.popMatrix();

            MatrixState.pushMatrix();//绘制椅子桌子
            MatrixState.scale(objScale, objScale, objScale);
            if (zhuoheyi != null)//绘制第一排
            {
                zhuoheyi.drawSelfTwo(mTexture, mSurfaceWidth, mSurfaceHeight);
            }
            MatrixState.translate(2000, 0, 0);
            if (zhuoheyi != null) {
                zhuoheyi.drawSelfTwo(mTexture, mSurfaceWidth, mSurfaceHeight);
            }
            MatrixState.translate(2000, 0, 0);
            if (zhuoheyi != null) {
                zhuoheyi.drawSelfTwo(mTexture, mSurfaceWidth, mSurfaceHeight);
            }
            MatrixState.translate(0, 0, -1200);//第二排
            if (zhuoheyi != null) {
                zhuoheyi.drawSelfTwo(mTexture, mSurfaceWidth, mSurfaceHeight);
            }
            MatrixState.translate(-2000, 0, 0);
            if (zhuoheyi != null) {
                zhuoheyi.drawSelfTwo(mTexture, mSurfaceWidth, mSurfaceHeight);
            }
            MatrixState.translate(-2000, 0, 0);
            if (zhuoheyi != null) {
                zhuoheyi.drawSelfTwo(mTexture, mSurfaceWidth, mSurfaceHeight);
            }
            MatrixState.translate(0, 0, -1200);//第三排
            if (zhuoheyi != null) {
                zhuoheyi.drawSelfTwo(mTexture, mSurfaceWidth, mSurfaceHeight);
            }
            MatrixState.translate(2000, 0, 0);
            if (zhuoheyi != null) {
                zhuoheyi.drawSelfTwo(mTexture, mSurfaceWidth, mSurfaceHeight);
            }
            MatrixState.translate(2000, 0, 0);
            if (zhuoheyi != null) {
                zhuoheyi.drawSelfTwo(mTexture, mSurfaceWidth, mSurfaceHeight);
            }
            MatrixState.translate(0, 0, -1200);//第四排
            if (zhuoheyi != null) {
                zhuoheyi.drawSelfTwo(mTexture, mSurfaceWidth, mSurfaceHeight);
            }
            MatrixState.translate(-2000, 0, 0);
            if (zhuoheyi != null) {
                zhuoheyi.drawSelfTwo(mTexture, mSurfaceWidth, mSurfaceHeight);
            }
            MatrixState.translate(-2000, 0, 0);
            if (zhuoheyi != null) {
                zhuoheyi.drawSelfTwo(mTexture, mSurfaceWidth, mSurfaceHeight);
            }
            MatrixState.translate(0, 0, -1200);//第五排
            if (zhuoheyi != null) {
                zhuoheyi.drawSelfTwo(mTexture, mSurfaceWidth, mSurfaceHeight);
            }
            MatrixState.translate(2000, 0, 0);
            if (zhuoheyi != null) {
                zhuoheyi.drawSelfTwo(mTexture, mSurfaceWidth, mSurfaceHeight);
            }
            MatrixState.translate(2000, 0, 0);
            if (zhuoheyi != null) {
                zhuoheyi.drawSelfTwo(mTexture, mSurfaceWidth, mSurfaceHeight);
            }
            MatrixState.popMatrix();
        }
    }
}
