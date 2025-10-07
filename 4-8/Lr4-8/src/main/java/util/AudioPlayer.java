package util;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Простий аудіо плеєр для відтворення WAV файлів.
 */
public class AudioPlayer {

    public static void playWav(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File not found: " + filePath);
            return;
        }

        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(file)) {
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);

            try (Clip clip = (Clip) AudioSystem.getLine(info)) {
                clip.open(audioStream);
                clip.start();
                System.out.println("▶ Playing: " + file.getName());

                // Чекаємо завершення відтворення
                while (!clip.isRunning())
                    Thread.sleep(10);
                while (clip.isRunning())
                    Thread.sleep(50);

                clip.close();
            }

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
            System.out.println("Error playing audio: " + e.getMessage());
        }
    }
}
