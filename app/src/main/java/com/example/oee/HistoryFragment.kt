import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oee.HistoryAdapter
import com.example.oee.HistoryDao
import com.example.oee.WorkOutApp
import com.example.oee.databinding.ActivityHistoryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class HistoryFragment : DialogFragment() {
    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = 850 // 픽셀 단위로 너비 설정
            val height = ViewGroup.LayoutParams.WRAP_CONTENT // 높이는 내용에 맞게 조절
            dialog.window?.setLayout(width, height)
        }
    }

    private var _binding: ActivityHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = ActivityHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Fragment에서 액션바 설정 (선택적)
        // 예: (activity as? AppCompatActivity)?.supportActionBar?.title = "HISTORY"

        val dao = (activity?.application as? WorkOutApp)?.db?.historyDao()

        // Arguments 또는 ViewModel을 사용하여 선택된 날짜 가져오기
        val selectedDate = arguments?.getString("selectedDate")
        if (selectedDate != null && dao != null) {
            getCompletedDatesForSelectedDate(dao, selectedDate)
        }
    }

    private fun getCompletedDatesForSelectedDate(historyDao: HistoryDao, date: String) {
        lifecycleScope.launch {
            historyDao.fetchHistoryByDate(date).collect { completedDatesList ->
                if (completedDatesList.isNotEmpty()) {
                    binding.tvHistory.visibility = View.VISIBLE
                    binding.rvHistory.visibility = View.VISIBLE
                    binding.tvNoDataAvailable.visibility = View.GONE

                    binding.rvHistory.layoutManager = LinearLayoutManager(context)
                    val historyAdapter = HistoryAdapter(ArrayList(completedDatesList.map { it.date }))
                    binding.rvHistory.adapter = historyAdapter
                } else {
                    binding.tvHistory.visibility = View.GONE
                    binding.rvHistory.visibility = View.GONE
                    binding.tvNoDataAvailable.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
