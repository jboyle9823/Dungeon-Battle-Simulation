/**
 * Name: Jonathan Boyle
 * Date: April 25th 2024
 * Description: BattleGrid Singleton class that implements the grid and general functionality for the simulation.
 * This class acts as a hub for all object actions and interactions.
 * This class implements the Singleton Design Pattern.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class BattleGrid {
    private static final int x = 5; //The x size of the grid, immutable.
    private static final int y = 5; //The y size of the grid, immutable.
    private static BattleGrid instance; //The singleton instance of the battlegrid.
    private List<Adventurer> adventurers; //List to store all adventurers
    private Semaphore movementSemaphore; //Semaphore to control movement access
    private WeaponSwitcher weaponSwitcher; //WeaponSwitcher object to allow for weapon switching for adventurers.

    /**
     * Private constructor to prevent instantiation from outside.
     * Initializes the weapon switcher, list of adventurers and semaphore.
     */
    private BattleGrid() {
        weaponSwitcher = new WeaponSwitcher();
        adventurers = new ArrayList<>();
        movementSemaphore = new Semaphore(1); //One permit to enable either movement or battling.
    }

    /**
     * GetInstance method for the singleton.
     */
    public static BattleGrid getInstance() {
        if (instance == null) {
            instance = new BattleGrid();
        }
        return instance;
    }

    /**
     * Method to add a new adventurer to the grid.
     *
     * @param adventurer The new adventurer subclass being added.
     * @param x          The starting x-coordinate for the adventurer.
     * @param y          The starting y-coordinate for the adventurer.
     */
    public void addAdventurer(Adventurer adventurer, int x, int y) {
        if (x >= 0 && x <= BattleGrid.x && y >= 0 && y <= BattleGrid.y) {
            adventurers.add(adventurer);
            adventurer.setX(x);
            adventurer.setY(y);
        } else {
            //If the x or y coordinate is outside the grid, print an error message.
            System.out.println("Invalid position for adventurer " + adventurer.getName());
        }
    }

    /**
     * Getter for the list of adventurers.
     */
    public List<Adventurer> getAdventurers() {
        return adventurers;
    }

    /**
     * Method that uses the weapon switcher to equip a sword weapon for a given adventurer.
     */
    public void equipSword() {
        weaponSwitcher.setWeaponStrategy(new SwordStrategy());
    }

    /**
     * Method that uses the weapon switcher to equip a bow weapon for a given adventurer.
     */
    public void equipBow() {
        weaponSwitcher.setWeaponStrategy(new BowStrategy());
    }

    /**
     * Method that uses the weapon switcher to equip a spear weapon for a given adventurer.
     */
    public void equipSpear() {
        weaponSwitcher.setWeaponStrategy(new SpearStrategy());
    }

    /**
     * Method that calls the useWeapon methods from weaponSwitcher to print a statement.
     */
    public void useEquippedWeapon() {
        weaponSwitcher.useWeapon();
    }

    /**
     * Method that check if two adventurers are on the same x and y coordinates, and returns the other if true.
     *
     * @param adventurer The adventurer that is checking if another adventurer is on the same tile.
     * @throws InterruptedException
     */
    public Adventurer isAdventurerEngaged(Adventurer adventurer) throws InterruptedException {
        int x = adventurer.getX();
        int y = adventurer.getY();
        for (Adventurer other : adventurers) {
            if (other != adventurer && other.getX() == x && other.getY() == y && !other.getClass().equals(adventurer.getClass())) {
                return other;
            }
        }
        return null;
    }

    /**
     * Method that moves the adventurer calling it, checks for battle and records the move statistic.
     *
     * @param adventurer
     * @throws InterruptedException
     */
    public void simulateMovement(Adventurer adventurer) throws InterruptedException {
        movementSemaphore.acquire(); //Semaphore is acquired for movement.
        //A random number between 1 and 4, and a switch case are used to determine movement decisions.
        int random_int = (int) Math.floor(Math.random() * (4 - 1 + 1) + 1);
        switch (random_int) {
            case 1: //x++ if random int is 1.
                adventurer.incrementX();
                break;
            case 2: //x-- if the random int is 2.
                adventurer.incrementY();
                break;
            case 3: //y++ if the random int is 3.
                adventurer.decrementX();
                break;
            case 4: //y-- if the random int is 4.
                adventurer.decrementY();
                break;
        }
        adventurer.incrementTimesMoved(); //Times moved is incremented to fit the new move.
        System.out.println(adventurer.getName() + " coordinates are " + adventurer.getX() + ", " + adventurer.getY()
                + " after move " + adventurer.getTimesMoved()); //The new coordinates are output in the log.
        adventurer.getStats().getStatistic("MovesPerformed").addValue(1); //The statistic is recorded.
        movementSemaphore.release(); //The semaphore is released since movement is over.
        if (isAdventurerEngaged(adventurer) != null) {
            //If there is another adventurer on the same tile, simulate a battle between the two.
            Adventurer other = isAdventurerEngaged(adventurer);
            simulateBattle(adventurer, other);
        }
    }

    /**
     * Method that makes the two adventurers battle, and outputs the winner.
     * This method uses the same semaphore for battle, so movement cannot be initiated during battle.
     * This method also controls the weapon assignment, and potion creating, as well as the adventurer mood.
     *
     * @param adv   The first adventurer fighting in the battle.
     * @param other The second adventure fighting in the battle.
     * @throws InterruptedException
     */
    public void simulateBattle(Adventurer adv, Adventurer other) throws InterruptedException {
        movementSemaphore.acquire(); //Semaphore is acquired for the battle.
        System.out.println("    >" + adv.getName() + " is engaged in combat with " + other.getName());
        //The potion builder object is created, and a new potion is generated and output in the log.
        PotionBuilder potionBuilder = new PotionBuilder();
        String potion = potionBuilder.createPotion();
        System.out.println("    >Potion created: " + potion);
        //The specs (atk, hp, lck) for the two adventurers are saved in arrays.
        int[] aOneSpecs = adv.getSpecs();
        int[] aTwoSpecs = other.getSpecs();
        //A weapon is generated for the two adventurers using a random int and switch case.
        int wpnChoice = (int) Math.floor(Math.random() * (4 - 1 + 1) + 1);
        switch (wpnChoice) {
            case 1:
                break; //fighting with fists.
            case 2:
                equipSword(); //fighting with sword.
            case 3:
                equipBow(); //fighting with bow.
            case 4:
                equipSpear(); //fighting with spear.
        }
        //The weapon being used is printed out.
        System.out.print("    >" + adv.getName() + " is ");
        useEquippedWeapon();
        //While loop for the fighting, while both adventurers are still alive the battle continues.
        while (adv.isAlive() && other.isAlive()) {
            //The first adventurer attacks, if the second is killed from this attack, break from the loop.
            advAttack(adv, other, aOneSpecs, aTwoSpecs);
            if (!other.isAlive()) break;
            //The second adventurer attacks.
            advAttack(other, adv, aTwoSpecs, aOneSpecs);
            System.out.println("-------------------------------");
            //Sleep for 2 seconds between turn sets to make the log easier to follow when generating.
            Thread.sleep(2000);
        }
        //If the first adventurer is alive and the second is dead, print that the first has won.
        if (adv.isAlive() && !other.isAlive()) {
            System.out.println("    >" + adv.getName() + " won the battle");
            System.out.println("    >" + other.getName() + " is defeated");
        }
        //if the second adventurer is alive and the second dead, print that the second has won.
        else if (!adv.isAlive() && other.isAlive()) {
            System.out.println("    >" + other.getName() + " won the battle");
            System.out.println("    >" + adv.getName() + " is defeated");

        }
        //If both die on the same turn, the fight is a draw (only possible through Mage Black Magic skill)./
        else {
            System.out.println("Both fighters have been defeated in a Draw!");
        }
        //The semaphore is released for use in movement after the fight it over.
        movementSemaphore.release();
    }

    /**
     * Method that controls the battle logic for a single turn for an adventurer.
     *
     * @param attacker      The adventurer who is attacking on the turn.
     * @param other         The adventurer who is being attacked on the turn.
     * @param attackerSpecs The array of specs for the attacker adventure.
     * @param otherSpecs    The array of specs for the attacked adventurer.
     * @throws InterruptedException
     */
    private void advAttack(Adventurer attacker, Adventurer other, int[] attackerSpecs, int[] otherSpecs) throws InterruptedException {
        //Random int decides how much the luck will boost the attack (10 lck = *1. 20 lck = *1 or *2, etc.)
        int luckBoost = (int) Math.floor(Math.random() * ((attackerSpecs[2] * 0.1) - 1 + 1) + 1);
        int attackerDamage = attackerSpecs[0] * luckBoost;
        //initiateUniqueSkill method is called to decide if the unique skill will be used on the turn.
        if (initiateUniqueSkill()) {
            //The unique skill for the specific subclass of adventurer is called.
            int uniqueSkillType = attacker.uniqueSkill();
            //Depending on the unique skill, run the associated logic.
            switch (uniqueSkillType) {
                //Enrage doubles the attack of the Knight.
                case 1:
                    attackerDamage = attackerDamage * 2;
                    break;
                //Black magic doubles the attack of the Mage, but uses up 10 hp (mage has the highest base atk).
                case 2:
                    attackerDamage = attackerDamage * 2;
                    attackerSpecs[1] = attackerSpecs[1] - 10;
                    break;
                //Gambling picks a 50/50, if 1 it doubles the luck of the Rogue, if 2 it decreases lck to 10.
                case 3:
                    int gamblingResult = (int) Math.floor(Math.random() * (2 - 1 + 1) + 1);
                    if (gamblingResult == 1)
                        attackerSpecs[2] = attackerSpecs[2] * 2;
                    else
                        attackerSpecs[2] = 10;
                    break;
                case 4:
                    attackerSpecs[1] = attackerSpecs[1] + 20;
                    break; //Heal heals 20 hp for the Healer
            }
        }
        //Log output for the damage dealt, and addition to the statistic for attacks performed.
        System.out.println("    >" + attacker.getName() + " attacks: deals " + attackerDamage + " damage.");
        attacker.getStats().getStatistic("AttacksPerformed").addValue(1);
        //Health of the attacked adventurer is decreased, new health value is printed.
        otherSpecs[1] = otherSpecs[1] - attackerDamage;
        System.out.println("    >" + other.getName() + " health is now " + otherSpecs[1]);
        //If at the end of this the attacked adventurer is dead, signal that it is dead using makeDead.
        if (checkDead(otherSpecs[1])) {
            other.makeDead();
            //Output the mood of the winner after the battle using the moodState object and methods.
            MoodState moodState = determineMoodState(attackerSpecs);
            attacker.setCurrentMoodState(moodState);
        }
    }

    /**
     * Method to decide if unique skill is initiated, 50/50 change between 1 and 2.
     */
    public boolean initiateUniqueSkill() {
        int random_int = (int) Math.floor(Math.random() * (2 - 1 + 1) + 1);
        return random_int == 2;
    }

    /**
     * Method to check if an adventurer is dead if it's hp is equal to or below 0.
     *
     * @param health The health of the adventure being checked.
     */
    public boolean checkDead(int health) {
        return health <= 0;
    }

    /**
     * Method to determine the mood state of an adventure.
     *
     * @param advSpecs The array of specs for the adventurer being examined.
     */
    private MoodState determineMoodState(int[] advSpecs) {
        //If the adventurer's hp is above 40, the adventurer is happy.
        if (advSpecs[1] > 40) {
            return new HappyMoodState();
        }
        //If the adventurer's hp is below 40, the adventurer is angry.
        else if (advSpecs[1] <= 39 && advSpecs[1] > 0) {
            return new AngryMoodState();
        }
        //if the adventurer is dead, they are neutral.
        else {
            return new NeutralMoodState();
        }
    }

    /**
     * Method that outputs the list of surviving adventurers at the end of the simulation.
     */
    public void survivingUnits() {
        int unitCount = 0;
        for (Adventurer adventurer : adventurers) {
            if (adventurer.isAlive()) {
                System.out.println(adventurer.getName() + " is alive at the end of the battle");
                unitCount++;
            }
        }
        //If only one adventurer is left, this adventurer has won the battle.
        if (unitCount == 1)
            System.out.println("This unit won the battle!");
    }

    /**
     * Getter for the x size of the grid.
     */
    public int getX() {
        return x;
    }

    /**
     * Getter for the y size of the grid.
     */
    public int getY() {
        return y;
    }
}
