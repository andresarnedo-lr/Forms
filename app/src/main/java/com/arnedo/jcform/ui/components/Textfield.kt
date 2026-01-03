package com.arnedo.jcform.ui.components

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.arnedo.jcform.R
import com.arnedo.jcform.convertMillisToDate

import com.arnedo.jcform.ui.theme.JCFormTheme


@Preview(showBackground = true)
@Composable
private fun FormTextFieldPreview() {
    JCFormTheme{
        FormTextField(
            Modifier,
            labelRes = R.string.hint_name,
            iconRes = R.drawable.ic_height,
            maxLengthRes = R.integer.name_max_length
        ) {}
    }
}

@Preview(showBackground = true)
@Composable
fun TfDatePreview() {
    JCFormTheme {
        TextFieldDate(
            Modifier,
            labelRes = R.string.hint_birthdate,
            selectDate = null
        ){}
    }
}


@Composable
fun FormTextField(
    modifier: Modifier = Modifier,
    labelRes: Int,
    iconRes: Int,
    maxLengthRes: Int? = null,
    minValue: Int = 0,
    errorRes: Int = R.string.supporting_required,
    keyboardOptions: KeyboardOptions? = null,
    isClean: Boolean = false,
    onValueChange: (String) -> Unit
) {

    var textValue by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    val maxLength = if (maxLengthRes == null) null else integerResource(maxLengthRes)



    if (isClean) {
        textValue = ""
    }

    OutlinedTextField(
        value = textValue,
        onValueChange = {
            if (maxLength == null) {
                textValue = it
            } else {
                if (it.length <= maxLength)
                    textValue = it
            }
            isError = it.trim().isEmpty()

            if (minValue > 0) {
                isError = (textValue.toIntOrNull() ?: 0) < minValue
            }

            onValueChange(textValue)
        },
        isError = isError,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(R.dimen.common_padding_min)),
        label = {
            Text(stringResource(labelRes),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis)
        },
        leadingIcon = {
            Icon(painterResource(iconRes), contentDescription = null)
        },
        keyboardOptions = KeyboardOptions(
            capitalization = keyboardOptions?.capitalization ?: KeyboardCapitalization.Sentences,
            keyboardType = keyboardOptions?.keyboardType ?: KeyboardType.Text,
            imeAction = if (keyboardOptions == null || keyboardOptions.imeAction == ImeAction.Default) ImeAction.Next
            else keyboardOptions.imeAction
        ),


        supportingText = {
            Row {
                Text(if (isError) stringResource(errorRes) else stringResource(R.string.supporting_required))

                Spacer(Modifier.weight(1f))

                if (maxLength != null && minValue == 0)
                    Text("${textValue.length}/$maxLength")
            }
        })
}


@Composable
fun TextFieldDate(
    modifier: Modifier,
    labelRes: Int,
    selectDate: Long? = null,
    onShowModal: () -> Unit
) {

    OutlinedTextField(
        value = selectDate?.let { convertMillisToDate(it) } ?: "",
        onValueChange = {},
        label = {
            Text(stringResource(labelRes),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis)
        },
        trailingIcon = {
            painterResource(R.drawable.ic_calendar_today)
        },
        modifier = modifier.pointerInput(selectDate){
                awaitEachGesture {
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                    if(upEvent != null) onShowModal()
                }
            })
}
