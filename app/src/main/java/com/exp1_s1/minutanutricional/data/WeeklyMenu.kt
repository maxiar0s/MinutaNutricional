package com.exp1_s1.minutanutricional.data

import com.exp1_s1.minutanutricional.model.Recipe

val weeklyMenu = listOf(
    Recipe(
        title = "Pollo con quinoa",
        day = "Lunes",
        description = "Un almuerzo completo con proteína y cereales integrales.",
        ingredients = listOf("Pechuga de pollo", "Quinoa", "Tomate", "Pepino", "Limón"),
        nutritionalRecommendation = "Prefiere una porción de quinoa del tamaño de tu puño y acompaña con abundantes verduras."
    ),
    Recipe(
        title = "Lentejas guisadas",
        day = "Martes",
        description = "Legumbres y verduras para una comida rica en fibra.",
        ingredients = listOf("Lentejas", "Zapallo", "Zanahoria", "Cebolla", "Pimenton"),
        nutritionalRecommendation = "Las legumbres aportan fibra y hierro. Agrega una fruta cítrica para favorecer la absorción de hierro."
    ),
    Recipe(
        title = "Pescado al horno",
        day = "Miércoles",
        description = "Pescado suave con papas y ensalada fresca.",
        ingredients = listOf("Filete de pescado", "Papas", "Lechuga", "Tomate", "Aceite de oliva"),
        nutritionalRecommendation = "Incluye pescado al menos dos veces por semana y limita la sal usando hierbas y limón."
    ),
    Recipe(
        title = "Tortilla de verduras",
        day = "Jueves",
        description = "Huevos y verduras en una preparacion simple y saciadora.",
        ingredients = listOf("Huevos", "Espinaca", "Champinones", "Cebolla", "Queso fresco"),
        nutritionalRecommendation = "Combina la tortilla con una ensalada y elige queso fresco bajo en grasa."
    ),
    Recipe(
        title = "Ensalada de garbanzos",
        day = "Viernes",
        description = "Una opcion fresca con proteina vegetal para cerrar la semana.",
        ingredients = listOf("Garbanzos", "Palta", "Tomate", "Zanahoria", "Perejil"),
        nutritionalRecommendation = "Para una comida más completa, sirve la ensalada con una rebanada de pan integral."
    )
)
