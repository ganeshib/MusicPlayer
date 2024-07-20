package com.example.musicplayer

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.musicplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var mediaPlayer: MediaPlayer
    var totalTime:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mediaPlayer=MediaPlayer.create(this,R.raw.music)
        mediaPlayer.setVolume(1f,1f)
        totalTime=mediaPlayer.duration

        binding.play.setOnClickListener{
            mediaPlayer.start()
            binding.pause.visibility= View.VISIBLE
            binding.play.visibility=View.GONE
        }

        binding.pause.setOnClickListener{
            mediaPlayer.pause()
            binding.pause.visibility= View.GONE
            binding.play.visibility=View.VISIBLE
        }

        binding.stop.setOnClickListener{
            mediaPlayer?.stop()
            mediaPlayer.reset()
            mediaPlayer.release()
        }

        binding.seekBar.max=totalTime
        binding.seekBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser){
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        val handler=Handler()
        handler.postDelayed(object:Runnable{
            override fun run() {
                binding.seekBar.progress=mediaPlayer.currentPosition
                handler.postDelayed(this,1000)
            }
        },0)
    }
}