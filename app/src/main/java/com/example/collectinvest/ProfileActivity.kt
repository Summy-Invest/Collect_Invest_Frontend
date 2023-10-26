package com.example.collectinvest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import com.example.collectinvest.databinding.ActivityProfileBinding
import io.ktor.client.*
import io.ktor.client.statement.*
import io.ktor.client.engine.android.Android
import io.ktor.client.engine.cio.*
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import android.graphics.BitmapFactory
class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.title_profile)
        setContentView(R.layout.activity_profile)
        val img = findViewById<ImageView>(R.id.imgProf)
        val img_url = "https://kartinki.pibig.info/uploads/posts/2023-04/1682323864_kartinki-pibig-info-p-obezyana-za-kompyuterom-kartinki-arti-inst-24.jpg"
        val client = HttpClient(Android)
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response: HttpResponse = client.get(img_url)
                if (response.status.value == 200) {
                    // Читаем байты из ответа и преобразуем их в Bitmap
                    val imageBytes = response.call.response.readBytes()
                    val imageBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

                    // Обновляем ImageView в главном потоке
                    launch(Dispatchers.Main) {
                        img.setImageBitmap(imageBitmap)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                client.close()
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }
}