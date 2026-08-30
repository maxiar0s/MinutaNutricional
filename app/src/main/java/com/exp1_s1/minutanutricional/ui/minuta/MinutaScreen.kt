package com.exp1_s1.minutanutricional.ui.minuta

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.exp1_s1.minutanutricional.data.DailyRecipeSummary
import com.exp1_s1.minutanutricional.data.summarizeWeeklyMenu
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
    val weeklySummary = remember { summarizeWeeklyMenu(weeklyMenu) }
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
            val isCompactWidth = maxWidth < 600.dp
            Column(modifier = Modifier.fillMaxSize()) {
                WeeklySummaryTable(summary = weeklySummary)
                if (isCompactWidth) {
                    LazyColumn(
                        contentPadding = PaddingValues(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.weight(1f)
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
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        items(weeklyMenu, key = { it.day }) { recipe ->
                            RecipeCard(recipe = recipe, onClick = { onRecipeClick(recipe) })
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun WeeklySummaryTable(summary: List<DailyRecipeSummary>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .semantics { contentDescription = "Resumen semanal de recetas" },
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text("Resumen semanal", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Row {
            Text("Día", modifier = Modifier.weight(0.3f), fontWeight = FontWeight.Bold)
            Text("Recetas", modifier = Modifier.weight(0.2f), fontWeight = FontWeight.Bold)
            Text("Preparación", modifier = Modifier.weight(0.5f), fontWeight = FontWeight.Bold)
        }
        summary.forEach { item ->
            Row(modifier = Modifier.semantics {
                contentDescription = "${item.day}: ${item.recipeCount} receta, ${item.recipeNames}"
            }) {
                Text(item.day, modifier = Modifier.weight(0.3f))
                Text(item.recipeCount.toString(), modifier = Modifier.weight(0.2f))
                Text(item.recipeNames, modifier = Modifier.weight(0.5f))
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
    var showNutritionHelp by remember { mutableStateOf(false) }
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
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val contentPadding = if (maxWidth < 600.dp) 24.dp else 72.dp
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(contentPadding),
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
                item {
                    Text(
                        text = "Ver ayuda para entender esta recomendación",
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier
                            .clickable(onClickLabel = "Mostrar ayuda nutricional") {
                                showNutritionHelp = !showNutritionHelp
                            }
                            .semantics {
                                contentDescription = "Vínculo: ver ayuda para entender esta recomendación"
                            }
                    )
                }
                if (showNutritionHelp) {
                    item {
                        Text(
                            "Esta información es educativa. Para una recomendación personal, consulta a un profesional de salud.",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}
