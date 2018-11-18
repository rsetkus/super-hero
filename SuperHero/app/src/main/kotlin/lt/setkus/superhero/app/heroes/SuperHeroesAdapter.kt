package lt.setkus.superhero.app.heroes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.super_hero_item.superHeroName
import kotlinx.android.synthetic.main.super_hero_item.superHeroTile
import lt.setkus.superhero.R

class SuperHeroesAdapter : RecyclerView.Adapter<SuperHeroesAdapter.SuperHeroViewHolder>() {

    private val superHeroes = arrayListOf<SuperHeroViewData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperHeroViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.super_hero_item, parent, false)
        return SuperHeroViewHolder(itemView)
    }

    override fun getItemCount(): Int = superHeroes.size

    override fun onBindViewHolder(holder: SuperHeroViewHolder, position: Int) {
        holder.bindData(superHeroes[position])
    }

    fun addSuperHeroes(items: List<SuperHeroViewData>) {
        superHeroes.addAll(items)
        notifyDataSetChanged()
    }

    inner class SuperHeroViewHolder(override val containerView: View) : ViewHolder(containerView), LayoutContainer {

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