package com.hzrelaper.hospitalmachine.ui.main.fragment.topics

import android.content.Context
import cn.bingoogolapple.baseadapter.BGAAdapterViewAdapter
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper
import com.hzrelaper.hospitalmachine.R
import com.hzrelaper.hospitalmachine.nettools.int.QuestionEntity

class NormalAdapterViewAdapter(context: Context?) : BGAAdapterViewAdapter<QuestionEntity>(context, R.layout.fragment_topics_item_normal) {
    override fun setItemChildListener(viewHolderHelper: BGAViewHolderHelper) {
//        viewHolderHelper.setItemChildClickListener(R.id.tv_item_normal_delete)
//        viewHolderHelper.setItemChildLongClickListener(R.id.tv_item_normal_delete)
    }

    public override fun fillData(viewHolderHelper: BGAViewHolderHelper, position: Int, model: QuestionEntity) {
        viewHolderHelper.setText(R.id.tv_item_normal_id,model.id.toString()).setText(R.id.tv_item_normal_title, model.name).setText(R.id.tv_item_time, model.renderTime())
                .setText(R.id.tv_item_normal_replys,model.replys.toString()+"回答")
//        viewHolderHelper.setTag(R.id.tv_item_normal_item,model)
    }
}