# Alien Symbol Generator
The generator creates a set of abstract symbols with various degrees of symmetry. Many different degrees of symmetry (and some less symmetrical) are included, and are picked at random. The generator uses simple cellular automata (using the Moore neighborhood) for creating an unshaded, black and white version. Additional cellular automata can create a shaded appearance for the symbols. The probabilities for which symmetry will be used, and other parameters of the cellular automata can be easily changed.

To learn more about how cellular automata work, there are details in the PCG book [chapter 3](http://antoniosliapis.com/articles/pcgbook_dungeons.php) and a better documented implementation for cave generation [here](https://github.com/sentientdesigns/constructive). 

Example unshaded symbols:

![unshaded_symbols](https://github.com/sentientdesigns/aliensymbols/blob/master/graphics/unshaded.png)


Example shaded symbols:

![shaded_symbols](https://github.com/sentientdesigns/aliensymbols/blob/master/graphics/shaded.png)


# Extensions

I have added circular and semi-circular tokens to be created with the same algorithm, under their respective Symbolizer files. These can be used as printouts for circular tokens, for logos or anything where these shapes would be preferred. Interestingly, having a gap of empty pixels between the circle's outline and the noise function can lead to very different results than if there is no gap. 

Example circular symbols:

![circular_symbols](https://github.com/sentientdesigns/aliensymbols/blob/master/graphics/circle_samples.png)


Example semicircular symbols:

![semicircular_symbols](https://github.com/sentientdesigns/aliensymbols/blob/master/graphics/semicircle_samples.png)


Example cellular automata process with a white 'gap' between circle and symbol:

![white CA process](https://github.com/sentientdesigns/aliensymbols/blob/master/graphics/white_symbol_process.gif | width=120)


Example cellular automata process with no 'gap' between circle and symbol:

<img alt="black CA process" src="https://github.com/sentientdesigns/aliensymbols/blob/master/graphics/black_symbol_process.gif" width=120 height=120 \>


# Intended Use
I made this generator as a companion for a [board game](https://globalgamejam.org/2018/games/aliens-last-stand) developed as part of the Global Game Jam 2018. The generator's output was the cards through which players communicated with each other to face off an alien threat. The theme of the 2018 GGJ was "transmission" and using new symbols in every playthrough underlined the board game's challenge of understanding the abstract symbols' meaning through interaction and iterative language-forming.

# Notes
This repository uses the core.jar file which is a (core) component of Processing, included in lib/core.jar. This jar file is provided within the [official zip file of Processing 1.5.1](https://processing.org/download/), and is merely included here for convenience. The core library uses GNU Lesser General Public License, and is included here as combined works. The current codebase is also released under the GNU Lesser General Public License. As with everything open-source, please use and hack away at your heart's content. If you really like it, it's always nice to give a shoutout back to this resource.
