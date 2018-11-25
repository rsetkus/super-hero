package lt.setkus.superhero.app.heroes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import lt.setkus.superhero.R

class HeroesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heroes)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.custom_fonts_container, HeroesFragment.newHeroesFragment()).commit()
        }
    }
}
