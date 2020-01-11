package com.sky.library.children;

/*
 * Copyright © 2015 Sky plc All Rights reserved.
 * Please do not make your solution publicly available in any way e.g. post in forums or commit to GitHub.
 */

public class BookNotFoundException extends Exception {
    private static final long serialVersionUID = 5631382140822892472L;

    public BookNotFoundException(String message) {
        super(message);
    }
}
