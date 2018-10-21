package lt.setkus.superhero.app.heroes

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.runner.AndroidJUnit4
import lt.setkus.superhero.app.espresso.MockApiActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var activityTestRule = MockApiActivityTestRule(MainActivity::class.java)

    @Test
    fun onSuccessfulResultShouldSeeSuperHeroName() {
        activityTestRule.launchActivity(Intent())
        onView(withText("Batman")).check(matches(isDisplayed()))
    }
}