package andrewafony.habitstracker.com

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class App : Application(), ProvideViewModel {

    private lateinit var viewModel: MainViewModel

    override fun onCreate() {
        super.onCreate()

        val preferences = if (BuildConfig.DEBUG) PreferencesProvider.Debug() else PreferencesProvider.Release()

        viewModel = MainViewModel(
            MainRepository.Base(
                CacheDataSource.Base(preferences.create(this)),
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

interface PreferencesProvider {

    fun create(context: Context): SharedPreferences

    abstract class Abstract(private val name: String): PreferencesProvider {

        override fun create(context: Context): SharedPreferences {
            return context.getSharedPreferences(name, Context.MODE_PRIVATE)
        }
    }

    class Release: Abstract("release")

    class Debug: Abstract("debug")
}