import com.rest.app.Book;
import com.rest.app.BookRepository;
import com.rest.app.BookService;
import com.rest.app.RessourceNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookService bookService;
    static Book book1, book2, book3;
    //AutoCloseable closeable;


    @BeforeAll
    public static void setUp() {
        book1 = Book.builder()
                .bookId(1L)
                .name("name 1")
                .summary("summary 1")
                .rating(1)
                .build();

        book2 = Book.builder()
                .bookId(2L)
                .name("name 2")
                .summary("summary 2")
                .rating(2)
                .build();

        book3 = Book.builder()
                .bookId(3L)
                .name("name 3")
                .summary("summary 3")
                .rating(3)
                .build();
    }

    @BeforeEach
    public void init() {
        //bookRepository = Mockito.mock(BookRepository.class);
        //closeable = MockitoAnnotations.openMocks(this);
        //bookService = new BookService(bookRepository);
    }

    @AfterEach
    public void close() throws Exception {
        //closeable.close();
    }


    @Test
    public void getAll_success() {
        List<Book> books = Arrays.asList(book1, book2, book3);

        Mockito.when(bookRepository.findAll()).thenReturn(books);
        List<Book> expectedBooks = bookService.getAll();

        Assertions.assertEquals(3, expectedBooks.size());
        Assertions.assertEquals("name 1", expectedBooks.get(0).getName());
        Mockito.verify(bookRepository).findAll();
        Mockito.verify(bookRepository, VerificationModeFactory.times(1))
                .findAll();
    }

    @Test
    public void getById_success() {

        Mockito.when(bookRepository.findById(book1.getBookId())).thenReturn(Optional.of(book1));
        Book expectedBook = bookService.getById(book1.getBookId());

        Assertions.assertEquals("name 1", book1.getName());
        Mockito.verify(bookRepository).findById(book1.getBookId());
        Mockito.verify(bookRepository, VerificationModeFactory.times(1))
                .findById(book1.getBookId());
    }

    @Test
    public void getById_thowRessourceNotFoundExecption() {
        Mockito.when(bookRepository.findById(book1.getBookId())).thenReturn(Optional.ofNullable(null));

        RessourceNotFoundException ex = Assertions.assertThrows(RessourceNotFoundException.class, () -> {
            bookService.getById(book1.getBookId());
        });

        String expectedMessage = "The Book with ID: " + book1.getBookId() + " does not exist";

        Assertions.assertEquals(expectedMessage, ex.getMessage());
    }

    @Test
    public void save_success() {
        Mockito.when(bookRepository.save(book1)).thenReturn(book1);
        Book expectedBook = bookService.save(book1);
        Assertions.assertEquals("name 1", expectedBook.getName());
    }

    @Test
    public void delete_success() {
        Mockito.when(bookRepository.findById(book1.getBookId())).thenReturn(Optional.of(book1));
        Mockito.doAnswer((i) -> {
            return null;
        }).when(bookRepository).delete(book1);

        bookService.delete(book1.getBookId());
    }

    @Test
    public void update_success() {
        Book updatedBook = Book.builder()
                .bookId(1L)
                .name("name 1 --updated")
                .summary("summary 2 --updated")
                .rating(1)
                .build();

        Mockito.when(bookRepository.findById(book1.getBookId()))
                .thenReturn(Optional.of(updatedBook));
        Mockito.when(bookRepository.save(updatedBook))
                .thenReturn(updatedBook);
        Book expectedBook = bookService.update(updatedBook);

        Assertions.assertEquals(expectedBook.getName(), updatedBook.getName());
    }

    @Test
    public void update_whenNull_thowException() {
        RessourceNotFoundException ex = Assertions.assertThrows(RessourceNotFoundException.class, () -> {
            bookService.update(null);
        });

        String expected = "Book or id must not be null";

        Assertions.assertEquals(expected, ex.getMessage());
    }


}
