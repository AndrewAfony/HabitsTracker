package andrewafony.habitstracker.com

import org.junit.Assert.assertEquals
import org.junit.Test

class MainRepositoryTest {

    @Test
    fun test_no_days() {
        val cacheDataSource = FakeDataSource.Base()
        val now = FakeNow.Base()
        val repository = MainRepository.Base(cacheDataSource, now)
        now.addTime(1544)
        val actual = repository.daysPastFromFirstLaunch()
        assertEquals(0, actual)
        assertEquals(1544, cacheDataSource.time(-1))
    }

    @Test
    fun test_some_days() {
        val cacheDataSource = FakeDataSource.Base()
        val now = FakeNow.Base()
        cacheDataSource.save(0)
        now.addTime(7 * 24 * 3600 * 1000)
        val repository = MainRepository.Base(cacheDataSource, now)
        val actual = repository.daysPastFromFirstLaunch()
        assertEquals(7, actual)
    }

    @Test
    fun test_reset() {
        val cacheDataSource = FakeDataSource.Base()
        val now = FakeNow.Base()
        val repository = MainRepository.Base(cacheDataSource, now)
        now.addTime(54321)
        repository.reset()
        assertEquals(54321, cacheDataSource.time(-1))
    }
}

private interface FakeNow : Now {

    fun addTime(difference: Long)

    class Base : FakeNow {

        private var time = 0L

        override fun time(): Long = time

        override fun addTime(difference: Long) {
            this.time += difference
        }
    }
}

private interface FakeDataSource: CacheDataSource {

    class Base() : FakeDataSource {

        private var time : Long = -100

        override fun save(time: Long) {
            this.time = time
        }

        override fun time(default: Long) : Long = if (time == -100L) default else time

    }
}