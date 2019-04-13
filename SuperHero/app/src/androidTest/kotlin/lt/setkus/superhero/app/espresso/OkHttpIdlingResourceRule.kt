package lt.setkus.superhero.app.espresso

import androidx.test.espresso.IdlingRegistry
import com.jakewharton.espresso.OkHttp3IdlingResource
import lt.setkus.superhero.data.http.OkHttpClientProvider
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class OkHttpIdlingResourceRule : TestRule {

    override fun apply(base: Statement?, description: Description?): Statement {
        return object : Statement() {
            override fun evaluate() {
                val idlingResource = OkHttp3IdlingResource.create("okhttp", OkHttpClientProvider.getClient())
                IdlingRegistry.getInstance().register(idlingResource)

                base?.evaluate()

                IdlingRegistry.getInstance().unregister(idlingResource)
            }
        }
    }
}