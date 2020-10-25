package edu.jiangxin.droiddemo.opengl.opengles.matrix.samples.fragment

import android.opengl.GLSurfaceView
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.jiangxin.droiddemo.R
import edu.jiangxin.droiddemo.opengl.opengles.matrix.samples.renderer.SampleColorBlendRenderer

/**
 *      这是一个使用颜色混合的例子
 *      This sample demonstrates color blend
 **/

class SampleColorBlend : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_common_sample, container,  false)
        val glSurfaceView = rootView.findViewById<GLSurfaceView>(R.id.glsurfaceview)
        // 设置RGBA颜色缓冲、深度缓冲及stencil缓冲大小
        // Set the size of RGBA、depth and stencil vertexDataBuffer
        glSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 0, 0)
        // 设置GL版本，这里设置为3.0
        // Set GL version, here I set it to 3.0
        glSurfaceView.setEGLContextClientVersion(3)
        // 设置对应sample的渲染器
        // Set the corresponding sample renderer
        glSurfaceView.setRenderer(SampleColorBlendRenderer())
        return rootView
    }
}