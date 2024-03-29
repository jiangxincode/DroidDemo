package edu.jiangxin.droiddemo.opengl.matrix

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import edu.jiangxin.droiddemo.R
import edu.jiangxin.droiddemo.opengl.matrix.samples.fragment.*

/**
 *      Sample展示类
 *      This sample activity
 **/

class SimpleActivity : AppCompatActivity() {

    private val samples =
        arrayOf(
                SampleShader(),
                SampleTextureArray(),
                SampleBinaryProgram(),
                SampleFenceSync(),
                SampleMultiRenderTarget(),
                SampleEGL(),
                SampleMatrixTransform(),
                SampleColorBlend(),
                SampleLighting()
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)
        title = intent.getStringExtra(GlobalConstants.KEY_SAMPLE_NAME)
        val sampleIndex = intent.getIntExtra(GlobalConstants.KEY_SAMPLE_INDEX, -1)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content, samples[sampleIndex])
        transaction.commit()
    }

}
