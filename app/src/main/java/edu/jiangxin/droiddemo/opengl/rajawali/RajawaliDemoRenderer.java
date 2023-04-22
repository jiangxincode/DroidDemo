package edu.jiangxin.droiddemo.opengl.rajawali;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;

import org.rajawali3d.lights.DirectionalLight;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.methods.DiffuseMethod;
import org.rajawali3d.materials.textures.ATexture;
import org.rajawali3d.materials.textures.Texture;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.primitives.Sphere;
import org.rajawali3d.renderer.Renderer;

import edu.jiangxin.droiddemo.R;

public class RajawaliDemoRenderer extends Renderer {
    private static final String TAG = "RajawaliDemoRenderer";

    private Sphere mEarthSphere;

    public RajawaliDemoRenderer(Context context) {
        super(context);
    }

    @Override
    public void initScene() {
        DirectionalLight directionalLight = new DirectionalLight(1f, .2f, -1.0f);
        directionalLight.setColor(1.0f, 1.0f, 1.0f);
        directionalLight.setPower(2);
        getCurrentScene().addLight(directionalLight);

        Material material = new Material();
        material.enableLighting(true);
        material.setDiffuseMethod(new DiffuseMethod.Lambert());
        material.setColor(0);

        Texture earthTexture = new Texture("Earth", R.drawable.earthtruecolor_nasa_big);
        try {
            material.addTexture(earthTexture);
        } catch (ATexture.TextureException exception) {
            Log.e(TAG, "addTexture exception");
        }

        mEarthSphere = new Sphere(1, 24, 24);
        mEarthSphere.setMaterial(material);
        getCurrentScene().addChild(mEarthSphere);
        getCurrentCamera().setZ(4.2f);
    }


    @Override
    public void onRender(final long elapsedTime, final double deltaTime) {
        super.onRender(elapsedTime, deltaTime);
        mEarthSphere.rotate(Vector3.Axis.Y, 1.0);
    }


    @Override
    public void onTouchEvent(MotionEvent event) {
    }

    @Override
    public void onOffsetsChanged(float x, float y, float z, float w, int i, int j) {
    }
}

