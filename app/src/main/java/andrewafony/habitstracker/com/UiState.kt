package andrewafony.habitstracker.com

import android.view.View
import android.widget.Button
import android.widget.TextView

sealed class UiState {

    abstract fun apply(daysText: TextView, resetButton: Button)

    object ZeroDays: UiState() {
        override fun apply(daysText: TextView, resetButton: Button) {
            daysText.text = "0"
            resetButton.visibility = View.GONE
        }
    }
    data class NDays(private val days: Int): UiState() {
        override fun apply(daysText: TextView, resetButton: Button) {
            daysText.text = days.toString()
            resetButton.visibility = View.VISIBLE
        }
    }
}