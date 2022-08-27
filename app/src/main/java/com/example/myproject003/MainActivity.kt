package com.example.myproject003

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import androidx.activity.viewModels
import com.example.myproject003.adapter.PostAdapter
import com.example.myproject003.databinding.ActivityMainBinding
import com.example.myproject003.util.hideKeyboard
import com.example.myproject003.util.showKeyboard
import com.example.myproject003.viewmodel.*

class MainActivity : AppCompatActivity() {

    val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PostAdapter(viewModel)
        binding.postsRecyclerView.adapter = adapter
        viewModel.data.observe(this) {posts ->
            adapter.submitList(posts)
        }
        binding.saveButton.setOnClickListener {
            with(binding.contentEditText) {
                val content = text.toString()
                viewModel.onSaveButtonClicked(content)
                clearFocus()
                hideKeyboard()
            }
        }
        viewModel.currentPost.observe(this) {currentPost ->
            with(binding.contentEditText) {
                val content = currentPost?.content
                setText(content)
                if (content != null) {
                    requestFocus()
                    showKeyboard()
                } else {
                    clearFocus()
                    hideKeyboard()
                }
                binding.group.visibility = View.GONE
            }
        }

        with(binding) {
            group.visibility = View.GONE
            contentEditText.setOnClickListener {
                cancel.visibility = View.VISIBLE
                pen.visibility = View.VISIBLE
                postText.visibility = View.VISIBLE
            }
            cancel.setOnClickListener {
                group.visibility = View.INVISIBLE
                contentEditText.visibility = View.INVISIBLE
                contentEditText.hideKeyboard()
            }
        }

    }
}
