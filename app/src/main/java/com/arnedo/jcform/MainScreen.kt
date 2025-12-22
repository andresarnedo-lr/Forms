package com.arnedo.jcform

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

}