package lt.setkus.superhero.app.heroes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_hero.superHeroName
import kotlinx.android.synthetic.main.item_hero.superHeroTile
import lt.setkus.superhero.R

private const val MAXIMUM_NAME_CHARS = 23

class HeroesAdapter : PagedListAdapter<SuperHeroViewData, HeroesAdapter.HeroViewHolder>(SuperHeroViewData.getDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        val containerView = LayoutInflater.from(parent.context).inflate(R.layout.item_hero, parent, false)
        return HeroViewHolder(containerView)
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) =
        holder.bindData(getItem(position))

    fun isSpannable(position: Int) = MAXIMUM_NAME_CHARS <= getItem(position)?.name?.length ?: 0

    inner class HeroViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindData(data: SuperHeroViewData?) {
            data?.run {
                superHeroName.text = this.name
                superHeroTile.contentDescription = this.name
                loadImage(this.tileUrl)

                ViewCompat.setTransitionName(superHeroTile, this.name)

                containerView.setOnClickListener {
                    val directions = HeroesFragmentDirections
                        .actionMainToDetails(tileUrl ?: "", name, heroId)
                    val extras = FragmentNavigator.Extras.Builder()
                        .addSharedElements(mapOf(superHeroTile to name))
                        .build()
                    Navigation.findNavController(it).navigate(directions, extras)
                }
            }
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