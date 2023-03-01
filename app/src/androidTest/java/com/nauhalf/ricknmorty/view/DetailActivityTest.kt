package com.nauhalf.ricknmorty.view

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nauhalf.ricknmorty.R
import com.nauhalf.ricknmorty.util.hasItem
import com.nauhalf.ricknmorty.util.withResourceText
import com.nauhalf.ricknmorty.view.detail.DetailActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class DetailActivityTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    lateinit var scenario: ActivityScenario<DetailActivity>

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun test_NameImageRecyclerViewExistsInDetail() {
        openDetailActivity(2)

        // Check Name is displayed
        onView(
            withId(
                R.id.tv_name
            )
        ).check(
            matches(
                isDisplayed()
            )
        )

        // Check Image is displayed
        onView(
            withId(
                R.id.iv_character
            )
        ).check(
            matches(
                isDisplayed()
            )
        )

        // Check RecyclerView is Displayed
        onView(
            withId(
                R.id.rv_character_detail
            )
        ).check(
            matches(
                isDisplayed()
            )
        )
    }

    @Test
    fun test_FirstSeenIsShowing() {
        openDetailActivity(1)

        // Check item with text First Seen In is Displayed
        onView(
            withId(
                R.id.rv_character_detail
            )
        ).check(
            matches(
                hasItem(
                    hasDescendant(
                        withResourceText(R.string.first_seen_in)
                    )
                )
            )
        )
    }

    @Test
    fun test_FirstSeenIsNotShowing() {
        openDetailActivity(20)
        // Check item with text First Seen In is not Displayed
        onView(
            withId(
                R.id.rv_character_detail
            )
        ).check(
            matches(
                not(
                    hasItem(
                        hasDescendant(
                            withResourceText(R.string.first_seen_in)
                        )
                    )
                )
            )
        )
    }

    @Test
    fun test_ScrollDetailToBottom() {
        openDetailActivity(1)
        // Scroll to bottom on detail character
        onView(
            withId(R.id.rv_character_detail)
        ).perform(RecyclerViewActions.scrollToLastPosition<RecyclerView.ViewHolder>())

    }

    @Test
    fun test_FabClick() {
        openDetailActivity(1)
        // click fab on detail character
        onView(
            withId(R.id.fab_back)
        ).perform(click())
    }

    private fun openDetailActivity(id: Int) {
        val intent = Intent(
            ApplicationProvider.getApplicationContext(),
            DetailActivity::class.java
        ).putExtra("id", id)
        scenario = ActivityScenario.launch(intent)

    }
}
