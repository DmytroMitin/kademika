package bookshop;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import static java.awt.GridBagConstraints.*;

public class BookshopUI {
    private final Bookshop bookshop;

    private int bookIndex;

    private final JPanel panels;

    public BookshopUI(Bookshop bookshop) {
        this.bookshop = bookshop;

        JFrame frame = new JFrame();
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setLocation(400, 200);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();
        panels = new JPanel(cardLayout);
        panels.add(createPaymentListPanel(), "paymentListPanel");
        panels.add(createSellingPanel(), "sellingPanel");
        frame.getContentPane().add(panels);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        JMenu menu = new JMenu("Menu");
        menuBar.add(menu);
        JMenuItem menuItem = new JMenuItem("Buy books");
        menu.add(menuItem);
        menuItem.addActionListener(e -> cardLayout.show(panels, "sellingPanel"));
        JMenuItem menuItem1 = new JMenuItem("Show payments");
        menu.add(menuItem1);
        menuItem1.addActionListener(e -> cardLayout.show(panels, "paymentListPanel"));

        frame.pack();
        frame.setVisible(true);
    }

    private JPanel createSellingPanel() {
        JPanel panel = new JPanel(new GridBagLayout());

        JLabel nameLabel = new JLabel("Your name ");
        panel.add(nameLabel, new GridBagConstraints(0, 0, 1, 1, 0, 0, CENTER, NONE, new Insets(0, 0, 0, 0), 0, 0));

        JTextField nameField = new JTextField(20);
        panel.add(nameField, new GridBagConstraints(1, 0, 1, 1, 0, 0, WEST, NONE, new Insets(0, 0, 0, 0), 0, 0));

        JLabel bookLabel = new JLabel("Book");
        panel.add(bookLabel, new GridBagConstraints(0, 1, 1, 1, 0, 0, NORTH, NONE, new Insets(0, 0, 0, 0), 0, 0));

        JPanel bookPanel = new JPanel(new GridBagLayout());
        bookPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        ButtonGroup bookButtonGroup = new ButtonGroup();
        ActionListener bookButtonListener = new BookButtonListener();
        for (int i = 0; i < bookshop.getNumberOfBooks(); i++) {
            Book book = bookshop.getBookById(i);
            JRadioButton button = new JRadioButton(book.getAuthor() + ". " + book.getTitle());
            if (i == 0) {
                button.setSelected(true);
            }
            button.setActionCommand(Integer.toString(i));
            button.addActionListener(bookButtonListener);
            bookButtonGroup.add(button);
            bookPanel.add(button, new GridBagConstraints(0, i, 1, 1, 0, 0, WEST, NONE, new Insets(0, 0, 0, 0), 0, 0));
        }
        panel.add(bookPanel, new GridBagConstraints(1, 1, 1, 1, 0, 0, CENTER, NONE, new Insets(0, 0, 0, 0), 0, 0));

        JLabel amountLabel = new JLabel("Amount");
        panel.add(amountLabel, new GridBagConstraints(0, 2, 1, 1, 0, 0, CENTER, NONE, new Insets(0, 0, 0, 0), 0, 0));
        JTextField amountField = new JTextField("1", 3);
        panel.add(amountField, new GridBagConstraints(1, 2, 1, 1, 0, 0, WEST, NONE, new Insets(0, 0, 0, 0), 0, 0));

        JButton buyButton = new JButton("Buy");
        buyButton.addActionListener(e -> {
            Book book = bookshop.getBooks().get(bookIndex);
            Payment payment = new Payment(
                    new User(nameField.getText(), null, null),
                    book,
                    "some address",
                    Integer.parseInt(amountField.getText())
            );

            bookshop.addPayment(payment);

            panels.add(createPaymentListPanel(), "paymentListPanel");
            CardLayout layout = (CardLayout) panels.getLayout();
            layout.show(panels, "paymentListPanel");
        });
        panel.add(buyButton, new GridBagConstraints(0, 3, 2, 1, 0, 0, CENTER, NONE, new Insets(0, 0, 0, 0), 0, 0));

        return panel;
    }

    private class BookButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String actionCommand = e.getActionCommand();
            bookIndex = Integer.parseInt(actionCommand);
        }
    }

    private JPanel createPaymentListPanel() {
        JPanel panel = new JPanel();

        String[][] data = new String[bookshop.getNumberOfPayments()][];

        for (int i = 0; i < bookshop.getNumberOfPayments(); i++) {
            Payment payment = bookshop.getPaymentById(i);
            data[i] = new String[]{
                    Integer.toString(payment.getId()),
                    new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(payment.getDate().getTime()),
                    payment.getUser().getName(),
                    payment.getBook().getAuthor() + ". " + payment.getBook().getTitle(),
                    Integer.toString(payment.getAmount())
            };
        }

        String[] columnNames = new String[]{"Id", "Date", "Name", "Book", "Amount"};
        JTable table = new JTable(data, columnNames);
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(20);
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(3).setPreferredWidth(400);
        columnModel.getColumn(4).setPreferredWidth(20);
        panel.add(table);

        return panel;
    }
}
