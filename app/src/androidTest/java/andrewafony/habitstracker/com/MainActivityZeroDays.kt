package andrewafony.habitstracker.com

import android.content.SharedPreferences
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityZeroDays : AbstractUiTest() {

    override fun init(preferences: SharedPreferences) {
        preferences.edit().clear().apply()
    }

    @Test
    fun test_zero_days_and_reset() {
        MainPage().run {
            daysText.check("0")
            buttonReset.checkNotDisplayed()
        }
    }
}