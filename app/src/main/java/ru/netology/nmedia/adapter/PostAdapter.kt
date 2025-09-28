package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post

typealias onItemListener = (post: Post) -> Unit

//===================================================================================
class PostAdapter(private val onItemLikeListener: onItemListener, private val onItemShareListener: onItemListener) : ListAdapter<Post, PostViewHolder>(PostDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onItemLikeListener, onItemShareListener)
    }

    override fun onBindViewHolder(
        holder: PostViewHolder,
        position: Int
    ) {
        val post = getItem(position)
        holder.bind(post)
    }

}

//===================================================================================
class PostViewHolder(
    private val binding: CardPostBinding,
    private val onItemLikeListener: onItemListener,
    private val onItemShareListener: onItemListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            content.text = post.content
            published.text = post.published
            likes.text = getShortSrt(post.likes)
            repost.text = getShortSrt(post.reposts)
            heart.setImageResource(
                if (post.likeByMe) {
                    R.drawable.ic_red_heart
                } else {
                    R.drawable.ic_heart
                }
            )
            heart.setOnClickListener {
                onItemLikeListener(post)
            }

            share.setOnClickListener {
                onItemShareListener (post)
            }
        }
    }

    private fun getShortSrt(n: Int): String {
        when {
            n < 1_000 -> return n.toString()
            (n < 10_000 && ((n / 100) - ((n / 1_000) * 10)) != 0) -> return (n / 1_000).toString() + "." + ((n / 100) - ((n / 1_000) * 10)).toString() + "К"
            n < 1_000_000 -> return ((n / 1_000).toString() + "K")
            (n < 10_000_000 && ((n / 100_000) - ((n / 1_000_000) * 10)) != 0) -> return (n / 1_000_000).toString() + "." + ((n / 100_000) - ((n / 1_000_000) * 10)).toString() + "M"
        }
        return ((n / 1_000_000).toString() + "M")
    }

}

//===================================================================================

object PostDiffCallback: DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(
        oldItem: Post,
        newItem: Post
    ): Boolean {
        return oldItem.id == newItem.id     // Пост тот же самый, если айдишники совпадают
    }

    override fun areContentsTheSame(
        oldItem: Post,
        newItem: Post
    ): Boolean {
        return oldItem == newItem       // Контент тотже самый, если все поля совпадают
    }

}
