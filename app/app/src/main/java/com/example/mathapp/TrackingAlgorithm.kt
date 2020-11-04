package com.example.mathapp

import android.os.Handler
import android.os.Looper
import android.util.Log

class TrackingAlgorithm() {
    var actualTimeWatched: Long = 0
    var compDecimal: Double = 0.0
    var mainHandler: Handler = Handler(Looper.getMainLooper())
    /*
    store current playback time, video in progress, current tracking value
     */
    fun onVideoPause(){
        mainHandler.removeCallbacks(updateTimeWatched)
    }
    private var updateTimeWatched = object: Runnable{
        override fun run(){
            watching(actualTimeWatched.toInt())
            mainHandler.postDelayed(this, 10000)
        }
    }

    /*
    onVideoResume starts running updateTimeWatched runnable
     */
    fun onVideoResume(){
        mainHandler.post(updateTimeWatched)
    }

    /*
    accepts an initial time as integer and the playback time of the video.
    while watching is true (based on results from opencv face detect)
    if first occurrence of watching, begin  keeping time with android.os.Handler().postDelayed
    else, restart timer from last time stamp
    return value of time watched
     */
    fun watching(timer: Int): Long{
        /*when(timer){
            in 0..3900->{
                incrementTime()
            }
            !in 0..3900->{
                Log.i("timeIncrement", "outside 3900 seconds")
            }

        }*/
        if(timer < 4900){
            incrementTime()
            Log.i("incrementActualWatch",""+actualTimeWatched)
        }
        return actualTimeWatched
    }


    /*
    test for video completion.
    when playback of video ceases, take final value actualTimeWatched and playback time of
    video. compare both times. if final timer value is within 5% of playback time, send
    to Firebase database video name and completion value (1). else, send video name and completion percentage
    value (decimal)
     */
    fun isVideoComplete(act: Long, playTime: Long): Boolean {
        var findIfTrue: Boolean = false
        /*if(act == playTime){
            sendCompletion(1.0)
            findIfTrue = true
        }*/
        if(act< playTime){
            if((act/playTime)>.95){
                sendCompletion(((act/playTime).toDouble()))
                findIfTrue = true
            }
            if((act/playTime)<.95){
                findIfTrue = false
            }
        }
        return findIfTrue
    }

    private fun incrementTime(){
        actualTimeWatched += 1

    }

    private fun sendCompletion(l: Double) {
        compDecimal = l
    }
}