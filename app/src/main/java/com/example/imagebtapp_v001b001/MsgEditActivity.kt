package com.example.imagebtapp_v001b001

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import kotlinx.android.synthetic.main.activity_msg_edit.*

class MsgEditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_msg_edit)
        val intent = intent
        val position = intent.getIntExtra("position", 255)
        var nameAlias = intent.getStringExtra("nameAlias")

        txvNameLocal.text = nameAlias
        txvEditNameAlias.setText(nameAlias)
        Logger.d(LogGbl, "position:$position name:${txvNameLocal.text} aliasName:${txvEditNameAlias.text}")
        btnCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED, intent)
            finish()
        }
        btnOk.setOnClickListener {
            intent.putExtra("nameAlias", txvEditNameAlias.text.toString())
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        Logger.d(LogGbl, "onCreateView")
        return super.onCreateView(name, context, attrs)
    }
}
