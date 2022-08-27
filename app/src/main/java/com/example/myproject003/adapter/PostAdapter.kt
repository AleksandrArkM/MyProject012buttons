package com.example.myproject003.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myproject003.R
import com.example.myproject003.data.Post
import com.example.myproject003.databinding.PostItemBinding
import com.example.myproject003.viewmodel.CountCheck

internal class PostAdapter (
    private val interactionListener: PostInteractionListener
        ): ListAdapter<Post, PostAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val binding = PostItemBinding.inflate(inflator,parent,false)
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }

    class ViewHolder(
        private val binding: PostItemBinding,
        listener: PostInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.options).apply {

                inflate(R.menu.options_post)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            listener.onRemoveClicked(post)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(post)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        init {
            binding.likesButton.setOnClickListener { listener.onLikeClicked(post) }
            binding.shareButton.setOnClickListener { listener.onRepostClicked(post) }
            binding.options.setOnClickListener { popupMenu.show() }
        }

        fun bind(post: Post) {
            this.post = post
            with(binding) {
                authorName.text = post.author
                headTextPost.text = post.content
                data.text = post.published
                likesButton.text = CountCheck(post.likeCount).outputNumber
                likesButton.isChecked = post.likeByMe
//                likesButton.setButtonDrawable(getLikeIconResId(post.likeByMe))
                shareButton.text = CountCheck(post.repostCount).outputNumber

            }
        }
    }

//        @DrawableRes
//        private fun getLikeIconResId(liked: Boolean) =
//            if (liked)
//            {R.drawable.ic_liked_24
//            } else
//            {R.drawable.ic_baseline_favorite_24}


    private object DiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
            oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem
    }
}