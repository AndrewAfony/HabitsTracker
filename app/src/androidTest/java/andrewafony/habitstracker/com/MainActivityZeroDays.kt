package andrewafony.habitstracker.com

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityZeroDays {

    @get:Rule
    val activityScenario = lazyActivityScenarioRule<MainActivity>(launchActivity = false)

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<App>()
        val preferences = PreferencesProvider.Debug().create(context)
        preferences.edit().clear().apply()
        activityScenario.launch(Intent(context, MainActivity::class.java))
    }

    @Test
    fun test_zero_days_and_reset() {
        onView(withId(R.id.daysNumber)).check(matches(ViewMatchers.withText("0")))
        onView(withId(R.id.buttonReset)).check(matches(not(isDisplayed())))
    }
}