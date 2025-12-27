package com.arnedo.jcform.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.arnedo.jcform.R

@Composable
fun ArnDialogInfo(info: String, titleRes: Int, onDismissRequest: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        title = { Text(stringResource(titleRes)) },
        text = { Text(info) },
        confirmButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(stringResource(R.string.dialog_ok))
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(stringResource(R.string.dialog_cancelar))
            }
        }
    )
}