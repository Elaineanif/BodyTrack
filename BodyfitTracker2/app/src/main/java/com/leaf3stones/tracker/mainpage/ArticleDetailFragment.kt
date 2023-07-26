package com.leaf3stones.tracker.mainpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceAround
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.leaf3stones.tracker.R

class ArticleDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val viewModel: ArticleDetailViewModel =
            ViewModelProvider(this)[ArticleDetailViewModel::class.java]


        val articleId = arguments?.getInt("articleId")
        viewModel.loadArticle(articleId)


        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    val article = viewModel.article
                    val saveMessage = viewModel.saveMessage
                    val editMessage = viewModel.editMessage
                    val contextEditable = viewModel.editable

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Post by ${viewModel.author!!.username}",
                                fontSize = 36.sp,
                                modifier = Modifier.padding(8.dp)
                            )

                            Image(
                                painter = painterResource(R.drawable.ic_delete),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(48.dp)
                                    .clickable {
                                        if (contextEditable) {
                                            viewModel.removeCurrent()
                                            Toast
                                                .makeText(
                                                    requireContext(),
                                                    "Removed the post '${article.title}'",
                                                    Toast.LENGTH_SHORT
                                                )
                                                .show()
                                            findNavController().navigate(R.id.action_articleDetailFragment_to_postFragment)
                                        } else {
                                            viewModel.checkContextEditable()
                                        }
                                    },
                                alignment = Alignment.BottomEnd
                            )
                        }

                        TextField(value = article.title,
                            onValueChange = { viewModel.setTitle(it) },
                            label = { Text("Title") },
                            enabled = contextEditable,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.08f)
                        )

                        TextField(value = article.content,
                            onValueChange = { viewModel.setContent(it) },
                            label = { Text("Content") },
                            enabled = contextEditable,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.65f)
                        )
                        Button(onClick = {
                            viewModel.trySave()
                        }, enabled = contextEditable) {
                            if (saveMessage != null) {
                                if (saveMessage == "saved!") {
                                    Toast.makeText(
                                            requireContext(),
                                            "Saved the post '${article.title}'",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    findNavController().navigate(R.id.action_articleDetailFragment_to_postFragment)
                                }
                                viewModel.onSaveMessageDisplay()
                            }

                            if (editMessage != null) {
                                val context = LocalContext.current
                                Toast.makeText(context, editMessage, Toast.LENGTH_SHORT).show()
                                viewModel.onEditMessageDisplay()
                            }

                            Text(
                                text = "Save",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .weight(0.2f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}