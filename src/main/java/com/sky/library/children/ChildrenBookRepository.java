package com.sky.library.children;

/*
 * Copyright © 2015 Sky plc All Rights reserved.
 * Please do not make your solution publicly available in any way e.g. post in forums or commit to GitHub.
 */

import java.util.Map;

import com.sky.library.Book;
import com.sky.library.BookRepository;

public class ChildrenBookRepository implements BookRepository {
    private static final String BOOK_REFERENCE_PREFIX = "BOOK-";

    private static final String THE_GRUFFALO_REFERENCE = BOOK_REFERENCE_PREFIX + "GRUFF472";
    private static final String WINNIE_THE_POOH_REFERENCE = BOOK_REFERENCE_PREFIX + "POOH222";
    private static final String THE_WIND_IN_THE_WILLOWS_REFERENCE = BOOK_REFERENCE_PREFIX + "WILL987";

    private static final Map<String, Book> books = Map.of(
            THE_GRUFFALO_REFERENCE, new Book(THE_GRUFFALO_REFERENCE,
                                             "The Gruffalo",
                                             "A mouse taking a walk in the woods."),

            WINNIE_THE_POOH_REFERENCE, new Book(WINNIE_THE_POOH_REFERENCE,
                                                "Winnie The Pooh",
                                                "In this first volume, we meet all the friends from the Hundred Acre Wood."),

            THE_WIND_IN_THE_WILLOWS_REFERENCE, new Book(THE_WIND_IN_THE_WILLOWS_REFERENCE,
                                                        "The Wind In The Willows",
                                                        "With the arrival of spring and fine weather outside, the " +
                                                                "good-natured Mole loses patience with spring cleaning. He " +
                                                                "flees his underground home, emerging to take in the air and " +
                                                                "ends up at the river, which he has never seen before. Here " +
                                                                "he meets Rat (a water vole), who at this time of year spends " +
                                                                "all his days in, on and close by the river. Rat takes Mole for " +
                                                                "a ride in his rowing boat. They get along well and spend many " +
                                                                "more days boating, with Rat teaching Mole the ways of the river.")
    );

    @Override
    public Book retrieveBook(String reference) {
        return books.get(reference);
    }

    @Override
    public String getReferencePrefix() {
        return BOOK_REFERENCE_PREFIX;
    }
}
