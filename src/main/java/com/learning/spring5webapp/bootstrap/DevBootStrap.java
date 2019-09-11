package com.learning.spring5webapp.bootstrap;

import com.learning.spring5webapp.model.Author;
import com.learning.spring5webapp.model.Book;
import com.learning.spring5webapp.model.Publisher;
import com.learning.spring5webapp.repository.AuthorRepository;
import com.learning.spring5webapp.repository.BookRepository;
import com.learning.spring5webapp.repository.PublisherRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DevBootStrap implements ApplicationListener<ContextRefreshedEvent> {

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;
    private PublisherRepository publisherRepository;

    public DevBootStrap(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    private void init() {
        Author eric = new Author("Eric", "Evans");
        Publisher hcpub = new Publisher("Harper collins", "London, UK");
        Book ddd = new Book("domain driven design", "1234");
        Set<Book> booksByEric = eric.getBooks();
        if (null == booksByEric) {
            booksByEric = new HashSet<>();
        }
        booksByEric.add(ddd);
        booksByEric.forEach(book -> book.setPublisher(hcpub));
        Set<Author> authorsForDdd = ddd.getAuthors();
        if (null == authorsForDdd) {
            authorsForDdd = new HashSet<>();
        }
        authorsForDdd.add(eric);
        publisherRepository.save(hcpub);
        authorRepository.save(eric);
        bookRepository.save(ddd);

        Author rod = new Author("Rod", "Jhonson");
        Publisher hfPublisher = new Publisher("hf", "new york");
        Book noEjb = new Book("Jee development without ejb", "2343");
        noEjb.setPublisher(hfPublisher);
        Set<Author> authorsNoEjb = noEjb.getAuthors();
        if (null == authorsNoEjb) {
            authorsNoEjb = new HashSet<>();
        }
        authorsNoEjb.add(rod);
        publisherRepository.save(hfPublisher);
        authorRepository.save(rod);
        bookRepository.save(noEjb);
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        init();
    }
}
