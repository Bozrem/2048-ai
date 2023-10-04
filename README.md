# 2048-ai
A short project to gain some experience with machine learning through solving the game 2048

### Overview
2048 is a simple 4 by 4 grid where the player slides tiles in one direction or another to combine them, adding more tiles until it cannot, in which case the game is over. The machine learning model built here to solve
it uses an Evolutionary Algorithm, running 35 models in a generation, then mutating the top-performing models

#### Model Process
The model uses Xavier initialization when creating the randomized models in generation 0. From there, the models are all tested to sort out the top performers. Out of the 35, 5 will be chosen to make 7 mutated models 
each. The models are mutated with Gaussian randomness, and calculated with a sigmoid activation function. The current version contains 16 input nodes (1 for each grid in the game), 20 nodes in the hidden layer, and 4
output nodes for the different directions.

#### UI
The training process is shown using a simple JFrame UI, displaying the statistics off to the left and the model simulations to the right. It uses a set of JPanels with grid layouts for both the models and the 
statistics. GameBoardPanel objects are created and assigned a modelID, which it will use with the ModelManager to get the next move.
The following is the current version of the UI

![alt text](https://github.com/Bozrem/2048-ai/blob/master/2048UIExample.png)

#### Timeline
Upcoming features
* UI
  * Add a separator line between models and information
  * Add graph showing mean scores over the last x generations
* Model
  * Top Performers weighting - 1st performer gets 16 models, 2nd gets 10, etc
  * Have models play multiple games before being mutated - Aims to reduce the likelihood of bad models getting lucky
  * Change to mutate all-time best models over generational best models - If a generation is performing poorly, they don't impact the next one
