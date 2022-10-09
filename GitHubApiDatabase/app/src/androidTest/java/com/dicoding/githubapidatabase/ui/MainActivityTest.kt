package com.dicoding.githubapidatabase.ui

import android.view.KeyEvent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dicoding.githubapidatabase.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class UiTest {

    private val dummyQuery = "aliefabdillah"

    @Before
    fun setup(){
        ActivityScenario.launch(MainActivity::class.java)
    }

    //ui test cek searchView
    @Test
    fun checkSearchView(){
        onView(withId(R.id.searchView)).check(matches(isDisplayed()))
        onView(withId(R.id.searchView)).perform(typeText(dummyQuery))
        onView(withId(R.id.searchView)).perform(pressKey(KeyEvent.KEYCODE_ENTER))

        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_user)).check(matches(isDisplayed()))
    }

    //ui test untuk cek tombol settingMenu
    @Test
    fun checkSettingBtn(){
        onView(withId(R.id.settingMenu)).check(matches(isDisplayed()))
        onView(withId(R.id.settingMenu)).perform(click())

        onView(withId(R.id.settingActivity))
        onView(withId(R.id.switchTheme)).check(matches(isDisplayed()))
    }

    //ui test favorite Button
    @Test
    fun checkFavBtn(){
        onView(withId(R.id.favoriteMenu)).check(matches(isDisplayed()))
        onView(withId(R.id.favoriteMenu)).perform(click())

        onView(withId(R.id.favoriteMenuActivity)).check(matches(isDisplayed()))
    }
}