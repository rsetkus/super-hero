package lt.setkus.superhero.app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import lt.setkus.superhero.app.di.module.ActivityInjector
import lt.setkus.superhero.app.di.module.DaggerActivityInjector
import lt.setkus.superhero.app.di.module.HttpModule
import lt.setkus.superhero.data.http.CharacterService
import superhero.setkus.lt.superhero.R
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val injector: ActivityInjector = DaggerActivityInjector.builder().httpModule(HttpModule).build()

    @Inject
    lateinit var characterService: CharacterService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector.inject(this)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.get_characters_button).setOnClickListener { loadCharacters() }
    }

    private fun loadCharacters() = Thread {
        val characterDataWrapper = characterService.getCharacters()
        val result = characterDataWrapper.execute()
        if (result.isSuccessful) {
            Timber.d(result.body().toString())
        }
    }.start()
}
