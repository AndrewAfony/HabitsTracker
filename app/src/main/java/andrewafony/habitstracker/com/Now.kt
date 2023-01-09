package andrewafony.habitstracker.com

interface Now {

    fun time(): Long

    class Base() : Now {

        override fun time() = System.currentTimeMillis()
    }
}