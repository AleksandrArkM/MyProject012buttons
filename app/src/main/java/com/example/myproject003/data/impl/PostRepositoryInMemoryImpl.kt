package com.example.myproject003.data.impl

import android.provider.SyncStateContract.Helpers.insert
import android.provider.SyncStateContract.Helpers.update
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myproject003.data.Post
import com.example.myproject003.data.PostRepository

class PostRepositoryInMemoryImpl: PostRepository {

    private var nextId = GENERATED_POSTS_AMOUNT.toLong()

    private var uniqueId = 1000L

    private var posts = List(GENERATED_POSTS_AMOUNT) { index ->
        Post(
            id = index + 1L,
            author = "Netology",
            content = "Random post $index",
            published = "04.08.2022",
            likeByMe = false,
            likeCount = 165,
            repostByMe = false,
            repostCount = 3
        )
    }
    val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data

    override fun like(postId: Long) {
        posts = posts.map {
            if (it.id != postId) it else
                run {
                    if (it.likeByMe) it.copy(likeByMe = !it.likeByMe, likeCount = it.likeCount - 1)
                    else it.copy(likeByMe = !it.likeByMe, likeCount = it.likeCount + 1)
                }
        }
        data.value = posts
    }

    override fun repost(postId: Long) {
        posts = posts.map { post ->
            if (post.id == postId) {
                post.copy(repostCount = post.repostCount + 1)
            } else post
        }
        data.value = posts
    }

    override fun delete(postId: Long) {
        posts = posts.filter { it.id != postId }
        data.value = posts
    }

    override fun save(post: Post) {
        if (post.id == 0L) {
            posts = listOf(
                post.copy(
                    id = ++uniqueId,
                    author = "Me",
                    published = "Today"
                )
            ) + posts

            data.value = posts
            return
        }
        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }

        data.value = posts
    }

    private fun update (post: Post) {
        data.value = posts.map {
            if (it.id == post.id) post else it
        }
    }

    private companion object {
        const val GENERATED_POSTS_AMOUNT = 100
    }

}