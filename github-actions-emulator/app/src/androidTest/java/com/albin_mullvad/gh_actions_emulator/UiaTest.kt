package com.albin_mullvad.gh_actions_emulator

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class UiaTest {
    @Test
    fun uiaTest() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val device = UiDevice.getInstance(instrumentation)
        val targetContext = InstrumentationRegistry.getInstrumentation().targetContext

        // Start from the home screen
        device.pressHome()

        // Wait for launcher
        val launcherPackage: String = device.launcherPackageName
        assertThat(launcherPackage, notNullValue())
//        assertNotNull(launcherPackage)
        device.wait(
            Until.hasObject(By.pkg(launcherPackage).depth(0)),
            3000
        )

        // Launch the app
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage(
            "com.albin_mullvad.gh_actions_emulator"
        )?.apply {
            // Clear out any previous instances
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        context.startActivity(intent)


        // Wait for the app to appear
        device.wait(
            Until.hasObject(By.pkg("com.albin_mullvad.gh_actions_emulator").depth(0)),
            3000
        )

        val selectorOne = By.text("Hello One!")

        device.wait(
            Until.hasObject(selectorOne),
            3000
        )


        assertThat(device.findObject(selectorOne), notNullValue())

        device.findObject(By.text("Change!")).click()

        assertThat(device.findObject(By.text("Hello Two!")), notNullValue())
    }

    @Test
    fun uiaTest2() {
    }
}
