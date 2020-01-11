# Library

A small command line aplication for a library.

## Assumptions

I assume that the database data is validated and that no books field have null values.
Title, reference and review are not allowed ot be null.
I also assume that the data was sanitised before it was entered in the database.
For instance, the strings were trimmed and there are no leading and trailing spaces.
(To be fair, I would just need to add a trim step where I used them.)

## Build and run

The application was developed in IntelliJ IDEA Ultimate 2019.3
It uses gradle wrapper 5 and Java 11.
I use the following libraries: 
* JUnit4 for testing
* Lombok for syntactic sugar
* JUnitParams for parameterised tests
* Mockito for mocking

With IntelliJ, you have to enable annotations for Lombok 
`Preferences > Build, Exection, Deployment > Compiler > Annotations Processors`

You can import it to IntelliJ using the `build.gradle` file.

### How to run tests

The `ChildrenBookServiceTest` is the class I wrote while developing the logic. (TDD)

The `ChildrenBookServiceStubTest` is the class I wrote after finishing my development to test the acceptance criteria in the instructions.

To run the tests navigate to the root of the project and run `./gradlew clean test -i`
Or use the IDE; with IntelliJ you right click on the test folder and choose run tests.

I logged some descriptions for the `ChildrenBookServiceStubTest` where I tested the criteria from the instructions.
You can see those in the console.
There is also a generated report in the `build > reports > tests > test` folder.
If you open that in the browser you can see the stats and also choose to see the standard output to see the logs.

### How to build

To build navigate to the root of the project and run `./gradlew clean build`

### How to run

After you have built it eitehr
* run `./gradlew run` (though there will be noise from gradle)
* or run `java -jar ./build/libs/sky-test-lirary-1.0-SNAPSHOT.jar`
* or use the run task from the IntelliJ Gradle side panel

You can use 4 commands:

- `book BOOK-REFERENCE`, will retrieve a book
- `summ BOOK-REFERENCE`, will get the summary of a book
- `help` pints the help text
- `exit` terminates the console application

## Notes

- Restriction that the reference should be prefixed with `BOOK-`.

There are a few approaches to this.
I decided to go with the easiest that required the less changes in the files I was given.
That was a validation method inside the service implementation.
Depending on the framework we have available, there could be easier and less obtrusive ways.
e.g. If this was a spring boot application we could use the built in bean validation it has or even build our own.

I decided to thrown an IllegalArgument exception here which is not checked. (I admit to a personal preference.)
Obviously, team and project convention comes first.
In cases like this, I believe the validation should happen as close to the input as possible.
Since the instructions wanted the methods to ensure this I decided not to stray too much.
Since I decided to do this as a command line application, I thought about throwing a checked exception.
In a console application we wouldn't want the application to terminate if the user gave an invalid reference.
Instead we should handle it and show him a message.

In the end I decided not to change the signature and just handle the runtime exception in the console application.

- Throw a BookNotFoundException where appropriate

Again, I debated with throwing a BookNotFoundException for the above condition again.
It depends a bit on the semantics and the project I would work on.
A wrong reference means that a book will not be found.
It depends on how much information we want to retrn to our users.

Since an explicit requirement was to ensure the format of the reference, I could not lump the above with this requirement.
Another approach would be to use Optionals in the repository and not throw exception or use them.

```
return bookRepository.retrieveBook(bookReference)
                .orElseThrow(() -> new BookNotFoundException(format("Could not find a book for reference %s", bookReference)));
```

- Tests

The `ChildrenBookServiceTest` is the class I wrote while developing the logic. (TDD

The `ChildrenBookServiceStubTest` is the class I wrote after finishing my development to test the acceptance criteria in the instructions.

- Console application

After I was done with the implementaion and the tests I decided to 
create a quick and simple console application for testing the inputs
and outputs with the mock repository, which I used for the console 
application. Mainly because it's been a while since I did a command 
line app :-)
