package com.arnedo.jcform

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.arnedo.jcform.ui.theme.JCFormTheme
import com.arnedo.jcform.ui.theme.Typography

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    JCFormTheme {
        MainView(Modifier.padding(top = dimensionResource(R.dimen.common_padding_middle)))
    }
}


@Composable
fun MainView(modifier: Modifier) {
    var nameValue by remember { mutableStateOf("") }


    Box(modifier.fillMaxWidth()) {
        Column(Modifier.padding(horizontal = dimensionResource(R.dimen.common_padding_default))) {
            Text(
                stringResource(R.string.form_title),
                style = Typography.titleLarge
            )
        }
        OutlinedTextField(
            value = nameValue,
            onValueChange = { nameValue = it},
            modifier = Modifier.fillMaxWidth(),
            label =  {
                Text(stringResource(R.string.hint_name))
            },
            leadingIcon = {
                Icon(Icons.Default.Person, contentDescription = null)
            })
            }


                    Box (Modifier
                        .fillMaxSize()
                        .background(colorResource(R.color.progress_background))
                        .clickable {},
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

    }