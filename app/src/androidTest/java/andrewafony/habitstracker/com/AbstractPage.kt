package andrewafony.habitstracker.com

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers.withId

abstract class AbstractPage {

    protected fun Int.view(): ViewInteraction = onView(withId(this))
}