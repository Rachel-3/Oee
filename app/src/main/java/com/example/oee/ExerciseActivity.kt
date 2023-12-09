package com.example.oee

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oee.databinding.ActivityExerciseBinding
import com.example.oee.databinding.DialogCustomBackConfirmationBinding
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
// 운동 관련 액티비티 클래스 시작

    // 타이머와 프로그레스(진행 상태) 관련 변수 선언
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0



    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0

    private var exerciseTimerDuration:Long = 30

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var binding: ActivityExerciseBinding? = null
    private var tts: TextToSpeech? = null

    private var player: MediaPlayer? = null

    private var exerciseAdapter: ExerciseStatusAdapter? = null

    // onCreate 함수는 액티비티가 생성될 때 호출
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // View 바인딩 설정, UI 컴포넌트와 연결
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // 액션바 설정
        setSupportActionBar(binding?.toolbarExercise)
        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarExercise?.setNavigationOnClickListener {
            customDialogForBackButton()
        }


        // TextToSpeech 초기화
        tts = TextToSpeech(this, this)

        // 운동 리스트 초기화
        exerciseList = Constants.defaultExerciseList()

        // 휴식 관련 뷰 설정
        setupRestView()

        // 운동 상태 리사이클러뷰 설정
        setupExerciseStatusRecyclerView()
    }


    // 휴식 관련 뷰를 설정하는 함수
    private fun setupRestView() {

        try {
            val soundURI =
                Uri.parse("android.resource://com.example.oee/" + R.raw.press_start)
            player = MediaPlayer.create(applicationContext, soundURI)
            player?.isLooping = false
            player?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding?.flRestView?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.upcomingLabel?.visibility = View.VISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.VISIBLE
        binding?.tvExerciseName?.visibility = View.INVISIBLE
        binding?.flExerciseView?.visibility = View.INVISIBLE
        binding?.ivImage?.visibility = View.INVISIBLE



        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }


        binding?.tvUpcomingExerciseName?.text = exerciseList!![currentExercisePosition + 1].getName()

        setRestProgressBar()
    }

    // 휴식 상태의 프로그레스바를 설정하는 함수
    private fun setRestProgressBar() {

        binding?.progressBar?.progress = restProgress


        restTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text =
                    (10 - restProgress).toString()
            }

            override fun onFinish() {

                currentExercisePosition++


                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter?.notifyDataSetChanged()

           setupExerciseView()
            }
        }.start()
    }



    // 운동 뷰를 설정하는 함수
    private fun setupExerciseView() {

        binding?.flRestView?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.INVISIBLE
        binding?.upcomingLabel?.visibility = View.INVISIBLE
        binding?.tvExerciseName?.visibility = View.VISIBLE
        binding?.flExerciseView?.visibility = View.VISIBLE
        binding?.ivImage?.visibility = View.VISIBLE


        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        speakOut(exerciseList!![currentExercisePosition].getName())

        binding?.ivImage?.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding?.tvExerciseName?.text = exerciseList!![currentExercisePosition].getName()

        setExerciseProgressBar()

    }


    // 운동 상태의 프로그레스바를 설정하는 함수
    private fun setExerciseProgressBar() {

        binding?.progressBarExercise?.progress = exerciseProgress

        exerciseTimer = object : CountDownTimer(exerciseTimerDuration * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress = exerciseTimerDuration.toInt() - exerciseProgress
               binding?.tvTimerExercise?.text = (exerciseTimerDuration.toInt() - exerciseProgress).toString()
            }

            override fun onFinish() {

                if (currentExercisePosition < exerciseList?.size!! - 1) {
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter?.notifyDataSetChanged()
                    setupRestView()
                } else {
                     finish()
                  val intent = Intent(this@ExerciseActivity,FinishActivity::class.java)
                    startActivity(intent)
                }

            }
        }.start()

    }

    // 액티비티가 파괴될 때 호출되는 함수
    public override fun onDestroy() {
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }


        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }


        if(player != null){
            player!!.stop()
        }


        super.onDestroy()
        binding = null
    }


    // TextToSpeech 초기화 결과를 처리하는 함수
    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {

            val result = tts?.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            }

        } else {
            Log.e("TTS", "Initialization Failed!")
        }

    }


    // TextToSpeech를 사용하여 텍스트를 음성으로 출력하는 함수
    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }


    // 운동 상태를 보여주는 리사이클러뷰를 설정하는 함수
    private fun setupExerciseStatusRecyclerView() {


       binding?.rvExerciseStatus?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)


        binding?.rvExerciseStatus?.adapter = exerciseAdapter
    }

    // 뒤로 가기 버튼을 눌렀을 때 나타나는 커스텀 다이얼로그를 설정하는 함수
    private fun customDialogForBackButton() {
        val customDialog = Dialog(this)

         val dialogBinding = DialogCustomBackConfirmationBinding.inflate(layoutInflater)

        customDialog.setContentView(dialogBinding.root)

        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.tvYes.setOnClickListener {

            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.tvNo.setOnClickListener {
            customDialog.dismiss()
        }

        customDialog.show()
    }

}