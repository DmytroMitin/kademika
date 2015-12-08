package bookshop;

import java.util.Date;

public class ReportMaker {

    private Bookshop bookshop;

    public ReportMaker(Bookshop bookshop) {
        this.bookshop = bookshop;
    }

    public void printPrices() {
        Book[] books = bookshop.getBooks();
        for (int i = 0; i < bookshop.getNumberOfBooks(); i++) {
            Book book = books[i];
            System.out.println(book.getAuthor() + ". " + book.getTitle() + " - " + book.getPrice());
        }
    }

    public void printAmounts() {
        Book[] books = bookshop.getBooks();
        for (int i = 0; i < bookshop.getNumberOfBooks(); i++) {
            Book book = books[i];
            System.out.println(book.getAuthor() + ". " + book.getTitle() + " - " + book.getAmount());
        }
    }

    public void printLastWeekPayments() {
        Payment[] payments = bookshop.getPayments();
        int numberOfPayments = bookshop.getNumberOfPayments();
//        Payment[][] dailyPayments = new Payment[7][numberOfPayments];
        int[] dailyNumbersOfPayments = new int[7];
        long oneDay = 24 * 60 * 60 * 1000;
        long weekAgo = System.currentTimeMillis() - 7 * oneDay;
        for (int i = 0; i < numberOfPayments; i++) {
            Payment payment = payments[i];
            long time = payment.getDate().getTime();
            int day = (int)((time - weekAgo) / oneDay);
//            dailyPayments[day][dailyNumbersOfPayments[day]] = payment;
            dailyNumbersOfPayments[day]++;
        }
        for (int day = 0; day < 7; day++) {
            System.out.print(dailyNumbersOfPayments[day] + " ");
        }
        System.out.println();
    }

    public void printTodayPayments() {
        Payment[] payments = bookshop.getPayments();
        int numberOfPayments = bookshop.getNumberOfPayments();
        long oneDay = 24 * 60 * 60 * 1000;
        long yesterday = System.currentTimeMillis() - oneDay;
        int totalSum = 0;
        int totalAmount = 0;
        for (int i = 0; i < numberOfPayments; i++) {
            Payment payment = payments[i];
            Date date = payment.getDate();
            if (date.after(new Date(yesterday))) {
                Book book = payment.getBook();
                totalSum += payment.getSum();
                totalAmount += payment.getAmount();
                System.out.println(payment.getId() + " | " + payment.getUser().getName() + " | "
                        + book.getAuthor() + ". " + book.getTitle() + " | " + book.getPrice() + " | " + payment.getAmount());
            }
        }

        System.out.println("Totally:                              " + totalSum + " | " + totalAmount);
    }

    public void printCatalog() {
        Genre[] genres = bookshop.getGenres();
        int numberOfGenres = bookshop.getNumberOfGenres();
        Book[] books = bookshop.getBooks();
        int numberOfBooks = bookshop.getNumberOfBooks();
        System.out.println("Catalog");
        for (int i = 0; i < numberOfGenres; i++) {
            Genre genre = genres[i];
            System.out.println("* Genre: " + genre.getName());
            for (int j = 0; j < numberOfBooks; j++) {
                Book book = books[j];
                if (book.getGenres()[0].equals(genre)) {
                    System.out.println(book.getAuthor() + ". " + book.getTitle());
                }
            }
        }
    }


}
