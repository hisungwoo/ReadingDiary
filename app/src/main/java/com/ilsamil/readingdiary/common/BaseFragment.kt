package com.ilsamil.readingdiary.common

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    companion object {
        private const val TAG = "FML"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate() ------------- [${this.javaClass.simpleName}], ${this.hashCode()}")
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated() -------- [${this.javaClass.simpleName}], ${this.hashCode()}, savedInstanceState : $savedInstanceState")
    }

    override fun onStart() {
        Log.i(TAG, "onStart() -------------- [${this.javaClass.simpleName}], ${this.hashCode()}")
        super.onStart()
    }

    override fun onResume() {
        Log.i(TAG, "onResume() ------------- [${this.javaClass.simpleName}], ${this.hashCode()}")
        super.onResume()
    }

    override fun onPause() {
        Log.i(TAG, "onPause() -------------- [${this.javaClass.simpleName}], ${this.hashCode()}")
        super.onPause()
    }

    override fun onStop() {
        Log.i(TAG, "onStop() --------------- [${this.javaClass.simpleName}], ${this.hashCode()}")
        super.onStop()
    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy() ------------ [${this.javaClass.simpleName}], ${this.hashCode()}")
        super.onDestroy()
    }
}