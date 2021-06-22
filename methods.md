# Class Definitions

### Private Variables
String m_name: a string value representing the unit’s name
String m_image: a value holding the string representation of the unit’s image location
int m_level: a value representing the unit’s level of power
int m_attack: the value representing the unit’s damage range
int m_defense: the value representing how much damage the unit is able to block
int m_max_health: the value representing the unit’s current health; when this reaches 0 the unit die
int m_health: the value representing the unit’s current health; when this reaches 0 the unit dies

### Public Methods
public character(): this method will create a new character with all variables set to an empty state
public character(String image, String name, int level, int attack, int defense, int max_health): this method will create a new character, setting the 
parameter values to its variables (this.image is assigned to image, this.name set to name, etc), and sets both health and max_health to the max_health parameter
public int attack(): this method will return how much damage the character will deal as an integer
public int defend(int damage): this method will return how much damage will be inflicted upon the character after mitigation
public boolean takeDamage(int damage): this method will change the character’s hp value based on the amount of damage inflicted; returns false if health goes below 0, true otherwise
public void recover(): this method will replenish the unit’s health based on a percentage of their max health and defense
public int getAttack(): this method returns the unit’s attack stat
public int getDefense(): this method returns the unit’s defense stat
public int getMaxHealth(): this method returns the unit’s maximum health stat
public int getCurrentHealth(): this method returns the unit’s current health stat
public int getLevel(): this method returns the unit’s level
public String getName(): this method returns the unit’s name
public String getImgLink(): this method returns the user’s file path to it’s image