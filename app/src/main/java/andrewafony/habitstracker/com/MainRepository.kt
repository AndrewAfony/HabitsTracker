package andrewafony.habitstracker.com

import android.content.SharedPreferences

interface MainRepository {

    fun daysPastFromFirstLaunch(): Int

    fun reset()

    class Base(
        private val cacheDataSource: CacheDataSource,
        private val now: Now
    ) : MainRepository {

        override fun daysPastFromFirstLaunch(): Int {
            val savedTime = cacheDataSource.time(-1)
            return if (savedTime == -1L) {
                reset()
                0
            } else {
                val diff = now.time() - savedTime
                (diff / (24 * 3600 * 1000)).toInt()
            }
        }

        override fun reset() {
            cacheDataSource.save(now.time())
        }

    }
}

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

interface Now {

    fun time(): Long

    class Base() : Now {

        override fun time() = System.currentTimeMillis()
    }
}
