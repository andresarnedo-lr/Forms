package com.arnedo.jcform

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.isDebugInspectorInfoEnabled
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arnedo.jcform.ui.components.DatePickerModal
import com.arnedo.jcform.ui.components.FormTextField
import com.arnedo.jcform.ui.components.TextFieldDate
import com.arnedo.jcform.ui.theme.JCFormTheme
import com.arnedo.jcform.ui.theme.Typography
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    JCFormTheme {
        MainView(Modifier.padding(top = 24.dp), false, {}, {}) {}
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MainView(
    modifier: Modifier,
    isClean: Boolean = false,
    onCleaned: () -> Unit,
    onError: (String) -> Unit,
    onSave: (User) -> Unit
) {

    val scope = rememberCoroutineScope()

    var nameValue by remember { mutableStateOf("") }
    var surnameValue by remember { mutableStateOf("") }
    var heightValue by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    var dateValue by remember { mutableStateOf<Long?>(null) }
    var notesValue by remember { mutableStateOf("") }
    var isAgree by remember { mutableStateOf(false) }
    var inProgress by remember { mutableStateOf(false) }

    val profiles = listOf("Estudiante", "Programador")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(profiles[0]) }


    val context = LocalContext.current
    val isShowKeyboard = WindowInsets.isImeVisible
    val focus = LocalFocusManager.current


    val saveFun = {
        focus.clearFocus()
        inProgress = true
        scope.launch {
            val errors = foundErrors(context, nameValue, surnameValue, heightValue)
            if (errors == null) {
                simulateDelayLong()
                val user = User(
                    nameValue,
                    surnameValue,
                    heightValue.toInt(),
                    dateValue ?: 0,
                    selectedOption,
                    notesValue
                )
                onSave(user)
            } else {
//              Log.e("CursosANT", "MainView: $errors")
                onError(errors)
            }
        }
        inProgress = false
    }

    if (isClean) {
        nameValue = ""
        surnameValue = ""
        heightValue = ""
        dateValue = null
        onOptionSelected(profiles[0])
        isAgree = false
        onCleaned()
    }


    Box(modifier.fillMaxWidth()) {
        Column(Modifier.padding(horizontal = dimensionResource(R.dimen.common_padding_default))) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    stringResource(R.string.form_title),
                    modifier = Modifier.weight(1f),
                    style = Typography.titleLarge
                )
                val saveAlpha = if (isShowKeyboard) 1f else 0f
                Button(
                    onClick = {
                        saveFun()
                    },
                    modifier = Modifier
                        .alpha(saveAlpha),
                    enabled = isAgree
                ) {
                    Icon(painterResource(R.drawable.ic_check), contentDescription = null)
                }
            }
            Column(
                Modifier
                    .imePadding()
                    .verticalScroll(rememberScrollState())
            ) {
                //Name
                FormTextField(
                    labelRes = R.string.hint_name,
                    iconRes = R.drawable.ic_person,
                    maxLengthRes = R.integer.name_max_length,
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next),
                    isClean = isClean,
                    onValueChange = { nameValue = it }
                )
                //Surname
                FormTextField(
                    labelRes = R.string.hint_surname,
                    iconRes = R.drawable.ic_person,
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next),
                    isClean = isClean,
                    onValueChange = { surnameValue = it })

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = dimensionResource(R.dimen.common_padding_min)),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.common_padding_default))
                ) {
                    //Height
                    FormTextField(
                        modifier = Modifier.weight(40f),
                        labelRes = R.string.hint_height,
                        iconRes = R.drawable.ic_height,
                        maxLengthRes = R.integer.height_max_length,
                        minValue = integerResource(R.integer.height_min_value),
                        errorRes = R.string.error_min_height_valid,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        isClean = isClean,
                        paddingTop = dimensionResource(R.dimen.common_padding_none),
                        onValueChange = { heightValue = it })

                    //BirthDate
                    TextFieldDate(
                        modifier = Modifier.weight(60f),
                        labelRes = R.string.hint_birthdate,
                        selectDate = dateValue
                    ) {
                        showDatePicker = true
                    }
                    if (showDatePicker) {
                        DatePickerModal(
                            onDateSelected = { dateValue = it },
                            onDismiss = { showDatePicker = false })
                    }
                }

                //Occupation
                Column(
                    Modifier
                        .padding(dimensionResource(R.dimen.common_padding_min))
                        .selectableGroup()
                ) {
                    Text(
                        stringResource(R.string.section_occupation),
                        style = Typography.labelLarge
                    )
                    profiles.forEach { text ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(dimensionResource(R.dimen.rb_row_height))
                                .selectable(
                                    selected = (text == selectedOption),
                                    onClick = { onOptionSelected(text) },
                                    role = Role.RadioButton
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (text == selectedOption),
                                onClick = null
                            )
                            Text(
                                text,
                                modifier = Modifier.padding(start = dimensionResource(R.dimen.common_padding_default))
                            )
                        }
                    }
                }

                //Notes
                FormTextField(
                    labelRes = R.string.hint_notes,
                    iconRes = R.drawable.ic_notes,
                    maxLengthRes = R.integer.note_max_length,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    singleLine = false,
                    isRequired = false,
                    isClean = isClean,
                    paddingTop = dimensionResource(R.dimen.common_padding_none),
                    onValueChange = { notesValue = it })

                //Agree
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        stringResource(R.string.checkbox_agree),
                        modifier = Modifier.clickable { isAgree == !isAgree })
                    Checkbox(
                        checked = isAgree,
                        onCheckedChange = { isAgree = it }
                    )
                }

                //Save
                val saveAlphaReversed = if (!isShowKeyboard) 1f else 0f
                Button(
                    onClick = {
                        saveFun()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(saveAlphaReversed)
                        .padding(vertical = dimensionResource(R.dimen.common_padding_default)),
                    enabled = isAgree
                ) {
                    Icon(painterResource(R.drawable.ic_check), contentDescription = null)
                    Text(stringResource(R.string.btn_register))
                }
            }
        }

        if (inProgress) {
            Box(Modifier
                    .fillMaxSize()
                    .background(colorResource(R.color.progress_background))
                    .clickable(interactionSource = null , indication = null) { },
                contentAlignment = Alignment.Center){
                CircularProgressIndicator()
            }
        }
    }
}