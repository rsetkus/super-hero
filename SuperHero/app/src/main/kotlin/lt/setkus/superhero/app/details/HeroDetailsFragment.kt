package lt.setkus.superhero.app.details

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.fragment_hero_details2.comicsList
import kotlinx.android.synthetic.main.fragment_hero_details2.headerImage
import kotlinx.android.synthetic.main.fragment_hero_details2.heroDescription
import kotlinx.android.synthetic.main.fragment_hero_details2.heroDetailsStateLayout
import kotlinx.android.synthetic.main.fragment_hero_details2.heroName
import lt.setkus.superhero.R
import lt.setkus.superhero.app.common.ViewState
import org.koin.android.ext.android.inject

class HeroDetailsFragment : Fragment() {

    private val detailsViewModel: HeroDetailsViewModel by inject()
    private val comicsAdapter = ComicsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition()
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_hero_details2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        comicsList.adapter = comicsAdapter

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        comicsList.layoutManager = layoutManager

        arguments?.let {
            val detailsFragmentArgs = HeroDetailsFragmentArgs.fromBundle(it)
            headerImage.transitionName = detailsFragmentArgs.heroName
            loadImage(detailsFragmentArgs.heroUrl)

            loadDetails(detailsFragmentArgs.heroId)
            loadComics(detailsFragmentArgs.heroId)
        }
    }

    private fun loadComics(heroId: Int) {
        val observer = Observer<ViewState> { state ->
            when (state) {
                is ViewState.Success<*> -> bindComics(state.data as List<HeroComicsViewData>)
                is ViewState.Loading -> heroDetailsStateLayout.loading()
                is ViewState.Finished -> heroDetailsStateLayout.content()
                is ViewState.Error -> heroDetailsStateLayout.error()
            }
        }

        detailsViewModel.comicsLiveData.observe(this, observer)
        detailsViewModel.loadSuperHeroComics(heroId)
    }

    private fun bindComics(comics: List<HeroComicsViewData>) {
        comicsAdapter.addComics(comics)
    }

    private fun loadDetails(heroId: Int) {
        val observer = Observer<ViewState> {
            when (it) {
                is ViewState.Success<*> -> bindData(it.data as HeroDetailsViewData)
            }
        }

        detailsViewModel.heroLiveData.observe(this, observer)
        detailsViewModel.loadSuperhero(heroId)
    }

    private fun bindData(details: HeroDetailsViewData) {
        heroName.text = details.name
        if (details.description.isNotEmpty()) {
            heroDescription.text = details.description
            heroDescription.visibility = View.VISIBLE
        }
    }

    private fun loadImage(url: String) {
        Glide.with(this)
            .load(url)
            .centerCrop()
            .dontAnimate()
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    startPostponedEnterTransition()
                    return false
                }
            })
            .into(headerImage)
    }
}
