package com.arnedo.jcform

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    JCFormTheme {
        MainView(Modifier.padding(top = 24.dp),false,{},{})
    }
}


@Composable
fun MainView(modifier: Modifier,
             isClean : Boolean = false,
             onCleaned : () -> Unit ,
             onSave : (User) -> Unit) {
    var nameValue by remember { mutableStateOf("") }
    var surnameValue by remember { mutableStateOf("") }
    var heightValue by remember {mutableStateOf("")}
    var showDatePicker by remember { mutableStateOf(false) }
    var dateValue by remember { mutableStateOf<Long?>(null) }

    if(isClean) {

        onCleaned()
    }


    Box(modifier.fillMaxWidth()) {
        Column(Modifier.padding(horizontal = dimensionResource(R.dimen.common_padding_default))) {
            Text(
                stringResource(R.string.form_title),
                style = Typography.titleLarge
            )
            //Name
            FormTextField(
                labelRes = R.string.hint_name,
                iconRes = R.drawable.ic_person,
                maxLengthRes = R.integer.name_max_length,
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
                isClean = isClean,
                onValueChange = {nameValue = it}
            )
            //Surname
            FormTextField(labelRes = R.string.hint_surname,
                iconRes = R.drawable.ic_person,
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
                isClean = isClean,
                onValueChange = {surnameValue = it})

            //Height
            FormTextField(labelRes = R.string.hint_height,
                iconRes = R.drawable.ic_height,
                maxLengthRes = R.integer.height_max_length,
                minValue = integerResource(R.integer.height_min_value),
                errorRes = R.string.error_min_height_valid,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done),
                isClean = isClean,
                onValueChange = {heightValue = it})

            //BirthDate
            TextFieldDate(
                labelRes = R.string.hint_birthdate,
                selectDate = dateValue){
                showDatePicker = true
            }
            if(showDatePicker){
                DatePickerModal(onDateSelected = {dateValue = it}, onDismiss = {showDatePicker = false})
            }

            //Save
            Button(onClick = {
                val user = User(nameValue, surnameValue, heightValue.toInt())
                onSave(user)
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(R.dimen.common_padding_default))){
                Icon(painterResource(R.drawable.ic_check), contentDescription = null)
                Text(stringResource(R.string.btn_register))

            }


        }
    }


//    if(false){
//        Box(
//            Modifier
//                .fillMaxSize()
//                .background(colorResource(R.color.progress_background))
//                .clickable {},
//            contentAlignment = Alignment.Center) {
//            CircularProgressIndicator()
//        }
//    }

}