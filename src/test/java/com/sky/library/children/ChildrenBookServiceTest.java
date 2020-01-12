package com.sky.library.children;

import static com.sky.library.children.ChildrenBookService.SUMMARY_FORMAT;
import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

import com.sky.library.Book;
import com.sky.library.BookNotFoundException;
import com.sky.library.BookRepository;
import com.sky.library.BookService;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.converters.Nullable;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

@RunWith(JUnitParamsRunner.class)
public class ChildrenBookServiceTest {

    public static final String REFERENCE_PREFIX = "BOOK-";

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private BookRepository bookRepository;

    private BookService classUnderTest;

    @Before
    public void setup() {
        doReturn(REFERENCE_PREFIX).when(bookRepository).getReferencePrefix();

        classUnderTest = new ChildrenBookService(bookRepository);
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters(method = "invalidReferences")
    public void retrieveBookShouldThrowExceptionWhenReferencePrefixIsWrong(@Nullable String invalidReference) throws BookNotFoundException {
        classUnderTest.retrieveBook(invalidReference);
    }

    @Test(expected = BookNotFoundException.class)
    public void retrieveBookShouldThrowExceptionWhenNoBookIsFound() throws BookNotFoundException {
        doReturn(null).when(bookRepository).retrieveBook(anyString());

        classUnderTest.retrieveBook(REFERENCE_PREFIX + "999");
    }

    @Test
    public void retrieveBookShouldReturnBookFromRepository() throws BookNotFoundException {
        String bookReference = REFERENCE_PREFIX + "999";
        Book returnedBook = new Book(bookReference, "title", "review");
        doReturn(returnedBook).when(bookRepository).retrieveBook(eq(bookReference));

        Book result = classUnderTest.retrieveBook(bookReference);

        assertThat(result, is(returnedBook));
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters(method = "invalidReferences")
    public void getSummaryShouldThrowExceptionWhenReferencePrefixIsWrong(@Nullable String invalidReference) throws BookNotFoundException {
        classUnderTest.getBookSummary(invalidReference);
    }

    @Test(expected = BookNotFoundException.class)
    public void getSummaryShouldThrowExceptionWhenNoBookIsFound() throws BookNotFoundException {
        doReturn(null).when(bookRepository).retrieveBook(anyString());

        classUnderTest.getBookSummary(REFERENCE_PREFIX + "999");
    }

    @Test
    @Parameters(method = "books")
    public void getSummaryShouldReturnBookSummaryWithWholeReviewIfLessThanTenWords(Book book, String expectedReview) throws BookNotFoundException {
        doReturn(book).when(bookRepository).retrieveBook(eq(book.getReference()));

        String result = classUnderTest.getBookSummary(book.getReference());

        assertThat(result, is(format(SUMMARY_FORMAT, book.getReference(), book.getTitle(), expectedReview)));
    }

    private Object[] invalidReferences() {
        return new Object[] {
                new Object[] {"INVALID-TEXT"},
                new Object[] {""},
                new Object[] {"null"},
        };
    }

    private Object[] books() {
        return new Object[] {
                new Object[] {
                        new Book(REFERENCE_PREFIX + "GF1", "Gravity Falls", "This is a review equal to nine words, yes."),
                        "This is a review equal to nine words, yes."},

                new Object[] {
                        new Book(REFERENCE_PREFIX + "SvtFoE", "Star VS the Forces of Evil", "An alien princess from another dimension comes as an exchange student in Earth and hijinks occur."),
                        "An alien princess from another dimension comes as an..."
                },

                new Object[] {
                        new Book(REFERENCE_PREFIX + "AdTi", "Adventure Time", "The last human, Finn, his magical talking dog, Jake, their friend princess Bubblegum..."),
                        "The last human, Finn, his magical talking dog, Jake..."
                },

                new Object[] {
                        new Book(REFERENCE_PREFIX + "AdTi", "Adventure Time", "The last human, Finn, his magical talking dog, Jake; their friend princess Bubblegum..."),
                        "The last human, Finn, his magical talking dog, Jake..."
                },

                new Object[] {
                        new Book(REFERENCE_PREFIX + "AdTi", "Adventure Time", "The last human, Finn, his magical talking dog, Jake- their friend princess Bubblegum..."),
                        "The last human, Finn, his magical talking dog, Jake..."
                },

                new Object[] {
                        new Book(REFERENCE_PREFIX + "AdTi", "Adventure Time", "The last human, Finn, his magical talking dog, Jake? their friend princess Bubblegum..."),
                        "The last human, Finn, his magical talking dog, Jake..."
                },

                new Object[] {
                        new Book(REFERENCE_PREFIX + "AdTi", "Adventure Time", "The last human, Finn, his magical talking dog, Jake. their friend princess Bubblegum..."),
                        "The last human, Finn, his magical talking dog, Jake..."
                }
        };
    }
}