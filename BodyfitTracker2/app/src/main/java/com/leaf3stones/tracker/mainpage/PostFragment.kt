package com.leaf3stones.tracker.mainpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.findNavController
import com.leaf3stones.tracker.R
import com.leaf3stones.tracker.data.Article


class PostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    PostScreen(onCreateButtonClicked = { findNavController().navigate(R.id.action_postFragment_to_articleDetailFragment) },
                        onListItemClicked = { id ->
                            val bundle = Bundle().apply {
                                putInt("articleId", id)
                            }
                            findNavController().navigate(
                                R.id.action_postFragment_to_articleDetailFragment,
                                bundle
                            )
                        }
                    )

                }
            }
        }

    }

}

@Composable
fun PostScreen(onCreateButtonClicked: () -> Unit, onListItemClicked: (articleId: Int) -> Unit) {
    val viewModel: PostViewModel = viewModel()
    val articles = viewModel.article
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        LazyColumn {
            items(articles().size) { id ->
                ArticleListItem(articles()[id], onListItemClicked)
            }
        }
        Image(
            painter = painterResource(id = R.drawable.ic_add),
            contentDescription = null,
            modifier = Modifier
                .align(
                    Alignment.BottomEnd
                )
                .padding(16.dp)
                .size(72.dp)
                .clickable { onCreateButtonClicked() }
        )
    }
}

@Composable
fun ArticleListItem(article: Article, onListItemClicked: (articleId: Int) -> Unit) {

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable {
            onListItemClicked(article.articleId)
        })
    {
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = article.title,
                fontSize = 24.sp,
                modifier = Modifier.weight(0.3f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = article.content,
                fontSize = 16.sp,
                modifier = Modifier.weight(0.7f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}