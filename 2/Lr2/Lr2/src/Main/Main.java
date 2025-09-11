package Main;

import Models.Book;
import Models.Library;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        library.addBook(new Book(1, "Кобзар", "Тарас Шевченко", "Просвіта", 2012, 400, 150));
        library.addBook(new Book(2, "Мандрівний замок Хаула", "Діана Вінн Джонс", "А-ба-ба-га-ла-ма-га", 2004, 320, 250));

        System.out.println("Книги Шевченка:");
        for (Book b : library.getBooksByAuthor("Тарас Шевченко")) {
            System.out.println(b);
        }

        System.out.println("\nКниги після 2010 року:");
        for (Book b : library.getBooksAfterYear(2010)) {
            System.out.println(b);
        }
    }
}