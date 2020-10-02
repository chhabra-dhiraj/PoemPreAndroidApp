package io.github.chhabra_dhiraj.poempre.ui.main.maincontent.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.chhabra_dhiraj.poempre.R
import io.github.chhabra_dhiraj.poempre.domain.Poem
import io.github.chhabra_dhiraj.poempre.ui.main.maincontent.home.HomeScreenFragmentDirections
import timber.log.Timber

class HomeScreenAdapter :
    ListAdapter<Poem, HomeScreenAdapter.ViewHolder>(SleepNightDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_title_list_item_poem_recycler_view)
        private val genre: TextView =
            itemView.findViewById(R.id.tv_genre_list_item_poem_recycler_view)

        fun bind(item: Poem) {
            itemView.setOnClickListener {
                itemView.findNavController()
                    .navigate(
                        HomeScreenFragmentDirections.actionHomeScreenFragmentToViewPoemFragment(
                            item
                        )
                    )
            }
            title.text = item.title
            genre.text = item.genre
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.list_item_poem_recycler_view, parent, false)

                return ViewHolder(view)
            }
        }
    }
}

class SleepNightDiffCallback : DiffUtil.ItemCallback<Poem>() {
    override fun areItemsTheSame(oldItem: Poem, newItem: Poem): Boolean {
        return oldItem.poetryId == newItem.poetryId
    }

    override fun areContentsTheSame(oldItem: Poem, newItem: Poem): Boolean {
        return oldItem == newItem
    }
}