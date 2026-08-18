package com.exp1_s1.minutanutricional.ui.minuta

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.exp1_s1.minutanutricional.data.weeklyMenu
import com.exp1_s1.minutanutricional.model.Recipe

@Composable
fun MinutaScreen(onLogOut: () -> Unit) {
    var selectedRecipe by remember { mutableStateOf<Recipe?>(null) }

    selectedRecipe?.let { recipe ->
        BackHandler { selectedRecipe = null }
        RecipeDetailScreen(recipe = recipe, onBack = { selectedRecipe = null })
    } ?: WeeklyMenuScreen(onRecipeClick = { selectedRecipe = it }, onLogOut = onLogOut)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WeeklyMenuScreen(onRecipeClick: (Recipe) -> Unit, onLogOut: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Minuta semanal", style = MaterialTheme.typography.headlineSmall) },
                actions = {
                    TextButton(onClick = onLogOut) {
                        Text("Salir")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (maxWidth < 600.dp) {
                LazyColumn(
                    contentPadding = PaddingValues(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(weeklyMenu, key = { it.day }) { recipe ->
                        RecipeCard(recipe = recipe, onClick = { onRecipeClick(recipe) })
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(24.dp),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    items(weeklyMenu, key = { it.day }) { recipe ->
                        RecipeCard(recipe = recipe, onClick = { onRecipeClick(recipe) })
                    }
                }
            }
        }
    }
}

@Composable
private fun RecipeCard(recipe: Recipe, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(role = Role.Button, onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(recipe.day, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text(recipe.title, style = MaterialTheme.typography.headlineSmall)
            Text(recipe.description, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = "Ver receta",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RecipeDetailScreen(recipe: Recipe, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(recipe.day, style = MaterialTheme.typography.headlineSmall) },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Text(recipe.title, style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
            }
            item {
                Text(recipe.description, style = MaterialTheme.typography.bodyLarge)
            }
            item {
                Text("Ingredientes", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            }
            items(recipe.ingredients) { ingredient ->
                Text("• $ingredient", style = MaterialTheme.typography.bodyLarge)
            }
            item {
                Text("Recomendación nutricional", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            }
            item {
                Text(recipe.nutritionalRecommendation, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
