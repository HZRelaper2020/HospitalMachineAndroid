package com.hzrelaper.hospitalmachine.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import com.hzrelaper.hospitalmachine.R

class TopicsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_topics, container, false)
//        var aa = view.findViewById<SearchView>(R.id.searchview)

        return  view;
    }


}