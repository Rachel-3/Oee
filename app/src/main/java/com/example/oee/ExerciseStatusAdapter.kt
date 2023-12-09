package com.example.oee

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.oee.databinding.ItemExerciseStatusBinding

// 운동 상태를 나타내는 RecyclerView 어댑터 클래스
class ExerciseStatusAdapter(val items: ArrayList<ExerciseModel>) :
    RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {

    // ViewHolder 생성 함수
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemExerciseStatusBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    // ViewHolder에 데이터를 바인딩하는 함수
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model: ExerciseModel = items[position]

        holder.tvItem.text = model.getId().toString()

        // https://stackoverflow.com/questions/8472349/how-to-set-text-color-to-a-text-view-programmatically
        // 운동의 상태에 따라 뷰의 배경과 텍스트 색상을 변경
        when {
            model.getIsSelected() -> {
                // 선택된 항목의 UI를 설정
                holder.tvItem.background =
                    ContextCompat.getDrawable(
                        holder.itemView.context,
                        R.drawable.item_circular_thin_color_accent_border
                    )
                holder.tvItem.setTextColor(Color.parseColor("#212121"))
            }
            model.getIsCompleted() -> {
                // 완료된 항목의 UI를 설정
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.itemView.context, R.drawable.item_circular_color_accent_background)
                holder.tvItem.setTextColor(Color.parseColor("#FFFFFF"))
            }
            else -> {
                // 기본 상태의 UI를 설정
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.itemView.context, R.drawable.item_circular_color_gray_background)
                holder.tvItem.setTextColor(Color.parseColor("#212121"))
            }
        }

    }

    // 총 항목 개수를 반환하는 함수
    override fun getItemCount(): Int {
        return items.size
    }

    // 운동 상태를 표시하는 개별 뷰를 관리하는 ViewHolder 클래스
    class ViewHolder(binding: ItemExerciseStatusBinding) : RecyclerView.ViewHolder(binding.root) {

        val tvItem = binding.tvItem     // 뷰 바인딩에서 텍스트 뷰 가져오기
    }
}