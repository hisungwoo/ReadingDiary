package com.ilsamil.readingdiary.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.ilsamil.readingdiary.R

class Util {
    fun showDialog(context: Context, operation: () -> Unit, title : String, yes : String) {
        val builder = AlertDialog.Builder(context)
            .setView(R.layout.dialog_message)
            .show()
            .also { alertDialog ->
                if (alertDialog == null) return@also
                alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                val titleTv = alertDialog.findViewById<TextView>(R.id.dialog_message_title_tv)
                val yesBtn = alertDialog.findViewById<Button>(R.id.dialog_message_yes_btn)
                val noBtn = alertDialog.findViewById<Button>(R.id.dialog_message_no_btn)

                titleTv?.text = title
                yesBtn?.text = yes

                yesBtn?.setOnClickListener {
                    alertDialog.dismiss()
                    operation()
                }

                noBtn?.setOnClickListener {
                    alertDialog.dismiss()
                }
            }
        builder.create()
        builder.show()
    }


}