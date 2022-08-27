package com.example.myproject003.data

data class Post(
    val id :Long,
    val author :String,
    val content :String,
    val published :String,
    val likeByMe :Boolean = false,
    val likeCount :Int = 0,
    val repostByMe :Boolean = false,
    val repostCount :Int = 0
)