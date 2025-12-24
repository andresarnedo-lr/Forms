package com.arnedo.jcform.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringResource
import com.arnedo.jcform.R
import kotlin.math.max

@Composable
fun FormTextField(labelRes : Int, maxLengthRes : Int? = null) {

    var nameValue by remember { mutableStateOf("") }
    val maxLength = if (maxLengthRes == null) null else integerResource(maxLengthRes)


    OutlinedTextField(
        value = nameValue,
        onValueChange = {
            if(maxLength == null) {
                nameValue = it
            }else {
                if (it.length <= maxLength)
                    nameValue = it
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(R.dimen.common_padding_min)),
        label = {
            Text(stringResource(labelRes))
        },
        leadingIcon = {
            Icon(Icons.Default.Person, contentDescription = null)
        },
        supportingText = {
            Row {
                Text(stringResource(R.string.supporting_required))
                Spacer(Modifier.weight(1f))
                if(maxLength != null)
                    Text("${nameValue.length}/$maxLength")
            }
        })
}