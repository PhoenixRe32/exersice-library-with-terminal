package com.sky.library.client;

import static com.sky.library.client.Console.BOOK;
import static com.sky.library.client.Console.SUMMARY;
import static org.mockito.Mockito.verify;

import com.sky.library.BookService;
import com.sky.library.children.BookNotFoundException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class ConsoleTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private BookService bookService;

    private Console classUnderTest;

    @Before
    public void setup() {
        classUnderTest = new Console(bookService);
    }

    @Test
    public void shouldCallServiceWhenABookIsRequested() throws BookNotFoundException {
        String argument = "some-book-ref";
        classUnderTest.executeCommand(BOOK, argument);

        verify(bookService).retrieveBook(argument);
    }

    @Test
    public void shouldCallServiceWhenASummaryOfABookIsRequested() throws BookNotFoundException {
        String argument = "some-book-ref";
        classUnderTest.executeCommand(SUMMARY, argument);

        verify(bookService).getBookSummary(argument);
    }
}