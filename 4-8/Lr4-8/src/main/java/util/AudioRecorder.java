package util;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioRecorder {

    public static void recordAudio(String filePath, int durationSeconds) {
        AudioFormat format = new AudioFormat(16000, 16, 1, true, true);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        if (!AudioSystem.isLineSupported(info)) {
            System.out.println("❌ Audio recording not supported.");
            return;
        }

        try (TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info)) {
            line.open(format);
            line.start();

            System.out.println("🎙️ Recording started (" + durationSeconds + " seconds)...");

            // Створюємо AudioInputStream без окремого потоку
            AudioInputStream ais = new AudioInputStream(line);

            // Пишемо у файл у поточному потоці і одночасно слухаємо line
            File outputFile = new File(filePath);
            long endTime = System.currentTimeMillis() + durationSeconds * 1000L;

            // Створимо окремий потік, який буде закривати line після duration
            Thread stopper = new Thread(() -> {
                try {
                    Thread.sleep(durationSeconds * 1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                line.stop();
                line.close();
            });
            stopper.start();

            // Блокуючий запис у WAV поки line відкритий
            AudioSystem.write(ais, AudioFileFormat.Type.WAVE, outputFile);

            stopper.join(); // чекаємо завершення stopper
            System.out.println("✅ Recording finished. File saved: " + outputFile.getAbsolutePath());

        } catch (LineUnavailableException | IOException | InterruptedException e) {
            System.out.println("Error recording audio: " + e.getMessage());
        }
    }
}
