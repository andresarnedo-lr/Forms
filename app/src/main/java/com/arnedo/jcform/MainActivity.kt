package com.arnedo.jcform

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arnedo.jcform.ui.components.ArnDialogInfo
import com.arnedo.jcform.ui.theme.JCFormTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JCFormTheme {
                var openDialog by remember { mutableStateOf(false) }
                var cleanForm by remember { mutableStateOf(false) }
                var userFilled : User? = null


                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainView(
                        modifier = Modifier.padding(innerPadding),
                        isClean = cleanForm,
                        onCleaned = { cleanForm = false}
                    ){user ->
                        Log.i("CursosANT", "onCreate: $user")
                        userFilled = user
                        openDialog = true
                    }

                    if(openDialog) {
                        userFilled?.let { user ->
                            ArnDialogInfo(info = user.toString(),
                                    titleRes = R.string.dialog_title,
                                confirmRes = R.string.dialog_clean){ clean ->
                                cleanForm = clean
                                openDialog = false
                            }
                        }
                    }
                }
            }
        }
    }
}



@Preview(showSystemUi = true)
@Composable
private fun LocalPreview() {
    JCFormTheme {
        MainPreview()
    }
}