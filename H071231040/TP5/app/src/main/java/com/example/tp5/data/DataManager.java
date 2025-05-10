package com.example.tp5.data;

import com.example.tp5.R;
import com.example.tp5.models.Book;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DataManager {
    private static DataManager instance;
    private List<Book> allBooks;

    private DataManager() {
        allBooks = new ArrayList<>();
        createDummyData();
    }

    public static synchronized DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    private void createDummyData() {
        // Adding 15 dummy books with various details
        allBooks.add(new Book("To Kill a Mockingbird", "Harper Lee", 2022,
                "A powerful story of racial inequality in the American South through the innocent eyes of a child.",
                R.drawable.book1, "Classic", 4.8f));

        allBooks.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", 2021,
                "The story of eccentric millionaire Jay Gatsby as he pursues his passion for the elusive Daisy Buchanan.",
                R.drawable.book2, "Classic", 4.5f));

        allBooks.add(new Book("1984", "George Orwell", 2021,
                "A dystopian social science fiction novel set in a world of perpetual war, government surveillance, and public manipulation.",
                R.drawable.book3, "Science Fiction", 4.7f));

        allBooks.add(new Book("Pride and Prejudice", "Jane Austen", 2020,
                "A romantic novel that follows the emotional development of Elizabeth Bennet.",
                R.drawable.book4, "Romance", 4.4f));

        allBooks.add(new Book("The Catcher in the Rye", "J.D. Salinger", 2020,
                "The story of a teenage boy's experiences in New York City over three days after being expelled from school.",
                R.drawable.book5, "Coming of Age", 4.2f));

        allBooks.add(new Book("The Hobbit", "J.R.R. Tolkien", 2019,
                "The journey of home-loving Bilbo Baggins, who is convinced to join a quest to raid the treasure hoard of a dragon.",
                R.drawable.book6, "Fantasy", 4.9f));

        allBooks.add(new Book("Brave New World", "Aldous Huxley", 2019,
                "A dystopian novel set in a futuristic World State where citizens are environmentally engineered into a social hierarchy.",
                R.drawable.book7, "Science Fiction", 4.6f));

        allBooks.add(new Book("The Lord of the Rings", "J.R.R. Tolkien", 2018,
                "An epic high-fantasy novel about a quest to destroy a powerful ring and defeat the Dark Lord.",
                R.drawable.book8, "Fantasy", 4.9f));

        allBooks.add(new Book("Crime and Punishment", "Fyodor Dostoevsky", 2018,
                "A novel about a poor student who struggles with the idea of murdering a pawnbroker for her money.",
                R.drawable.book9, "Classic", 4.5f));

        allBooks.add(new Book("One Hundred Years of Solitude", "Gabriel García Márquez", 2017,
                "The multi-generational story of the Buendía family in the fictional town of Macondo.",
                R.drawable.book10, "Magic Realism", 4.7f));

        allBooks.add(new Book("War and Peace", "Leo Tolstoy", 2017,
                "A novel that chronicles the history of the French invasion of Russia through the stories of five families.",
                R.drawable.book11, "Historical Fiction", 4.8f));

        allBooks.add(new Book("Moby Dick", "Herman Melville", 2016,
                "The voyage of a whaling ship and its captain's obsessive quest for a white whale.",
                R.drawable.book12, "Adventure", 4.3f));

        allBooks.add(new Book("The Divine Comedy", "Dante Alighieri", 2016,
                "An epic poem that represents the journey of the soul toward God, with Hell, Purgatory, and Paradise.",
                R.drawable.book13, "Poetry", 4.6f));

        allBooks.add(new Book("Don Quixote", "Miguel de Cervantes", 2015,
                "The story of a man who loses his sanity and decides to become a knight-errant.",
                R.drawable.book14, "Classic", 4.4f));

        allBooks.add(new Book("The Odyssey", "Homer", 2015,
                "An ancient Greek epic poem that follows the hero Odysseus on his journey home after the Trojan War.",
                R.drawable.book15, "Epic", 4.5f));
    }

    public List<Book> getAllBooks() {
        // Sort by publish year (newest first)
        allBooks.sort(Comparator.comparing(Book::getPublishYear).reversed());
        return allBooks;
    }

    public List<Book> getFavoriteBooks() {
        return allBooks.stream()
                .filter(Book::isFavorite)
                .collect(Collectors.toList());
    }

    public List<Book> searchBooks(String query) {
        return allBooks.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Book> getBooksByGenre(String genre) {
        return allBooks.stream()
                .filter(book -> book.getGenre().equalsIgnoreCase(genre))
                .collect(Collectors.toList());
    }

    public void addBook(Book book) {
        allBooks.add(book);
    }

    public List<String> getAllGenres() {
        return allBooks.stream()
                .map(Book::getGenre)
                .distinct()
                .collect(Collectors.toList());
    }

    public void updateBookFavoriteStatus(Book updatedBook) {
        // Find the book in the master list and update its favorite status
        for (Book book : allBooks) {
            if (book.getId().equals(updatedBook.getId())) {
                book.setFavorite(updatedBook.isFavorite());
                break;
            }
        }
    }
}
