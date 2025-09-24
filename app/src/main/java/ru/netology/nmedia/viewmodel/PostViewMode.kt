package ru.netology.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl

class PostViewMode: ViewModel() {

    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.get()
    fun like(id: Int) = repository.likeById(id)
    fun repost(id: Int) = repository.repostById(id)
}