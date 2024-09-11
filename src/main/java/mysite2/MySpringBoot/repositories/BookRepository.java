package mysite2.MySpringBoot.repositories;


import mysite2.MySpringBoot.models.Book;
import mysite2.MySpringBoot.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByTitleStartingWith(String title);
    Optional<Book> findByTitle(String name);
}
