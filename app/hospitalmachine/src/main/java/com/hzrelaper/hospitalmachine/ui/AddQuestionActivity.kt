package com.hzrelaper.hospitalmachine.ui

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.hzrelaper.hospitalmachine.R
import com.hzrelaper.hospitalmachine.data.pref.SharePref
import com.hzrelaper.hospitalmachine.nettools.int.QuestionResult
import com.hzrelaper.hospitalmachine.nettools.int.QuestionServiceImp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddQuestionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_question)
        setTitle("提问题")
    }

    fun submitForm(view: View) {
        var title = findViewById<EditText>(R.id.text_title).text.toString()
        var description = findViewById<EditText>(R.id.text_description).text.toString()
        if (TextUtils.isEmpty(title)){
            AlertDialog.Builder(this).setMessage("标题不能为空").create().show()
        }else{
            AlertDialog.Builder(this).setMessage("确认提交吗？").
                setPositiveButton("确定",object:DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        addQuesiton(title,description)
                    }
                }).
                setNegativeButton("取消",null).create().show()
        }
    }

    fun addQuesiton(title:String,description:String){
        var sharepref = SharePref(this)
        sharepref.getUsername()?.let {
            QuestionServiceImp().addQuestion(it,title,description,object:Callback<QuestionResult?> {
                override fun onResponse(
                    call: Call<QuestionResult?>,
                    response: Response<QuestionResult?>
                ) {
                    if (response.body()?.result == 0){
                        AlertDialog.Builder(this@AddQuestionActivity).setMessage("提交成功,请手动下拉进行刷新").setOnDismissListener(object :DialogInterface.OnDismissListener{
                            override fun onDismiss(p0: DialogInterface?) {
                                finish()
                            }
                        }).create().show()
                    }else{
                        AlertDialog.Builder(this@AddQuestionActivity).setMessage("提交失败 "+ response.body()?.message).create().show()
                    }
                }

                override fun onFailure(call: Call<QuestionResult?>, t: Throwable) {
                    AlertDialog.Builder(this@AddQuestionActivity).setMessage("提交失败，网络错误").create().show()
                }
            })
        }
    }
}