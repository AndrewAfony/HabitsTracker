package andrewafony.habitstracker.com

import android.app.Application

class App : Application(), ProvideViewModel {

    private lateinit var viewModel: MainViewModel

    override fun onCreate() {
        super.onCreate()

        viewModel = MainViewModel(
            MainRepository.Base(
                CacheDataSource.Base(PreferencesProvider.Factory(BuildConfig.DEBUG).create(this)),
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