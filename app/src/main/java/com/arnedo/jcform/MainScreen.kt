package com.arnedo.jcform

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.arnedo.jcform.ui.theme.JCFormTheme

@Preview(showBackground = true)
@Composable
fun MainPreview(){
    JCFormTheme {
        MainView(Modifier.padding(top = dimensionResource(R.dimen.common_padding_middle)))
    }
}


@Composable
fun MainView(modifier: Modifier) {
    Box(modifier.fillMaxWidth()){
        Column() { }
    }
    Box(Modifier
        .fillMaxSize()
        .background(colorResource(R.color.progress_background))
        .clickable{}
    ) {

    }

}