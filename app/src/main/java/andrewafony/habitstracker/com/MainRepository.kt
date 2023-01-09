package andrewafony.habitstracker.com

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

