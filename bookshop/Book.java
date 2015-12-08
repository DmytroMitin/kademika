package bookshop;

public class Book {
    private static int currentId = 0;

    private final int id;

    private String title;

    private String author;

    private Genre[] genres;

    private int price;

    private int amount;

    private Book[] similarBooks;

    public Book(String title, String author, int price, int amount, Genre[] genres) {
        this.id = currentId;
        currentId++;
        this.genres = genres;
        this.title = title;
        this.author = author;
        this.price = price;
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Genre[] getGenres() {
        return genres;
    }

    public int getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }
}
