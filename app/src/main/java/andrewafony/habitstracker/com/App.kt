package andrewafony.habitstracker.com

import android.app.Application
import android.content.Context

class App: Application(), ProvideViewModel {

    private lateinit var viewModel: MainViewModel

    override fun onCreate() {
        super.onCreate()

        viewModel = MainViewModel(
            MainRepository.Base(
                CacheDataSource.Base(getSharedPreferences("base", Context.MODE_PRIVATE)),
                Now.Base()
            ),
            MainCommunication.Base()
        )
    }

    override fun provideViewModel(): MainViewModel {
        return viewModel
    }
}

interface ProvideViewModel {

    fun provideViewModel(): MainViewModel
}