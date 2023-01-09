package andrewafony.habitstracker.com

import android.content.Context
import android.content.SharedPreferences

interface PreferencesProvider {

    fun create(context: Context): SharedPreferences

    private abstract class Abstract(private val name: String) : PreferencesProvider {

        override fun create(context: Context): SharedPreferences {
            return context.getSharedPreferences(name, Context.MODE_PRIVATE)
        }
    }

    private class Release : Abstract("release")

    private class Debug : Abstract("debug")

    class Factory(private val isDebug: Boolean) : PreferencesProvider {

        override fun create(context: Context): SharedPreferences =
            (if (isDebug) Debug() else Release()).create(context)

    }
}