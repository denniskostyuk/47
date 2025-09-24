package ru.netology.nmedia.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.netology.nmedia.viewmodel.PostViewMode
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostAdapterLike
import ru.netology.nmedia.adapter.PostAdapterRepost
import ru.netology.nmedia.adapter.onItemListener
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel: PostViewMode by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
           insets
        }

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //================================================

        var adapterLike = PostAdapterLike { post ->
            viewModel.like(post.id)
        }
        binding.list.adapter = adapterLike

        //================================================

        viewModel.data.observe(this) { posts ->
            adapterLike.list = posts
        }

        //================================================

        val adapterRepost = PostAdapterRepost { post ->
            viewModel.repost(post.id)
        }
        binding.list.adapter = adapterRepost

        //================================================

        viewModel.data.observe(this) { posts ->
            adapterLike.list = posts
            adapterRepost.list = posts
        }




//        findViewById<ImageButton>(R.id.heart).setOnClickListener {
//            if (it !is ImageButton) {
//                return@setOnClickListener
//            }
//            (it as ImageButton).setImageResource(R.drawable.ic_red_heart)
//        }
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