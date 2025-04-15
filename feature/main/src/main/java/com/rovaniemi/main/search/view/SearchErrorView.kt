package com.rovaniemi.main.search.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import com.rovaniemi.main.R
import java.net.UnknownHostException

@Composable
internal fun SearchErrorView(
    modifier: Modifier = Modifier,
    loadState: LoadState,
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
    ) {
        val errorMessage = when (val error = (loadState as? LoadState.Error)?.error) {
            is UnknownHostException -> stringResource(R.string.search_paging_network_message)
            is IndexOutOfBoundsException -> stringResource(R.string.search_paging_no_search_message)
            else -> error?.localizedMessage ?: stringResource(R.string.search_paging_unknown_error_message)
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            text = errorMessage,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
private fun PreviewSearchErrorView() {
    SearchErrorView(
        loadState = LoadState.Error(
            error = IndexOutOfBoundsException(),
        )
    )
}