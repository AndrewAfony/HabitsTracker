package andrewafony.habitstracker.com

import android.content.SharedPreferences

interface CacheDataSource {

    fun save(time: Long)

    fun time(default: Long): Long

    class Base(private val preferences: SharedPreferences) : CacheDataSource {

        override fun save(time: Long) {
            preferences.edit().putLong(KEY, time).apply()
        }

        override fun time(default: Long): Long = preferences.getLong(KEY, default)

        companion object {
            private const val KEY = "savedTime"
        }
    }
}