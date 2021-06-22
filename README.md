# Monster Fighter
You can access the project's raw code in the .java file found in this branch. Disclaimer: the .java file in this branch contain __only functions that I wrote__, meaning that it is not runnable, and is primarily for evaluating the code I wrote. Additionally, you can view screenshots of the game in the screenshots folder also found in this branch. If you wish to run this project on your computer, you can access all of the required NetBeans files in the master project. To do so, click on the button around the top left titled main, and select master. Download those files and place them in your root NetBeans folder, and they should be fully runnable. To find a full documentation of the methods you will find in the project and a short description, open the methods.md file in this branch.

### The Game's Rules
#### What is this project?
The goal for this project was to create a simple turn-based combat system in which the player progresses through an infinite number of stages where they oppose a monster selected at random. The simulation features three statistics: health, attack, and defense, which will affect the damage sent and received during in a battle. The amount of health is set at the beginning of the game, and will decrease every time the unit is attacked. The attack stat defines a range of damage that the unit can deal. The defense stat defines the amount of damage the unit can block. The fight ends when one of the units loses all its health. The player will level up after each fight, increasing their ATK and DEF by 2, and their HP by 15. If the player loses, the simulation resets and the player’s level returns to one.

#### What are the game's core functions?
Each turn, the player will have the option to either attack or recover. Attacking will deal damage based on the player’s ATK stat and the monster’s DEF stat. Recovering will replenish health based on a percent of the player’s HP in addition to a percentage of their DEF stat. After each turn, the monster will attack the player, dealing a portion of the player’s HP as damage. The user has a choice to save at any point in the game, writing all progress to a file that will be read upon program startup. The user also has a choice to revert all progress, resetting their stats and level to the base amount.

#### How does the game scale?
Each monster has a set of information representing it's attack, defense, and health stats, which are used each time a random monster is selected. However, the game gain less and less difficulty as it progresses, meaning that although it might be a challenge at the start, once the player levels up several times, it'd be easy to defeat oncoming monsters. To fix this, a method of monster scaling was implemented, such that a monster's level, similar to that of a player, will determine how powerful they are. The base stats of a monster are affected by a multipler that involves their level. In order to prevent the player from encountering high level monsters right at the start of the game, a player-level based system was added, such that a monster's level is dependent on a range set by the player's level. A monster's level will not exceed the player's level beyond a certain point, which increases the more the player levels up.

### Functions and Features
#### What new features and knowledge does this project showcase?
Monster Fighter features images representing monsters in its GUI in order to make it more appealing to a player. Using images in a GUI is a topic that has been mentioned in a few lessons, however was not ever taught or used in our classes and assignments. The project also features classes in order to store and manipulate the player and monster’s stats and data. Two instances of this class will be created as members of the main program's class: one for the player and one for the monster. Aditionally, although file manipulation has been explored in class previously, there was no mention of writing to a text file rather than just reading it, a concept vital to the project's save system.

#### How does this project implement a database?
The project features 3 methods of storing information. The first involves manipulation of a text file. This data is mainly for starting up the project and loading the text file, however will also be used whenever the player saves or resets the game. The second method involves custom classes, where the temporary information about a player and monster is stored. This method isn't particularly for storing information, as it's more for keeping track of player progress throughout the game, however it is the way the player (and the monster the player is currently fighting against)'s data is kept track of. The final involves parallel arrays, where the different base stats, names, png file locations for individual monsters will be held. This system will be used every time a fight is over and a new random monster is generated, and is the main database of the project. A given monster will have all its stats at a given index i, such that MONSTER_NAMES[i] aligns with MONSTER_ATK[i] and MONSTER_DEF[i].

### Top-Down Design
![image](https://user-images.githubusercontent.com/59585745/122836829-08050580-d2c1-11eb-84e7-adfd779e7f0a.png)
