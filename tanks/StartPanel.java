package tanks;

import tanks.field.BattleField;

import javax.swing.*;
import java.awt.*;

import static java.awt.GridBagConstraints.CENTER;
import static java.awt.GridBagConstraints.NONE;

public class StartPanel extends JPanel {
    private String defenderType;

    public StartPanel(BattleField battleField) {
        JFrame frame = new JFrame("START MENU");
        frame.setLocation(750, 150);
        frame.setMinimumSize(new Dimension(400, 400));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);

        setLayout(new GridBagLayout());

        JLabel label = new JLabel("Choose type of your tank:");
        add(label, new GridBagConstraints(0, 0, 1, 1, 0, 0, CENTER, NONE, new Insets(0, 0, 0, 0), 0, 0));

        JComboBox dropDownList = new JComboBox(new String[]{"T34", "Tiger"});
        dropDownList.setSelectedIndex(-1);
        dropDownList.addActionListener(e -> defenderType = (String) dropDownList.getSelectedItem());
        add(dropDownList, new GridBagConstraints(0, 1, 1, 1, 0, 0, CENTER, NONE, new Insets(0, 0, 0, 0), 0, 0));

        JButton button = new JButton("Start");
        button.addActionListener(e -> {
            if (defenderType != null) {
                frame.setVisible(false);

                ActionField actionField = new ActionField(battleField,
                        new Quadrant(1, 1), Direction.RIGHT, defenderType,
                        new Quadrant(9, 1), Direction.LEFT);

                new Thread(() -> {
                    try {
                        actionField.runTheGame();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }).start();

                ActionFieldPanel actionFieldPanel = new ActionFieldPanel(actionField);
                actionField.setPanel(actionFieldPanel);
            } else {
                JLabel errorLabel = new JLabel("Choose something!");
                errorLabel.setForeground(Color.RED);
                add(errorLabel, new GridBagConstraints(0, 3, 1, 1, 0, 0, CENTER, NONE, new Insets(0, 0, 0, 0), 0, 0));
                revalidate();
                repaint();
            }
        });
        add(button, new GridBagConstraints(0, 2, 1, 1, 0, 0, CENTER, NONE, new Insets(0, 0, 0, 0), 0, 0));

        frame.pack();
        frame.setVisible(true);
    }
}
