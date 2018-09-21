package lt.setkus.superhero.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import lt.setkus.superhero.data.http.CharacterService
import org.koin.android.ext.android.inject
import superhero.setkus.lt.superhero.R
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val characterService: CharacterService by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
