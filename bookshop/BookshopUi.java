package bookshop;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Enumeration;

import static java.awt.GridBagConstraints.*;

public class BookshopUi {
    private Bookshop bookshop;

    private int bookIndex;

    private JFrame paymentFrame;

    public BookshopUi(Bookshop bookshop) {
        this.bookshop = bookshop;

        createSellingFrame();

        createPaymentFrame();
    }

    private void createSellingFrame() {
        JFrame sellingFrame = new JFrame();
        sellingFrame.setMinimumSize(new Dimension(800, 600));
        sellingFrame.setLocation(300, 100);
        sellingFrame.getContentPane().add(createSellingPanel());
        sellingFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        sellingFrame.pack();
        sellingFrame.setVisible(true);
    }

    private void createPaymentFrame() {
        paymentFrame = new JFrame();
        paymentFrame.setMinimumSize(new Dimension(800, 600));
        paymentFrame.setLocation(400, 200);
        paymentFrame.getContentPane().add(createPaymentPanel());
        paymentFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        paymentFrame.pack();
        paymentFrame.setVisible(true);
    }

    private JPanel createSellingPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        JLabel nameLabel = new JLabel("Your name ");
        panel.add(nameLabel, new GridBagConstraints(0, 0, 1, 1, 0, 0, CENTER, NONE, new Insets(0, 0, 0, 0), 0, 0));

        JTextField nameField = new JTextField(20);
        panel.add(nameField, new GridBagConstraints(1, 0, 1, 1, 0, 0, WEST, NONE, new Insets(0, 0, 0, 0), 0, 0));

        JLabel bookLabel = new JLabel("Book");
        panel.add(bookLabel, new GridBagConstraints(0, 1, 1, 1, 0, 0, NORTH, NONE, new Insets(0, 0, 0, 0), 0, 0));

        JPanel bookPanel = new JPanel();
        bookPanel.setLayout(new GridBagLayout());
        bookPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        ButtonGroup bookButtonGroup = new ButtonGroup();
        ActionListener bookButtonListener = new BookButtonListener();
        int i = 0;
        for (Book book : bookshop.getBooks()) {
            JRadioButton button = new JRadioButton(book.getAuthor() + ". " + book.getTitle());
            if (i == 0) {
                button.setSelected(true);
            }
            button.setActionCommand(Integer.toString(i));
            button.addActionListener(bookButtonListener);
            bookButtonGroup.add(button);
            bookPanel.add(button, new GridBagConstraints(0, i, 1, 1, 0, 0, WEST, NONE, new Insets(0, 0, 0, 0), 0, 0));
            i++;
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

            paymentFrame.setVisible(false);
            createPaymentFrame();
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

    private JPanel createPaymentPanel() {
        JPanel panel = new JPanel();

        String[][] data = new String[bookshop.getNumberOfPayments()][];
        int i = 0;
        for (Payment payment : bookshop.getPayments()) {
            data[i] = new String[]{
                    Integer.toString(payment.getId()),
                    new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(payment.getDate().getTime()),
                    payment.getUser().getName(),
                    payment.getBook().getAuthor() + ". " + payment.getBook().getTitle(),
                    Integer.toString(payment.getAmount())
            };
            i++;
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
