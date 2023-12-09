package com.example.oee

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oee.databinding.ItemHistoryRowBinding


// 운동 기록을 표시하기 위한 리사이클러 뷰 어댑터
class HistoryAdapter(private val items: ArrayList<String>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    // 뷰 홀더 생성을 위한 함수
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemHistoryRowBinding.inflate(
            LayoutInflater.from(parent.context),parent,false)
        )
    }

    // 뷰 홀더에 데이터 바인딩을 위한 함수
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // 운동 완료 날짜
        val date: String = items.get(position)

        // 항목의 위치와 날짜 표시
        holder.tvPosition.text = (position + 1).toString()
        holder.tvItem.text = date

        // 짝수 위치의 항목 배경색 변경
        if (position % 2 == 0) {
            holder.llHistoryItemMain.setBackgroundColor(
                Color.parseColor("#EBEBEB")
            )
        } else {
            // 홀수 위치의 항목 배경색 변경
            holder.llHistoryItemMain.setBackgroundColor(
                Color.parseColor("#FFFFFF")
            )
        }
    }

    // 아이템 개수 반환 함수
    override fun getItemCount(): Int {
        return items.size
    }

    // 뷰 홀더 클래스
    class ViewHolder(binding: ItemHistoryRowBinding) : RecyclerView.ViewHolder(binding.root) {

        // 각 항목의 메인 레이아웃
        val llHistoryItemMain = binding.llHistoryItemMain
        // 날짜 표시 텍스트 뷰
        val tvItem = binding.tvItem
        // 위치 표시 텍스트 뷰
        val tvPosition = binding.tvPosition
    }
}