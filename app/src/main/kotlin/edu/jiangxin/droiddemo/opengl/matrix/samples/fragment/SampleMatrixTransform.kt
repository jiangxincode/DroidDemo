package edu.jiangxin.droiddemo.opengl.matrix.samples.fragment

import android.opengl.GLSurfaceView
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.jiangxin.droiddemo.R
import edu.jiangxin.droiddemo.databinding.FragmentSampleMatrixTransformBinding
import edu.jiangxin.droiddemo.databinding.ItemParameterListBinding
import edu.jiangxin.droiddemo.opengl.Utils
import edu.jiangxin.droiddemo.opengl.matrix.samples.renderer.OnParameterChangeCallback
import edu.jiangxin.droiddemo.opengl.matrix.samples.renderer.SampleMatrixTransformRenderer

/**
 * OpenGL 3D渲染技术：坐标系及矩阵变换: https://juejin.im/post/6844903862973759496
 **/

class SampleMatrixTransform : Fragment() {

    private var _binding: FragmentSampleMatrixTransformBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var glSurfaceView: GLSurfaceView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSampleMatrixTransformBinding.inflate(inflater, container, false)
        val rootView = binding.root
        glSurfaceView = rootView.findViewById(R.id.glsurfaceview)
        glSurfaceView.setEGLContextClientVersion(Utils.OPENGL_ES_VERSION)
        // 设置RGBA颜色缓冲、深度缓冲及stencil缓冲大小
        // Set the size of RGBA、depth and stencil vertexDataBuffer
        glSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 8, 0)

        // 设置对应sample的渲染器
        // Set the corresponding sample renderer
        val renderer = SampleMatrixTransformRenderer()
        glSurfaceView.setRenderer(renderer)
        glSurfaceView.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY

        glSurfaceView.post {
            val parameters = getParameterItems()
            val layoutManager = LinearLayoutManager(activity)
            layoutManager.orientation = RecyclerView.VERTICAL
            binding.parameterList.layoutManager = layoutManager
            val adapter = Adapter(parameters)
            adapter.onParameterChangeCallback = renderer
            binding.parameterList.adapter = adapter

            binding.resetButton.setOnClickListener {
                renderer.onParameterReset()
                adapter.parameters = getParameterItems()
                adapter.notifyDataSetChanged()
                glSurfaceView.requestRender()
            }
        }
        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getParameterItems(): Array<ParameterItem> {
        return arrayOf(
                ParameterItem("translateX", 0f),
                ParameterItem("translateY", 0f),
                ParameterItem("translateZ", 0f),
                ParameterItem("rotateX", 0f),
                ParameterItem("rotateY", 0f),
                ParameterItem("rotateZ", 0f),
                ParameterItem("scaleX", 1f),
                ParameterItem("scaleY", 1f),
                ParameterItem("scaleZ", 1f),
                ParameterItem("cameraPositionX", 0f),
                ParameterItem("cameraPositionY", 0f),
                ParameterItem("cameraPositionZ", 5f),
                ParameterItem("lookAtX", 0f),
                ParameterItem("lookAtY", 0f),
                ParameterItem("lookAtZ", 0f),
                ParameterItem("cameraUpX", 0f),
                ParameterItem("cameraUpY", 1f),
                ParameterItem("cameraUpZ", 0f),
                ParameterItem("nearPlaneLeft", -1f),
                ParameterItem("nearPlaneRight", 1f),
                ParameterItem("nearPlaneBottom", - glSurfaceView.height.toFloat() / glSurfaceView.width),
                ParameterItem("nearPlaneTop", glSurfaceView.height.toFloat() / glSurfaceView.width),
                ParameterItem("nearPlane", 2f),
                ParameterItem("farPlane", 100f)
        )
    }

    inner class Adapter(var parameters: Array<ParameterItem>) : RecyclerView.Adapter<VH>() {

        lateinit var onParameterChangeCallback: OnParameterChangeCallback

        private var _binding: ItemParameterListBinding? = null
        // This property is only valid between onCreateView and
        // onDestroyView.
        private val binding get() = _binding!!

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): VH {
            _binding = ItemParameterListBinding.inflate(LayoutInflater.from(p0.context), null, false)
            return VH(binding);
        }

        override fun getItemCount(): Int {
            return parameters.size
        }

        override fun onBindViewHolder(vh: VH, index: Int) {
            vh.parameterKey.text = parameters[index].key
            vh.parameterValue.text = String.format("%.2f", parameters[index].value)
            vh.reduceButton.setOnClickListener {
                val oldValue = vh.parameterValue.text.toString().toFloat()
                val newValue = oldValue - if (parameters[index].key.startsWith("scale")) { 0.1f } else { 1f }
                vh.parameterValue.text = String.format("%.2f", newValue)
                onParameterChangeCallback.onParameterChange(parameters[index].key, newValue)
                glSurfaceView.requestRender()
            }
            vh.addButton.setOnClickListener {
                val oldValue = vh.parameterValue.text.toString().toFloat()
                val newValue = oldValue + if (parameters[index].key.startsWith("scale")) { 0.1f } else { 1f }
                vh.parameterValue.text = String.format("%.2f", newValue)
                onParameterChangeCallback.onParameterChange(parameters[index].key, newValue)
                glSurfaceView.requestRender()
            }
        }

    }

    inner class VH(itemView: ItemParameterListBinding) : RecyclerView.ViewHolder(itemView.root) {
        val parameterKey = itemView.parameterKey
        val parameterValue = itemView.parameterValue
        val reduceButton = itemView.reduceButton
        val addButton = itemView.addButton
    }

    inner class ParameterItem(val key: String, val value: Float)
}