package command;

import model.Artist;
import model.Genre;
import model.Track;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.AlbumService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindByDurationCommandTest {

    @Mock
    private AlbumService mockService;

    @Test
    void execute_whenTracksFound_shouldCallServiceAndPrint() {
        int min = 100, max = 200;
        FindByDurationCommand command = new FindByDurationCommand(mockService, min, max);
        List<Track> foundTracks = List.of(new Track("Test Track", new Artist("Test", "Test"), Genre.OTHER, 150));

        when(mockService.findByDurationRange(min, max)).thenReturn(foundTracks);

        boolean result = command.execute();

        assertTrue(result);
        verify(mockService, times(1)).findByDurationRange(min, max);
    }

    @Test
    void execute_whenNoTracksFound_shouldCallService() {
        int min = 100, max = 200;
        FindByDurationCommand command = new FindByDurationCommand(mockService, min, max);

        when(mockService.findByDurationRange(min, max)).thenReturn(Collections.emptyList());

        boolean result = command.execute();

        assertTrue(result);
        verify(mockService, times(1)).findByDurationRange(min, max);
    }
}