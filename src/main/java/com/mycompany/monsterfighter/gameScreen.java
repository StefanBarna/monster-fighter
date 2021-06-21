/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Stefan
 */
// imports
import java.io.*;
import javax.swing.*;

// manages a single character (player, monster, etc)
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
    // final parallel arrays (database)
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
    
    /**
     * Creates new form gameScreen
     */
    public gameScreen() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlPlayerOptions = new javax.swing.JPanel();
        btnAttack = new javax.swing.JButton();
        btnRecover = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        pnlMonsterStats = new javax.swing.JPanel();
        lblMonsterName = new javax.swing.JLabel();
        lblMonsterLevel = new javax.swing.JLabel();
        lblMonsterHP = new javax.swing.JLabel();
        lblMonsterATK = new javax.swing.JLabel();
        lblMonsterDEF = new javax.swing.JLabel();
        lblMonsterDEFVal = new javax.swing.JLabel();
        lblMonsterATKVal = new javax.swing.JLabel();
        lblMonsterHPVal = new javax.swing.JLabel();
        pnlDescriptiveLabels = new javax.swing.JPanel();
        lblPlayerAction = new javax.swing.JLabel();
        lblPlayerCalculation = new javax.swing.JLabel();
        lblMonsterAction = new javax.swing.JLabel();
        lblMonsterCalculation = new javax.swing.JLabel();
        pnlPlayerStats = new javax.swing.JPanel();
        lblPlayerName = new javax.swing.JLabel();
        lblPlayerLevel = new javax.swing.JLabel();
        lblPlayerHP = new javax.swing.JLabel();
        lblPlayerATK = new javax.swing.JLabel();
        lblPlayerDEF = new javax.swing.JLabel();
        lblPlayerDEFVal = new javax.swing.JLabel();
        lblPlayerATKVal = new javax.swing.JLabel();
        lblPlayerHPVal = new javax.swing.JLabel();
        pnlMonsterImg = new javax.swing.JPanel();
        lblMonsterImage = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlPlayerOptions.setBackground(new java.awt.Color(224, 224, 253));
        pnlPlayerOptions.setPreferredSize(new java.awt.Dimension(200, 120));

        btnAttack.setBackground(new java.awt.Color(215, 215, 248));
        btnAttack.setText("ATTACK");
        btnAttack.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnAttack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAttackActionPerformed(evt);
            }
        });

        btnRecover.setBackground(new java.awt.Color(215, 215, 248));
        btnRecover.setText("RECOVER");
        btnRecover.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnRecover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecoverActionPerformed(evt);
            }
        });

        btnSave.setBackground(new java.awt.Color(215, 215, 248));
        btnSave.setText("SAVE");
        btnSave.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnReset.setBackground(new java.awt.Color(215, 215, 248));
        btnReset.setText("RESET");
        btnReset.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlPlayerOptionsLayout = new javax.swing.GroupLayout(pnlPlayerOptions);
        pnlPlayerOptions.setLayout(pnlPlayerOptionsLayout);
        pnlPlayerOptionsLayout.setHorizontalGroup(
            pnlPlayerOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPlayerOptionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPlayerOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRecover, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAttack, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlPlayerOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        pnlPlayerOptionsLayout.setVerticalGroup(
            pnlPlayerOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPlayerOptionsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlPlayerOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAttack, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlPlayerOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRecover, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7))
        );

        pnlMonsterStats.setBackground(new java.awt.Color(237, 237, 253));
        pnlMonsterStats.setPreferredSize(new java.awt.Dimension(50, 135));

        lblMonsterName.setFont(new java.awt.Font("Malgun Gothic Semilight", 0, 14)); // NOI18N
        lblMonsterName.setText("monster");

        lblMonsterLevel.setFont(new java.awt.Font("Malgun Gothic Semilight", 0, 12)); // NOI18N
        lblMonsterLevel.setText("lvl ???");

        lblMonsterHP.setFont(new java.awt.Font("Malgun Gothic Semilight", 0, 11)); // NOI18N
        lblMonsterHP.setText("HP");

        lblMonsterATK.setFont(new java.awt.Font("Malgun Gothic Semilight", 0, 11)); // NOI18N
        lblMonsterATK.setText("ATK");

        lblMonsterDEF.setFont(new java.awt.Font("Malgun Gothic Semilight", 0, 11)); // NOI18N
        lblMonsterDEF.setText("DEF");

        lblMonsterDEFVal.setFont(new java.awt.Font("Malgun Gothic Semilight", 0, 11)); // NOI18N
        lblMonsterDEFVal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMonsterDEFVal.setText("???");

        lblMonsterATKVal.setFont(new java.awt.Font("Malgun Gothic Semilight", 0, 11)); // NOI18N
        lblMonsterATKVal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMonsterATKVal.setText("???");

        lblMonsterHPVal.setFont(new java.awt.Font("Malgun Gothic Semilight", 0, 11)); // NOI18N
        lblMonsterHPVal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMonsterHPVal.setText("???");

        javax.swing.GroupLayout pnlMonsterStatsLayout = new javax.swing.GroupLayout(pnlMonsterStats);
        pnlMonsterStats.setLayout(pnlMonsterStatsLayout);
        pnlMonsterStatsLayout.setHorizontalGroup(
            pnlMonsterStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMonsterStatsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMonsterStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMonsterStatsLayout.createSequentialGroup()
                        .addGroup(pnlMonsterStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMonsterHP)
                            .addComponent(lblMonsterATK)
                            .addComponent(lblMonsterDEF))
                        .addGroup(pnlMonsterStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlMonsterStatsLayout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(lblMonsterHPVal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(9, 9, 9))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMonsterStatsLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pnlMonsterStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblMonsterDEFVal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblMonsterATKVal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())))
                    .addGroup(pnlMonsterStatsLayout.createSequentialGroup()
                        .addGroup(pnlMonsterStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMonsterName)
                            .addComponent(lblMonsterLevel))
                        .addContainerGap(33, Short.MAX_VALUE))))
        );
        pnlMonsterStatsLayout.setVerticalGroup(
            pnlMonsterStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMonsterStatsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMonsterName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblMonsterLevel)
                .addGap(18, 18, 18)
                .addGroup(pnlMonsterStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMonsterStatsLayout.createSequentialGroup()
                        .addComponent(lblMonsterHPVal)
                        .addGap(18, 18, 18)
                        .addComponent(lblMonsterATKVal)
                        .addGap(18, 18, 18)
                        .addComponent(lblMonsterDEFVal))
                    .addGroup(pnlMonsterStatsLayout.createSequentialGroup()
                        .addComponent(lblMonsterHP)
                        .addGap(18, 18, 18)
                        .addComponent(lblMonsterATK)
                        .addGap(18, 18, 18)
                        .addComponent(lblMonsterDEF)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlDescriptiveLabels.setBackground(new java.awt.Color(238, 238, 253));
        pnlDescriptiveLabels.setPreferredSize(new java.awt.Dimension(200, 120));
        pnlDescriptiveLabels.setRequestFocusEnabled(false);

        lblPlayerAction.setFont(new java.awt.Font("Malgun Gothic Semilight", 0, 11)); // NOI18N
        lblPlayerAction.setText(" ");

        lblPlayerCalculation.setFont(new java.awt.Font("Malgun Gothic Semilight", 0, 11)); // NOI18N
        lblPlayerCalculation.setText(" ");

        lblMonsterAction.setFont(new java.awt.Font("Malgun Gothic Semilight", 0, 11)); // NOI18N
        lblMonsterAction.setText(" ");

        lblMonsterCalculation.setFont(new java.awt.Font("Malgun Gothic Semilight", 0, 11)); // NOI18N
        lblMonsterCalculation.setText(" ");

        javax.swing.GroupLayout pnlDescriptiveLabelsLayout = new javax.swing.GroupLayout(pnlDescriptiveLabels);
        pnlDescriptiveLabels.setLayout(pnlDescriptiveLabelsLayout);
        pnlDescriptiveLabelsLayout.setHorizontalGroup(
            pnlDescriptiveLabelsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDescriptiveLabelsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDescriptiveLabelsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPlayerAction, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                    .addComponent(lblPlayerCalculation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMonsterAction, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMonsterCalculation, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlDescriptiveLabelsLayout.setVerticalGroup(
            pnlDescriptiveLabelsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDescriptiveLabelsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPlayerAction)
                .addGap(11, 11, 11)
                .addComponent(lblPlayerCalculation)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblMonsterAction)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblMonsterCalculation)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlPlayerStats.setBackground(new java.awt.Color(237, 237, 253));
        pnlPlayerStats.setPreferredSize(new java.awt.Dimension(50, 135));

        lblPlayerName.setFont(new java.awt.Font("Malgun Gothic Semilight", 0, 14)); // NOI18N
        lblPlayerName.setText("player");

        lblPlayerLevel.setFont(new java.awt.Font("Malgun Gothic Semilight", 0, 12)); // NOI18N
        lblPlayerLevel.setText("lvl ???");

        lblPlayerHP.setFont(new java.awt.Font("Malgun Gothic Semilight", 0, 11)); // NOI18N
        lblPlayerHP.setText("HP");

        lblPlayerATK.setFont(new java.awt.Font("Malgun Gothic Semilight", 0, 11)); // NOI18N
        lblPlayerATK.setText("ATK");

        lblPlayerDEF.setFont(new java.awt.Font("Malgun Gothic Semilight", 0, 11)); // NOI18N
        lblPlayerDEF.setText("DEF");

        lblPlayerDEFVal.setFont(new java.awt.Font("Malgun Gothic Semilight", 0, 11)); // NOI18N
        lblPlayerDEFVal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPlayerDEFVal.setText("???");

        lblPlayerATKVal.setFont(new java.awt.Font("Malgun Gothic Semilight", 0, 11)); // NOI18N
        lblPlayerATKVal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPlayerATKVal.setText("???");

        lblPlayerHPVal.setFont(new java.awt.Font("Malgun Gothic Semilight", 0, 11)); // NOI18N
        lblPlayerHPVal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPlayerHPVal.setText("???");

        javax.swing.GroupLayout pnlPlayerStatsLayout = new javax.swing.GroupLayout(pnlPlayerStats);
        pnlPlayerStats.setLayout(pnlPlayerStatsLayout);
        pnlPlayerStatsLayout.setHorizontalGroup(
            pnlPlayerStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPlayerStatsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPlayerStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPlayerStatsLayout.createSequentialGroup()
                        .addGroup(pnlPlayerStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPlayerHP)
                            .addComponent(lblPlayerATK)
                            .addComponent(lblPlayerDEF))
                        .addGroup(pnlPlayerStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlPlayerStatsLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblPlayerHPVal, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(12, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPlayerStatsLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pnlPlayerStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblPlayerATKVal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblPlayerDEFVal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())))
                    .addGroup(pnlPlayerStatsLayout.createSequentialGroup()
                        .addGroup(pnlPlayerStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPlayerName)
                            .addComponent(lblPlayerLevel))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        pnlPlayerStatsLayout.setVerticalGroup(
            pnlPlayerStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPlayerStatsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPlayerName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblPlayerLevel)
                .addGap(18, 18, 18)
                .addGroup(pnlPlayerStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPlayerStatsLayout.createSequentialGroup()
                        .addGroup(pnlPlayerStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPlayerHP)
                            .addComponent(lblPlayerHPVal))
                        .addGap(18, 18, 18)
                        .addComponent(lblPlayerATK)
                        .addGap(18, 18, 18)
                        .addComponent(lblPlayerDEF))
                    .addGroup(pnlPlayerStatsLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(lblPlayerATKVal)
                        .addGap(18, 18, 18)
                        .addComponent(lblPlayerDEFVal)))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        pnlMonsterImg.setBackground(new java.awt.Color(248, 248, 255));
        pnlMonsterImg.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pnlMonsterImgLayout = new javax.swing.GroupLayout(pnlMonsterImg);
        pnlMonsterImg.setLayout(pnlMonsterImgLayout);
        pnlMonsterImgLayout.setHorizontalGroup(
            pnlMonsterImgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblMonsterImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlMonsterImgLayout.setVerticalGroup(
            pnlMonsterImgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblMonsterImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlDescriptiveLabels, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                    .addComponent(pnlMonsterImg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlPlayerStats, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlMonsterStats, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlPlayerOptions, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlPlayerStats, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                    .addComponent(pnlMonsterStats, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                    .addComponent(pnlMonsterImg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlDescriptiveLabels, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlPlayerOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        turn(SAVE);
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        turn(RESET);
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnAttackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAttackActionPerformed
        turn(ATTACK);
    }//GEN-LAST:event_btnAttackActionPerformed

    private void btnRecoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRecoverActionPerformed
        turn(RECOVER);
    }//GEN-LAST:event_btnRecoverActionPerformed

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
        lblPlayerName.setText(player.getName());
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
                // push the data into the character
                player = new character(buffer.readLine(),
                        buffer.readLine(),
                        Integer.parseInt(buffer.readLine()),
                        Integer.parseInt(buffer.readLine()),
                        Integer.parseInt(buffer.readLine()),
                        Integer.parseInt(buffer.readLine()),
                        Integer.parseInt(buffer.readLine()));
                // ignore a line
                buffer.readLine();
                monster = new character(buffer.readLine(),
                        buffer.readLine(),
                        Integer.parseInt(buffer.readLine()),
                        Integer.parseInt(buffer.readLine()),
                        Integer.parseInt(buffer.readLine()),
                        Integer.parseInt(buffer.readLine()),
                        Integer.parseInt(buffer.readLine()));
            }
        } catch (IOException e) {
            System.out.println("[error loading save file]");
        }
        
        updateLabels();
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
        damage = attacker.attack();         // raw damage
        damage -= defender.defend(damage);  // mitidaged damage
        if (defender.takeDamage(damage))    // take damage
            return true;
        else
            return false;
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
            
            // close file
            svfile.close();
        } catch (IOException e) {
            System.out.println("[error loading save file]");
        }
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
            System.out.println("[error loading save file]");
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
                // calculate amount recovered
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
            lblMonsterAction.setText("You died.");
            reset();
        }
        else
            lblMonsterCalculation.setText("");
         
         // update labels
         updateLabels();
   }
            
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(gameScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(gameScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(gameScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(gameScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                gameScreen game = new gameScreen();
                game.setVisible(true);
                game.load();
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAttack;
    private javax.swing.JButton btnRecover;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel lblMonsterATK;
    private javax.swing.JLabel lblMonsterATKVal;
    private javax.swing.JLabel lblMonsterAction;
    private javax.swing.JLabel lblMonsterCalculation;
    private javax.swing.JLabel lblMonsterDEF;
    private javax.swing.JLabel lblMonsterDEFVal;
    private javax.swing.JLabel lblMonsterHP;
    private javax.swing.JLabel lblMonsterHPVal;
    private javax.swing.JLabel lblMonsterImage;
    private javax.swing.JLabel lblMonsterLevel;
    private javax.swing.JLabel lblMonsterName;
    private javax.swing.JLabel lblPlayerATK;
    private javax.swing.JLabel lblPlayerATKVal;
    private javax.swing.JLabel lblPlayerAction;
    private javax.swing.JLabel lblPlayerCalculation;
    private javax.swing.JLabel lblPlayerDEF;
    private javax.swing.JLabel lblPlayerDEFVal;
    private javax.swing.JLabel lblPlayerHP;
    private javax.swing.JLabel lblPlayerHPVal;
    private javax.swing.JLabel lblPlayerLevel;
    private javax.swing.JLabel lblPlayerName;
    private javax.swing.JPanel pnlDescriptiveLabels;
    private javax.swing.JPanel pnlMonsterImg;
    private javax.swing.JPanel pnlMonsterStats;
    private javax.swing.JPanel pnlPlayerOptions;
    private javax.swing.JPanel pnlPlayerStats;
    // End of variables declaration//GEN-END:variables
}