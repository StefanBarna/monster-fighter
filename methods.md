# Class Definitions

### Character Class
#### Private Variables
private String m_name: a string value representing the unit’s name

private String m_image: a string holding the character's file location (null for player)

private int m_level: a value representing the unit’s level of power

private int m_attack: the value representing the unit’s damage range

private int m_defense: the value representing how much damage the unit is able to negate

private int m_max_health: the value representing the unit’s maximum health; health will not exceed this capacity

private int m_health: the value representing the unit’s current health; when this reaches 0 the unit dies

#### Public Methods
public character(): creates a new character with all variables set to an empty state; default constructor

public character(String image, String name, int level, int attack, int defense, int max_health): constructor used when creating a random new monster, or when the current health is irrelevant

public character(String image, String name, int level, int attack, int defense, int max_health, int current_health): constructor used when recreating a monster (via save file load), or when the current health is relevant

public int attack(): returns how much damage the character will deal as an integer, based on a range set by the attack variable

public int defend(int damage): returns how much damage will be negated, based on the unit defense

public boolean takeDamage(int damage): decrements the character’s hp value based on the amount of damage inflicted; returns false if health goes below 0, true otherwise

public void recover(): increments the unit’s health based on a percentage of its max health and defense

public void levelUp(): increments the unit's level by one, increasing appropriate stats

public String getName(): returns the unit’s name; name query

public String getImgName(): returns the user’s file path to it’s image; file location query

public int getLevel(): returns the unit’s level; level query

public int getAttack(): returns the unit’s attack stat; attack query

public int getDefense(): returns the unit’s defense stat; defense query

public int getMaxHealth(): returns the unit’s maximum health stat; max health query

public int getCurrentHealth(): returns the unit’s current health stat; current health query


### Main Class
#### Database (Parallel Arrays)
public final int MONSTER_CNT: an integer holding the number of different monsters (acts as the maximum index)

public final String MONSTER_NAMES[]: an array of strings holding monster names

public final String MONSTER_IMAGES[]: an array of strings holding monster image file locations

public final int MONSTER_ATTACKS[]: an array of strings holding monster attack stats

public final int MONSTER_DEFENCES[]: an array of strings holding monster defense stats

public final int MONSTER_MAX_HEALTHS[]: an array of strings holding monster maximum health stats

#### Private Variables
private character player: a character storing values representing the player

private character monster: a character storing values representing the monster

#### Signals 
public final int ATTACK: integer value signal sent to turn method to attack

public final int RECOVER: integer value signal sent to turn method to recover

public final int SAVE: integer value signal sent to turn method to save

public final int RESET: integer value signal sent to turn method to reset

#### Public Methods
public void newMonster(): creates a new variable of type character with a random monster's data, and updates the monster member with the new data

public void updateLabels(): refreshes the status boxes for both the monster and the player, updating each label with its respective stat

public void load(): loads the player and monster’s information from a save file

public void save(): saves the player’s progress to a text file

public void reset(): this method resets the player’s save

public void attack(): receives the attacker and defender, and deals damage to the defender based on the attacker's attack stat and the defender's defense stat; returns true if the defender lives, false if it dies

public void turn(int signal): this method runs all of the logic that must run each turn, calling one of the functions for saving, attacking, healing, and resetting, running the monster’s attack and updating the stats for both the player and the monster
