import javax.swing.*;
import java.awt.*;

public class ColorChanger extends JButton {
    private final int ID;
    private final Color color;

    private static boolean clickable = false;
    public static final int DELAY = 600;

    public ColorChanger() {
        ID = 1;
        color = Color.BLACK;
        this.setSize(167, 167);
        this.addActionListener(_ -> {
            if (!clickable) {
                Sequence.getInstance().guess(ID);
                setBackground(color);
                clickable = true;
                Timer t = new Timer(1000, _ -> {
                    setBackground(null);
                    repaint();
                    clickable = false;
                });
                t.setRepeats(false);
                t.start();
            }
        });
    }

    public ColorChanger(int id, Color c) {
        ID = id;
        color = c;
        this.setSize(167, 167);
        this.addActionListener(_ -> {
            if (!clickable) {
                Sequence.getInstance().guess(ID);
                setBackground(color);
                Timer t = new Timer(DELAY, _ -> {
                    setBackground(null);
                    repaint();
                });
                t.setRepeats(false);
                t.start();
            }
        });
    }

    public void setClickable(boolean flag) {
        clickable = !flag;
    }

    public Color getColor() {
        return color;
    }
}
