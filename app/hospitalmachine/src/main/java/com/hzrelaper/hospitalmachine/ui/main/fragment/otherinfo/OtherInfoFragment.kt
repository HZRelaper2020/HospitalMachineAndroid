package com.hzrelaper.hospitalmachine.ui.main.fragment.otherinfo

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.hzrelaper.hospitalmachine.R
import com.hzrelaper.hospitalmachine.nettools.int.SuggestinoServiceImp
import com.hzrelaper.hospitalmachine.nettools.int.SuggestionResult
import com.hzrelaper.hospitalmachine.utils.MyUtils
import com.loveplusplus.update.UpdateChecker
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OtherInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OtherInfoFragment : Fragment() {
    private lateinit var mMainView: View
    private  lateinit var mContext: Context

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mContext = this.context!!
        mMainView =inflater.inflate(R.layout.fragment_other_info, container, false)
        // Inflate the layout for this fragment
        mMainView.findViewById<TextView>(R.id.current_version).text="当前版本 "+MyUtils.getAppVersionName(this.context)+
                "  versionCode " + MyUtils.getAppVersionCode(this.context)
        mMainView.findViewById<Button>(R.id.btn_check_update).setOnClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                UpdateChecker.checkForDialog(mContext);
            }
        })
        mMainView.findViewById<Button>(R.id.submit_suggestion).setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
                val suggestion = mMainView.findViewById<EditText>(R.id.txt_suggestion).text.toString()
                if (!TextUtils.isEmpty(suggestion)){
                    androidx.appcompat.app.AlertDialog.Builder(mContext).setMessage("确认提交意见吗？").
                        setPositiveButton("确认",object:DialogInterface.OnClickListener{
                            override fun onClick(p0: DialogInterface?, p1: Int) {
                                SuggestinoServiceImp().add(suggestion,object :
                                    Callback<SuggestionResult?>{
                                    override fun onResponse(
                                        call: Call<SuggestionResult?>,
                                        response: Response<SuggestionResult?>
                                    ) {
                                        var body = response.body()
                                        if (body?.result == 0){
                                            AlertDialog.Builder(mContext).setMessage("提交成功").create().show()
                                        }else{
                                            AlertDialog.Builder(mContext).setMessage("提交失败" + body?.message).create().show()
                                        }
                                    }

                                    override fun onFailure(
                                        call: Call<SuggestionResult?>,
                                        t: Throwable
                                    ) {
                                        Toast.makeText(mContext,"无法连接服务器",Toast.LENGTH_SHORT).show()
                                    }
                                })
                            }
                        }).
                        setNegativeButton("取消",null).create().show()
                }else{
                    androidx.appcompat.app.AlertDialog.Builder(mContext).setMessage("内容不能为空").create().show()
                }
            }
        })
        return mMainView
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OtherInfoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OtherInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}