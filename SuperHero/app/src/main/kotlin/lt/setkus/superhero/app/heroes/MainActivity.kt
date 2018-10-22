package lt.setkus.superhero.app.heroes

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import lt.setkus.superhero.R

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.main_container, SuperHeroesFragment.newInstance()).commit()
        }
    }
}
