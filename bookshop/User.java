package bookshop;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class User {
    private static int currentId = 0;

    private final int id;

    private String name;

    private String email;

    private Calendar registrationDate;

    private String defaultDeliveryAddress;

    private List<Payment> payments;

    private List<Book> booksBought;

    private List<Card> cards;

    private Card defaultCard;

    private List<Genre> favoriteGenres;

    public User(String name, String email, Calendar registrationDate) {
        this.id = currentId;
        currentId++;
        this.name = name;
        this.email = email;
        this.registrationDate = registrationDate;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", registrationDate=" + registrationDate +
                ", defaultDeliveryAddress='" + defaultDeliveryAddress + '\'' +
                ", payments=" + payments +
                ", booksBought=" + booksBought +
                ", cards=" + cards +
                ", defaultCard=" + defaultCard +
                ", favoriteGenres=" + favoriteGenres +
                '}';
    }
}
