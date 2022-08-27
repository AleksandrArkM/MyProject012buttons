package com.example.myproject003.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myproject003.adapter.PostInteractionListener
import com.example.myproject003.data.Post
import com.example.myproject003.data.PostRepository
import com.example.myproject003.data.impl.PostRepositoryInMemoryImpl

class PostViewModel : ViewModel(), PostInteractionListener {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()

    val data = repository.getAll()

    val currentPost = MutableLiveData<Post?>(null)

    fun onSaveButtonClicked (content: String) {
        if (content.isBlank()) return

        val post = currentPost.value?.copy(
            content = content
        ) ?: Post(
            id = PostRepository.NEW_POST_ID,
            author = "Me",
            content = content,
            published = "Today"
        )
        repository.save(post)
        currentPost.value = null
    }

    override fun onLikeClicked(post: Post) = repository.like(post.id)
    override fun onRepostClicked(post: Post) = repository.repost(post.id)
    override fun onRemoveClicked(post: Post) = repository.delete(post.id)
    override fun onEditClicked(post: Post) {currentPost.value = post}
}