package com.example.taskapi

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.taskapi.newmodel.PetsViewModel
import com.example.taskapi.pets.Pet
import com.example.taskapi.ui.theme.TaskApiTheme


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {


            TaskApiTheme {
                // A surface container using the 'background' color from the theme
                val viewModel: PetsViewModel = viewModel()
                val navController = rememberNavController()


                Scaffold(
                    topBar = { TopAppBar(title = { Text("My Pet List") }) }
                    ,
                    floatingActionButton = {
                        FloatingActionButton(onClick = { navController.navigate("addPet")}) {
                            Text("+")
                        }
                    }
                ) { padding ->
                    NavHost(navController = navController, startDestination = "petList") {
                        composable("petList") {
                            // Ensure BookListScreen is correctly implemented and contains visible content
                            PetsListScreen(viewModel , padding)
                        }
                        composable("addPet") {
                            AddPetScreen()

                        }
                    }
                }
            }
            }
        }
    }

@Composable
fun PetsListScreen(viewModel: PetsViewModel , padding: PaddingValues) {
    val pets = viewModel.pets

    LazyColumn(modifier = Modifier.padding(padding)) {
        items(pets) { pet ->
            PetItem(pet)
        }
    }
}


@Composable
fun PetItem(pet: Pet , petsViewModel: PetsViewModel = viewModel()) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),

        ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ){
        Button(modifier = Modifier,
            onClick = {
                petsViewModel.deletePet(pet.id)
            }) {
            Text("Delete")
        }
    }
                AsyncImage(model = pet.image,
                    contentDescription = null,
                    modifier = Modifier
                        .height(100.dp)
                        .width(100.dp)
                )

                Column {
                    Text(
                        text = pet.name,
                        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    )
                    Text("Age: ${pet.age} years", fontSize = 14.sp)
                    Text("ID: ${pet.id}", fontSize = 14.sp, color = Color.Gray)
                    Text("adopted: ${pet.adopted}", fontSize = 14.sp, color = Color.Gray)
                }
            }
        }

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddPetScreen() {

    val petViewModel: PetsViewModel  = viewModel()

    var id by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var adopted by remember { mutableStateOf("") }
    var image by remember { mutableStateOf("")
    }
    Scaffold {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Add a New pet",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )


            InputField(
                value = name,
                onValueChange = { name = it },
                label = "name"
            )
            InputField(
                value = age,
                onValueChange = { age = it },
                label = "age"
            )
            InputField(
                value = image,
                onValueChange = { image = it },
                label = "Image URL"
            )

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val newPet = Pet(
                        id = 0,  // Assuming ID is generated by the server
                        name = name,
                        age = age,
                        adopted = adopted,
                        image =  "image",
                    )
                    petViewModel.addPet(newPet)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Pet")
            }
        }
    }
}

@Composable
fun InputField(value: String, onValueChange: (String) -> Unit, label: String, keyboardType: KeyboardType = KeyboardType.Text) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}