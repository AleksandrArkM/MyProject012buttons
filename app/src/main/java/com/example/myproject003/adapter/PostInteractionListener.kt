package com.example.myproject003.adapter

import com.example.myproject003.data.Post

interface PostInteractionListener {
    fun onLikeClicked(post: Post)
    fun onRepostClicked(post: Post)
    fun onRemoveClicked(post: Post)
    fun onEditClicked(post: Post)
}