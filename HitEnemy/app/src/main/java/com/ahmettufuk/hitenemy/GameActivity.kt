package com.ahmettufuk.hitenemy

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.ahmettufuk.hitenemy.databinding.ActivityGameBinding
import java.util.*
import kotlin.collections.ArrayList

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    var score: Int = 0
    var time: Int = 0
    var gameVelocity: Long = 0
    var imageArrayList = ArrayList<ImageView>()
    var handler = Handler()
    var runnable = Runnable {}
    var mediaPlayer: MediaPlayer = MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mediaPlayer = MediaPlayer.create(this@GameActivity, R.raw.hit)

        createArrayImage()
        for (i in imageArrayList) {
            i.visibility = View.INVISIBLE
        }
        imageArrayList[4].visibility = View.VISIBLE

        takeTimeData()
        takeModeData()

    }

    fun takeTimeData() {
        val intent = intent
        val timeInfo = intent.getIntExtra("time", 15)
        if (timeInfo.equals(15)) {
            time = 15
            binding.timeTextview.text = "Time : $time"
        } else if (timeInfo.equals(30)) {
            time = 30
            binding.timeTextview.text = " Time : $time"
        } else if (timeInfo.equals(45)) {
            time = 45
            binding.timeTextview.text = " Time : $time"
        } else {
            time = 30
            binding.timeTextview.text = "Time : $time"
        }

    }

    fun takeModeData() {
        val intent = intent
        val modeInfo = intent.getStringExtra("mode")
        if (modeInfo.equals("easy")) {
            println("kolay mod aktif")
            gameVelocity = 750
        } else if (modeInfo.equals("medium")) {
            println("medium mod aktif")
            gameVelocity = 500
        } else if (modeInfo.equals("hard")) {
            println("hard mode aktif")
            gameVelocity = 350
        } else {
            gameVelocity = 500
        }


    }

    fun selectedImage(view: View) {
        if (score < 1) {
            hideImage()
            timer(time.toLong())
        }
        mediaPlayer.start()
        score++
        binding.scoreText.text = "Score : $score"

    }

    fun timer(a: Long) {


         object : CountDownTimer((a * 1000), 1000) {
            override fun onTick(a: Long) {
                binding.timeTextview.text = "Time : ${a / 1000}"
            }

            override fun onFinish() {
                binding.timeTextview.text = "Time : 0"
                handler.removeCallbacks(runnable)
                for (i in imageArrayList) {
                    i.visibility = View.INVISIBLE
                }
                imageArrayList[4].visibility = View.VISIBLE
                alertDialog()
            }

        }


    }

    fun createArrayImage() {
        imageArrayList.add(binding.imageView1)
        imageArrayList.add(binding.imageView2)
        imageArrayList.add(binding.imageView3)
        imageArrayList.add(binding.imageView4)
        imageArrayList.add(binding.imageView5)
        imageArrayList.add(binding.imageView6)
        imageArrayList.add(binding.imageView7)
        imageArrayList.add(binding.imageView8)
        imageArrayList.add(binding.imageView9)
        imageArrayList.add(binding.imageView10)
        imageArrayList.add(binding.imageView11)
        imageArrayList.add(binding.imageView12)

    }

    fun hideImage() {
        runnable = Runnable {
            for (i in imageArrayList) {
                i.visibility = View.INVISIBLE
            }
            val random = Random()
            val randomIndex = random.nextInt(12)
            imageArrayList[randomIndex].visibility = View.VISIBLE
            handler.postDelayed(runnable, gameVelocity)
        }
        handler.post(runnable)


    }

    fun alertDialog() {
        val alert = AlertDialog.Builder(this@GameActivity)
        alert.setTitle("Game Over")
        alert.setMessage("Restart?")
        alert.setPositiveButton("Yes") { dialog, which ->

            score = 0
            binding.scoreText.text = "Score : $score"
            this@GameActivity.finish()
            this@GameActivity.startActivity(intent)

        }
        alert.setNegativeButton("No") { dialog, which ->
            Toast.makeText(this@GameActivity, "Game Over", Toast.LENGTH_SHORT).show()
        }
        alert.show()

    }


}