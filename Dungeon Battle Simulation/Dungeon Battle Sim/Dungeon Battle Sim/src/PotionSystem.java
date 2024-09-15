/**
 * Name: Jonathan Boyle
 * Date: April 25th 2024
 * Description: The file containing the PotionHandler interface, with inner classes for the handlers and builder.
 * This file implements the Chain of Responsibility Design pattern, as the potion creating and run through
 * multiple handlers that fill it with different ingredients it is completed.
 */

/**
 * The potion handler interface that defines the methods used by the individual handler classes.
 */
interface PotionHandler {
    String handleIngredient(); //Insert an ingredient into the potion.

    void setNextHandler(PotionHandler handler); //Go to the next handler.
}

/**
 * The class that defines the concrete handler for the first ingredient.
 */
class IngredientHandlerOne implements PotionHandler {
    PotionHandler nextHandler; //Handler object that holds the next handler object.

    /**
     * Method to pick a random ingredient between three fruits.
     */
    public String handleIngredient() {
        int ingredientChoice = (int) Math.floor(Math.random() * (3 - 1 + 1) + 1);
        return switch (ingredientChoice) {
            case 1 -> "Raspberry";
            case 2 -> "Blueberry";
            case 3 -> "Strawberry";
            default -> null;
        };
    }

    /**
     * Setter for the next handler.
     *
     * @param handler The next handler in the chain.
     */
    public void setNextHandler(PotionHandler handler) {
        this.nextHandler = handler;
    }
}

/**
 * The class that defines the concrete handler for the second ingredient.
 */
class IngredientHandlerTwo implements PotionHandler {
    PotionHandler nextHandler; //Handler object that holds the next handler object.

    /**
     * Method to pick a random ingredient between three vegetables.
     */
    public String handleIngredient() {
        int ingredientChoiceTwo = (int) Math.floor(Math.random() * (3 - 1 + 1) + 1);
        return switch (ingredientChoiceTwo) {
            case 1 -> "Lettuce";
            case 2 -> "Carrot";
            case 3 -> "Potato";
            default -> null;
        };
    }

    /**
     * Setter for the next handler.
     *
     * @param handler The next handler in the chain.
     */
    public void setNextHandler(PotionHandler handler) {
        this.nextHandler = handler;
    }
}

/**
 * The class that defines the concrete handler for the third ingredient.
 */
class IngredientHandlerThree implements PotionHandler {
    private PotionHandler nextHandler; //Handler object that holds the next handler object.

    /**
     * Method to pick a random ingredient between three proteins.
     */
    public String handleIngredient() {
        int ingredientChoiceThree = (int) Math.floor(Math.random() * (3 - 1 + 1) + 1);
        return switch (ingredientChoiceThree) {
            case 1 -> "Steak";
            case 2 -> "Chicken";
            case 3 -> "Fish";
            default -> null;
        };
    }

    /**
     * Setter for the next handler.
     *
     * @param handler The next handler in the chain.
     */
    public void setNextHandler(PotionHandler handler) {
        this.nextHandler = handler;
    }
}

/**
 * Potion builder class that calls the chain in order to create a potion.
 */
class PotionBuilder {
    private PotionHandler firstHandler; //The object for the first handler in the chain.

    /**
     * Method to build the potions using the chain of handlers.
     */
    public PotionBuilder() {
        //Initialize the chain of handlers.
        PotionHandler handlerOne = new IngredientHandlerOne();
        PotionHandler handlerTwo = new IngredientHandlerTwo();
        PotionHandler handlerThree = new IngredientHandlerThree();

        //Set the next handlers to create the chain.
        handlerOne.setNextHandler(handlerTwo);
        handlerTwo.setNextHandler(handlerThree);

        //Define the first handler in the chain.
        firstHandler = handlerOne;
    }

    /**
     * The method to traverse the chain in order to output the String of the potion with ingredients.
     */
    public String createPotion() {
        return firstHandler.handleIngredient() + " + " +
                ((IngredientHandlerOne) firstHandler).nextHandler.handleIngredient() + " + " +
                ((IngredientHandlerTwo) ((IngredientHandlerOne) firstHandler).nextHandler).nextHandler.handleIngredient();
    }
}