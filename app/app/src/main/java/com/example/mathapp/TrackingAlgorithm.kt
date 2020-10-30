package com.example.mathapp

class TrackingAlgorithm() {
    /*
    store current playback time, video in progress, current tracking value
     */
    fun onVideoPause(){

    }

    /*
    while watching is true (based on results from opencv face detect)
    if first occurrence of watching, create timer object and start timer
    else, restart timer from last time stamp
     */
    fun watching(){

    }

    /*
    test for video completion.
    when playback of video ceases, take final value from timer object and playback time of
    video. compare both times. if final timer value is within 10 seconds of playback time, send
    to Firebase database video name and completion value (1). else, send video name and completion percentage
    value (decimal)
     */
    fun isVideoComplete(){

    }
}