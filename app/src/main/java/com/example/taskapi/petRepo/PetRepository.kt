package com.example.taskapi.petRepo
import com.example.taskapi.petsInterface.PetsApiService

class PetRepository (private val api: PetsApiService) {
    suspend fun getAllPets() = api.getAllPets()

}
