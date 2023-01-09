package andrewafony.habitstracker.com

import android.content.SharedPreferences
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.CoreMatchers.not
import org.junit.Test

class MainActivitySomeDaysTest: AbstractUiTest() {

    override fun init(preferences: SharedPreferences) {
        CacheDataSource.Base(preferences).save(System.currentTimeMillis() - 17L * 24 * 3600 * 1000)
    }

    @Test
    fun test_n_days_and_reset() {
        MainPage().run {
            daysText.check("17")
            buttonReset.checkDisplayed()
            buttonReset.click()
            daysText.check("0")
            buttonReset.checkNotDisplayed()
        }
    }
}