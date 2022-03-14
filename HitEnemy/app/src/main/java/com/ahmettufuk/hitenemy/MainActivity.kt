package com.ahmettufuk.hitenemy

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ahmettufuk.hitenemy.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.io.ByteArrayOutputStream
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferencesTime: SharedPreferences
    private lateinit var sharedPreferencesMode: SharedPreferences

    var selectedBitmap: Bitmap?= null
    var timeSelection: Int = 0
    var modeSelection: String = "medium"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




    }

    fun selectMode(View: View) {
        val popup = PopupMenu(this, binding.modeButton)
        popup.inflate(R.menu.mode)
        popup.setOnMenuItemClickListener {

            if (it.title.toString().equals("Easy")) {
                modeSelection = "easy"
                sharedPreferencesMode.edit().putString("mode", modeSelection).apply()
            } else if (it.title.toString().equals("Medium")) {
                modeSelection = "medium"
                sharedPreferencesMode.edit().putString("mode", modeSelection).apply()
            } else if (it.title.toString().equals("Hard")) {
                modeSelection = "hard"
                sharedPreferencesMode.edit().putString("mode", modeSelection).apply()
            }

            Toast.makeText(this, it.title, Toast.LENGTH_SHORT).show()
            true
        }
        popup.show()
    }

    fun selectTime(view: View) {
        val popup = PopupMenu(this, binding.timeButton)
        popup.inflate(R.menu.time)
        popup.setOnMenuItemClickListener {

            if (it.title.toString().equals("Time : 15")) {
                timeSelection = 15
                sharedPreferencesTime.edit().putInt("time", timeSelection).apply()
            } else if (it.title.toString().equals("Time : 30")) {
                timeSelection = 30
                sharedPreferencesTime.edit().putInt("time", timeSelection).apply()
            } else if (it.title.toString().equals("Time : 45")) {
                timeSelection = 45
                sharedPreferencesTime.edit().putInt("time", timeSelection).apply()
            }
            Toast.makeText(this, it.title, Toast.LENGTH_SHORT).show()
            true
        }

        popup.show()
    }

    fun play(view: View) {
        sharedPreferencesTime =
            this.getSharedPreferences("com.ahmettufuk.hitenemy", Context.MODE_PRIVATE)
        sharedPreferencesMode =
            this.getSharedPreferences("com.ahmettufuk.hitenemy1", Context.MODE_PRIVATE)

        var intent = Intent(this@MainActivity, GameActivity::class.java)

        if (sharedPreferencesTime != null || sharedPreferencesMode != null) {
            intent.putExtra("time", sharedPreferencesTime.getInt("time", timeSelection))
            intent.putExtra("mode", sharedPreferencesMode.getString("mode", modeSelection))
            startActivity(intent)
        } else {
            startActivity(intent)
        }

    }





    }








