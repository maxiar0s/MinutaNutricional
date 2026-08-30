package com.exp1_s1.minutanutricional

import com.exp1_s1.minutanutricional.data.summarizeWeeklyMenu
import com.exp1_s1.minutanutricional.model.Recipe
import org.junit.Assert.assertEquals
import org.junit.Test

class WeeklyMenuTest {
    @Test
    fun summarizeWeeklyMenuGroupsRecipesByDayAndCountsThem() {
        val recipes = listOf(
            recipe(day = "Lunes", title = "Sopa"),
            recipe(day = "Martes", title = "Ensalada"),
            recipe(day = "Lunes", title = "Fruta")
        )

        val summary = summarizeWeeklyMenu(recipes)

        assertEquals(2, summary.size)
        assertEquals("Lunes", summary[0].day)
        assertEquals(2, summary[0].recipeCount)
        assertEquals("Sopa, Fruta", summary[0].recipeNames)
    }

    private fun recipe(day: String, title: String) = Recipe(
        title = title,
        day = day,
        description = "Descripción",
        ingredients = emptyList(),
        nutritionalRecommendation = "Recomendación"
    )
}
