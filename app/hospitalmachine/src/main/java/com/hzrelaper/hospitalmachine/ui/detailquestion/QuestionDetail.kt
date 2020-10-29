package com.hzrelaper.hospitalmachine.ui.detailquestion

import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Html.ImageGetter
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.hzrelaper.hospitalmachine.R
import com.hzrelaper.hospitalmachine.data.pref.SharePref
import com.hzrelaper.hospitalmachine.nettools.int.AnswerResult
import com.hzrelaper.hospitalmachine.nettools.int.AnswerServiceImp
import com.hzrelaper.hospitalmachine.nettools.int.QuestionServiceImp
import com.hzrelaper.hospitalmachine.nettools.int.QuestionSingleResult
import org.sufficientlysecure.htmltextview.HtmlResImageGetter
import org.sufficientlysecure.htmltextview.HtmlTextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class QuestionDetail : AppCompatActivity() {

    private lateinit var mLayoutAnswers: LinearLayout
    private lateinit var mTxtAuthorAndTime: TextView
    private lateinit var mTxtDescription: HtmlTextView
    private lateinit var mTxtTitle: TextView
    private var mId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_detail)
        setTitle("问题详情")
        mId = intent.extras?.get("id").toString().toInt()
        mTxtTitle = findViewById<TextView>(R.id.text_title)
        mTxtDescription= findViewById<HtmlTextView>(R.id.html_description)
        mTxtAuthorAndTime= findViewById<TextView>(R.id.text_author_and_time)
        mLayoutAnswers=findViewById<LinearLayout>(R.id.layout_answers)

        refreshUI()
    }

    private  fun refreshUI(){
        var answerView = findViewById<EditText>(R.id.txt_answer)
        answerView.setText("")
        answerView.visibility = View.GONE

        addQuestionUi(mId)
    }

    private fun addAnswersUi(id: Int, questionStatus:Int?) {
        mLayoutAnswers.removeAllViews()
        AnswerServiceImp().getAnswersByQuestionId(mId,object: Callback<AnswerResult?>{
            override fun onResponse(
                call: Call<AnswerResult?>,
                response: Response<AnswerResult?>
            ) {
                var data = response.body()?.data
                if (data != null) {
                    for( entry in data) {
                        var view =
                            layoutInflater.inflate(R.layout.activity_question_detail_answer_item,
                                null)
                        mLayoutAnswers.addView(view)
                        view.findViewById<TextView >(R.id.txt_username).text = entry.username
                        view.findViewById<TextView >(R.id.txt_time).text = entry.renderAnswerTime()
                        view.findViewById<TextView >(R.id.txt_answer_body).text = entry.answerBody
                        if (entry.answerStatus == 1){
                            view.findViewById<View>(R.id.show_answer_status).visibility = View.VISIBLE
                        }
                        var btn = view.findViewById<Button>(R.id.btn_get_this_answer)
                        if (questionStatus == null || questionStatus == 0){
                            btn.visibility = View.VISIBLE
                            btn.setOnClickListener(object :View.OnClickListener{
                                override fun onClick(p0: View?) {
                                    // 采纳此答案
                                    AlertDialog.Builder(this@QuestionDetail).setMessage("确认采用此回答吗？")
                                            .setNegativeButton("取消",null)
                                            .setPositiveButton("确认",object:DialogInterface.OnClickListener{
                                                override fun onClick(p0: DialogInterface?, p1: Int) {
                                                    QuestionServiceImp().useThisAnswer(mId,entry.id,object : Callback<QuestionSingleResult?> {
                                                        override fun onResponse(call: Call<QuestionSingleResult?>, response: Response<QuestionSingleResult?>) {
                                                            AlertDialog.Builder(this@QuestionDetail).setMessage("提交成功")
                                                                    .setOnDismissListener(object :DialogInterface.OnDismissListener{
                                                                        override fun onDismiss(p0: DialogInterface?) {
                                                                            refreshUI()
                                                                        }
                                                                    })
                                                                    .create().show()
                                                        }

                                                        override fun onFailure(call: Call<QuestionSingleResult?>, t: Throwable) {
                                                            Toast.makeText(this@QuestionDetail,"网络错误",0).show()
                                                        }
                                                    })
                                                }
                                            })
                                            .create().show()
                                }
                            })
                        }else{
                            btn.visibility = View.GONE
                        }
                    }
                }
            }

            override fun onFailure(call: Call<AnswerResult?>, t: Throwable) {
                Toast.makeText(this@QuestionDetail,"网络错误",0).show()
            }
        })
    }


    fun addQuestionUi(id: Int){
        QuestionServiceImp().getSingle(id, object : Callback<QuestionSingleResult?> {
            override fun onResponse(
                call: Call<QuestionSingleResult?>,
                response: Response<QuestionSingleResult?>
            ) {
                var entity = response.body()?.data
                if (entity != null) {
                    mTxtTitle.text = entity.name
                    mTxtDescription.setHtml(entity.description.toString(),
                         HtmlResImageGetter(this@QuestionDetail));
                    mTxtAuthorAndTime.text = entity.authorName+"    "+ entity.renderTime()
                    var closeBtn =findViewById<View>(R.id.btn_close_question)
                    if (entity.status == 0){
                        closeBtn.visibility = View.VISIBLE
                    }else{
                        closeBtn.visibility = View.GONE
                    }
                    addAnswersUi(id,entity.status)
                }
            }

            override fun onFailure(call: Call<QuestionSingleResult?>, t: Throwable) {
                Toast.makeText(this@QuestionDetail, "网络错误 " + t.message, 0).show()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> finish()
        }
        return true
    }

    private fun getImageGetter(): ImageGetter? {
        return ImageGetter { source ->
            val drawable: Drawable =
                getResources().getDrawable(source.toInt())
            drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
            drawable
        }
    }

    fun answerQuestion(view: View) {
        var answerView = findViewById<EditText>(R.id.txt_answer)
        if (answerView.visibility != View.VISIBLE){
            answerView.visibility = View.VISIBLE
        }else{
            var answerstr = answerView.text.toString()
            if (TextUtils.isEmpty(answerstr)){
                AlertDialog.Builder(this).setMessage("回答内容不能为空").show()
            }else{
                AlertDialog.Builder(this).setMessage("确认回复吗？").
                    setPositiveButton("确认",object :DialogInterface.OnClickListener{
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            AnswerServiceImp().addAnswer(SharePref(this@QuestionDetail).getUsername().toString(),mId,answerstr,object:Callback<AnswerResult?>{
                                override fun onResponse(
                                    call: Call<AnswerResult?>,
                                    response: Response<AnswerResult?>
                                ) {
                                    if (response.body()?.result == 0){
                                        AlertDialog.Builder(this@QuestionDetail).setMessage("提交成功")
                                                .setOnDismissListener(object :DialogInterface.OnDismissListener{
                                                    override fun onDismiss(p0: DialogInterface?) {
                                                        refreshUI()
                                                    }
                                                })
                                                .create().show()
                                    }else{
                                        Toast.makeText(this@QuestionDetail,"提交失败",0).show()
                                    }
                                }

                                override fun onFailure(call: Call<AnswerResult?>, t: Throwable) {
                                    Toast.makeText(this@QuestionDetail,"网络错误",0).show()
                                }
                            })
                        }
                    }).
                    setNegativeButton("取消",null).show()
            }
        }
    }

    fun eventCloseAnswer(view: View) {
        AlertDialog.Builder(this).setMessage("确认关闭此问题吗？")
                .setNegativeButton("取消",null)
                .setPositiveButton("确认",object :DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        // 关闭问题
                        QuestionServiceImp().closeQuestion(mId,object :Callback<QuestionSingleResult?>{
                            override fun onResponse(call: Call<QuestionSingleResult?>, response: Response<QuestionSingleResult?>) {
                                AlertDialog.Builder(this@QuestionDetail).setMessage("关闭成功")
                                        .setOnDismissListener(object :DialogInterface.OnDismissListener{
                                            override fun onDismiss(p0: DialogInterface?) {
                                                refreshUI()
                                            }
                                        })
                                        .create().show()
                            }

                            override fun onFailure(call: Call<QuestionSingleResult?>, t: Throwable) {
                                Toast.makeText(this@QuestionDetail,"网络错误",0).show()
                            }
                        })
                    }
                })
                .create().show()
    }
}