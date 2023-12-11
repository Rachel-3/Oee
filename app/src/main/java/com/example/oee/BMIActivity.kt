package com.example.oee

import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.oee.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"

    }

    private var currentVisibleView: String =
        METRIC_UNITS_VIEW

    private var binding: ActivityBmiBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBmiBinding.inflate(layoutInflater)

        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBmiActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "CALCULATE BMI"
        binding?.toolbarBmiActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        makeVisibleMetricUnitsView()

        binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId: Int ->

            if (checkedId == R.id.rbMetricUnits) {
                makeVisibleMetricUnitsView()
            } else {

            }
        }

        binding?.btnCalculateUnits?.setOnClickListener {
            calculateUnits()
        }

    }

    private fun calculateUnits(){

        if (currentVisibleView == METRIC_UNITS_VIEW) {

            if (validateMetricUnits()) {

                val heightValue: Float = binding?.etMetricUnitHeight?.text.toString().toFloat() / 100

                val weightValue: Float = binding?.etMetricUnitWeight?.text.toString().toFloat()

                val bmi = weightValue / (heightValue * heightValue)

                displayBMIResult(bmi)
            } else {
                Toast.makeText(
                    this@BMIActivity,
                    "Please enter valid values.",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        } else {


        }
    }


    private fun makeVisibleMetricUnitsView() {
        currentVisibleView = METRIC_UNITS_VIEW
        binding?.tilMetricUnitWeight?.visibility = View.VISIBLE
        binding?.tilMetricUnitHeight?.visibility = View.VISIBLE
        binding?.tilUsMetricUnitWeight?.visibility = View.GONE


        binding?.etMetricUnitHeight?.text!!.clear()
        binding?.etMetricUnitWeight?.text!!.clear()

        binding?.llDiplayBMIResult?.visibility = View.INVISIBLE
    }





    private fun validateMetricUnits(): Boolean {
        var isValid = true

        if (binding?.etMetricUnitWeight?.text.toString().isEmpty()) {
            isValid = false
        } else if (binding?.etMetricUnitHeight?.text.toString().isEmpty()) {
            isValid = false
        }

        return isValid
    }


    private fun validateUsUnits(): Boolean {
        var isValid = true





        return isValid
    }


    private fun displayBMIResult(bmi: Float) {

        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "매우 심각한 저체중"
            bmiDescription = "헉! 당신을 더 잘 돌봐야할거같아요. 더 많이 먹어야해요 !!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "심각한 저체중"
            bmiDescription = "아! 정말 당신의 건강을 더 신경쓰세요 더 많이 먹어요 !!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "저체중"
            bmiDescription = "이제 좀 더 노력하면돼요!! 꾸준히 먹어요 !!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "정상"
            bmiDescription = "축하해요! 당신은 좋은 체형을 유지하고 있어요!"
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(
                bmi,
                30f
            ) <= 0
        ) {
            bmiLabel = "과체중"
            bmiDescription = "건강에 조금 더 신경을 써야할 거 같아요. 운동을 하세요"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "비만 정도 | (적당한 비만)"
            bmiDescription = "자기 건강에 신경을 써야해요. 운동을 해야할 거 같아요!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "비만 정도 || (심각한 비만)"
            bmiDescription = "당신은 매우 위험한 몸이에요 !! 지금 행동하세요"
        } else {
            bmiLabel = "비만 정도 ||| (매우 심각한 비만)"
            bmiDescription = "당신은 매우 위험한 상태에 있어요!! 제발 지금 당장 운동하세요"
        }


        binding?.llDiplayBMIResult?.visibility = View.VISIBLE


        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding?.tvBMIValue?.text = bmiValue
        binding?.tvBMIType?.text = bmiLabel
        binding?.tvBMIDescription?.text = bmiDescription
    }
    // END


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}