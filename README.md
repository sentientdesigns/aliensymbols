# Alien Symbol Generator
The generator creates a set of abstract symbols with various degrees of symmetry. Many different degrees of symmetry (and some less symmetrical) are included, and are picked at random. The generator uses simple cellular automata (using the Moore neighborhood) for creating an unshaded, black and white version. Additional cellular automata can create a shaded appearance for the symbols. The probabilities for which symmetry will be used, and other parameters of the cellular automata can be easily changed.

To learn more about how cellular automata work, there are details in the PCG book [chapter 3](http://antoniosliapis.com/articles/pcgbook_dungeons.php) and a better documented implementation for cave generation [here](https://github.com/sentientdesigns/constructive). 

Example unshaded symbols:

![unshaded_symbols](https://github.com/sentientdesigns/aliensymbols/blob/master/unshaded.png)


Example shaded symbols:

![shaded_symbols](https://github.com/sentientdesigns/aliensymbols/blob/master/shaded.png)

# Intended Use
I made this generator as a companion for a [board game](https://globalgamejam.org/2018/games/aliens-last-stand) developed as part of the Global Game Jam 2018. The generator's output was the cards through which players communicated with each other to face off an alien threat. The theme of the 2018 GGJ was "transmission" and using new symbols in every playthrough underlined the board game's challenge of understanding the abstract symbols' meaning through interaction and iterative language-forming.

# Notes on Installation
You will need to add the core.jar as an external library to the project. This jar file is provided within the zip file of Processing (processing.org), and is merely included here for convenience.
