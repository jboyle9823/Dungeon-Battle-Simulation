/**
 * Name: Jonathan Boyle
 * Date: April 25th 2024
 * Description: The class that is responsible for running the simulation.
 */

import Skeleton.SimulationInput;

import java.lang.Thread;
import java.util.ArrayList;

public class Matrix {
    /**
     * The run method for the simulation.
     *
     * @param input The simulation input object for the statistics.
     */
    public static void run(SimulationInput input) {
        //The singleton instance for the grid for the battle is created.
        BattleGrid grid = BattleGrid.getInstance();

        //The adventurers that will battle are created, along with defined specs.
        Knight knight = new Knight("Knight", 15, 100, 10, input);
        Mage mage = new Mage("Mage", 20, 50, 10, input);
        Rogue rogue = new Rogue("Rogue", 10, 60, 30, input);
        Healer healer = new Healer("Healer", 10, 70, 20, input);

        //The adventurers are added to the grid.
        grid.addAdventurer(knight, 4, 4);
        grid.addAdventurer(mage, 1, 1);
        grid.addAdventurer(rogue, 1, 4);
        grid.addAdventurer(healer, 4, 1);

        //The adventurer threads are put into an array.
        Thread[] threads = new Thread[4];
        threads[0] = new Thread(knight);
        threads[1] = new Thread(mage);
        threads[2] = new Thread(rogue);
        threads[3] = new Thread(healer);

        //The adventurer threads are started.
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
            }
        }
    }
}
