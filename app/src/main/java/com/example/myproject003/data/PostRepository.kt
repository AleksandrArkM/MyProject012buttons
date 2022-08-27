package com.example.myproject003.data

import androidx.lifecycle.LiveData

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun like(postId: Long)
    fun repost(postId: Long)
    fun delete (postId: Long)
    fun save (post: Post)

    companion object{
        const val NEW_POST_ID = 0L
    }
}