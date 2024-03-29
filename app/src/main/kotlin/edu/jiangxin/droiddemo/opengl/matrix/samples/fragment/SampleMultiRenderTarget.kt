package edu.jiangxin.droiddemo.opengl.matrix.samples.fragment

import android.opengl.GLSurfaceView
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.jiangxin.droiddemo.R
import edu.jiangxin.droiddemo.opengl.Utils
import edu.jiangxin.droiddemo.opengl.matrix.samples.renderer.SampleMultiRenderTargetRenderer

/**
 *      这是多渲染目标的例子，可以一次渲染到多个纹理上
 *      This is a sample of multiple render targets, with which we can render to multiple textures at a time
 **/

class SampleMultiRenderTarget : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_common_sample, container,  false)
        val glSurfaceView = rootView.findViewById<GLSurfaceView>(R.id.glsurfaceview)
        glSurfaceView.setEGLContextClientVersion(Utils.OPENGL_ES_VERSION)
        // 设置RGBA颜色缓冲、深度缓冲及stencil缓冲大小
        // Set the size of RGBA、depth and stencil vertexDataBuffer
        glSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 0, 0)

        // 设置对应sample的渲染器
        // Set the corresponding sample renderer
        glSurfaceView.setRenderer(SampleMultiRenderTargetRenderer())
        return rootView
    }
}