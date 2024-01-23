package com.example.evfinalpii

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GasMeter
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.evfinalpii.db.serviciosBasicos
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.example.evfinalpii.ui.ListaRegistrosViewModel
import androidx.lifecycle.viewmodel.compose.viewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO){}
        setContent {
            App()
        }
    }
}
@Composable
fun App(){
    var currentPage by remember { mutableStateOf("Inicio")}
    when (currentPage){
        "Inicio" -> Inicio(onNavigateToSecondPage = {currentPage="PaginaFormulario"})
        "PaginaFormulario" -> PaginaFormulario()
    }
}
@Composable
fun Inicio(onNavigateToSecondPage: ()-> Unit,vmListaRegistros: ListaRegistrosViewModel = viewModel(factory = ListaRegistrosViewModel.Factory))
{
    LaunchedEffect(Unit){
        vmListaRegistros.obtenerRegistros()
    }
    Column {
        LazyColumn {
            items(vmListaRegistros.registros){ registro ->
                Card(modifier = Modifier.padding(2.dp)) {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        when (registro.nombre) {
                            "AGUA" -> Icon(imageVector = Icons.Default.WaterDrop, contentDescription = null )
                            "LUZ" ->  Icon(imageVector = Icons.Default.Lightbulb, contentDescription = null )
                            "GAS" ->  Icon(imageVector = Icons.Default.LocalGasStation, contentDescription = null )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = registro.nombre,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = registro.precio.toString(),
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.End
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = registro.fecha,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.End
                        )
                    }
                }
            }
        }
        Box(modifier = Modifier
            .fillMaxSize()
        ){
            Button(onClick = onNavigateToSecondPage,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Text("+")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaginaFormulario(vmListaRegistros: ListaRegistrosViewModel = viewModel(factory = ListaRegistrosViewModel.Factory)){
    var medidor by remember { mutableStateOf("")}
    var fecha by remember { mutableStateOf("")}
    val opciones = listOf("AGUA","LUZ","GAS")
    var nombre by remember { mutableStateOf(opciones[0])}
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(
            text = "Registro Medidor",
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        OutlinedTextField(value = medidor,
            onValueChange = {medidor = it},
            label = { Text(text = "Medidor")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        OutlinedTextField(value = fecha,
            onValueChange = {fecha = it},
            label = { Text(text = "Fecha")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        Text(
            text = "Medidor de:",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
        )
        opciones.forEach {text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clickable(onClick = { nombre = text })
                ){
                RadioButton(selected = text == nombre,
                    onClick = {nombre = text}
                )
                Text(text = text,
                    modifier = Modifier
                        .padding(12.dp))
            }
        }
        Button(onClick = {
            val nuevoServicio = serviciosBasicos(
                nombre = nombre,
                precio = medidor.toInt(),
                fecha = fecha
            )
        vmListaRegistros.insertarRegistro(serviciosBasicos(null,nombre,medidor.toInt(),fecha))
        },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text("Registrar medicion")
        }
    }
}
