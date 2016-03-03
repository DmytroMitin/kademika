package tanks;

import tanks.field.BattleField;
import tanks.tank.AbstractTank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionFieldPanel extends JPanel implements ActionListener {
    private final ActionField actionField;
    private final JFrame frame;

    public ActionFieldPanel(ActionField actionField) {
        this.actionField = actionField;

        setLayout(new BorderLayout());

        frame = new JFrame("BATTLE FIELD");
        frame.setLocation(750, 150);
        BattleField battleField = actionField.getBattleField();
        Dimension size = new Dimension(battleField.getWidth(), battleField.getHeight());
        frame.setMinimumSize(size);
        frame.setPreferredSize(size);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(this, BorderLayout.CENTER);

        Timer repaintTimer = new Timer(40, this);
        repaintTimer.setInitialDelay(0);
        repaintTimer.start();

        frame.pack();
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BattleField battleField = actionField.getBattleField();
        battleField.draw(g);
        AbstractTank defender = actionField.getDefender();
        defender.draw(g);
        AbstractTank aggressor = actionField.getAggressor();
        aggressor.draw(g);
        Bullet bullet = actionField.getBullet();
        bullet.draw(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        revalidate();
        repaint();
    }

    public void gameOver(Object object) {
        SwingUtilities.invokeLater(() -> {
            new GameOverPanel(object);
            frame.setVisible(false);
        });
    }
}
