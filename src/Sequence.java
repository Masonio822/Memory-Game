import javax.swing.Timer;
import java.util.*;

public class Sequence {
    private static final Sequence instance = new Sequence();

    private ArrayList<Integer> sequenceList, sequenceGuesses;
    private int lowBound, highBound;
    private int roundsComplete;

    public Sequence() {
        sequenceGuesses = new ArrayList<>();
        sequenceList = new ArrayList<>();
        lowBound = 1;
        highBound = 10;
        roundsComplete = 0;
    }

    public void setBounds(int low, int high) {
        lowBound = low;
        highBound = high;
    }

    public void addRandomToSequence() {
        Random r = new Random();
        sequenceList.add(r.nextInt(lowBound, highBound));
    }

    public void guess(int id) {
        sequenceGuesses.add(id);
        if (!sequenceGuesses.equals(sequenceList.subList(0, sequenceGuesses.size()))) {
            App.getInstance().endGame();
        } else if (sequenceList.size() == sequenceGuesses.size()) {
            App.getButtons().forEach((_, value) -> value.setClickable(false));
            javax.swing.Timer t = new Timer(1000, _ -> {
                addRandomToSequence();
                sequenceGuesses = new ArrayList<>();
                showSequence(App.getButtons());
                App.getButtons().forEach((_, value) -> value.setClickable(true));
            });
            t.setRepeats(false);
            t.start();
            AudioManager.getInstance().getRoundComplete().start();
            roundsComplete++;
        } else {
            AudioManager.getInstance().getCorrect().start();
        }
    }

    public void showSequence(Map<Integer,ColorChanger> colorChangers) {
        App.getButtons().forEach((_, value) -> value.setClickable(false));
        Thread t = new Thread(() -> {
            for (Integer i : sequenceList) {
                ColorChanger c = colorChangers.get(i);
                c.setBackground(c.getColor());
                try {
                    Thread.sleep(ColorChanger.DELAY);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                c.setBackground(null);
                c.repaint();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            App.getButtons().forEach((_, value) -> value.setClickable(true));
        });
        t.start();

    }

    public void clearSequence() {
        sequenceList = new ArrayList<>();
    }

    public void clearGuesses() {
        sequenceGuesses = new ArrayList<>();
    }

    public void resetRoundsComplete() {
        roundsComplete = 0;
    }

    public int getRoundsComplete() {
        return roundsComplete;
    }

    public ArrayList<Integer> getSequence() {
        return sequenceList;
    }

    public ArrayList<Integer> getGuesses() {
        return sequenceGuesses;
    }

    public static Sequence getInstance() {
        return instance;
    }
}
