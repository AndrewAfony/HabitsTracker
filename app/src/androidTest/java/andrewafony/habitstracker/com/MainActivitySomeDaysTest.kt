package andrewafony.habitstracker.com

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivitySomeDaysTest {

    @get:Rule
    val activityScenario = lazyActivityScenarioRule<MainActivity>(launchActivity = false)

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<App>()
        val preferences = PreferencesProvider.Debug().create(context)
        CacheDataSource.Base(preferences).save(System.currentTimeMillis() - 17L * 24 * 3600 * 1000)
        activityScenario.launch(Intent(context, MainActivity::class.java))
    }

    @Test
    fun test_n_days_and_reset() {
        onView(withId(R.id.daysNumber)).check(matches(withText("17")))
        onView(withId(R.id.buttonReset)).check(matches(isDisplayed()))
        onView(withId(R.id.buttonReset)).perform(click())
        onView(withId(R.id.daysNumber)).check(matches(withText("0")))
        onView(withId(R.id.buttonReset)).check(matches(not(isDisplayed())))
    }
}