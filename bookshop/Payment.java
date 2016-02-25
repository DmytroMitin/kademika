package bookshop;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Payment {
    private static int currentId = 0;

    private final int id;

    private Calendar date;

    private User user;

    private Book book;

    private int amount;

    private String deliveryAddress;

    private int sum;

    private Card card;

    private boolean status;

    public Payment(User user, Book book, String deliveryAddress, int amount) {
        this.id = currentId;
        currentId++;

        this.date = new GregorianCalendar();
        this.date.setTime(new Date());

        this.user = user;
        this.book = book;
        this.amount = amount;
        this.deliveryAddress = deliveryAddress;
        this.sum = book.getPrice() * amount;
    }

    public int getId() {
        return id;
    }

    public Calendar getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }

    public Book getBook() {
        return book;
    }

    public int getAmount() {
        return amount;
    }

    public int getSum() {
        return sum;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ",\n" +
                "date=" + date +
                ",\n" +
                "user=" + user +
                ",\n" +
                "book=" + book +
                ",\n" +
                "amount=" + amount +
                ",\n" +
                "deliveryAddress='" + deliveryAddress + '\'' +
                ",\n" +
                "sum=" + sum +
                ",\n" +
                "card=" + card +
                ",\n" +
                "status=" + status +
                '}';
    }
}
