package bookshop;

import java.util.Date;

public class Payment {
    private static int currentId = 0;

    private final int id;

    private Date date;

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
        this.date = new Date();
        this.user = user;
        this.book = book;
        this.amount = amount;
        this.deliveryAddress = deliveryAddress;
        this.sum = book.getPrice() * amount;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
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

    public void setDate(Date date) {
        this.date = date;
    }
}
