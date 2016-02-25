package bookshop;

import java.util.Arrays;
import java.util.List;

public class Book {
    private static int currentId = 0;

    private final int id;

    private String title;

    private String author;

    private List<Genre> genres;

    private int price;

    private int amount;

    private List<Book> similarBooks;

    public Book(String title, String author, int price, int amount, Genre... genres) {
        this.id = currentId;
        currentId++;

        this.genres = Arrays.asList(genres);

        for (Genre genre : genres) {
            genre.addBook(this);
        }

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

    public List<Genre> getGenres() {
        return genres;
    }

    public int getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genres=" + genres +
                ", price=" + price +
                ", amount=" + amount +
                ", similarBooks=" + similarBooks +
                '}';
    }
}
