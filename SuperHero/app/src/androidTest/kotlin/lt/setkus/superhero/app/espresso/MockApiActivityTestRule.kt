package lt.setkus.superhero.app.espresso

import android.app.Activity
import androidx.test.rule.ActivityTestRule
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.loadKoinModules

class MockApiActivityTestRule<T : Activity> : ActivityTestRule<T> {
    constructor(activityClass: Class<T>?) : super(activityClass, false, false)

    override fun beforeActivityLaunched() {
        super.beforeActivityLaunched()

        loadKoinModules(mockHttpModule)
    }
}

val mockHttpModule = module {  }