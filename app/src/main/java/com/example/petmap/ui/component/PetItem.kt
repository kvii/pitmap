package com.example.petmap.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.petmap.data.repository.Pet

@Composable
fun PetItem(pet: Pet) {
    Row(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        NameAvatar(pet.petName, modifier = Modifier.padding(end = 16.dp))
        Text(pet.petName, style = MaterialTheme.typography.titleLarge)
    }
}

@Preview
@Composable
fun PetItemPreview() {
    val pet = Pet(
        petName = "猫",
        owner = "kvii",
        longitude = 1.0,
        latitude = 1.0,
    )
    PetItem(pet)
}