package com.sky.library.children;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;

import java.util.logging.Logger;

import com.sky.library.Book;
import com.sky.library.BookService;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class ChildrenBookServiceStubTest {

    private static final Logger LOGGER = Logger.getLogger(ChildrenBookServiceStubTest.class.getName());

    private BookService bookService;

    @Before
    public void setup() {
        bookService = new ChildrenBookService(new BookRepositoryStub());
    }

    @Test
    @Parameters(method = "retrieveBookAcceptanceCriteria")
    public void testRetrieveBook(String ref, Class<Exception> expectedThrowable, String title) throws BookNotFoundException {
        if (expectedThrowable == null) {
            Book result = bookService.retrieveBook(ref);
            assertThat(result.getTitle(), is(title));
            LOGGER.info(format("retrieveBook( %s ) returned: %s", ref, result));
        } else {
            Exception e = assertThrows(expectedThrowable, () -> bookService.retrieveBook(ref));
            LOGGER.info(format("retrieveBook( %s ) threw: %s", ref, e));
        }

    }

    private Object[] retrieveBookAcceptanceCriteria() {
        return new Object[] {
                new Object[] {"INVALID-TEXT", IllegalArgumentException.class, null},
                new Object[] {"BOOK-999", BookNotFoundException.class, null},
                new Object[] {"BOOK-GRUFF472", null, "The Gruffalo"},
        };
    }

    @Test
    @Parameters(method = "getBookSummaryAcceptanceCriteria")
    public void testGetBookSummary(String ref, Class<Exception> expectedThrowable, String expectedResult) throws BookNotFoundException {
        if (expectedThrowable == null) {
            String result = bookService.getBookSummary(ref);
            assertThat(result, is(expectedResult));
            LOGGER.info(format("getBookSummary( %s ) returned: %s", ref, result));
        } else {
            Exception e = assertThrows(expectedThrowable, () -> bookService.getBookSummary(ref));
            LOGGER.info(format("getBookSummary( %s ) threw: %s", ref, e));
        }

    }

    private Object[] getBookSummaryAcceptanceCriteria() {
        return new Object[] {
                new Object[] {"INVALID-TEXT", IllegalArgumentException.class, null},
                new Object[] {"BOOK-999", BookNotFoundException.class, null},
                new Object[] {"BOOK-GRUFF472", null, "[BOOK-GRUFF472] The Gruffalo - A mouse taking a walk in the woods."},
                new Object[] {"BOOK-POOH222", null, "[BOOK-POOH222] Winnie The Pooh - In this first volume, we meet all the friends..."},
                new Object[] {"BOOK-WILL987", null, "[BOOK-WILL987] The Wind In The Willows - With the arrival of spring and fine weather outside..."},
        };
    }
}
