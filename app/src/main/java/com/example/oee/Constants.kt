package com.example.oee

import java.util.*


class Constants {
    // 상수 선언

    // 클래스 레벨의 함수나 속성을 담기 위한 싱글톤 객체
    companion object {

        // 기본 운동 목록을 ArrayList 형태로 반환
        fun defaultExerciseList(): ArrayList<ExerciseModel> {

            // 운동 목록을 담을 ArrayList 초기화
            val exerciseList = ArrayList<ExerciseModel>()

            // 각 운동을 ExerciseModel 객체로 생성하고 리스트에 추가
            val jumpingJacks =
                ExerciseModel(1, "Jumping Jacks", R.drawable.jumpingjack, false, false,
                    900,
                    1100
                )
            exerciseList.add(jumpingJacks)

            val wallSit = ExerciseModel(2, "Wall Sit", R.drawable.wallsit, false, false,
                900,
                1100
            )
            exerciseList.add(wallSit)

            val pushUp = ExerciseModel(3, "Push Up", R.drawable.pushup, false, false,
                900,
                1100
            )
            exerciseList.add(pushUp)

            val crossleggedcrunches =
                ExerciseModel(4, "cross legged crunches", R.drawable.crossleggedcrunches, false, false,
                    900,
                    1100
                )
            exerciseList.add(crossleggedcrunches)

            val reversecrunch =
                ExerciseModel(
                    5,
                    "reverse crunch",
                    R.drawable.reversecrunch,
                    false,
                    false,
                    900,
                    1100
                )
            exerciseList.add(reversecrunch)

            val squat = ExerciseModel(6, "Squat", R.drawable.squart, false, false,
                900,
                1100
            )
            exerciseList.add(squat)

            val situp =
                ExerciseModel(
                    7,
                    "sit up",
                    R.drawable.situp,
                    false,
                    false,
                    900,
                    1100

                )
            exerciseList.add(situp)

            val plank = ExerciseModel(8, "Plank", R.drawable.plank, false, false,
                900,
                1100
            )
            exerciseList.add(plank)

            val burpees =
                ExerciseModel(
                    9, "burpees",
                    R.drawable.burpees,
                    false,
                    false,
                    900,
                    1100
                )
            exerciseList.add(burpees)

            val lunges = ExerciseModel(10, "Lunges", R.drawable.lunge, false, false,
                900,
                1100
            )
            exerciseList.add(lunges)

            val pushupAndRotation =
                ExerciseModel(
                    11,
                    "Push up and Rotation",
                    R.drawable.pushuprotation,
                    false,
                    false,
                    900,
                    1100
                )
            exerciseList.add(pushupAndRotation)

            val bridge = ExerciseModel(12, "bridge", R.drawable.bridge, false, false,
                900,
                1100
            )
            exerciseList.add(bridge)

            // 생성된 운동 목록 반환
            return exerciseList
        }
    }
}
