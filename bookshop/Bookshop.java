package bookshop;

public class Bookshop {
    private final int DEFAULT_CAPACITY = 100;

    private Book[] books;

    private User[] users;

    private Card[] cards;

    private Genre[] genres;

    private Payment[] payments;

    private int numberOfBooks;

    private int numberOfUsers;

    private int numberOfCards;

    private int numberOfGenres;

    private int numberOfPayments;

    public Bookshop() {
        books = new Book[DEFAULT_CAPACITY];
        users = new User[DEFAULT_CAPACITY];
        cards = new Card[DEFAULT_CAPACITY];
        genres = new Genre[DEFAULT_CAPACITY];
        payments = new Payment[DEFAULT_CAPACITY];
    }

    public Book[] getBooks() {
        return books;
    }

    public User[] getUsers() {
        return users;
    }

    public Card[] getCards() {
        return cards;
    }

    public Genre[] getGenres() {
        return genres;
    }

    public Payment[] getPayments() {
        return payments;
    }

    public int getNumberOfBooks() {
        return numberOfBooks;
    }

    public int getNumberOfUsers() {
        return numberOfUsers;
    }

    public int getNumberOfCards() {
        return numberOfCards;
    }

    public int getNumberOfGenres() {
        return numberOfGenres;
    }

    public int getNumberOfPayments() {
        return numberOfPayments;
    }

    public void addBook(Book book) {
        if (numberOfBooks < DEFAULT_CAPACITY) {
            books[numberOfBooks] = book;
            numberOfBooks++;
        }
    }

    public void addUser(User user) {
        if (numberOfUsers < DEFAULT_CAPACITY) {
            users[numberOfUsers] = user;
            numberOfUsers++;
        }
    }

    public void addCard(Card card) {
        if (numberOfCards < DEFAULT_CAPACITY) {
            cards[numberOfCards] = card;
            numberOfCards++;
        }
    }

    public void addGenre(Genre genre) {
        if (numberOfGenres < DEFAULT_CAPACITY) {
            genres[numberOfGenres] = genre;
            numberOfGenres++;
        }
    }

    public void addPayment(Payment payment) {
        if (numberOfPayments < DEFAULT_CAPACITY) {
            payments[numberOfPayments] = payment;
            numberOfPayments++;
        }
    }
}
