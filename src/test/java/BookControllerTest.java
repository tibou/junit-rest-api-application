import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rest.app.Book;
import com.rest.app.BookController;
import com.rest.app.BookRepository;
import com.rest.app.BookService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MockMvcBuilderSupport;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    Book book1 = new Book(1L, "name 1", "summary 1", 1);
    Book book2 = new Book(2L, "name 2", "summary 2", 2);
    Book book3 = new Book(3L, "name 3", "summary 3", 3);


    @BeforeEach
    public void init() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void getAllBooks_success() throws Exception {
        List<Book> books = Arrays.asList(book1, book2, book3);

        Mockito.when(bookService.getAll()).thenReturn(books);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name", Matchers.is("name 3")));
    }

    @Test
    public void getBookById_success() throws Exception {
        Mockito.when(bookService.getById(book1.getBookId())).thenReturn(book1);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/books/" + book1.getBookId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("name 1")));
    }

    @Test
    public void createBook_success() throws Exception {
        Book book = Book.builder()
                .bookId(4L)
                .name("Name 4")
                .summary("Summary 4")
                .rating(4)
                .build();
        Mockito.when(bookService.save(book)).thenReturn(book);

        String content = objectWriter.writeValueAsString(book);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Name 4")));

    }

    @Test
    public void updateBook_success() throws Exception {
        Book updatedBook = Book.builder()
                .bookId(1L)
                .name("Updated name 1")
                .summary("Updated summary 1")
                .rating(1)
                .build();

        Mockito.when(bookService.update(updatedBook)).thenReturn(updatedBook);

        String updatedContent = objectWriter.writeValueAsString(updatedBook);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updatedContent);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Updated name 1")));

    }

    @Test
    public void deleteBookById_success() throws Exception {
        //Mockito.when(bookService.delete(book1.getBookId())).thenReturn(null);
        Mockito.doAnswer((i)-> {
            System.out.println("Argument: " + i.getArgument(0));
            return null;
        }).when(bookService).delete(Mockito.anyLong());

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/books/" + book1.getBookId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }


}
