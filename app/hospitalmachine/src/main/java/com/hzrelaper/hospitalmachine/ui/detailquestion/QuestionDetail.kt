package com.hzrelaper.hospitalmachine.ui.detailquestion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hzrelaper.hospitalmachine.R

class QuestionDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_detail)
        setTitle("问题详情")
        var id = intent.extras?.get("id")

    }
}