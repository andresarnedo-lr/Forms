package com.arnedo.jcform.ui.components


import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.arnedo.jcform.R


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun DatePickerModal(onDateSelected: (Long?) -> Unit,
                    onDismiss: () -> Unit) {
    val datePickerState = rememberDatePickerState(System.currentTimeMillis())

    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text(stringResource(R.string.dialog_ok))
            }
        },
        dismissButton = {
            TextButton(onClick = {onDismiss()}){
                Text(stringResource(R.string.dialog_cancelar))
            }
        }
    ){
        DatePicker(state = datePickerState)
    }


}