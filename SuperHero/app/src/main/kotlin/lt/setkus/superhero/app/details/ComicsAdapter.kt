package lt.setkus.superhero.app.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_comic.comicTitle
import kotlinx.android.synthetic.main.item_comic.comicThumbnail
import lt.setkus.superhero.R

class ComicsAdapter : RecyclerView.Adapter<ComicsAdapter.ComicViewHolder>() {

    private val comics = arrayListOf<HeroComicsViewData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        val containerView = LayoutInflater.from(parent.context).inflate(R.layout.item_comic, parent, false)
        return ComicViewHolder(containerView)
    }

    override fun getItemCount() = comics.size

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) = holder.bindComic(comics[position])

    fun addComics(comicsIn: List<HeroComicsViewData>) {
        comics.addAll(comicsIn)
        notifyDataSetChanged()
    }

    class ComicViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindComic(comic: HeroComicsViewData) {
            comicTitle.text = comic.title

            Glide
                .with(itemView.context)
                .load(comic.thumbnailUrl)
                .centerCrop()
                .into(comicThumbnail)
        }
    }
}