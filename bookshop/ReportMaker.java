package bookshop;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReportMaker {

    private Bookshop bookshop;

    public ReportMaker(Bookshop bookshop) {
        this.bookshop = bookshop;
    }

    public void printPrices() {
        for (Book book : bookshop.getBooks()) {
            System.out.println(book.getAuthor() + ". " + book.getTitle() + " - " + book.getPrice());
        }
    }

    public void printAmounts() {
        for (Book book : bookshop.getBooks()) {
            System.out.println(book.getAuthor() + ". " + book.getTitle() + " - " + book.getAmount());
        }
    }

    public void printLastWeekPayments() {
        List<Payment> payments = bookshop.getPayments();
        Calendar today = getToday();

        Calendar[] week = new Calendar[7];
        for (int day = 0; day < 7; day++) {
            week[day] = (Calendar) today.clone();
            week[day].add(Calendar.DAY_OF_MONTH, -6 + day); // week[6] is today
        }

        int[] dailyNumbersOfPayments = new int[7];
        for (Payment payment : payments) {
            Calendar date = payment.getDate();
            for (int day = 6; day >= 0; day--) {
                if (date.after(week[day])) {
                    dailyNumbersOfPayments[day]++;
                    break;
                }
            }
        }

        for (int day = 0; day < 7; day++) {
            System.out.print(dailyNumbersOfPayments[day] + " ");
        }
        System.out.println();
    }

    private static Calendar getToday() {
        Calendar today = Calendar.getInstance();
        today.setTime(new Date());
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        return today;
    }

    public void printTodayPayments() {
        Calendar today = getToday();

        int totalSum = 0;
        int totalAmount = 0;

        List<Payment> payments = bookshop.getPayments();
        for (Payment payment : payments) {
            Calendar date = payment.getDate();
            if (date.after(today)) {
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
        List<Genre> genres = bookshop.getGenres();
        System.out.println("Catalog");
        for (Genre genre : genres) {
            System.out.println("* Genre: " + genre.getName());
            for (Book book : genre.getBooks()) {
                System.out.println(book.getAuthor() + ". " + book.getTitle());
            }
        }
    }


}
