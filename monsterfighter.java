// imports
import java.io.*;
import javax.swing.*;

// manages a single character (player, monster)
class character {
    private String m_name;    // character name
    private String m_image;   // file address of character png
    private int m_level;      // character level
    private int m_attack;     // attack stat
    private int m_defense;    // defense stat
    private int m_max_health; // maximum health
    private int m_health;     // current health
    
    // default constructor
    public character() {
        // set all values to empty state
        this.m_name = "";
        this.m_image = "";
        this.m_level = 0;
        this.m_attack = 0;
        this.m_defense = 0;
        this.m_max_health = 0;
        this.m_health = this.m_max_health;
    }
    
    // custom data constructor (when you only need max HP)
    public character(String name, String image, int level, int attack, int defense, int max_health) {
        // set all values to provided parameters
        this.m_name = name;
        this.m_image = image;
        this.m_level = level;
        this.m_attack = attack;
        this.m_defense = defense;
        this.m_max_health = max_health;
        this.m_health = this.m_max_health;
    }
    
    // custom full data constructor (for reading save file)
    public character(String name, String image, int level, int attack, int defense, int max_health, int current_health) {
        // set all values to provided parameters
        this.m_name = name;
        this.m_image = image;
        this.m_level = level;
        this.m_attack = attack;
        this.m_defense = defense;
        this.m_max_health = max_health;
        this.m_health = current_health;
    }
    
    // returns how much damage the character will deal
    public int attack() {
        // check that the attack stat is greater than 0
        int damage = 0;
        if (this.m_attack > 0) {
            // chose a number from a range of 10 (attack - 5 to attack + 5)
            damage = (int)Math.ceil(Math.random() * 10.0 + ((double)this.m_attack - 5.0));
        }
        return damage;
    }
    
    // returns the amount of damage to mitigate
    public int defend(int damage) {
        // check if damage is less than defense
        if (damage > this.m_defense / 2) {
            // negate damage equal to half the instance's defense
            return this.m_defense / 2;
        }
        // otherwise negate all damage (deal no damage)
        else {
            return damage;
        }
    }
    
    // decrements instance health by the parameter damage; returns false if health goes below 0, true otherwise
    public boolean takeDamage(int damage) {
        // decrement health
        this.m_health -= damage;
        // check if instance dies
        if (this.m_health <= 0) {
            this.m_health = 0;
            return false;
        }
        return true;
    }
    
    // increments instance health by the instance defense and maximum health
    public void recover() {
        // get amount to heal
        int heal = (int)((double)this.m_max_health / 100.0 * 60.0);
        heal += (double)this.m_defense;
        // check if you can recover the maximum amount
        if (this.m_health + heal <= this.m_max_health)
            this.m_health += heal;
        else
            this.m_health = this.m_max_health;
    }
    
    // increments the instance level by one, upgrading stats
    public void levelUp() {
        ++this.m_level;
        this.m_attack += 2;
        this.m_defense += 2;
        this.m_max_health += 15;
        this.m_health += 15;
    }
    
    // returns the current instance's name
    public String getName() {
        return this.m_name;
    }
    
    // returns the current instance's file path to image
    public String getImgName() {
        return this.m_image;
    }
    
    // returns the current instance's level
    public int getLevel() {
        return this.m_level;
    }
    
    // returns the current instance's attack stat
    public int getAttack() {
        return this.m_attack;
    }
    
    // returns the current instance's defense stat
    public int getDefense() {
        return this.m_defense;
    }
    
    // returns the current instance's maximum health
    public int getMaxHealth() {
        return this.m_max_health;
    }
    
    // returns the current instance's current health
    public int getCurrentHealth() {
        return this.m_health;
    }
}

// main class
public class gameScreen extends javax.swing.JFrame {
    // constant parallel arrays (database)
    public final int MONSTER_CNT = 6;
    public final String[] MONSTER_NAMES = {"Treant", "Dragon", "Phoenix", "Ghost", "Spider", "Rock"};
    public final String[] MONSTER_IMAGES = {"C:\\Users\\Stefan\\Documents\\NetBeansProjects\\monsterFighter\\src\\main\\java\\treant.png", 
        "C:\\Users\\Stefan\\Documents\\NetBeansProjects\\monsterFighter\\src\\main\\java\\dragon.png",
        "C:\\Users\\Stefan\\Documents\\NetBeansProjects\\monsterFighter\\src\\main\\java\\phoenix.png",
        "C:\\Users\\Stefan\\Documents\\NetBeansProjects\\monsterFighter\\src\\main\\java\\ghost.png",
        "C:\\Users\\Stefan\\Documents\\NetBeansProjects\\monsterFighter\\src\\main\\java\\spider.png",
        "C:\\Users\\Stefan\\Documents\\NetBeansProjects\\monsterFighter\\src\\main\\java\\rock.png"};
    public final int[] MONSTER_ATK = {10, 20, 80, 1, 100, 0};
    public final int[] MONSTER_DEF = {50, 5, 2, 0, 10, 0};
    public final int[] MONSTER_HP = {30, 20, 15, 5, 5, 200};
    
   // signals 
   public final int ATTACK = 1;
   public final int RECOVER = 2;
   public final int SAVE = 3;
   public final int RESET = 4;

   // player and monster variables
   character player;
   character monster;
    
    // tell program to save
    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {                                        
        turn(SAVE);
    }                                       

    // tell program to reset save
    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {                                         
        turn(RESET);
    }                                        

    // tell program player wants to attack
    private void btnAttackActionPerformed(java.awt.event.ActionEvent evt) {                                          
        turn(ATTACK);
    }                                         

    // tell program player wants to recover
    private void btnRecoverActionPerformed(java.awt.event.ActionEvent evt) {                                           
        turn(RECOVER);
    }                                              

    /*
    newMonster
    
    This method will create a new character with a random monster's stats
    and update the monster member with the new data.
    
    Receives: void
    
    Returns: void
    */
    public void newMonster() {
        // select a random monster
        int randMonster = (int) Math.floor(Math.random() * MONSTER_CNT);
        
        // randomize monster level based on player level to make the game scale in difficulty
        int randLevel = (int) Math.floor(Math.random() * ((int) player.getLevel() * 0.2) + (int) player.getLevel());
        
        this.monster = new character(MONSTER_NAMES[randMonster],
                MONSTER_IMAGES[randMonster],
                randLevel,
                (int)((double)MONSTER_ATK[randMonster] * ((double)randLevel * 0.1)),
                (int)((double)MONSTER_DEF[randMonster] * ((double)randLevel * 0.1)),
                MONSTER_HP[randMonster] + ((randLevel - 1) * 10));
    }
    
    /*
    updateLabels
    
    This method will refresh the data displayed to the screen (the player
    and monster's health, attack, defense, level, etc).
    
    Receives: void
    
    Returns: void
    */
    public void updateLabels() {
        // set player values
        lblPlayerName.setText("Player");
        lblPlayerLevel.setText("lvl " + player.getLevel());
        lblPlayerHPVal.setText(player.getCurrentHealth() + "/" + player.getMaxHealth());
        lblPlayerATKVal.setText(String.valueOf(player.getAttack()));
        lblPlayerDEFVal.setText(String.valueOf(player.getDefense()));

        // set monster values
        lblMonsterName.setText(monster.getName());
        lblMonsterLevel.setText("lvl " + monster.getLevel());
        lblMonsterHPVal.setText(monster.getCurrentHealth() + "/" + monster.getMaxHealth());
        lblMonsterATKVal.setText(String.valueOf(monster.getAttack()));
        lblMonsterDEFVal.setText(String.valueOf(monster.getDefense()));
        
        // update the icon
        lblMonsterImage.setIcon(new javax.swing.ImageIcon(monster.getImgName()));
    }
    
    /*
    load
    
    This method is called at the start of the class to get the contents of
    the save file and load it to the player and monster.
    
    Receives: void
     
   Returns: void
    */
    public void load() {
        // read the save file
        try {
            // find the ave file
            FileReader svfile = new FileReader("save.txt");
            
            // check if the file is empty
            if (svfile.read() == -1) {
                // create a new player
                player = new character("Player", null, 1, 10, 2, 50);
                newMonster();
            }
            // otherwise load the file
            else {
                // data from file storage
                BufferedReader buffer = new BufferedReader(svfile);
                // load the data into the character
                player = new character(buffer.readLine(),
                        buffer.readLine(),
                        Integer.parseInt(buffer.readLine()),
                        Integer.parseInt(buffer.readLine()),
                        Integer.parseInt(buffer.readLine()),
                        Integer.parseInt(buffer.readLine()),
                        Integer.parseInt(buffer.readLine()));
                // ignore a line
                buffer.readLine();
                // load the data into the monster
                monster = new character(buffer.readLine(),
                        buffer.readLine(),
                        Integer.parseInt(buffer.readLine()),
                        Integer.parseInt(buffer.readLine()),
                        Integer.parseInt(buffer.readLine()),
                        Integer.parseInt(buffer.readLine()),
                        Integer.parseInt(buffer.readLine()));
            }
        } catch (IOException e) {
            lblPlayerAction.setText("[error loading save file]");
            lblPlayerCalculation.setText("");
            lblMonsterAction.setText("");
            lblMonsterCalculation.setText("");
        }
        
        updateLabels();
    }
    
    /*
    save
    
    This method takes the player and monster and saves their
    data to a file that will be loaded next time the program is
    run.
    
    Receives: void
    
    Returns: void
    */
    public void save() {
        // open the save file
        try {
            // open the save file
            FileWriter svfile = new FileWriter("save.txt");
            
            // write contents of player
            svfile.write(player.getName() + "\n");
            svfile.write(player.getImgName() + "\n");
            svfile.write(player.getLevel() + "\n");
            svfile.write(player.getAttack() + "\n");
            svfile.write(player.getDefense() + "\n");
            svfile.write(player.getMaxHealth() + "\n");
            svfile.write(player.getCurrentHealth() + "\n");
            svfile.write("\n");
            
            // write contents of monster
            svfile.write(monster.getName() + "\n");
            svfile.write(monster.getImgName() + "\n");
            svfile.write(monster.getLevel() + "\n");
            svfile.write(monster.getAttack() + "\n");
            svfile.write(monster.getDefense() + "\n");
            svfile.write(monster.getMaxHealth() + "\n");
            svfile.write(monster.getCurrentHealth() + "\n");          
            
            // close save file
            svfile.close();
        } catch (IOException e) {
            lblPlayerAction.setText("[error loading save file]");
            lblPlayerCalculation.setText("");
            lblMonsterAction.setText("");
            lblMonsterCalculation.setText("");
        }
    }
    
    /*
    attack
    
    This method receives the attacker and the defender, and deals
    damage based on the attacker's ATK stat and the defender's 
    DEF stat to the defender. Returns true if defender lives, false
    if it dies.
    
    Receives: character, character
    
    Returns: boolean
    */
    public boolean attack(character attacker, character defender) {
        // how much damage the defender will take
        int damage;
        damage = attacker.attack();         // calculate raw damage
        damage -= defender.defend(damage);  // calculate mitidaged damage
        if (defender.takeDamage(damage))    // defender takes damage
            return true;
        else
            return false;
    }
    
    /*
    reset
    
    This method loads the save file and empties it. It then
    creates a new monster and resets the player's stats.
    
    Receives: void
    
    Returns: void
    */
    public void reset() {
        // open the save file
        try {
            FileWriter svfile = new FileWriter("save.txt");
            
            // empty the file
            svfile.write("");
            svfile.close();
            
            // create a new save
            load();
        } catch (IOException e) {
            lblPlayerAction.setText("[error loading save file]");
            lblPlayerCalculation.setText("");
            lblMonsterAction.setText("");
            lblMonsterCalculation.setText("");
        }
    }
    
    /*
    turn
    
    This method iterates over a full turn. It receives an integer
    representing the way this function was called. If it receives
    1, the user chose to attack. If it receives 2, the user chose
    to recover. If it receives 3, the user chose to save. If it
    receives 4, the user chose to reset their save. It then acts
    out the turn of the monster, and calls to end the match if 
    either the player or the monster dies.
    
    Receives: int
    
    Returns: void
    */
    public void turn(int signal) {
        // update label info
        updateLabels();
        
        // execute command from user
        boolean alive = true;
        switch (signal) {
            case ATTACK:
            {
                // calculate damage dealt
                int damage = monster.getCurrentHealth();
                alive = attack(player, monster);
                damage -= monster.getCurrentHealth();
                
                // update descriptive labels
                lblPlayerAction.setText("You attack the " + monster.getName() + ".");
                lblPlayerCalculation.setText("You dealt " + damage + " damage.");
                break;
            }
            case RECOVER:
            {
                // calculate health recovered
                int heal = player.getCurrentHealth();
                player.recover();
                heal -= player.getCurrentHealth();
                heal = Math.abs(heal);
                
                // update descriptive labels
                lblPlayerAction.setText("You recover.");
                lblPlayerCalculation.setText("You regained " + heal + "HP.");
                break;
            }
            case SAVE:
            {
                save();
                
                // update descriptive labels
                lblPlayerAction.setText("Your progress has been saved.");
                lblPlayerCalculation.setText("");
                lblMonsterAction.setText("");
                lblMonsterCalculation.setText("");
                return;
            }
            case RESET:
            {
                reset();
                
                // update descriptive labels
                lblPlayerAction.setText("Your progress has been reset.");
                lblPlayerCalculation.setText("");
                lblMonsterAction.setText("");
                lblMonsterCalculation.setText("");
                return;
            }
        }
         
        // check if the monster died
        if (!alive) {
            lblMonsterAction.setText("You killed the " + monster.getName() + ".");
            lblMonsterCalculation.setText("You levelled up.");
            player.levelUp();
            // get a new monster
            newMonster();
           
            updateLabels(); 
            return;
        }
         
         // update labels
        updateLabels();
         
         // monster attacks player
        int damage = player.getCurrentHealth();
        alive = attack(monster, player);
        damage -= player.getCurrentHealth();
        
        lblMonsterAction.setText("The " + monster.getName() + " dealt " + damage + " damage.");
        // check if player died
        if (!alive) {
            lblMonsterCalculation.setText("You died.");
            reset();
        }
        else
            lblMonsterCalculation.setText("");
         
         // update labels
         updateLabels();
   }
            
    // main function
    public static void main(String args[]) {
        // Create and display the form
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                gameScreen game = new gameScreen();
                game.setVisible(true);
                game.load();
            }
        });
    }
