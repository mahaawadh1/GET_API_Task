package com.example.taskapi.newmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskapi.petRepo.PetRepository
import com.example.taskapi.pets.Pet
import com.example.taskapi.petsInterface.PetsApiService
import com.example.taskapi.singletone.RetrofitHelper
import kotlinx.coroutines.launch

class PetsViewModel : ViewModel() {

    private val petsApiService =
        RetrofitHelper.getInstance().create(PetsApiService::class.java)
    private val repository = PetRepository(petsApiService)
    var pets by mutableStateOf(listOf<Pet>())

    init {
        fetchPets()
    }


    fun fetchPets() {
        viewModelScope.launch {
            try {

                pets = repository.getAllPets()

            } catch (e: Exception) {

            }
        }
    }

    fun addPet(pet: Pet) {
        viewModelScope.launch {
            try {
                val response = petsApiService.addPet(pet)
                if (response.isSuccessful && response.body() != null) {
                    // Handle successful addition of the book, e.g., updating the UI or list of books
                } else {
                }
            } catch (e: Exception) {
            }


        }
    }

    fun deletePet(petID: Int) {

        viewModelScope.launch {
            try {
                var response = petsApiService.deletePet(petID)

                if (response.isSuccessful) {

                } else {
                }
            } catch (e: Exception) {
                ///
            }


        }
    }
}