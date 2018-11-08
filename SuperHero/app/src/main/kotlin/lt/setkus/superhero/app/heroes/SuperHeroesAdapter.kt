package lt.setkus.superhero.app.heroes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
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

    inner class SuperHeroViewHolder : RecyclerView.ViewHolder {

        private var name: TextView

        constructor(itemView: View): super(itemView) {
            name = itemView.findViewById(R.id.super_hero_name)
        }

        fun bindData(data: SuperHeroViewData) {
            name.text = data.name
        }
    }
}