package lt.setkus.superhero.app.heroes

import android.content.Intent
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
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

private const val HEROES_PATH = "json/characters/characters.json"
private const val HERO_PATH = "json/characters/character.json"

@RunWith(AndroidJUnit4::class)
class HeroesActivityTest {

    val mockWebServer = MockWebServer()

    @get:Rule
    var activityTestRule = MockApiActivityTestRule(HeroesActivity::class.java)

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
        mockListHeroesResponse(HEROES_PATH)

        activityTestRule.launchActivity(Intent())
        onView(withText("Karvasnukis")).check(matches(isDisplayed()))
    }

    private fun mockListHeroesResponse(path: String) {
        mockWebServer.enqueue(MockResponse()
            .setResponseCode(200)
            .setBody(getJson(InstrumentationRegistry.getContext(), path))
        )
    }

    @Test
    fun onSingleHeroTapShouldShowDetails() {
        mockListHeroesResponse(HEROES_PATH)

        activityTestRule.launchActivity(Intent())
        onView(withText("Karvasnukis")).perform(click())
        mockListHeroesResponse(HERO_PATH)

        onView(withText("Karvasnukis")).check(matches(isDisplayed()))
        onView(withText("Labai blogas blogietis")).check(matches(isDisplayed()))
    }
}