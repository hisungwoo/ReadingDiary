package com.ilsamil.readingdiary.view

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.ilsamil.readingdiary.R
import com.ilsamil.readingdiary.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSettingBinding.inflate(layoutInflater)

        binding.settingDevBtn.setOnClickListener {
            AlertDialog.Builder(inflater.context)
                .setView(R.layout.dialog_setting_dev)
                .show()
                .also { alertDialog ->
                    if (alertDialog == null) return@also
                    alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                    val closeBtn = alertDialog.findViewById<Button>(R.id.dialog_dev_close_btn)
                    closeBtn?.setOnClickListener {
                        alertDialog.dismiss()
                    }
                }
        }

        binding.settingApiBtn.setOnClickListener {
            AlertDialog.Builder(inflater.context)
                .setView(R.layout.dialog_setting_license)
                .show()
                .also { alertDialog ->
                    if (alertDialog == null) return@also
                    alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                    val closeBtn = alertDialog.findViewById<Button>(R.id.dialog_license_close_btn)
                    val licenseUrl = alertDialog.findViewById<TextView>(R.id.dialog_license_url)
                    val licenseUrl2 = alertDialog.findViewById<TextView>(R.id.dialog_license_url2)

                    licenseUrl?.setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(licenseUrl.text.toString())
                        startActivity(intent)
                    }

                    licenseUrl2?.setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(licenseUrl2.text.toString())
                        startActivity(intent)
                    }

                    closeBtn?.setOnClickListener {
                        alertDialog.dismiss()
                    }
                }
        }

        binding.settingVerBtn.setOnClickListener {
            AlertDialog.Builder(inflater.context)
                .setView(R.layout.dialog_setting_version)
                .show()
                .also { alertDialog ->
                    if (alertDialog == null) return@also
                    alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                    val closeBtn = alertDialog.findViewById<Button>(R.id.dialog_version_close_btn)
                    closeBtn?.setOnClickListener {
                        alertDialog.dismiss()
                    }
                }
        }

        binding.settingEvalBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("market://details?id=" + context?.packageName)
            startActivity(intent)
        }

        return binding.root
    }

}