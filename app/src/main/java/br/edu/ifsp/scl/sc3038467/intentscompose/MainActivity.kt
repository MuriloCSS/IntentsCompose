package br.edu.ifsp.scl.sc3038467.intentscompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.edu.ifsp.scl.sc3038467.intentscompose.ui.theme.IntentsComposeTheme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import android.Manifest.permission.CALL_PHONE
import android.content.Intent
import android.content.Intent.ACTION_CALL
import android.content.Intent.ACTION_DIAL
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    private var parameterTextState = mutableStateOf("Not set yet!!!")

    private lateinit var parameterActivityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var callPhonePermissionActivityResultLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        parameterActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.getStringExtra("EXTRA_PARAMETER")?.let {
                    // Atualiza o estado da UI
                    parameterTextState.value = it
                }
            }
        }

        callPhonePermissionActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionGranted ->
            if (permissionGranted.getOrDefault("android.Manifest.permission.CALL_PHONE", false)) {
                callOrDialPhoneNumber(call = true, phoneNumber = parameterTextState.value)
            } else {
                Toast.makeText(this, "Permission required!", Toast.LENGTH_SHORT).show()
            }
        }

        setContent {
            MaterialTheme {
                MainScreen(
                    parameterText = parameterTextState.value,
                    onSetParameterClick = {
                        val parameterIntent = Intent(this@MainActivity, ParameterActivity::class.java)
                        parameterIntent.putExtra("EXTRA_PARAMETER", parameterTextState.value)
                        parameterActivityResultLauncher.launch(parameterIntent)
                    },
                    onViewClick = {
                        val uri = Uri.parse(parameterTextState.value)
                        val navigatorIntent = Intent(Intent.ACTION_VIEW, uri)
                        startActivity(navigatorIntent)
                    },
                    onDialClick = {
                        callOrDialPhoneNumber(call = false, phoneNumber = parameterTextState.value)
                    },
                    onCallClick = {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (checkSelfPermission(CALL_PHONE) == PERMISSION_GRANTED) {
                                callOrDialPhoneNumber(call = true, phoneNumber = parameterTextState.value)
                            } else {
                                callPhonePermissionActivityResultLauncher.launch(arrayOf(CALL_PHONE))
                            }
                        } else {
                            callOrDialPhoneNumber(call = true, phoneNumber = parameterTextState.value)
                        }
                    },
                    title = getString(R.string.app_name),
                    subtitle = this@MainActivity.javaClass.simpleName
                )
            }
        }
    }

    private fun callOrDialPhoneNumber(call: Boolean, phoneNumber: String) {
        Intent(if (call) ACTION_CALL else ACTION_DIAL).apply {
            data = Uri.parse("tel: $phoneNumber")
            startActivity(this)
        }
    }
}
