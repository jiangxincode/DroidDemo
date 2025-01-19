package edu.jiangxin.droiddemo.opengl.matrix.samples.fragment

import android.content.Context
import android.opengl.GLSurfaceView
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.jiangxin.droiddemo.R
import edu.jiangxin.droiddemo.databinding.FragmentSampleLightingBinding
import edu.jiangxin.droiddemo.opengl.Utils
import edu.jiangxin.droiddemo.opengl.matrix.samples.renderer.lighting.BumpedLightRenderer
import edu.jiangxin.droiddemo.opengl.matrix.samples.renderer.lighting.DirectionalLightRenderer
import edu.jiangxin.droiddemo.opengl.matrix.samples.renderer.lighting.PointLightRenderer
import edu.jiangxin.droiddemo.opengl.matrix.samples.renderer.lighting.SpotLightRenderer

/**
 *      光照例子，包括平行光、点光、聚光和法向图
 *      Lighting samples, including directional light, point light, spot light and bumped
 **/

class SampleLighting : Fragment() {
    private var _binding: FragmentSampleLightingBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSampleLightingBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.directionalLight.setOnClickListener {
            updateSample(binding.glSurfaceViewContainer, DirectionalLightRenderer())
        }
        binding.pointLight.setOnClickListener {
            updateSample(binding.glSurfaceViewContainer, PointLightRenderer())
        }
        binding.spotLight.setOnClickListener {
            updateSample(binding.glSurfaceViewContainer, SpotLightRenderer())
        }
        binding.bumped.setOnClickListener {
            updateSample(binding.glSurfaceViewContainer, BumpedLightRenderer())
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