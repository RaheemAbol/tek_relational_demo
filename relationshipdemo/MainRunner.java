package relationshipdemo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class MainRunner {

    public static void main(String[] args) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            // Create author
            Author author = new Author("John Doe");
            Author author2 = new Author("John Doe2");

            // Create books
            Book book1 = new Book("Book Title 1");
            Book book2 = new Book("Book Title 2");

            // Set bidirectional relationship (Author-Book)
            author.addBook(book1);
            author2.addBook(book2);
            book1.setAuthor(author);
            book2.setAuthor(author2);




//             Create biography and set it to author
            Biography biography = new Biography("This is a biography of John Doe.");
            author.setBiography(biography); // Linking author to biography

            // Create publisher and link to books
            Publisher publisher = new Publisher("Amazing Publishing");
            session.persist(publisher);  // Persist publisher
            book1.addPublisher(publisher);
            book2.addPublisher(publisher);

            // Persist entities
            session.persist(biography);
            session.persist(book1);
            session.persist(book2);
            session.persist(author);  // Persisting author will also persist biography due to cascading

            transaction.commit();
            System.out.println("Author, Books, Biography, and Publisher saved successfully!");

        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            factory.close(); // Close sessionFactory
        }
    }
}
