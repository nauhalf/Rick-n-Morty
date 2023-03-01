package com.nauhalf.ricknmorty.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nauhalf.ricknmorty.R
import com.nauhalf.ricknmorty.util.atPosition
import com.nauhalf.ricknmorty.view.main.CharacterAdapter
import com.nauhalf.ricknmorty.view.main.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun test_CheckItemCharacter() {
        //Check Character Name text view exists
        onView(withId(R.id.rv_character))
            .check(matches(atPosition(0, hasDescendant(withId(R.id.tv_character_name)))))
        //Check Character Status text view exists
        onView(withId(R.id.rv_character))
            .check(matches(atPosition(0, hasDescendant(withId(R.id.tv_character_status)))))
        //Check Character Image text view exists
        onView(withId(R.id.rv_character))
            .check(matches(atPosition(0, hasDescendant(withId(R.id.iv_character)))))

    }

    @Test
    fun test_ScrollToLastAndBackToTheFirstPositionCharacter() {
        // Scroll to last position
        onView(
            withId(R.id.rv_character)
        ).perform(RecyclerViewActions.scrollToLastPosition<CharacterAdapter.CharacterViewHolder>())

        // Scroll to the first position
        onView(
            withId(R.id.rv_character)
        ).perform(RecyclerViewActions.scrollToPosition<CharacterAdapter.CharacterViewHolder>(0))
    }

    @Test
    fun test_ClickedCharacter() {
        val target = hasDescendant(
            withText("Morty Smith")
        )
        // Scroll to last position
        onView(
            withId(R.id.rv_character)
        ).perform(RecyclerViewActions.scrollToLastPosition<CharacterAdapter.CharacterViewHolder>())

        // Back scroll to target
        onView(
            withId(R.id.rv_character)
        ).perform(
            RecyclerViewActions.scrollTo<CharacterAdapter.CharacterViewHolder>(
                target
            )
        )

        // click on target
        onView(
            withId(R.id.rv_character)
        ).perform(
            RecyclerViewActions.actionOnItem<CharacterAdapter.CharacterViewHolder>(
                target, click()
            )
        )

        // check textview in DetailActivity is displayed
        onView(
            withId(R.id.tv_name)
        ).check(matches(isDisplayed()))

        // check FloatingActionButton in DetailActivity is displayed
        onView(
            withId(R.id.fab_back)
        ).check(matches(isDisplayed()))


        // click FloatingActionButton to back to MainActivity
        onView(
            withId(R.id.fab_back)
        ).perform(click())

        // Check RecyclerView is showing again
        onView(
            withId(R.id.rv_character)
        ).check(matches(isDisplayed()))

    }
}
