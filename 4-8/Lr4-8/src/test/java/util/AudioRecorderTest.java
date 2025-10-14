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
class AudioRecorderTest {

    @Mock
    private TargetDataLine mockLine;

    private MockedStatic<AudioSystem> mockedAudioSystem;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() throws LineUnavailableException {
        System.setOut(new PrintStream(outContent));
        mockedAudioSystem = mockStatic(AudioSystem.class);

        // Налаштовуємо поведінку моків
        mockedAudioSystem.when(() -> AudioSystem.isLineSupported(any(DataLine.Info.class))).thenReturn(true);
        mockedAudioSystem.when(() -> AudioSystem.getLine(any(DataLine.Info.class))).thenReturn(mockLine);
        // Ми не можемо перевірити, що саме пишеться у файл, але можемо перевірити, що метод write був викликаний
        mockedAudioSystem.when(() -> AudioSystem.write(any(AudioInputStream.class), any(AudioFileFormat.Type.class), any(File.class)))
                .thenReturn(0); // Повертаємо будь-яке int значення
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        mockedAudioSystem.close();
    }




}