package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post

typealias onItemListener = (post: Post) -> Unit

//=============================================================================================
class PostAdapterLike(private val onItemLikeListener: onItemListener): RecyclerView.Adapter<PostViewHolderLike>() {

    var list: List<Post> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolderLike {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolderLike(binding, onItemLikeListener)
    }

    override fun onBindViewHolder(holder: PostViewHolderLike, position: Int) {
        val post = list[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int = list.size
}
//=============================================================================================
class PostAdapterRepost(private val onItemRepostListener: onItemListener): RecyclerView.Adapter<PostViewHolderRepost>() {

    var list: List<Post> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolderRepost {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolderRepost(binding, onItemRepostListener)
    }

    override fun onBindViewHolder(holder: PostViewHolderRepost, position: Int) {
        val post = list[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int = list.size
}
//=============================================================================================

class PostViewHolderLike(
    private val binding: CardPostBinding,
    private val onItemLikeListener: onItemListener,

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

//            share.setOnClickListener {
//                onItemRepostListener(post)
//            }
        }

    }

}

//=============================================================================================


class PostViewHolderRepost(
    private val binding: CardPostBinding,
    private val onItemRepostListener: onItemListener,

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
//            heart.setOnClickListener {
//                onItemListener(post)
//            }

            share.setOnClickListener {
                onItemRepostListener(post)
            }
        }

    }

}

//=============================================================================================

fun getShortSrt(n: Int): String {
    when {
        n < 1_000 -> return n.toString()
        (n < 10_000 && ((n / 100) - ((n / 1_000) * 10)) != 0) -> return (n / 1_000).toString() + "." + ((n / 100) - ((n / 1_000) * 10)).toString() + "К"
        n < 1_000_000 -> return ((n / 1_000).toString() + "K")
        (n < 10_000_000 && ((n / 100_000) - ((n / 1_000_000) * 10)) != 0) -> return (n / 1_000_000).toString() + "." + ((n / 100_000) - ((n / 1_000_000) * 10)).toString() + "M"
    }
    return ((n / 1_000_000).toString() + "M")
}
