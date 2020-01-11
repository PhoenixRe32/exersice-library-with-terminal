package com.sky.library;

import com.sky.library.children.ChildrenBookRepository;
import com.sky.library.children.ChildrenBookService;
import com.sky.library.client.Console;

public final class Application {

    private Application() {
        // don't allow instantiation
    }

    public static void main(String[] args) {
        BookRepository bookRepository = new ChildrenBookRepository();
        BookService bookService = new ChildrenBookService(bookRepository);
        Console console = new Console(bookService);

        console.start();
    }
}
