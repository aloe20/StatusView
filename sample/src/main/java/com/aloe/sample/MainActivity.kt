package com.aloe.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aloe.status.StatusType
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn1.setOnClickListener {
            animator.showView(StatusType.CONTENT)
        }
        btn2.setOnClickListener {
            animator.showView(StatusType.LOADING)
        }
        btn3.setOnClickListener {
            animator.showView(StatusType.ERROR)
        }
        btn4.setOnClickListener {
            animator.showView(StatusType.EMPTY)
        }
        //animator.showView(StatusType.CONTENT)
    }
}
