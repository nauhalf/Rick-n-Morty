package com.nauhalf.ricknmorty.core.base.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {
    protected lateinit var binding: T

    abstract fun onViewBindingInflate(layoutInflater: LayoutInflater): T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the binding variable from onViewBindingInflate function
        binding = onViewBindingInflate(layoutInflater)
        //set content view from root view of the ViewBinding
        setContentView(binding.root)
    }
}
