/**
 * Name: Jonathan Boyle
 * Date: April 25th 2024
 * Description: The Mage class that extends from the Adventurer class.
 * Implements the uniqueSkill method, validating the Template Pattern.
 */

import Skeleton.SimulationInput;

class Mage extends Adventurer {
    /**
     * General constructor for the Mage object with name, atk, hp, lck and input.
     *
     * @param name  The String name for the adventurer.
     * @param atk   The int attack value for the adventurer.
     * @param hp    The int hp value for the adventurer.
     * @param lck   The int luck value for the adventurer.
     * @param input The simulation input object for the adventurer object.
     */
    public Mage(String name, int atk, int hp, int lck, SimulationInput input) {
        super(name, atk, hp, lck, input);
    }

    /**
     * SimulationInput Constructor for the Adventurer object with input.
     * All subclasses will inherit from this superclass constructor.
     *
     * @param input The simulation input object for the adventurer object.
     */
    public Mage(SimulationInput input) { super(input); }

    /**
     * uniqueSkill method that signals the BattleGrid to perform the unique skill.
     */
    public int uniqueSkill() {
        System.out.println("    >Mage uses unique skill Black Magic.");
        return 2;
    }
}