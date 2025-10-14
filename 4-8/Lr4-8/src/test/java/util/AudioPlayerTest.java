package util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AudioPlayerTest {

    // Мокуємо залежності, щоб не використовувати реальну аудіосистему
    @Mock
    private Clip mockClip;
    @Mock
    private AudioInputStream mockAudioStream;

    private MockedStatic<AudioSystem> mockedAudioSystem;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        // Перенаправляємо системний вивід, щоб перевіряти повідомлення в консолі
        System.setOut(new PrintStream(outContent));

        // Мокуємо статичні методи класу AudioSystem
        mockedAudioSystem = mockStatic(AudioSystem.class);

        // Налаштовуємо поведінку моків
        mockedAudioSystem.when(() -> AudioSystem.getAudioInputStream(any(File.class))).thenReturn(mockAudioStream);
        mockedAudioSystem.when(() -> AudioSystem.getLine(any(DataLine.Info.class))).thenReturn(mockClip);
    }

    @AfterEach
    void tearDown() {
        // Повертаємо системний вивід і закриваємо статичний мок
        System.setOut(originalOut);
        mockedAudioSystem.close();
    }

    @Test
    void playWav_whenFileExists_shouldPlayAudio(@TempDir Path tempDir) throws Exception {
        // Arrange
        File dummyFile = tempDir.resolve("test.wav").toFile();
        dummyFile.createNewFile();

        // Імітуємо поведінку кліпа: спочатку не запущений, потім запущений, потім зупинений
        when(mockClip.isRunning()).thenReturn(false, true, false);

        // Act
        AudioPlayer.playWav(dummyFile.getAbsolutePath());

        // Assert
        // Перевіряємо, що кліп був відкритий, запущений і закритий
        verify(mockClip).open(mockAudioStream);
        verify(mockClip).start();
        verify(mockClip).close();
        assertTrue(outContent.toString().contains("▶ Playing: " + dummyFile.getName()));
    }

    @Test
    void playWav_whenFileDoesNotExist_shouldPrintErrorMessage() {
        // Arrange
        String nonExistentFilePath = "nonexistent/file.wav";

        // Act
        AudioPlayer.playWav(nonExistentFilePath);

        // Assert
        // Перевіряємо, що було виведено повідомлення про помилку
        assertTrue(outContent.toString().contains("File not found: " + nonExistentFilePath));
        // Перевіряємо, що аудіосистема навіть не викликалась
        mockedAudioSystem.verify(() -> AudioSystem.getAudioInputStream(any(File.class)), never());
    }

    @Test
    void playWav_whenAudioSystemThrowsException_shouldPrintErrorMessage(@TempDir Path tempDir) throws Exception {
        // Arrange
        File dummyFile = tempDir.resolve("test.wav").toFile();
        dummyFile.createNewFile();
        String errorMessage = "Unsupported audio format";

        // Налаштовуємо мок, щоб він кидав виняток
        mockedAudioSystem.when(() -> AudioSystem.getAudioInputStream(any(File.class)))
                .thenThrow(new UnsupportedAudioFileException(errorMessage));

        // Act
        AudioPlayer.playWav(dummyFile.getAbsolutePath());

        // Assert
        assertTrue(outContent.toString().contains("Error playing audio: " + errorMessage));
    }
}