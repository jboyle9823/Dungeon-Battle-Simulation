/**
 * Name: Jonathan Boyle
 * Date: April 25th 2024
 * Description: The file containing the WeaponStrategy interface, with inner classes each weapon and the switcher class.
 * This file implements the Strategy Design pattern, as each weapon represents a new strategy for the adventurer.
 */

/**
 * The weapon strategy interface that defines the method to use the current weapon.
 */
interface WeaponStrategy {
    void useWeapon();
}

/**
 * Sword strategy class that prints the action for using the sword.
 */
class SwordStrategy implements WeaponStrategy {
    /**
     * Method that prints the weapon used.
     */
    public void useWeapon() {
        System.out.println("attacking with Sword");
    }
}

/**
 * Bow strategy class that prints the action for using the bow.
 */
class BowStrategy implements WeaponStrategy {
    /**
     * Method that prints the weapon used.
     */
    public void useWeapon() {
        System.out.println("attacking with Bow");
    }
}

/**
 * Spear strategy class that prints the action for using the spear.
 */
class SpearStrategy implements WeaponStrategy {
    /**
     * Method that prints the weapon used.
     */
    public void useWeapon() {
        System.out.println("attacking with Spear");
    }
}

/**
 * The class that allows for weapon switching.
 */
class WeaponSwitcher {
    private WeaponStrategy weaponStrategy; //Weapon strategy object to allow for the strategy.

    /**
     * Method to set the weapon strategy dynamically.
     *
     * @param weaponStrategy The new weapon strategy being implemented.
     */
    public void setWeaponStrategy(WeaponStrategy weaponStrategy) {
        this.weaponStrategy = weaponStrategy;
    }

    /**
     * Method to use the weapon based on the current strategy.
     */
    public void useWeapon() {
        if (weaponStrategy != null) {
            weaponStrategy.useWeapon();
        } else {
            //If no strategy is used, the adventurer attacks with their fists.
            System.out.println("attacking with their fists!");
        }
    }
}

