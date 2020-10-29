package com.hzrelaper.hospitalmachine.ui.main.fragment

import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder
import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import com.hzrelaper.hospitalmachine.R
import com.hzrelaper.hospitalmachine.nettools.int.QuestionResult
import com.hzrelaper.hospitalmachine.nettools.int.QuestionServiceImp
import com.hzrelaper.hospitalmachine.ui.AddQuestionActivity
import com.hzrelaper.hospitalmachine.ui.detailquestion.QuestionDetail
import com.hzrelaper.hospitalmachine.ui.main.fragment.topics.NormalAdapterViewAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class TopicsFragment : Fragment(), BGARefreshLayout.BGARefreshLayoutDelegate {

    private lateinit var mSearchBar: EditText
    private lateinit var mRefreshLayout: BGARefreshLayout
    private lateinit var mAdapter: NormalAdapterViewAdapter
    private lateinit var mMainView: View
    private lateinit var mDataLv: ListView
    private  var mCurrentPage=0
    private  val mPageSize = 10
    private var mQuestionStatus = -1
    private  var mUserId = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            var id = arguments!!.getString("userId")
            if (id != null) {
                mUserId = id.toInt()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_topics, container, false)

        mMainView = view;
        initRefreshLayout(view.findViewById(R.id.bgaRefreshLayout))

        mMainView.findViewById<Button>(R.id.btn_new_question).setOnClickListener(object :
            View.OnClickListener {
            override fun onClick(p0: View?) {
                var intent = Intent()
                this@TopicsFragment.context?.let {
                    intent.setClass(
                        it,
                        AddQuestionActivity::class.java
                    )
                }
                startActivity(intent)
            }
        })
        mSearchBar = mMainView.findViewById<EditText>(R.id.searchview)
        mSearchBar.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(p0: View?, keycode: Int, event: KeyEvent?): Boolean {
                if (event?.action == KeyEvent.ACTION_DOWN && keycode == KeyEvent.KEYCODE_ENTER) {
                    mCurrentPage = 0
                    val imm =
                        this@TopicsFragment.context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
                    imm!!.hideSoftInputFromWindow(p0?.getWindowToken(), 0)
                    loadPageData()
                }
                return false
            }
        })

        val radiolistener = View.OnClickListener(){
            onRadioButtonClicked(it)
        }
        mMainView.findViewById<RadioButton>(R.id.all).setOnClickListener(radiolistener)
        mMainView.findViewById<RadioButton>(R.id.resolved).setOnClickListener(radiolistener)
        mMainView.findViewById<RadioButton>(R.id.not_resolved).setOnClickListener(radiolistener)

        loadPageData()

        return  view;
    }

    fun onRadioButtonClicked(view: View){
        val checked = (view as RadioButton).isChecked
        if (checked){
            when(view.id){
                R.id.all -> mQuestionStatus = -1
                R.id.resolved -> mQuestionStatus = 1 // 已解决是1
                R.id.not_resolved -> mQuestionStatus = 0 // 未解决是 0
            }
            mCurrentPage = 0
            loadPageData()
        }
    }

    private fun initRefreshLayout(refreshLayout: BGARefreshLayout) {
        mRefreshLayout =  mMainView.findViewById(R.id.bgaRefreshLayout);
//        // 为BGARefreshLayout 设置代理
        mRefreshLayout.setDelegate(this);
        mDataLv = mMainView.findViewById(R.id.listview1)
        mAdapter = NormalAdapterViewAdapter(this.context)
        mRefreshLayout.setRefreshViewHolder(BGANormalRefreshViewHolder(this.context, true))
        mDataLv.adapter = mAdapter
        mDataLv.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(adapterview: AdapterView<*>?, view: View?, p2: Int, p3: Long) {
                val id = view?.findViewById<TextView>(R.id.tv_item_normal_id)?.text.toString()
                val status =view?.findViewById<TextView>(R.id.tv_item_normal_status)?.text.toString()
                val intent = Intent()
                this@TopicsFragment.context?.let { intent.setClass(it, QuestionDetail::class.java) }
                intent.putExtra("id", id)
                intent.putExtra("status",status)
                startActivity(intent)
            }
        })
    }

    public  fun setUserId(userId: Int){

    }
    private  fun loadPageData(){
        if (mCurrentPage < 0)
            mCurrentPage = 0
        var start = mPageSize * mCurrentPage
        var len = mPageSize
        var searchtext = mSearchBar.text.toString()
        var status = mQuestionStatus
        QuestionServiceImp().getList(
            start,
            len,
            searchtext,
            status,
            mUserId,
            object : Callback<QuestionResult?> {
                override fun onResponse(
                    call: Call<QuestionResult?>,
                    response: Response<QuestionResult?>
                ) {
                    mRefreshLayout.endLoadingMore()
                    var quResult = response.body()
                    if (mCurrentPage == 0) {
                        mDataLv.smoothScrollToPosition(0)
                        mAdapter.data = quResult?.data
                    } else {
                        mAdapter.addMoreData(quResult?.data)
                        if (quResult?.data?.size == 0) {
                            toast("没有更多数据了")
                        }
                    }
                }

                override fun onFailure(call: Call<QuestionResult?>, t: Throwable) {
                    mCurrentPage -= 1
                    toast("无法连接服务器")
                    mRefreshLayout.endLoadingMore()
                }

            })
    }
    fun toast(msg: String){
        try {
            Toast.makeText(this@TopicsFragment.context, msg, Toast.LENGTH_SHORT).show()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
    override fun onBGARefreshLayoutBeginRefreshing(refreshLayout: BGARefreshLayout?) {
        mRefreshLayout.endRefreshing()
        loadPageData()
    }

    override fun onBGARefreshLayoutBeginLoadingMore(refreshLayout: BGARefreshLayout?): Boolean {
        mCurrentPage += 1
        loadPageData()
        return  true
    }


}