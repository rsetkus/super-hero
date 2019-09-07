package lt.setkus.superhero.app.heroes

import androidx.recyclerview.widget.DiffUtil
import lt.setkus.superhero.domain.heroes.SuperHero

data class SuperHeroViewData(val name: String, val heroId: Int, val tileUrl: String?) {

    companion object {
        fun getDiff() =
            object : DiffUtil.ItemCallback<SuperHeroViewData>() {

                override fun areItemsTheSame(oldItem: SuperHeroViewData, newItem: SuperHeroViewData) =
                    oldItem.heroId == newItem.heroId

                override fun areContentsTheSame(oldItem: SuperHeroViewData, newItem: SuperHeroViewData) =
                    oldItem == newItem
            }
    }
}

val superHeroViewDataMapper: (domain: SuperHero) -> SuperHeroViewData = {
    SuperHeroViewData(it.name, it.id, it.imageUrl)
}