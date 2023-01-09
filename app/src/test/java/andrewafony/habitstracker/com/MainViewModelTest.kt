package andrewafony.habitstracker.com

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import org.junit.Assert.*
import org.junit.Test

class MainViewModelTest {

    @Test
    fun test_zero_days() {
        val repository = FakeRepository(0)
        val communication = FakeMainCommunication.Base()
        val viewModel = MainViewModel(repository, communication)
        viewModel.init(isFirstRun = true)
        assertEquals(true, communication.checkCalledCount(1))
        assertEquals(true, communication.isSame(UiState.ZeroDays))
        viewModel.init(isFirstRun = false)
        assertEquals(true, communication.checkCalledCount(1))
    }

    @Test
    fun test_n_days() {
        val repository = FakeRepository(5)
        val communication = FakeMainCommunication.Base()
        val viewModel = MainViewModel(repository, communication)
        viewModel.init(isFirstRun = true)
        assertEquals(true, communication.checkCalledCount(1))
        assertEquals(true, communication.isSame(UiState.NDays(days = 5)))
        viewModel.init(isFirstRun = false)
        assertEquals(true, communication.checkCalledCount(1))
    }
}

private class FakeRepository(private val days: Int) : MainRepository {

    override fun daysPastFromFirstLaunch(): Int {
        return days
    }
}

private interface FakeMainCommunication : MainCommunication.Mutable {

    fun checkCalledCount(count: Int) : Boolean

    fun isSame(uiState: UiState): Boolean

    class Base: FakeMainCommunication {

        private lateinit var state: UiState
        private var callCount = 0

        override fun checkCalledCount(count: Int): Boolean = count == callCount

        override fun isSame(uiState: UiState): Boolean = state.equals(uiState)


        override fun put(value: UiState) {
            callCount++
            state = value
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<UiState>) = Unit
    }
}