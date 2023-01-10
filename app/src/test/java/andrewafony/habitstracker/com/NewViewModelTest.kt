package andrewafony.habitstracker.com

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import org.junit.Assert.assertEquals
import org.junit.Test

class NewViewModelTest {

    @Test
    fun start() {
        val communication = FakeCommunication()
        val interactor = FakeInteractor(listOf(Card.Add))
        val viewModel = NewViewModel(communication, interactor)
        viewModel.init(isFirstRun = true)
        assertEquals(NewUiState.Add(Card.Add), communication.list[0])
        assertEquals(1, communication.list.size)
    }

    @Test
    fun add_first_card() {
        val communication = FakeCommunication()
        val interactor = FakeInteractor(listOf(Card.Add))
        val viewModel = NewViewModel(communication, interactor)
        viewModel.init(isFirstRun = true)
        assertEquals(NewUiState.Add(Card.Add), communication.list[0])
        assertEquals(1, communication.list.size)

        viewModel.addCard(position = 0)
        assertEquals(NewUiState.Replace(position = 0, Card.Make), communication.list[1])
        assertEquals(2, communication.list.size)
    }

    @Test
    fun cancel_make_card() {
        val communication = FakeCommunication()
        val interactor = FakeInteractor(listOf(Card.Add))
        val viewModel = NewViewModel(communication, interactor)
        viewModel.init(isFirstRun = true)
        assertEquals(NewUiState.Add(Card.Add), communication.list[0])
        assertEquals(1, communication.list.size)

        viewModel.addCard(position = 0)
        assertEquals(NewUiState.Replace(position = 0, Card.Make), communication.list[1])
        assertEquals(2, communication.list.size)

        viewModel.cancelCardCreation(position = 0)
        assertEquals(NewUiState.Replace(position = 0, Card.Add), communication.list[2])
        assertEquals(3, communication.list.size)
    }

    @Test
    fun save_first_card() {
        val communication = FakeCommunication()
        val interactor = FakeInteractor(listOf(Card.Add))
        val viewModel = NewViewModel(communication, interactor)
        viewModel.init(isFirstRun = true)
        assertEquals(NewUiState.Add(Card.Add), communication.list[0])
        assertEquals(1, communication.list.size)

        viewModel.addCard(position = 0)
        assertEquals(NewUiState.Replace(position = 0, Card.Make), communication.list[1])
        assertEquals(2, communication.list.size)

        interactor.canAddNewCard = true
        viewModel.saveCardCreation(text = "days without smoking", position = 0)
        assertEquals("days without smoking", interactor.saveNewCardList[0])
        assertEquals(1, interactor.saveNewCardList.size)
        assertEquals(NewUiState.Replace(position = 0, Card.ZeroDays("days without smoking")), communication.list[2])
        assertEquals(NewUiState.Add(Card.Add), communication.list[3])
        assertEquals(4, communication.list.size)
        assertEquals(1, interactor.canAddNewCardList.size)
        assertEquals(true, interactor.canAddNewCardList[0])
    }

    @Test
    fun save_only_one_card() {
        val communication = FakeCommunication()
        val interactor = FakeInteractor(listOf(Card.Add))
        val viewModel = NewViewModel(communication, interactor)
        viewModel.init(isFirstRun = true)
        assertEquals(NewUiState.Add(Card.Add), communication.list[0])
        assertEquals(1, communication.list.size)

        viewModel.addCard(position = 0)
        assertEquals(NewUiState.Replace(position = 0, Card.Make), communication.list[1])
        assertEquals(2, communication.list.size)

        interactor.canAddNewCard = false
        viewModel.saveCardCreation(text = "days without smoking", position = 0)
        assertEquals("days without smoking", interactor.saveNewCardList[0])
        assertEquals(1, interactor.saveNewCardList.size)
        assertEquals(NewUiState.Replace(position = 0, Card.ZeroDays("days without smoking")), communication.list[2])
        assertEquals(3, communication.list.size)
        assertEquals(1, interactor.canAddNewCardList.size)
        assertEquals(true, interactor.canAddNewCardList[0])
    }
}

private class FakeInteractor(private val cards: List<Card>) : MainInteractor {

    override fun cards(): List<Card> {
        return cards
    }

    var canAddNewCard: Boolean = true
    var canAddNewCardList = mutableListOf<Boolean>()

    override fun canAddNewCard(): Boolean {
        canAddNewCardList.add(canAddNewCard)
        return canAddNewCard
    }

    var saveNewCardList = mutableListOf<String>()

    override fun saveNewCard(text: String) {
        saveNewCardList.add(text)
    }

}

private class FakeCommunication : NewMainCommunication {

    val list = mutableListOf<NewUiState>()

    override fun put(newUiState: NewUiState) {
        list.add(newUiState)
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<NewUiState>) = Unit

}