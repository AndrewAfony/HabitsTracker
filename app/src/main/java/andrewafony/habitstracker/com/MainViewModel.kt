package andrewafony.habitstracker.com

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

class MainViewModel(
    private val repository: MainRepository,
    private val communication: MainCommunication.Mutable
): MainCommunication.Observe {

    fun init(isFirstRun: Boolean) {
        if (isFirstRun) {
            val days = repository.daysPastFromFirstLaunch()
            if (days == 0) {
                communication.put(UiState.ZeroDays)
            } else{
                communication.put(UiState.NDays(days))
            }
        }
    }

    fun reset() {
        repository.reset()
        communication.put(UiState.ZeroDays)
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<UiState>) {
        communication.observe(owner, observer)
    }
}

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

interface MainCommunication {

    interface Put {

        fun put(value: UiState)
    }

    interface Observe {
        fun observe(owner: LifecycleOwner, observer: Observer<UiState>)
    }

    interface Mutable: Put, Observe

//    class Base(private val liveData: MutableLiveData<UiState> = MutableLiveData()): Mutable {
//
//        override fun put(value: UiState) {
//            liveData.value = value
//        }
//
//        override fun observe(owner: LifecycleOwner, observer: Observer<UiState>) {
//            liveData.observe(owner, observer)
//        }
//    }
}

interface MainRepository {

    fun daysPastFromFirstLaunch(): Int

    fun reset()
}