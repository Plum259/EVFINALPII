package com.example.evfinalpii.ui

import android.text.Spannable.Factory
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.evfinalpii.Aplicacion
import com.example.evfinalpii.db.serviciosBasicos
import com.example.evfinalpii.db.serviciosDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ListaRegistrosViewModel(private val serviciosDao: serviciosDao):ViewModel() {

    var registros by mutableStateOf(listOf<serviciosBasicos>())

    fun insertarRegistro(serviciosBasicos:serviciosBasicos){
        viewModelScope.launch(Dispatchers.IO) {
            serviciosDao.insertar(serviciosBasicos)
            obtenerRegistros()
        }
    }
    fun obtenerRegistros(): List<serviciosBasicos> {
        viewModelScope.launch(Dispatchers.IO) {
            registros = serviciosDao.obtenerRegistro()
        }
        return registros
    }
    fun borrarRegistro(serviciosBasicos: serviciosBasicos){
        viewModelScope.launch(Dispatchers.IO) {
            serviciosDao.borrar(serviciosBasicos)
            obtenerRegistros()
        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val aplicacion = (this[APPLICATION_KEY] as Aplicacion)
                ListaRegistrosViewModel(aplicacion.serviciosDao)
            }
        }
    }

}