package com.jolly.androidx

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.jolly.androidx.Adapter.RecycleViewAdapter
import com.jolly.androidx.Utilities.InjectUtil
import com.jolly.androidx.ViewModels.CopyViewModel
import com.jolly.androidx.databinding.AllDataFragmentBinding

class AllFragment: Fragment() {

        private val viewModel:CopyViewModel by viewModels {
            InjectUtil.provideAllDataViewModelFactory(requireContext())
        }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding =AllDataFragmentBinding.inflate(inflater,container,false)
        val adapter = RecycleViewAdapter()
        binding.recyclerview.adapter =adapter
        subscribeUI(adapter,binding)

        return binding.root


    }

    private fun subscribeUI(adapter: RecycleViewAdapter, binding: AllDataFragmentBinding) {
        viewModel.allData.observe(viewLifecycleOwner, Observer { t ->
            binding.hasData = !t.isNullOrEmpty()
        })

        viewModel.allData.observe(viewLifecycleOwner, Observer { t ->
            //if(!t.isNullOrEmpty()){
                adapter.updateLsit(t)
            //}
        })

    }

}