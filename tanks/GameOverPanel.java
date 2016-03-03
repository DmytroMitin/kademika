package tanks;

import javax.swing.*;
import java.awt.*;

import static java.awt.GridBagConstraints.*;

public class GameOverPanel extends JPanel {
    public GameOverPanel(Object object) {
        JFrame frame = new JFrame("Game Over");
        frame.setLocation(750, 150);
        frame.setMinimumSize(new Dimension(400, 400));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);

        setLayout(new GridBagLayout());

        JLabel label = new JLabel(object + " destroyed. Play again?");
        add(label, new GridBagConstraints(0, 0, 2, 1, 0, 0, CENTER, NONE, new Insets(0, 0, 0, 0), 0, 0));

        JButton yesButton = new JButton("Yes");
        yesButton.addActionListener(e -> {
            try {
                frame.setVisible(false);
                Launcher.main(new String[0]);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
        add(yesButton, new GridBagConstraints(0, 1, 1, 1, 0, 0, WEST, NONE, new Insets(0, 0, 0, 0), 0, 0));

        JButton noButton = new JButton("No");
        noButton.addActionListener(e -> {
            frame.setVisible(false);
            System.exit(0);
        });
        add(noButton, new GridBagConstraints(1, 1, 1, 1, 0, 0, EAST, NONE, new Insets(0, 0, 0, 0), 0, 0));

        frame.pack();
        frame.setVisible(true);
    }
}
