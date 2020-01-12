package com.sky;

import com.sky.library.BookRepository;
import com.sky.library.BookService;
import com.sky.library.children.ChildrenBookRepository;
import com.sky.library.children.ChildrenBookService;
import com.sky.library.children.client.ChildrenConsole;

public final class Application {

    private Application() {
        // don't allow instantiation
    }

    public static void main(String[] args) {
        BookRepository bookRepository = new ChildrenBookRepository();
        BookService bookService = new ChildrenBookService(bookRepository);
        ChildrenConsole childrenConsole = new ChildrenConsole(bookService);

        childrenConsole.start();
    }
}
