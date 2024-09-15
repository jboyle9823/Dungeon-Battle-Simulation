/**
 * Name: Jonathan Boyle
 * Date: April 25th 2024
 * Description: Adventurer class that extends the Unit class for runnable.
 * Is the superclass for all the different adventurer subclasses, defines general methods and variables.
 * Implementation of Template Design Pattern with the method uniqueSkill.
 */

import Skeleton.SimulationInput;
import Skeleton.Unit;
import Skeleton.WorkerStatistic;

class Adventurer extends Unit {
    private int timesMoved = 0; //Count for the amount of times moved for the adventurer.
    private int x; //Starting x-coordinate for the adventurer
    private int y; //Starting y-coordinate for the adventurer.
    private int attackVal; //Attack value of the unit;
    private int healthVal; //Health value of the unit;
    private int luckVal; //Luck value for damage bonus of the unit;
    private boolean currentlyAlive; //Boolean that shows whether the adventurer is alive.
    private MoodState currentMoodState; //Moodstate object to show the mood of the adventurer.

    /**
     * General constructor for the Adventurer object with name, atk, hp, lck and input.
     * All subclasses will inherit from this superclass constructor
     *
     * @param name  The String name for the adventurer.
     * @param atk   The int attack value for the adventurer.
     * @param hp    The int hp value for the adventurer.
     * @param lck   The int luck value for the adventurer.
     * @param input The simulation input object for the adventurer object.
     */
    public Adventurer(String name, int atk, int hp, int lck, SimulationInput input) {
        super(name, input);
        this.attackVal = atk;
        this.healthVal = hp;
        this.luckVal = lck;
        this.currentlyAlive = true;
        //Statistics calls to track the moves and attacks performed.
        this.getStats().addStatistic(
                "MovesPerformed",
                new WorkerStatistic("MovesPerformed")
        );
        this.getStats().addStatistic(
                "AttacksPerformed",
                new WorkerStatistic("AttacksPerformed")
        );
    }

    /**
     * SimulationInput Constructor for the Adventurer object with input.
     * All subclasses will inherit from this superclass constructor.
     *
     * @param input The simulation input object for the adventurer object.
     */
    public Adventurer(SimulationInput input) {
        super("DefaultAdventurerName", input);
    }

    /**
     * performAction method for the adventurer.
     * If the adventurer is alive the object performs a move via the battlegrid.
     * if the adventurer is dead it is moved off the grid to avoid conflict with alive units.
     *
     * @throws InterruptedException
     */
    public void performAction() throws InterruptedException {
        if (currentlyAlive)
            BattleGrid.getInstance().simulateMovement(this);
        else {
            this.setX(99);
            this.setY(99);
        }
    }

    /**
     * General method for uniqueSkill, follows Template Pattern.
     */
    public int uniqueSkill() {
        return 0;
    }

    ;

    /**
     * Submit the number of actions performed (always 1).
     * <p>
     * Use submitStatisics for things you want to submit AFTER the action is complete
     * (this could be for things involving combination metrics, or conditional metrics).
     **/
    public void submitStatistics() {
    }

    /**
     * Method that returns if the adventurer is alive.
     */
    public boolean isAlive() {
        return this.currentlyAlive;
    }

    /**
     * Method that makes the adventurer dead by making currentlyAlive to false.
     */
    public void makeDead() {
        this.currentlyAlive = false;
    }

    /**
     * Method that sets the mood state of the adventurer and then calls the associated mood action.
     *
     * @param moodState The mood state object being set onto the adventurer.
     * @throws InterruptedException
     */
    public void setCurrentMoodState(MoodState moodState) throws InterruptedException {
        this.currentMoodState = moodState;
        this.currentMoodState.moodAction(this);
    }

    /**
     * The method for the happy mood action, outputs text.
     *
     * @throws InterruptedException
     */
    public void performHappyAction() throws InterruptedException {
        System.out.println("    >The winning unit is happy");
    }

    /**
     * The method for the angry mood action, outputs text.
     *
     * @throws InterruptedException
     */
    public void performAngryAction() throws InterruptedException {
        System.out.println("    >The winning unit is angry");
    }

    /**
     * The method for the neutral mood action, outputs text.
     *
     * @throws InterruptedException
     */
    public void performNeutralAction() throws InterruptedException {
        System.out.println("    >This unit is dead (no emotion).");
    }

    /**
     * Getter for the attack value of the adventurer.
     */
    public int getAttack() {
        return this.attackVal;
    }

    /**
     * Getter for the health value of the adventurer.
     */
    public int getHealth() {
        return this.healthVal;
    }

    /**
     * Getter for the luck value of the adventurer.
     */
    public int getLuck() {
        return this.luckVal;
    }

    /**
     * Getter for the array of specs for the adventurer.
     */
    public int[] getSpecs() {
        int[] specs = new int[3];
        specs[0] = this.getAttack();
        specs[1] = this.getHealth();
        specs[2] = this.getLuck();
        return specs;
    }

    /**
     * Getter for the x-coordinate of the adventurer.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Getter for the y-coordinate of the adventurer.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Setter for the x-coordinate of the adventurer.
     *
     * @param x The x position to be moved to.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Setter for the y-coordinate of the adventurer.
     *
     * @param y The y position to be moved to.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Method to increment the x position of the adventurer.
     */
    public void incrementX() {
        if (this.x < BattleGrid.getInstance().getX()) {
            this.x++;
        }
    }

    /**
     * Method to decrement the x position of the adventurer.
     */
    public void decrementX() {
        if (this.x > 0) {
            this.x--;
        }
    }

    /**
     * Method to increment the y position of the adventurer.
     */
    public void incrementY() {
        if (this.y < BattleGrid.getInstance().getY()) {
            this.y++;
        }
    }

    /**
     * Method to decrement the y position of the adventurer.
     */
    public void decrementY() {
        if (this.y > 0) {
            this.y--;
        }
    }

    /**
     * Getter for the amount of times moved by the adventurer.
     */
    public int getTimesMoved() {
        return this.timesMoved;
    }

    /**
     * Method that increments the number of times moved by the adventurer.
     */
    public void incrementTimesMoved() {
        this.timesMoved++;
    }
}