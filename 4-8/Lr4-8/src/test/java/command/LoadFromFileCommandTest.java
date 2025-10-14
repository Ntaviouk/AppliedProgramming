package command;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.AlbumService;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoadFromFileCommandTest {

    @Mock
    private AlbumService mockService;



    @Test
    void execute_shouldCallLoadFromFile() throws IOException {
        String path = "test.txt";
        LoadFromFileCommand command = new LoadFromFileCommand(mockService, path);

        boolean result = command.execute();

        assertTrue(result);
        verify(mockService, times(1)).loadFromFile(path);
    }

    @Test
    void execute_whenServiceThrowsException_shouldHandleItGracefully() throws IOException {
        String path = "nonexistent.txt";
        LoadFromFileCommand command = new LoadFromFileCommand(mockService, path);

        doThrow(new IOException("File not found")).when(mockService).loadFromFile(path);

        boolean result = command.execute();

        assertTrue(result);
        verify(mockService, times(1)).loadFromFile(path);
    }
}