package lt.setkus.superhero.app.espresso

import androidx.appcompat.app.AppCompatActivity
import androidx.test.rule.ActivityTestRule
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.loadKoinModules
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MockApiActivityTestRule<T : AppCompatActivity> : ActivityTestRule<T> {
    constructor(activityClass: Class<T>?) : super(activityClass, false, false)

    override fun beforeActivityLaunched() {
        super.beforeActivityLaunched()

        loadKoinModules(
            module {
                single(override = true) { provideMockRetrofit() }
            }
        )
    }

    private fun provideMockRetrofit() =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl("http://localhost:8080")
            .build()
}
