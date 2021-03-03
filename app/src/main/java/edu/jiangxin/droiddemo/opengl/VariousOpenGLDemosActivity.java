package edu.jiangxin.droiddemo.opengl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import edu.jiangxin.droiddemo.R;
import edu.jiangxin.droiddemo.opengl.ball.BallActivity;
import edu.jiangxin.droiddemo.opengl.blur.OpenGlBlurActivity;
import edu.jiangxin.droiddemo.opengl.book.texture.MipMap2D;
import edu.jiangxin.droiddemo.opengl.book.fragshader.MultiTexture;
import edu.jiangxin.droiddemo.opengl.book.advanced.ParticleSystem;
import edu.jiangxin.droiddemo.opengl.book.texture.SimpleTexture2D;
import edu.jiangxin.droiddemo.opengl.book.texture.SimpleTextureCubemap;
import edu.jiangxin.droiddemo.opengl.book.vertexshader.SimpleVertexShader;
import edu.jiangxin.droiddemo.opengl.book.texture.TextureWrap;
import edu.jiangxin.droiddemo.opengl.matrix.OpenGLMatrixActivity;
import edu.jiangxin.droiddemo.opengl.pbuffer.PBufferActivity;
import edu.jiangxin.droiddemo.opengl.rajawali.RajawaliDemoActivity;

public class VariousOpenGLDemosActivity extends Activity implements View.OnClickListener {

    private TextView mTvBackground, mTvTriangle, mTvTriangleJni, mTvSquare, mTvCircle;
    private TextView mTvMatrix;
    private TextView mTvBall;
    private TextView mTvBlur;
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

        mTvBackground = findViewById(R.id.tv_background);
        mTvTriangle = findViewById(R.id.tv_triangle);
        mTvTriangleJni = findViewById(R.id.tv_triangle_jni);
        mTvSquare = findViewById(R.id.tv_square);
        mTvCircle = findViewById(R.id.tv_circle);

        mTvMatrix = findViewById(R.id.tv_matrix);

        mTvBall = findViewById(R.id.tv_ball);

        mTvBlur = findViewById(R.id.tv_blur);

        mTvVertexWithoutBuffer = findViewById(R.id.tv_VertexArrays);
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

        mTvBackground.setOnClickListener(this);
        mTvTriangle.setOnClickListener(this);
        mTvTriangleJni.setOnClickListener(this);
        mTvSquare.setOnClickListener(this);
        mTvCircle.setOnClickListener(this);

        mTvMatrix.setOnClickListener(this);

        mTvBall.setOnClickListener(this);

        mTvBlur.setOnClickListener(this);

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
            case R.id.tv_background: {
                Intent intent = new Intent(this, VariousRenderersActivity.class);
                intent.setAction(VariousRenderersActivity.ACTION_NAME_BACKGROUND);
                startActivity(intent);
                break;
            }
            case R.id.tv_triangle: {
                Intent intent = new Intent(this, VariousRenderersActivity.class);
                intent.setAction(VariousRenderersActivity.ACTION_NAME_TRIANGLE);
                startActivity(intent);
                break;
            }
            case R.id.tv_triangle_jni: {
                Intent intent = new Intent(this, VariousRenderersActivity.class);
                intent.setAction(VariousRenderersActivity.ACTION_NAME_TRIANGLE_JNI);
                startActivity(intent);
                break;
            }
            case R.id.tv_square: {
                Intent intent = new Intent(this, VariousRenderersActivity.class);
                intent.setAction(VariousRenderersActivity.ACTION_NAME_SQUARE);
                startActivity(intent);
                break;
            }
            case R.id.tv_circle: {
                Intent intent = new Intent(this, VariousRenderersActivity.class);
                intent.setAction(VariousRenderersActivity.ACTION_NAME_CIRCLE);
                startActivity(intent);
                break;
            }
            case R.id.tv_matrix:
                enterTestActivity(OpenGLMatrixActivity.class);
                break;
            case R.id.tv_ball:
                enterTestActivity(BallActivity.class);
                break;
            case R.id.tv_blur:
                enterTestActivity(OpenGlBlurActivity.class);
                break;
            case R.id.tv_VertexArrays: {
                Intent intent = new Intent(this, VariousRenderersActivity.class);
                intent.setAction(VariousRenderersActivity.ACTION_NAME_VERTEX_ARRAYS);
                startActivity(intent);
                break;
            }
            case R.id.tv_VertexBufferObjects: {
                Intent intent = new Intent(this, VariousRenderersActivity.class);
                intent.setAction(VariousRenderersActivity.ACTION_NAME_VERTEX_BUFFER_OBJECTS);
                startActivity(intent);
                break;
            }
            case R.id.tv_SeparateVboPerAttribute: {
                Intent intent = new Intent(this, VariousRenderersActivity.class);
                intent.setAction(VariousRenderersActivity.ACTION_NAME_SEPARATE_VBO_PER_ATTRIBUTE);
                startActivity(intent);
                break;
            }
            case R.id.tv_VertexArrayObjects: {
                Intent intent = new Intent(this, VariousRenderersActivity.class);
                intent.setAction(VariousRenderersActivity.ACTION_NAME_VERTEX_ARRAY_OBJECTS);
                startActivity(intent);
                break;
            }
            case R.id.tv_MapBuffers: {
                Intent intent = new Intent(this, VariousRenderersActivity.class);
                intent.setAction(VariousRenderersActivity.ACTION_NAME_MAP_BUFFERS);
                startActivity(intent);
                break;
            }
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