package bookshop;

import java.util.ArrayList;
import java.util.List;

public class Bookshop {
    private List<Book> books;

    private List<User> users;

    private List<Card> cards;

    private List<Genre> genres;

    private List<Payment> payments;

    public Bookshop() {
        books = new ArrayList<>();
        users = new ArrayList<>();
        cards = new ArrayList<>();
        genres = new ArrayList<>();
        payments = new ArrayList<>();
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Card> getCards() {
        return cards;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public int getNumberOfBooks() {
        return books.size();
    }

    public int getNumberOfUsers() {
        return users.size();
    }

    public int getNumberOfCards() {
        return cards.size();
    }

    public int getNumberOfGenres() {
        return genres.size();
    }

    public int getNumberOfPayments() {
        return payments.size();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void addGenre(Genre genre) {
        genres.add(genre);
    }

    public void addPayment(Payment payment) {
        payments.add(payment);
    }

    public Payment getPaymentById(int i) {
        return payments.get(i);
    }

    public Book getBookById(int i) {
        return books.get(i);
    }
}
