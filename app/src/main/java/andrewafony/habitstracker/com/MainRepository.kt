package andrewafony.habitstracker.com

interface MainRepository {

    fun daysPastFromFirstLaunch(): Int

    fun reset()
}