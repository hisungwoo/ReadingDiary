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
    fun showDialog(context: Context, operation: () -> Unit, title : String, message : String) {
        AlertDialog.Builder(context)
            .setView(R.layout.dialog_message)
            .show()
            .also { alertDialog ->
                if (alertDialog == null) return@also

                alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                val titleTv = alertDialog.findViewById<TextView>(R.id.dialog_message_title_tv)
                val massageTv = alertDialog.findViewById<TextView>(R.id.dialog_message_tv)
                val yesBtn = alertDialog.findViewById<Button>(R.id.dialog_message_yes_btn)
                val noBtn = alertDialog.findViewById<Button>(R.id.dialog_message_no_btn)

                titleTv?.text = title
                massageTv?.text = message

                yesBtn?.setOnClickListener {
                    operation()
                    alertDialog.dismiss()
                }

                noBtn?.setOnClickListener {
                    alertDialog.dismiss()
                }
            }
    }


}