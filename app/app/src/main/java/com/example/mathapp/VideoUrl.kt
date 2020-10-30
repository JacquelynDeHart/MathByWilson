package com.example.mathapp


import android.Manifest.permission
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.SurfaceView
import android.view.View
import android.view.WindowManager
import android.webkit.URLUtil
import android.widget.MediaController
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.getInstance
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_video_url.*
import org.opencv.android.BaseLoaderCallback
import org.opencv.android.CameraBridgeViewBase
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame
import org.opencv.android.LoaderCallbackInterface
import org.opencv.android.OpenCVLoader
import org.opencv.android.OpenCVLoader.OPENCV_VERSION
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.MatOfRect
import org.opencv.imgproc.Imgproc
import org.opencv.objdetect.CascadeClassifier
import java.io.File
import java.io.FileOutputStream


class VideoUrl: AppCompatActivity(), CameraBridgeViewBase.CvCameraViewListener2, Runnable {
    private val TAG = "OCVSampleFaceDetect"
    private var cameraBridgeViewBase: CameraBridgeViewBase? = null

    private val baseLoaderCallback: BaseLoaderCallback = object : BaseLoaderCallback(this) {
        override fun onManagerConnected(status: Int) {
            if (status == SUCCESS) {
                Log.i(TAG, "OpenCV loaded successfully")
                cameraBridgeViewBase!!.enableView()
            } else {
                super.onManagerConnected(status)
            }
        }
    }

    @Volatile
    private var running = false
    @Volatile
    private var qtdFaces = 0
    @Volatile
    private var matTmpProcessingFace: Mat? = null

    private var cascadeClassifier: CascadeClassifier? = null
    private var mCascadeFile: File? = null
    private var infoFaces: TextView? = null

    private var video_link = ""
    private var url_code = -1
    private var activity_num = -1
    lateinit var VideoView_URL: VideoView
    lateinit var loading_text: TextView

    private var mCurrentPosition = 0

    private val PLAYBACK_TIME = "play_time"

    //firebase and user var/val
    lateinit var database: FirebaseDatabase
    lateinit var user: DatabaseReference
    lateinit var currentUser: FirebaseUser
    private lateinit var auth: FirebaseAuth
    private val playTimeCurrent: Long = 0
    //create trackingAlgorithm object from appropriate class
    val trackAlgo:TrackingAlgorithm = TrackingAlgorithm()
    val act = trackAlgo.actualTimeWatched

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //call method to instantiate db vars
        dbInstance()


        //set up window elements
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(R.layout.activity_video_url)

        //collect data from intent bundle
        url_code = intent.getIntExtra("urlCode", 0)
        activity_num = intent.getIntExtra("activity", 0)

        //calls function to set values for the url based on the activity_num and url_code
        //passed in the intent bundle
        setVideoUrlCode(url_code, activity_num)

        //hooks in the videoView and textView
        VideoView_URL = findViewById(R.id.videoview_url)
        loading_text = findViewById(R.id.loading_textview)
        infoFaces = findViewById(R.id.face)
        cameraBridgeViewBase = findViewById(R.id.main_surface)

        //creates onclick listener for button
        returnToCourseSelect.setVisibility(View.INVISIBLE)
        returnToCourseSelect.setOnClickListener {
            //return user to previous course activity based on the activity_num
            when(activity_num){
                1 -> {
                    startActivity(Intent(this, CollegeAlgebra::class.java))
                }
                2 -> {
                    startActivity(Intent(this, Calculus1::class.java))
                }
                3 -> {
                    startActivity(Intent(this, Calculus2::class.java))
                }

                else -> startActivity(Intent(this, CourseSelection::class.java))
            }
            finishAffinity()
        }

        loadHaarCascadeFile()
        checkPermissions()
        initializePlayer()


        if (savedInstanceState != null) {
            mCurrentPosition =
                savedInstanceState.getInt(PLAYBACK_TIME)
        }

       val controller = MediaController(this)
        controller.setMediaPlayer(VideoView_URL)
        VideoView_URL.setMediaController(controller)
    }
    /*
    method for dynamically setting url for video_link based on button click
     */
    private fun setVideoUrlCode(urlCode: Int, activity_num: Int) {

        when(activity_num){
            1 -> when (url_code) {
                1 -> video_link =
                    "http://www.mathbywilson.com/wp-content/uploads/2020/08/CA-Equ_Linear.mp4"
                2 -> video_link =
                    "http://www.mathbywilson.com/wp-content/uploads/2020/08/CA-Complex_Numb.mp4"
                3 -> video_link =
                    "http://www.mathbywilson.com/wp-content/uploads/2020/08/CA-Equ_Quad.mp4"
                4 -> video_link =
                    "http://www.mathbywilson.com/wp-content/uploads/2020/08/Applications_Quadratic.mp4"
                5 -> video_link =
                    "http://www.mathbywilson.com/wp-content/uploads/2020/08/CA-Eq_QuadInForm.mp4"
                6 -> video_link =
                    "http://www.mathbywilson.com/wp-content/uploads/2020/09/CA-Eq_Rational.mp4"
                7 -> video_link =
                    "http://www.mathbywilson.com/wp-content/uploads/2020/09/CA-Equ_Radical.mp4"
                8 -> video_link =
                    "http://www.mathbywilson.com/wp-content/uploads/2020/09/CA-Equ_Abs_Value.mp4"
                9 -> video_link =
                    "http://www.mathbywilson.com/wp-content/uploads/2020/09/CA-Inequalities.mp4"
                10 -> video_link =
                    "http://www.mathbywilson.com/wp-content/uploads/2020/09/CA-Inequalities_Abs_Value.mp4"
                11 -> video_link =
                    "http://www.mathbywilson.com/wp-content/uploads/2020/09/CA-Rect_Coord_Sys.mp4"
                12 -> video_link =
                    "http://www.mathbywilson.com/wp-content/uploads/2020/09/CA-Circles.mp4"
                13 -> video_link =
                    "http://www.mathbywilson.com/wp-content/uploads/2020/09/FUN_Domain.mp4"
                14 -> video_link =
                    "http://www.mathbywilson.com/wp-content/uploads/2020/09/CA-FUN_Range.mp4"
            }
            2 -> when (url_code) {
                1 -> video_link =
                    "http://www.mathbywilson.com/wp-content/uploads/2020/08/C1-FUN_Lim_Quest.mp4"
                2 -> video_link =
                    "http://www.mathbywilson.com/wp-content/uploads/2020/08/C1-FUN_Limit_Graphs.mp4"
                3 -> video_link =
                    "http://www.mathbywilson.com/wp-content/uploads/2020/08/C1_Lim_Fin_2sided.mp4"
                4 -> video_link =
                    "http://www.mathbywilson.com/wp-content/uploads/2020/08/C2-Finite-1-sided-Limits.mp4"
                5 -> video_link =
                    "http://www.mathbywilson.com/wp-content/uploads/2020/08/C1-Continuity.mp4"
                6 -> video_link =
                    "http://www.mathbywilson.com/wp-content/uploads/2020/09/C1-Infinite-2-1-sided-Limits.mp4"
                7 -> video_link =
                    "http://www.mathbywilson.com/wp-content/uploads/2020/09/C1-Finite_Limits_at_Infinity.mp4"
                8 -> video_link =
                    "http://www.mathbywilson.com/wp-content/uploads/2020/09/C1-Infinite_Limits_at_Infinity.mp4"
                9 -> video_link =
                    "http://www.mathbywilson.com/wp-content/uploads/2020/09/C1-Der_Intro.mp4"
                10 -> video_link =
                    "http://www.mathbywilson.com/wp-content/uploads/2020/09/Der_Specific.mp4"
                11 -> video_link =
                    "http://www.mathbywilson.com/wp-content/uploads/2020/09/C1-Der_Generic_01.mp4"
                12 -> video_link =
                    "http://www.mathbywilson.com/wp-content/uploads/2020/09/C1-Der_Generic_02.mp4"
                13 -> video_link =
                    "http://www.mathbywilson.com/wp-content/uploads/2020/09/C1-Der_Intro_02.mp4"
            }
            3 -> when (url_code) {
                1 -> video_link =
                    "http://www.mathbywilson.com/wp-content/uploads/2020/06/zoom_Position_PLUS.mp4"
            }
        }

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
        disableCamera()
    }

    override fun onStop() {
        super.onStop()

        releasePlayer()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        /*captures total playback time of video and current player position
        and saves it
         */
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
            trackAlgo.isVideoComplete(act, mCurrentPosition.toLong())
            // Return the video position to the start.
            VideoView_URL.seekTo(0)
            //show return to video selection button
            returnToCourseSelect.setVisibility(View.VISIBLE)
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

    open fun checkPermissions() {
        if (isPermissionGranted()) {
            loadCameraBridge()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(permission.CAMERA), 1)
        }
    }

    open fun isPermissionGranted(): Boolean {
        return ActivityCompat.checkSelfPermission(this, permission.CAMERA) === PERMISSION_GRANTED
    }

    internal open fun onRequestPermissionsResult(
        requestCode: Int,
        @NonNull permissions: Array<String?>?,
        @NonNull grantResults: IntArray?
    ) {
        super.onRequestPermissionsResult(requestCode, permissions!!, grantResults!!)
        checkPermissions()
    }

    open fun loadCameraBridge() {
        cameraBridgeViewBase!!.setCameraIndex(CameraBridgeViewBase.CAMERA_ID_FRONT)
        cameraBridgeViewBase!!.visibility = SurfaceView.VISIBLE
        cameraBridgeViewBase!!.setCvCameraViewListener(this)
    }

    open fun loadHaarCascadeFile() {
        try {
            val cascadeDir = getDir("haarcascade_frontalface_alt", MODE_PRIVATE)
            mCascadeFile = File(cascadeDir, "haarcascade_frontalface_alt.xml")
            if (!mCascadeFile!!.exists()) {
                val os = FileOutputStream(mCascadeFile)
                val `is` = resources.openRawResource(R.raw.haarcascade_frontalface_alt)
                val buffer = ByteArray(4096)
                var bytesRead: Int
                while (`is`.read(buffer).also { bytesRead = it } != -1) {
                    os.write(buffer, 0, bytesRead)
                }
                `is`.close()
                os.close()
            }
        } catch (throwable: Throwable) {
            throw RuntimeException("Failed to load Haar Cascade file")
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isPermissionGranted()) return
        resumeOCV()
    }

    open fun resumeOCV() {
        if (OpenCVLoader.initDebug()) {
            Log.d(TAG, "OpenCV library found inside package. Using it!")
            baseLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS)
        } else {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization")
            OpenCVLoader.initAsync(OPENCV_VERSION, this, baseLoaderCallback)
        }
        cascadeClassifier = CascadeClassifier(mCascadeFile!!.absolutePath)
        cascadeClassifier!!.load(mCascadeFile!!.absolutePath)
        startFaceDetect()
    }


    override fun onCameraFrame(inputFrame: CvCameraViewFrame): Mat? {
        if (matTmpProcessingFace == null) {
            matTmpProcessingFace = inputFrame.gray()
        }
        return inputFrame.rgba()
    }


    override fun onCameraViewStarted(width: Int, height: Int) {}

    override fun onCameraViewStopped() {}


    open fun startFaceDetect() {
        if (running) return
        Thread(this).start()
    }

    override fun run() {
        running = true
        while (running) {
            try {
                if (matTmpProcessingFace != null) {
                    val matOfRect = MatOfRect()
                    cascadeClassifier!!.detectMultiScale(matTmpProcessingFace, matOfRect)
                    val newQtdFaces = matOfRect.toList().size
                    if (qtdFaces != newQtdFaces) {
                        qtdFaces = newQtdFaces
                        runOnUiThread {
                            infoFaces!!.text =
                                java.lang.String.format(
                                    getString(R.string.faces_detected),
                                    qtdFaces
                                )
                        }
                    }
                    Thread.sleep(500) //if you want an interval
                    matTmpProcessingFace = null
                }
                Thread.sleep(50)
            } catch (t: Throwable) {
                try {
                    Thread.sleep(10000)
                } catch (tt: Throwable) {
                }
            }
        }
    }

    open fun disableCamera() {
        running = false
        if (cameraBridgeViewBase != null) cameraBridgeViewBase!!.disableView()
    }
    /*
        sets up firebase db and user variables to push data to later
         */
    fun dbInstance(){
        auth = FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()
        currentUser = auth.currentUser!!
        user=FirebaseDatabase.getInstance().getReference("Users/$currentUser")
    }
}