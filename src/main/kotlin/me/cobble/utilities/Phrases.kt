package me.cobble.utilities

class Phrases {

    companion object {
        private val a = listOf(
            "Dinosaur",
            "Spaceship",
            "Ship",
            "Monster",
            "Tree",
            "Rock",
            "Instrument",
            "Banana",
            "Apple",
            "Pear",
            "Strawberry",
            "Orange",
            "Grape",
            "Game",
            "Computer",
            "Algorithm",
            "Tool",
            "Weapon",
            "Utility"
        )
        private val b = listOf<String>(
            "That Flys",
            "That Plays Music",
            "That Attacks",
            "With Technology",
            "Boss",
            "Dimension",
            "Of Horror",
            "That Is Very Funny",
            "With Superpowers"
        )

        fun getPhrase(): String {
            return "${a.random()} ${b.random()}"
        }
    }

}