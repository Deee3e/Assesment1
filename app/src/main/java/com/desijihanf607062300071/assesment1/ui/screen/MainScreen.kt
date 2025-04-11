package com.desijihanf607062300071.assesment1.ui.screen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.desijihanf607062300071.assesment1.R
import com.desijihanf607062300071.assesment1.navigation.Screen

@Composable
fun MainScreen(navController: NavHostController) {
    ScreenContent(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenContent(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    var beratBadan by rememberSaveable { mutableStateOf("") }
    var kebutuhanAir by rememberSaveable { mutableStateOf(0) }
    var inputError by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("about")
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = "Tentang"
                        )
                    }
                }
            )
        }

    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.orang_minum),
                contentDescription = "Orang minum air",
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 16.dp)
            )

            Text(
                text = stringResource(R.string.kebutuhan_air, kebutuhanAir),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = beratBadan,
                onValueChange = {
                    beratBadan = it
                    inputError = false
                },
                label = { Text(text = stringResource(R.string.berat_badan_label)) },
                isError = inputError,
                supportingText = {
                    if (inputError) {
                        Text(
                            text = stringResource(R.string.input_error),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )

            Button(
                onClick = {
                    inputError = beratBadan.isBlank()
                    if (inputError) return@Button

                    val berat = beratBadan.toIntOrNull()
                    if (berat != null) {
                        kebutuhanAir = berat * 30 // ml per hari
                    } else {
                        inputError = true
                    }
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "Hitung")
            }

            if (kebutuhanAir > 0) {
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    thickness = 1.dp
                )

                Text(
                    text = "Kebutuhan Air Harian: $kebutuhanAir ml",
                    style = MaterialTheme.typography.titleLarge
                )

                Button(
                    onClick = {
                        shareData(
                            context = context,
                            message = "Berat Badan: $beratBadan kg\nKebutuhan Air Harian: $kebutuhanAir ml"
                        )
                    },
                    modifier = Modifier.padding(top = 8.dp),
                    contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
                ) {
                    Text(text = stringResource(R.string.bagikan))
                }
            }
        }
    }
}

fun shareData(context: Context, message: String) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, message)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}
