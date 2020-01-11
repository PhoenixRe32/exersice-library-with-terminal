package com.sky.library.children;

import static java.lang.String.format;
import static java.util.regex.Pattern.compile;
import static java.util.stream.Collectors.joining;

import java.util.Arrays;
import java.util.Formatter;
import java.util.regex.Pattern;

import com.sky.library.Book;
import com.sky.library.BookRepository;
import com.sky.library.BookService;

public class ChildrenBookService implements BookService {

    static final String SUMMARY_FORMAT = "[%s] %s - %s";
    private static final int SB_CAPACITY = 128;
    private static final Pattern SPACES = compile("\\s+");
    private static final Pattern PUNCTUATION = compile("\\p{Punct}");
    private static final String SPACE = " ";
    private static final String ELLIPSIS = "...";
    private static final int REVIEW_WORD_LIMIT = 9;

    private final BookRepository bookRepository;

    public ChildrenBookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book retrieveBook(String bookReference) throws BookNotFoundException {
        validateReference(bookReference);

        Book book = bookRepository.retrieveBook(bookReference);
        if (book == null) {
            throw new BookNotFoundException(format("Could not find a book for reference %s", bookReference));
        }

        return book;
    }

    private void validateReference(String bookReference) {
        if (bookReference == null || bookReference.isBlank()) {
            throw new IllegalArgumentException("The book reference should not be empty");
        }

        String referencePrefix = bookRepository.getReferencePrefix();
        if (!bookReference.startsWith(referencePrefix)) {
            throw new IllegalArgumentException(format("The book reference should start with %s", referencePrefix));
        }
    }

    @Override
    public String getBookSummary(String bookReference) throws BookNotFoundException {
        Book book = retrieveBook(bookReference);

        String summaryOfReview = getSummaryOfReview(book.getReview());

        return buildFormatter()
                .format(SUMMARY_FORMAT, book.getReference(), book.getTitle(), summaryOfReview)
                .toString();
    }

    private String getSummaryOfReview(String review) {
        String[] words = SPACES.split(review);
        return words.length <= REVIEW_WORD_LIMIT
                ? review
                : truncateReview(words);
    }

    private String truncateReview(String[] words) {
        String truncatedReview = Arrays.stream(words, 0, REVIEW_WORD_LIMIT).collect(joining(SPACE));

        if (isLastCharacterPunctuation(truncatedReview)) {
            truncatedReview = removeLastCharacter(truncatedReview);
        }
        return truncatedReview + ELLIPSIS;
    }

    private boolean isLastCharacterPunctuation(String s) {
        return PUNCTUATION.matcher(getLastCharacterAsString(s)).matches();
    }

    private String getLastCharacterAsString(String s) {
        return String.valueOf(getLastCharacter(s));
    }

    private char getLastCharacter(String s) {
        return s.charAt(s.length() - 1);
    }

    private String removeLastCharacter(String truncatedReview) {
        return truncatedReview.substring(0, truncatedReview.length() - 1);
    }

    private Formatter buildFormatter() {
        return new Formatter(new StringBuilder(SB_CAPACITY));
    }
}
