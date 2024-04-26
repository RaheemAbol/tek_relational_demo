package relationshipdemo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;


    @ManyToMany(mappedBy = "books")
    private List<Publisher> publishers = new ArrayList<>();

    public void addPublisher(Publisher publisher) {
        publishers.add(publisher);
        if (!publisher.getBooks().contains(this)) {
            publisher.getBooks().add(this); // Ensure bidirectional relationship
        }
    }

    public void removePublisher(Publisher publisher) {
        publishers.remove(publisher);
        publisher.getBooks().remove(this);
    }

}
