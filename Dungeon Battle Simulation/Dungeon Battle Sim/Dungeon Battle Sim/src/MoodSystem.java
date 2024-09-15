/**
 * Name: Jonathan Boyle
 * Date: April 25th 2024
 * Description: The file containing the MoodState interface, and inner classes for the action methods for each mood.
 * This file implements the State Design pattern, as each mood is a state for a given adventurer object.
 */

/**
 * The mood state interface that defines the mood action method.
 */
interface MoodState {
    void moodAction(Adventurer adventurer) throws InterruptedException;
}

/**
 * The class that defines the concrete state for the happy mood, with mood action for the adventurer.
 */
class HappyMoodState implements MoodState {
    public void moodAction(Adventurer adventurer) throws InterruptedException {
        adventurer.performHappyAction();
    }
}

/**
 * The class that defines the concrete state for the angry mood, with mood action for the adventurer.
 */
class AngryMoodState implements MoodState {
    public void moodAction(Adventurer adventurer) throws InterruptedException {
        adventurer.performAngryAction();
    }
}

/**
 * The class that defines the concrete state for the neutral mood, with mood action for the adventurer.
 */
class NeutralMoodState implements MoodState {
    public void moodAction(Adventurer adventurer) throws InterruptedException {
        adventurer.performNeutralAction();
    }
}