package study.com.findflight.ui.activity

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.Rule
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.Espresso.onView
import android.content.Intent
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import okhttp3.mockwebserver.MockResponse
import org.junit.Test
import study.com.findflight.R
import study.com.findflight.data.JSON_RESPONSE_SUCCESS


@RunWith(AndroidJUnit4ClassRunner::class)
class FlightsActivityTest {

    private lateinit var server: MockWebServer

    @get:Rule var mActivityRule: ActivityTestRule<FlightsActivity> =
        ActivityTestRule(FlightsActivity::class.java, false, false)

    @Before @Throws(Exception::class)
    fun setUp(){
        server = MockWebServer()
        server.start()
        setupServerUrl()
    }

    @After
    fun tearDown(){
        server.shutdown()
    }

    private fun setupServerUrl() {server.url("/")}

    @Test
    fun whenResultIsOk_shouldDisplayListWithFlights() {
        server.enqueue(MockResponse().setResponseCode(200).setBody(JSON_RESPONSE_SUCCESS))
        mActivityRule.launchActivity(Intent())
        onView(withId(R.id.fabFilter)).check(matches(isDisplayed()))
    }
}