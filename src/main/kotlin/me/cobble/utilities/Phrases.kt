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
            "Utility",
            "Website",
            "Program",
            "Software",
            "Script",
            "Datapack",
            "Mod",
            "Plugin",
            "Programming Library",
            "Framework",
        )
        private val b = listOf(
            "That Flies",
            "That Plays Music",
            "That Attacks",
            "With Technology",
            "Boss",
            "Dimension",
            "Of Horror",
            "That Is Very Funny",
            "With Superpowers",
            "That Is Very Smart",
            "That Is Very Clever",
            "That Is Very Strong",
            "That Is Very Intelligent",
            "That Is Very Fast",
            "With a Long Name",
            "With a Short Name",
            "That does something",
            "That is something",
            "Controlled by a computer",
            "That is a game",
            "That is a website",
            "That is a program",
            "That is a software",
            "That is a script",
            "That is a tool",
            "That is a weapon",
            "That is a utility",
        )

        fun getPhrase(): String {
            return "${a.random()} ${b.random()}"
        }
    }
}