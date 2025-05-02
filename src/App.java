import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class App {
    private static final App instance = new App();

    private final JFrame frame;
    private static Map<Integer,ColorChanger> buttons;

    private App() {
        frame = new JFrame("Memory Game");
        frame.setSize(500, 500);
        frame.setLayout(new GridLayout(3, 3));
        frame.setMinimumSize(new Dimension(500, 500));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(new ImageIcon(Main.class.getResource("cube.png")).getImage());

        initGame();
    }

    public void endGame() {
        AudioManager.getInstance().getGameOver().start();

        for (Component c : frame.getContentPane().getComponents()) {
            frame.remove(c);
        }
        frame.repaint();

        frame.setLayout(new BorderLayout());

        JLabel gameOver = new JLabel("GAME OVER - Your score was: " + Sequence.getInstance().getRoundsComplete());
        gameOver.setForeground(Color.RED);
        gameOver.setFont(gameOver.getFont().deriveFont(Font.BOLD, 30));
        JPanel gameOverPanel = new JPanel();
        gameOverPanel.add(gameOver);
        frame.add(gameOverPanel, BorderLayout.CENTER);

        JButton quit = new JButton("Quit");
        quit.addActionListener(_ -> System.exit(0));
        JButton restart = new JButton("Restart");
        restart.addActionListener(_ -> {
            Sequence.getInstance().clearSequence();
            Sequence.getInstance().clearGuesses();
            Sequence.getInstance().resetRoundsComplete();
            for (Component c : frame.getContentPane().getComponents()) {
                frame.remove(c);
            }
            frame.setLayout(new GridLayout(3, 3));
            initGame();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(quit);
        buttonPanel.add(restart);

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.repaint();
        frame.revalidate();
    }

    public static Map<Integer, ColorChanger> getButtons() {
        return buttons;
    }

    public static App getInstance() {
        return instance;
    }

    private void initGame() {
        Random r = new Random();
        buttons = new HashMap<>(9);

        for (int i = 1; i < 10; i++) {
            buttons.put(i, new ColorChanger(i, new Color(r.nextFloat(), r.nextFloat(), r.nextFloat()).brighter()));
        }
        Sequence.getInstance().setBounds(1, buttons.size() + 1);

        for (ColorChanger cc : buttons.values()) {
            frame.add(cc);
            cc.setClickable(false);
        }

        Sequence.getInstance().addRandomToSequence();
        Sequence.getInstance().showSequence(buttons);
        App.getButtons().forEach((_, value) -> value.setClickable(true));

        frame.revalidate();
    }

    public void show() {
        frame.setVisible(true);
    }
}
