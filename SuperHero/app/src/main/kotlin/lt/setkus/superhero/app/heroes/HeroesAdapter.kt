package lt.setkus.superhero.app.heroes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_hero.superHeroName
import kotlinx.android.synthetic.main.item_hero.superHeroTile
import lt.setkus.superhero.R

class HeroesAdapter : RecyclerView.Adapter<HeroesAdapter.HeroViewHolder>() {

    private val superHeroes = arrayListOf<SuperHeroViewData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        val containerView = LayoutInflater.from(parent.context).inflate(R.layout.item_hero, parent, false)
        return HeroViewHolder(containerView)
    }

    override fun getItemCount() = superHeroes.size

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) =
        holder.bindData(superHeroes[position])

    fun addSuperHeroes(items: List<SuperHeroViewData>) {
        superHeroes.addAll(items)
        notifyDataSetChanged()
    }

    inner class HeroViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindData(data: SuperHeroViewData) {
            superHeroName.text = data.name
            superHeroTile.contentDescription = data.name
            loadImage(data.tileUrl)
        }

        private fun loadImage(url: String?) {
            Glide
                .with(itemView.context)
                .load(url)
                .centerCrop()
                .into(superHeroTile)
        }
    }
}