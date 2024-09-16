package com.hdteam.appquality.trackinghd

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.hdteam.appquality.taq.model.GmailModel
import com.hdteam.appquality.taq.tracking.TAQ
import com.hdteam.appquality.taq.tracking.email.GmailSender
import com.hdteam.appquality.taq.utils.util.InfoDevice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

            val gmail = findViewById<EditText>(R.id.edtGmail).text.toString()
//            val id = GmailSender.sendGmailEnqueue(
//                context = this, GmailModel(
//                    subject = "Xin chào ",
//                    filePathAttach = listOf(file.path),
//                    body = InfoDevice.getAllInfoAppHtml(this),
//                    recipients = gmail
//                )
//            )
//            lifecycleScope.launch {
//                GmailSender.registerStateGmailWithIDGmail(this@MainActivity, id).collect {
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(this@MainActivity, it.toString(), Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//            val a = 3/0
            TAQ.deleteDatabaseLog()
        }



    }
}