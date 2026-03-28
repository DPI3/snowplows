package skeleton.src;

import java.util.Scanner;

/**
 * A central utility class responsible for formatting and logging the execution 
 * flow of the skeleton program. 
 * <p>
 * It helps visualize the sequence diagrams by automatically indenting method 
 * calls and returns based on the current call depth. 
 * It also centralizes user input handling for test decisions.
 */
public class Skeleton {

    /**
     * Tracks the current depth of method calls to provide proper indentation.
     */
    private static int depth = 0;

    /**
     * Logs the entry point of a method call.
     * Automatically increases the indentation depth for subsequent calls.
     * Output format: {@code >>> [className].methodName}
     *
     * @param className  the name of the class making the call (e.g., "Car")
     * @param methodName the name of the method being called, including parameters (e.g., "move()")
     */
    public static void printCall(String className, String methodName) {
        printIndent();
        System.out.println(">>> [" + className + "]." + methodName);
        depth++;
    }

    /**
     * Logs the return from a method call.
     * Automatically decreases the indentation depth.
     * Output format: {@code <<< return returnValue}
     *
     * @param returnValue a string representation of the returned value. 
     * If the method is void, pass an empty string ("").
     */
    public static void printReturn(String returnValue) {
        depth--;
        printIndent();
        if (returnValue == null || returnValue.isEmpty()) {
            System.out.println("<<< return");
        } else {
            System.out.println("<<< return " + returnValue);
        }
    }

    /**
     * Logs an internal state change within an object.
     * Output format: {@code [STATE] description}
     *
     * @param stateDescription a brief description of the state change (e.g., "Speed set to 0")
     */
    public static void printState(String stateDescription) {
        printIndent();
        System.out.println("[STATE] " + stateDescription);
    }

    /**
     * Prints a standardized question to the console and reads an integer 
     * answer from the user. Useful for interactive testing.
     *
     * @param scanner the Scanner object to read user input
     * @param question the question to display to the user
     * @return the integer value entered by the user
     */
    public static int requestInput(Scanner scanner, String question) {
        printIndent();
        System.out.println("Decision: " + question);
        printIndent();
        System.out.print(">> ");
        return scanner.nextInt();
    }

    /**
     * Helper method to print the correct number of spaces based on the 
     * current call depth. Prints two spaces per depth level.
     */
    private static void printIndent() {
        for (int i = 0; i < depth; i++) {
            System.out.print("  "); 
        }
    }
}