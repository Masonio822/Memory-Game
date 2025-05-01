import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class App {
    private static final App instance = new App();

    private final JFrame frame;
    private static Map<Integer,ColorChanger> buttons;
    private static boolean gameGoing = true;

    private App() {
        frame = new JFrame("Memory Game");
        frame.setExtendedState(JFrame.NORMAL);
        frame.setSize(500, 500);
        frame.setLayout(new GridLayout(3, 3));
        frame.setMinimumSize(new Dimension(500, 500));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        Random r = new Random();
        buttons = new HashMap<>(9);

        for (int i = 1; i < 10; i++) {
            buttons.put(i, new ColorChanger(i, new Color(r.nextFloat(), r.nextFloat(), r.nextFloat()).brighter()));
        }

        for (ColorChanger cc : buttons.values()) {
            frame.add(cc);
            cc.setClickable(false);
        }

        Sequence.getInstance().addRandomToSequence();
        Sequence.getInstance().showSequence(buttons);
        App.getButtons().forEach((_, value) -> value.setClickable(true));
    }

    public void endGame() {
        System.exit(0);
    }

    public static Map<Integer, ColorChanger> getButtons() {
        return buttons;
    }

    public static App getInstance() {
        return instance;
    }

    public void show() {
        frame.setVisible(true);
    }
}
