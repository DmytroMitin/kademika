package bookshop;

import java.util.Date;

public class Launcher {
    public static void main(String[] args) {
        Bookshop bookshop = new Bookshop();

        Genre fictionGenre = new Genre("Fiction");
        bookshop.addGenre(fictionGenre);
        Genre computersGenre = new Genre("Computers");
        bookshop.addGenre(computersGenre);
        Genre historyGenre = new Genre("History");
        bookshop.addGenre(historyGenre);
        Genre fantasyGenre = new Genre("Fantasy");
        bookshop.addGenre(fantasyGenre);
        Genre foodGenre = new Genre("Food");
        bookshop.addGenre(foodGenre);

        Book book = new Book("A Journey to the Center of the Earth", "Verne, Jules", 100, 10,
                new Genre[]{fictionGenre, fantasyGenre});
        bookshop.addBook(book);
        Book book1 = new Book("Twenty Thousand Leagues Under the Sea", "Verne, Jules", 100, 10,
                new Genre[]{fictionGenre, fantasyGenre});
        bookshop.addBook(book1);
        Book book2 = new Book("Around the World in Eighty Days", "Verne, Jules", 100, 10,
                new Genre[]{fictionGenre});
        bookshop.addBook(book2);
        Book book3 = new Book("The Mysterious Island", "Verne, Jules", 100, 10,
                new Genre[]{fictionGenre});
        bookshop.addBook(book3);
        Book book4 = new Book("The Sign of the Four", "Conan Doyle, Arthur", 100, 10,
                new Genre[]{fictionGenre});
        bookshop.addBook(book4);
        Book book5 = new Book("The Adventures of Sherlock Holmes", "Conan Doyle, Arthur", 100, 10,
                new Genre[]{fictionGenre});
        bookshop.addBook(book5);
        Book book6 = new Book("The Hound of the Baskervilles", "Conan Doyle, Arthur", 100, 10,
                new Genre[]{fictionGenre});
        bookshop.addBook(book6);
        Book book7 = new Book("The Lost World", "Conan Doyle, Arthur", 100, 10,
                new Genre[]{fictionGenre});
        bookshop.addBook(book7);
        Book book8 = new Book("The Time Machine", "Wells, Herbert", 100, 10,
                new Genre[]{fictionGenre, fantasyGenre});
        bookshop.addBook(book8);
        Book book9 = new Book("The Island of Doctor Moreau", "Verne, Jules", 100, 10,
                new Genre[]{fictionGenre, fantasyGenre});
        bookshop.addBook(book9);
        Book book10 = new Book("The Invisible Man", "Wells, Herbert", 100, 10,
                new Genre[]{fictionGenre, fantasyGenre});
        bookshop.addBook(book10);
        Book book11 = new Book("The War of the Worlds", "Wells, Herbert", 100, 10,
                new Genre[]{fictionGenre, fantasyGenre});
        bookshop.addBook(book11);
        Book book12 = new Book("Effective Java", "Bloch, Joshua", 200, 20,
                new Genre[]{computersGenre});
        bookshop.addBook(book12);
        Book book13 = new Book("Head First Java", "Sierra, Kathy", 200, 20,
                new Genre[]{computersGenre});
        bookshop.addBook(book13);

        User johnSmith = new User("Smith, John", "111@222.com", new Date(115, 10, 20));
        bookshop.addUser(johnSmith);
        User jamesDaw = new User("Daw, James", "333@444.com", new Date(115, 10, 21));
        bookshop.addUser(jamesDaw);

        Payment payment = new Payment(johnSmith, book1, "Some Street, 10", 1);
        payment.setDate(new Date(115, 11, 6)); // 6 December 2015
        bookshop.addPayment(payment);
        Payment payment1 = new Payment(jamesDaw, book12, "Other Street, 15", 1);
        payment.setDate(new Date(115, 11, 7)); // 7 December 2015
        bookshop.addPayment(payment1);
        Payment payment2 = new Payment(johnSmith, book1, "Some Street, 10", 1);
        bookshop.addPayment(payment2);
        Payment payment3 = new Payment(jamesDaw, book12, "Other Street, 15", 1);
        bookshop.addPayment(payment3);

        ReportMaker reportMaker = new ReportMaker(bookshop);
        reportMaker.printPrices();
        System.out.println();
        reportMaker.printAmounts();
        System.out.println();
        reportMaker.printLastWeekPayments();
        System.out.println();
        reportMaker.printTodayPayments();
        System.out.println();
        reportMaker.printCatalog();
    }
}
