import javax.sound.sampled.*;

public class AudioManager {
    private static final AudioManager instance = new AudioManager();

    private AudioManager() {}

    public Clip getCorrect() {
        try {
            AudioInputStream audioIS = AudioSystem.getAudioInputStream(Main.class.getResource("sounds\\correct.wav"));
            Clip correctSound = AudioSystem.getClip();
            correctSound.open(audioIS);
            return correctSound;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Clip getRoundComplete() {
        try {
            AudioInputStream audioIS = AudioSystem.getAudioInputStream(Main.class.getResource("sounds\\round_complete.wav"));
            Clip roundCompleteSound = AudioSystem.getClip();
            roundCompleteSound.open(audioIS);
            return roundCompleteSound;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Clip getGameOver() {
        try {
            AudioInputStream audioIS = AudioSystem.getAudioInputStream(Main.class.getResource("sounds\\game_over.wav"));
            Clip gameOverSound = AudioSystem.getClip();
            gameOverSound.open(audioIS);
            return gameOverSound;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static AudioManager getInstance() {
        return instance;
    }
}
