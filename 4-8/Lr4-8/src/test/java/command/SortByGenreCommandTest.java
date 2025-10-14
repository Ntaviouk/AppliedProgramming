package command;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.AlbumService;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SortByGenreCommandTest {

    @Mock
    private AlbumService mockService;

    @InjectMocks
    private SortByGenreCommand sortCommand;

    @Test
    void execute_whenServiceExists_shouldCallSortByGenre() {

        boolean result = sortCommand.execute();

        assertTrue(result);
        verify(mockService, times(1)).sortByGenre();
        verifyNoMoreInteractions(mockService);
    }

    @Test
    void execute_whenServiceIsNull_shouldReturnTrueAndNotCrash() {
        SortByGenreCommand commandWithNullService = new SortByGenreCommand(null);

        boolean result = commandWithNullService.execute();

        assertTrue(result);
    }
}