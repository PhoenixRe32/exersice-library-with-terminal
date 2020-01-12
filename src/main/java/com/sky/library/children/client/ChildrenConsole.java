package com.sky.library.children.client;

import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.util.Scanner;
import java.util.regex.Pattern;

import com.sky.library.BookService;
import com.sky.library.BookNotFoundException;

public class ChildrenConsole {

    private static final Pattern SPACES = Pattern.compile("\\s+");
    static final String BOOK = "book";
    static final String SUMMARY = "summ";
    static final String HELP = "help";
    static final String EXIT = "exit";
    private static final String PROMPT = "> What do you want to do?";
    private static final String HELP_TEXT =
            "Welcome to the children library of Ankh-Morpork"
                    + System.lineSeparator()
                    + "---------------------------------"
                    + System.lineSeparator()
                    + "You have the following commands available:"
                    + System.lineSeparator()
                    + "* `" + BOOK + " BOOK-REF`\t Retrieve the book for the reference you provided, a book reference must start with `BOOK-`"
                    + System.lineSeparator()
                    + "* `" + SUMMARY + " BOOK-REF`\t Retrieve the summary for the " + BOOK + " specified by the reference you provided"
                    + System.lineSeparator()
                    + "* `" + HELP + "`\t Print this text"
                    + System.lineSeparator()
                    + "* `" + EXIT + "`\t Terminate the application"
                    + System.lineSeparator();

    private final Scanner scanner;
    private final BookService bookService;

    public ChildrenConsole(BookService bookService) {
        this.bookService = bookService;
        scanner = new Scanner(System.in, UTF_8);
    }

    public void start() {
        printHelp();
        String latestCommand = null;
        while (!EXIT.equalsIgnoreCase(latestCommand)) {
            String[] input = getInput();

            latestCommand = input[0];
            String argument = input.length > 1 ? input[1] : null;

            try {
                executeCommand(latestCommand, argument);
            }
            catch (BookNotFoundException | IllegalArgumentException e) {
                System.out.println(format("Error: %s", e.getMessage()));
            }
        }

        System.out.println("BYE!");
    }

    // Visible for testing
    private String[] getInput() {
        System.out.println(PROMPT);

        String input = scanner.nextLine();
        return SPACES.split(input);
    }

    void executeCommand(String latestCommand, String argument) throws BookNotFoundException {
        if (EXIT.equalsIgnoreCase(latestCommand)) {
            // do nothing
        } else if (HELP.equalsIgnoreCase(latestCommand)) {
            printHelp();
        } else if (BOOK.equalsIgnoreCase(latestCommand)) {
            System.out.println(bookService.retrieveBook(argument));
        } else if (SUMMARY.equalsIgnoreCase(latestCommand)) {
            System.out.println(bookService.getBookSummary(argument));
        } else {
            System.out.println("Invalid command...");
        }
    }

    private void printHelp() {
        System.out.println(HELP_TEXT);
    }
}
