package edu.jiangxin.droiddemo.graphics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import edu.jiangxin.droiddemo.R;
import edu.jiangxin.droiddemo.graphics.opengles.ball.BallActivity;
import edu.jiangxin.droiddemo.graphics.opengles.book.vbo.MapBuffers;
import edu.jiangxin.droiddemo.graphics.opengles.book.vbo.VertexArrayObjects;
import edu.jiangxin.droiddemo.graphics.opengles.book.vbo.VertexBufferObjects;
import edu.jiangxin.droiddemo.graphics.opengles.book.vbo.VertexWithoutBuffer;
import edu.jiangxin.droiddemo.graphics.opengles.book.vbo.SeparateVboPerAttribute;
import edu.jiangxin.droiddemo.graphics.opengles.book.hellotriangle.HelloTriangle;
import edu.jiangxin.droiddemo.graphics.opengles.book.texture.MipMap2D;
import edu.jiangxin.droiddemo.graphics.opengles.book.fragshader.MultiTexture;
import edu.jiangxin.droiddemo.graphics.opengles.book.advanced.ParticleSystem;
import edu.jiangxin.droiddemo.graphics.opengles.book.texture.SimpleTexture2D;
import edu.jiangxin.droiddemo.graphics.opengles.book.texture.SimpleTextureCubemap;
import edu.jiangxin.droiddemo.graphics.opengles.book.vertexshader.SimpleVertexShader;
import edu.jiangxin.droiddemo.graphics.opengles.book.texture.TextureWrap;
import edu.jiangxin.droiddemo.graphics.opengles.matrix.OpenGLMatrixActivity;
import edu.jiangxin.droiddemo.graphics.opengles.pbuffer.PBufferActivity;
import edu.jiangxin.droiddemo.graphics.opengles.simple.OpenGLDemoActivity;
import edu.jiangxin.droiddemo.graphics.rajawali.RajawaliDemoActivity;

public class VariousGraphicsActivity extends Activity implements View.OnClickListener {

    private TextView mTvOpenGLDemo;
    private TextView mTvMatrix;
    private TextView mTvBall;
    private TextView mTvHelloTriangle;
    private TextView mTvVertexWithoutBuffer, mTvVertexBufferObjects, mTvSeparateVboPerAttribute, mTvVertexArrayObjects, mTvMapBuffers;
    private TextView mTvSimpleVetexShader;
    private TextView mTvSimpleTexture2D, mTvMipmap2D, mTvTextureWrap, mTvSimpleTextureCubeMap;
    private TextView mTvMultiTexture, mTvParticleSystem;
    private TextView mTvPBuffer;
    private TextView mTvRajaWaliDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_various_graphics);

        mTvOpenGLDemo = findViewById(R.id.tv_opengl);

        mTvMatrix = findViewById(R.id.tv_matrix);

        mTvBall = findViewById(R.id.tv_ball);

        mTvHelloTriangle = findViewById(R.id.tv_HelloTriangle);

        mTvVertexWithoutBuffer = findViewById(R.id.tv_VertexWithoutBuffer);
        mTvVertexBufferObjects = findViewById(R.id.tv_VertexBufferObjects);
        mTvSeparateVboPerAttribute = findViewById(R.id.tv_SeparateVboPerAttribute);
        mTvVertexArrayObjects = findViewById(R.id.tv_VertexArrayObjects);
        mTvMapBuffers = findViewById(R.id.tv_MapBuffers);

        mTvSimpleVetexShader = findViewById(R.id.tv_SimpleVetexShader);

        mTvSimpleTexture2D = findViewById(R.id.tv_SimpleTexture2D);
        mTvMipmap2D = findViewById(R.id.tv_Mipmap2D);
        mTvTextureWrap = findViewById(R.id.tv_TextureWrap);
        mTvSimpleTextureCubeMap = findViewById(R.id.tv_SimpleTextureCubeMap);

        mTvMultiTexture = findViewById(R.id.tv_MultiTexture);

        mTvParticleSystem = findViewById(R.id.tv_ParticleSystem);

        mTvPBuffer = findViewById(R.id.tv_PBuffer);

        mTvRajaWaliDemo = findViewById(R.id.tv_rajawali);

        mTvOpenGLDemo.setOnClickListener(this);

        mTvMatrix.setOnClickListener(this);

        mTvBall.setOnClickListener(this);

        mTvHelloTriangle.setOnClickListener(this);

        mTvVertexWithoutBuffer.setOnClickListener(this);
        mTvVertexBufferObjects.setOnClickListener(this);
        mTvSeparateVboPerAttribute.setOnClickListener(this);
        mTvVertexArrayObjects.setOnClickListener(this);
        mTvMapBuffers.setOnClickListener(this);

        mTvSimpleVetexShader.setOnClickListener(this);

        mTvSimpleTexture2D.setOnClickListener(this);
        mTvMipmap2D.setOnClickListener(this);
        mTvTextureWrap.setOnClickListener(this);
        mTvSimpleTextureCubeMap.setOnClickListener(this);

        mTvMultiTexture.setOnClickListener(this);

        mTvParticleSystem.setOnClickListener(this);

        mTvPBuffer.setOnClickListener(this);

        mTvRajaWaliDemo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_opengl:
                enterTestActivity(OpenGLDemoActivity.class);
                break;
            case R.id.tv_matrix:
                enterTestActivity(OpenGLMatrixActivity.class);
                break;
            case R.id.tv_ball:
                enterTestActivity(BallActivity.class);
                break;
            case R.id.tv_HelloTriangle:
                enterTestActivity(HelloTriangle.class);
                break;
            case R.id.tv_VertexWithoutBuffer:
                enterTestActivity(VertexWithoutBuffer.class);
                break;
            case R.id.tv_VertexBufferObjects:
                enterTestActivity(VertexBufferObjects.class);
                break;
            case R.id.tv_SeparateVboPerAttribute:
                enterTestActivity(SeparateVboPerAttribute.class);
                break;
            case R.id.tv_VertexArrayObjects:
                enterTestActivity(VertexArrayObjects.class);
                break;
            case R.id.tv_MapBuffers:
                enterTestActivity(MapBuffers.class);
                break;
            case R.id.tv_SimpleVetexShader:
                enterTestActivity(SimpleVertexShader.class);
                break;
            case R.id.tv_SimpleTexture2D:
                enterTestActivity(SimpleTexture2D.class);
                break;
            case R.id.tv_Mipmap2D:
                enterTestActivity(MipMap2D.class);
                break;
            case R.id.tv_TextureWrap:
                enterTestActivity(TextureWrap.class);
                break;
            case R.id.tv_SimpleTextureCubeMap:
                enterTestActivity(SimpleTextureCubemap.class);
                break;
            case R.id.tv_MultiTexture:
                enterTestActivity(MultiTexture.class);
                break;
            case R.id.tv_ParticleSystem:
                enterTestActivity(ParticleSystem.class);
                break;
            case R.id.tv_PBuffer:
                enterTestActivity(PBufferActivity.class);
                break;
            case R.id.tv_rajawali:
                enterTestActivity(RajawaliDemoActivity.class);
                break;
            default:
                break;
        }
    }

    private void enterTestActivity(Class clazz) {
        startActivity(new Intent(this, clazz));
    }
}