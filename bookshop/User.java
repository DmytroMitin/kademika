package bookshop;

import java.util.Date;

public class User {
    private static int currentId = 0;

    private final int id;

    private String name;

    private String email;

    private Date registrationDate;

    private String defaultDeliveryAddress;

    private Payment[] payments;

    private Book[] booksBought;

    private Card[] cards;

    private Card defaultCard;

    private Genre[] favoriteGenres;

    public User(String name, String email, Date registrationDate) {
        this.id = currentId;
        currentId++;
        this.name = name;
        this.email = email;
        this.registrationDate = registrationDate;
    }

    public String getName() {
        return name;
    }
}
