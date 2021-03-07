package edu.jiangxin.droiddemo.opengl.matrix.samples.fragment

import android.content.Context
import android.opengl.GLSurfaceView
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.jiangxin.droiddemo.R
import edu.jiangxin.droiddemo.opengl.Utils
import edu.jiangxin.droiddemo.opengl.matrix.samples.renderer.lighting.BumpedLightRenderer
import edu.jiangxin.droiddemo.opengl.matrix.samples.renderer.lighting.DirectionalLightRenderer
import edu.jiangxin.droiddemo.opengl.matrix.samples.renderer.lighting.PointLightRenderer
import edu.jiangxin.droiddemo.opengl.matrix.samples.renderer.lighting.SpotLightRenderer
import kotlinx.android.synthetic.main.fragment_sample_lighting.*

/**
 *      光照例子，包括平行光、点光、聚光和法向图
 *      Lighting samples, including directional light, point light, spot light and bumped
 **/

class SampleLighting : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sample_lighting, container,  false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        directionalLight.setOnClickListener {
            updateSample(glSurfaceViewContainer, DirectionalLightRenderer())
        }
        pointLight.setOnClickListener {
            updateSample(glSurfaceViewContainer, PointLightRenderer())
        }
        spotLight.setOnClickListener {
            updateSample(glSurfaceViewContainer, SpotLightRenderer())
        }
        bumped.setOnClickListener {
            updateSample(glSurfaceViewContainer, BumpedLightRenderer())
        }
    }

    private fun updateSample(rootView: View, renderer: GLSurfaceView.Renderer) {
        rootView as ViewGroup
        rootView.removeAllViews()
        rootView.addView(createGLSurfaceView(rootView.context, renderer))
    }

    private fun createGLSurfaceView(context: Context, renderer: GLSurfaceView.Renderer): GLSurfaceView {
        val glSurfaceView = GLSurfaceView(context)
        glSurfaceView.setEGLContextClientVersion(Utils.OPENGL_ES_VERSION)
        glSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 8, 0)
        glSurfaceView.setRenderer(renderer)
        return glSurfaceView
    }

}