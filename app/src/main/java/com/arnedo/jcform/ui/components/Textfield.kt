package com.arnedo.jcform.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.arnedo.jcform.R
import com.arnedo.jcform.ui.theme.JCFormTheme


@Preview(showBackground = true)
@Composable
private fun FormTextFieldPreview() {
    JCFormTheme() {
        FormTextField(labelRes = R.string.hint_name,
            iconRes = R.drawable.ic_height,
            maxLengthRes = R.integer.name_max_length){}
    }
}



@Composable
fun FormTextField(labelRes : Int,
                  iconRes : Int,
                  maxLengthRes : Int? = null,
                  onValueChange : (String) -> Unit) {

    var textValue by remember { mutableStateOf("") }
    val maxLength = if (maxLengthRes == null) null else integerResource(maxLengthRes)





    OutlinedTextField(
        value = textValue,
        onValueChange = {
            if(maxLength == null) {
                textValue = it
            }else {
                if (it.length <= maxLength)
                    textValue = it
            }

            onValueChange(textValue)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(R.dimen.common_padding_min)),
        label = {
            Text(stringResource(labelRes))
        },
        leadingIcon = {
            Icon(painterResource(iconRes), contentDescription = null)
        },
        supportingText = {
            Row {
                Text(stringResource(R.string.supporting_required))
                Spacer(Modifier.weight(1f))
                if(maxLength != null)
                    Text("${textValue.length}/$maxLength")
            }
        })
}