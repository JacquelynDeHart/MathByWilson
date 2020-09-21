package com.example.mathapp


import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.webkit.URLUtil
import android.widget.MediaController
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class VideoUrl: AppCompatActivity() {
    private val video_link = "http://www.mathbywilson.com/wp-content/uploads/2020/08/C1-FUN_Dom.mp4"

    lateinit var VideoView_URL: VideoView
    lateinit var loading_text: TextView

    private var mCurrentPosition = 0

    private val PLAYBACK_TIME = "play_time"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_url)

        VideoView_URL = findViewById(R.id.videoview_url)
        loading_text = findViewById(R.id.loading_textview)

        if (savedInstanceState != null) {
            mCurrentPosition =
                savedInstanceState.getInt(PLAYBACK_TIME)
        }

       val controller = MediaController(this)
        controller.setMediaPlayer(VideoView_URL)
        VideoView_URL.setMediaController(controller)
    }

    override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    override fun onPause() {
        super.onPause()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            VideoView_URL.pause()
        }
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(
            PLAYBACK_TIME, VideoView_URL.currentPosition
        )
    }

    private fun initializePlayer() {

        loading_text.visibility = VideoView.VISIBLE

        val videoUri = getMedia(video_link)
        VideoView_URL.setVideoURI(videoUri)

        VideoView_URL.setOnPreparedListener {
            loading_text.visibility = VideoView.INVISIBLE


            if (mCurrentPosition > 0) {
                VideoView_URL.seekTo(mCurrentPosition)
            } else {
                // Skipping to 1 shows the first frame of the video.
                VideoView_URL.seekTo(1)
            }

            // Start playing!
            VideoView_URL.start()
        }


        VideoView_URL.setOnCompletionListener {
            Toast.makeText(
                this,
                R.string.toast_message,
                Toast.LENGTH_SHORT
            ).show()

            // Return the video position to the start.
            VideoView_URL.seekTo(0)
        }
    }

    private fun releasePlayer() {
        VideoView_URL.stopPlayback()
    }


    private fun getMedia(mediaName: String): Uri {
        return if (URLUtil.isValidUrl(mediaName)) {
            Uri.parse(mediaName)
        } else {

             Uri.parse(
                "android.resource://" + packageName +
                        "/raw/" + mediaName
            )
        }
    }
}