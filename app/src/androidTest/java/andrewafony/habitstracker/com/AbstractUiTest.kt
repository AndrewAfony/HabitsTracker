package andrewafony.habitstracker.com

import android.content.Intent
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class AbstractUiTest {

    @get:Rule
    val activityScenario = lazyActivityScenarioRule<MainActivity>(launchActivity = false)

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<App>()
        val preferences = PreferencesProvider.Debug().create(context)
        init(preferences)
        activityScenario.launch(Intent(context, MainActivity::class.java))
    }

    protected abstract fun init(preferences: SharedPreferences)

    protected fun ViewInteraction.check(text: String): ViewInteraction = check(matches(withText(text)))
    protected fun ViewInteraction.checkDisplayed(): ViewInteraction = check(matches(isDisplayed()))
    protected fun ViewInteraction.checkNotDisplayed(): ViewInteraction = check(matches(not(isDisplayed())))
    protected fun ViewInteraction.click(): ViewInteraction = perform(ViewActions.click())
}