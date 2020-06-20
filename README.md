# Sokoswitch
Sokoban is a puzzle game in which a box like object must be pushed into certain locations, walls restricting the movement and many other things. 

This project aims to do a few things: 
1. To learn a new library (LibGDX) from scratch,
2. To get some knowledge in what kinds of work is needed in game development,
3. And to test my skills in game/level design.

## Mechanics
Currently, the game is in its early stages. Here are some mechanics (probably due to change):
- **Blocks** The normal blocks which have an on and off state.
  - **Locked** Cannot be manipulated by the user - state can only be changed indirectly.
  - **Paired** Only connects with its colored pair.
  - **Disconnected** Only certain sides can be connected with - rotation can be changed with a rotator.
  - **Holographic** Cannot be pushed or interacted with, but still connects with blocks. Players can stand inside the block.
- **Connections** Blocks connect together when they touch sides. They move together after being connected, allowing for interesting gimmicks.
  - **Stickpad** When an entity goes on top of the stickpad, they get locked in place. Connected blocks will seperate with the stuck block. 
  - **Splitter** Between tiles. When a block is pushed through the splitter, the two sides become unconnectable.
  
## Release?
I haven't even thought about this step, but it's probably good to set myself a deadline. Currently, a "playable" version is aimed to be released on July 20th. The menu work, assets, and animations will be very rough. Expect more releases after this date.
