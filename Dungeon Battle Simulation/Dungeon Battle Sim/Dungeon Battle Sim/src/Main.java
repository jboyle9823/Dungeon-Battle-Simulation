/**
 * Name: Jonathan Boyle
 * Date: April 25th 2024
 * Description: The main class is responsible for the testing. It has a helper method
 * that makes it easier to run many tests.
 */

import Skeleton.SimulationInput;
import Skeleton.StatisticsContainer;

import java.util.ArrayList;
import java.util.List;

public class Main {
    /**
     * Runs a test with the given input and returns the statistics
     * produced from the test run. Simplifies the testing process.
     *
     * @param input The input to run the test with.
     * @return The statistics of the test run.
     **/
    public static StatisticsContainer runTest(SimulationInput input) {
        // Initialize the stats singleton here so the input can
        // be ignored in future calls
        StatisticsContainer stats = StatisticsContainer.getInstance(input);
        Matrix.run(input);

        return stats;
    }

    /**
     * See method above for details.
     **/
    public static StatisticsContainer runTest(ArrayList<ArrayList<String>> input) {
        return runTest(new SimulationInput(input));
    }

    public static void main(String[] args) {
        //Print statement clarifying which simulation it is in the log.
        System.out.println("Simulation 1: T -> 150s, Actions/s -> 1");
        SimulationInput si = new SimulationInput();
        si.addInput("Time", List.of("150")); // In seconds
        si.addInput("ActionsPerSecond", List.of("1"));

        // Run the simulation
        StatisticsContainer stats = runTest(si);
        //Surviving units are printed once the simulation is done.
        BattleGrid.getInstance().survivingUnits();

        // Post the finalized statistics
        stats.printStatisticsContainer();

        //Second simulation probing 0 time edge case.
        System.out.println("Simulation 2: T -> 0s, Actions/s -> 1");
        SimulationInput siTwo = new SimulationInput();
        si.addInput("Time", List.of("20")); // In seconds
        si.addInput("ActionsPerSecond", List.of("0"));

        // Run the simulation
        StatisticsContainer statsTwo = runTest(siTwo);
        BattleGrid.getInstance().survivingUnits();

        // Post the finalized statistics
        statsTwo.printStatisticsContainer();

        //Third simulation probing 0 actions/seconds edge case.
        System.out.println("Simulation 3: T -> 20s, Actions/s -> 0");
        SimulationInput siThree = new SimulationInput();
        si.addInput("Time", List.of("20")); // In seconds
        si.addInput("ActionsPerSecond", List.of("0"));

        // Run the simulation
        StatisticsContainer statsThree = runTest(siThree);
        BattleGrid.getInstance().survivingUnits();

        // Post the finalized statistics
        statsThree.printStatisticsContainer();
    }
}
