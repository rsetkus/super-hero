package lt.setkus.superhero.app.heroes

import android.content.Intent
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.runner.AndroidJUnit4
import lt.setkus.superhero.app.espresso.MockApiActivityTestRule
import lt.setkus.superhero.util.getJson
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    val mockWebServer = MockWebServer()

    @get:Rule
    var activityTestRule = MockApiActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        mockWebServer.start(8080)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun onSuccessfulResultShouldSeeSuperHeroName() {

        mockWebServer.enqueue(MockResponse()
            .setResponseCode(200)
            .setBody(getJson(InstrumentationRegistry.getContext(), "json/characters/characters.json"))
        )

        activityTestRule.launchActivity(Intent())
        onView(withText("3-D Man")).check(matches(isDisplayed()))
    }
}