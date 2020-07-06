package edu.jiangxin.droiddemo.graphics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import edu.jiangxin.droiddemo.R;
import edu.jiangxin.droiddemo.graphics.opengl.es.book.MapBuffers.MapBuffers;
import edu.jiangxin.droiddemo.graphics.opengl.es.book.VertexArrayObjects.VAO;
import edu.jiangxin.droiddemo.graphics.opengl.es.book.VertexBufferObjects.VBO;
import edu.jiangxin.droiddemo.graphics.opengl.es.book.example6_3.Example6_3;
import edu.jiangxin.droiddemo.graphics.opengl.es.book.example6_6.Example6_6;
import edu.jiangxin.droiddemo.graphics.opengl.es.book.hellotriangle.HelloTriangle;
import edu.jiangxin.droiddemo.graphics.opengl.es.book.mipmap2d.MipMap2D;
import edu.jiangxin.droiddemo.graphics.opengl.es.book.multitexture.MultiTexture;
import edu.jiangxin.droiddemo.graphics.opengl.es.book.particlesystem.ParticleSystem;
import edu.jiangxin.droiddemo.graphics.opengl.es.book.simpletexture2d.SimpleTexture2D;
import edu.jiangxin.droiddemo.graphics.opengl.es.book.simpletexturecubemap.SimpleTextureCubemap;
import edu.jiangxin.droiddemo.graphics.opengl.es.book.simplevertexshader.SimpleVertexShader;
import edu.jiangxin.droiddemo.graphics.opengl.es.book.texturewrap.TextureWrap;
import edu.jiangxin.droiddemo.graphics.opengles.OpenGLDemoActivity;
import edu.jiangxin.droiddemo.graphics.rajawali.RajawaliDemoActivity;

public class VariousGraphicsActivity extends Activity implements View.OnClickListener {

    private TextView mTvRajaWaliDemo, mTvOpenGLDemo;
    private TextView mTvExample6_3, mTvExample6_6, mTvHelloTriangle, mTvMapBuffers, mTvMipmap2D, mTvMultiTexture, mTvParticleSystem, mTvSimpleTexture2D, mTvSimpleTextureCubeMap,
            mTvSimpleVetexShader, mTvTextureWrap, mTvVertexArrayObjects, mTvVertexBufferObjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_various_graphics);

        mTvRajaWaliDemo = findViewById(R.id.tv_rajawali);
        mTvOpenGLDemo = findViewById(R.id.tv_opengl);

        mTvExample6_3 = findViewById(R.id.tv_Example6_3);
        mTvExample6_6 = findViewById(R.id.tv_Example6_6);
        mTvHelloTriangle = findViewById(R.id.tv_HelloTriangle);
        mTvMapBuffers = findViewById(R.id.tv_MapBuffers);
        mTvMipmap2D = findViewById(R.id.tv_Mipmap2D);
        mTvMultiTexture = findViewById(R.id.tv_MultiTexture);
        mTvParticleSystem = findViewById(R.id.tv_ParticleSystem);
        mTvSimpleTexture2D = findViewById(R.id.tv_SimpleTexture2D);
        mTvSimpleTextureCubeMap = findViewById(R.id.tv_SimpleTextureCubeMap);
        mTvSimpleVetexShader = findViewById(R.id.tv_SimpleVetexShader);
        mTvTextureWrap = findViewById(R.id.tv_TextureWrap);
        mTvVertexArrayObjects = findViewById(R.id.tv_VertexArrayObjects);
        mTvVertexBufferObjects = findViewById(R.id.tv_VertexBufferObjects);

        mTvRajaWaliDemo.setOnClickListener(this);
        mTvOpenGLDemo.setOnClickListener(this);

        mTvExample6_3.setOnClickListener(this);
        mTvExample6_6.setOnClickListener(this);
        mTvHelloTriangle.setOnClickListener(this);
        mTvMapBuffers.setOnClickListener(this);
        mTvMipmap2D.setOnClickListener(this);
        mTvMultiTexture.setOnClickListener(this);
        mTvParticleSystem.setOnClickListener(this);
        mTvSimpleTexture2D.setOnClickListener(this);
        mTvSimpleTextureCubeMap.setOnClickListener(this);
        mTvSimpleVetexShader.setOnClickListener(this);
        mTvTextureWrap.setOnClickListener(this);
        mTvVertexArrayObjects.setOnClickListener(this);
        mTvVertexBufferObjects.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_rajawali:
                enterTestActivity(RajawaliDemoActivity.class);
                break;
            case R.id.tv_opengl:
                enterTestActivity(OpenGLDemoActivity.class);
                break;
            case R.id.tv_Example6_3:
                enterTestActivity(Example6_3.class);
                break;
            case R.id.tv_Example6_6:
                enterTestActivity(Example6_6.class);
                break;
            case R.id.tv_HelloTriangle:
                enterTestActivity(HelloTriangle.class);
                break;
            case R.id.tv_MapBuffers:
                enterTestActivity(MapBuffers.class);
                break;
            case R.id.tv_Mipmap2D:
                enterTestActivity(MipMap2D.class);
                break;
            case R.id.tv_MultiTexture:
                enterTestActivity(MultiTexture.class);
                break;
            case R.id.tv_ParticleSystem:
                enterTestActivity(ParticleSystem.class);
                break;
            case R.id.tv_SimpleTexture2D:
                enterTestActivity(SimpleTexture2D.class);
                break;
            case R.id.tv_SimpleTextureCubeMap:
                enterTestActivity(SimpleTextureCubemap.class);
                break;
            case R.id.tv_SimpleVetexShader:
                enterTestActivity(SimpleVertexShader.class);
                break;
            case R.id.tv_TextureWrap:
                enterTestActivity(TextureWrap.class);
                break;
            case R.id.tv_VertexArrayObjects:
                enterTestActivity(VAO.class);
                break;
            case R.id.tv_VertexBufferObjects:
                enterTestActivity(VBO.class);
                break;
            default:
                break;
        }
    }

    private void enterTestActivity(Class clazz) {
        startActivity(new Intent(this, clazz));
    }
}