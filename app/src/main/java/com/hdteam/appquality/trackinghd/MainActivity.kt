package com.hdteam.appquality.trackinghd

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.hdteam.appquality.taq.model.GmailModel
import com.hdteam.appquality.taq.tracking.email.GmailSender
import com.hdteam.appquality.taq.utils.util.InfoDevice
import kotlinx.coroutines.launch
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val file = File(filesDir, "a.txt")
        file.writeText("Hung")

        findViewById<Button>(R.id.btnSend).setOnClickListener {
//            GmailSender.sendMailNormalAsync(
//                subject = "My name is Hung ${System.currentTimeMillis()}",
//                filePathAttach = listOf(file.path),
//                body = InfoDevice.getAllInfoAppHtml(this),
//                recipients = "hungvv@govo.tech",
//                onFailure = {
//                    Log.e("ABC", "onCreate:  ${it.toString()}")
//                }, onSuccess = {
//                    Log.e("ABC", "done: ")
//                }
//            )

            val id = GmailSender.sendGmailEnqueue(
                context = this, GmailModel(
                    subject = "My name",
                    filePathAttach = listOf(file.path),
                    body = InfoDevice.getAllInfoAppHtml(this),
                    recipients = "hungvv@govo.tech"
                )
            )
            lifecycleScope.launch {
                GmailSender.registerStateGmailWithIDGmail(this@MainActivity, id).collect {
                    Log.e("MMM", "onCreate: ${it.toString()} ")
                }
            }
        }


    }
}