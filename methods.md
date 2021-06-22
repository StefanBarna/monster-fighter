# Class Definitions

## Character Class
### Private Variables
String m_name: a string value representing the unit’s name

String m_image: a string holding the character's file location (null for player)

int m_level: a value representing the unit’s level of power

int m_attack: the value representing the unit’s damage range

int m_defense: the value representing how much damage the unit is able to negate

int m_max_health: the value representing the unit’s maximum health; health will not exceed this capacity

int m_health: the value representing the unit’s current health; when this reaches 0 the unit dies


### Public Methods
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
