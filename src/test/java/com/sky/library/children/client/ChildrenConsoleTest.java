package com.sky.library.children.client;

import static com.sky.library.children.client.ChildrenConsole.BOOK;
import static com.sky.library.children.client.ChildrenConsole.SUMMARY;
import static org.mockito.Mockito.verify;

import com.sky.library.BookService;
import com.sky.library.BookNotFoundException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class ChildrenConsoleTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private BookService bookService;

    private ChildrenConsole classUnderTest;

    @Before
    public void setup() {
        classUnderTest = new ChildrenConsole(bookService);
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