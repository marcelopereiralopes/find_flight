package study.com.findflight.ui.activity

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import study.com.findflight.R
import study.com.findflight.resource.MockedFlightsForInterfaceTest


@RunWith(AndroidJUnit4ClassRunner::class)
class FlightsActivityTest {

    private lateinit var server: MockWebServer

    @get:Rule
    var mActivityRule: ActivityTestRule<FlightsActivity> =
        ActivityTestRule(FlightsActivity::class.java, false, false)

    @Before
    @Throws(Exception::class)
    fun setUp() {
        server = MockWebServer()
        server.start(8080)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun whenResultIsOk_shouldDisplayFlightListAndShowFAB() {
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(MockedFlightsForInterfaceTest.flights)
        )
        mActivityRule.launchActivity(Intent())
        onView(withId(R.id.fabFilter)).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.recyclerView), isDisplayed()))
    }

    @Test
    fun whenResultIsOk_butNotFoundFlights_shouldDisplayEmptyListMessageAndShowFAB() {
        server.enqueue(MockResponse().setResponseCode(200).setBody("{}"))
        mActivityRule.launchActivity(Intent())
        onView(withId(R.id.fabFilter)).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.emptyListMessage), isDisplayed()))
    }

    @Test
    fun whenResultIsTimeout_shouldDisplayEmptyListMessageAndHideFAB() {
        server.enqueue(
            MockResponse()
                .setResponseCode(408)
                .setHeader("Connection", "Close")
                .setBody("You took too long!")
        )
        mActivityRule.launchActivity(Intent())
        onView(withId(R.id.fabFilter)).check(matches(not(isDisplayed())))
        onView(allOf(withId(R.id.emptyListMessage), isDisplayed()))
    }
}