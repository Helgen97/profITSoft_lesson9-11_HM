package com.profitsoft.profitsofttask9.service.implementations;

import com.profitsoft.profitsofttask9.dao.AuthorRepo;
import com.profitsoft.profitsofttask9.dao.BookRepo;
import com.profitsoft.profitsofttask9.dto.BookCreateDto;
import com.profitsoft.profitsofttask9.dto.BookFullDetailsDto;
import com.profitsoft.profitsofttask9.dto.BookInfoDto;
import com.profitsoft.profitsofttask9.dto.BookSearchDto;
import com.profitsoft.profitsofttask9.entity.Author;
import com.profitsoft.profitsofttask9.entity.Book;
import com.profitsoft.profitsofttask9.exceptions.NotFoundException;
import com.profitsoft.profitsofttask9.service.interfaces.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;

    private final AuthorRepo authorRepo;

    @Override
    @Transactional(readOnly = true)
    public BookFullDetailsDto getBook(Long id) {
        Book book = getBookByIdOrThrowNotFound(id);
        return BookFullDetailsDto.of(book);
    }

    private Book getBookByIdOrThrowNotFound(Long id) {
        return bookRepo.findById(id).orElseThrow(() -> new NotFoundException("Book with id %d not found".formatted(id)));
    }

    @Override
    @Transactional
    public Long createBook(BookCreateDto bookToCreate) {
        Book newBook = new Book();
        fillBookDataFromDto(bookToCreate, newBook);
        return bookRepo.save(newBook).getId();
    }

    private void fillBookDataFromDto(BookCreateDto bookToCreate, Book bookToSave) {
        Author author = getAuthorByIdOrThrowNotFound(bookToCreate.getAuthorId());

        bookToSave.setName(bookToCreate.getName());
        bookToSave.setDescription(bookToCreate.getDescription());
        bookToSave.setPublicationDate(bookToCreate.getPublicationDate());
        bookToSave.setAuthor(author);
    }

    private Author getAuthorByIdOrThrowNotFound(Long authorId) {
        return authorRepo.findById(authorId).orElseThrow(() -> new NotFoundException("Author with id %d not found".formatted(authorId)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookInfoDto> searchBooks(BookSearchDto bookQuery) {
        PageRequest pageRequest = getPageRequestByParameters(bookQuery.getPage(), bookQuery.getSize());
        Page<Book> bookPage = getBookPageByQuery(bookQuery, pageRequest);

        return convertEntityPageToDtoPage(bookPage);
    }

    private Page<Book> getBookPageByQuery(BookSearchDto bookQuery, PageRequest pageRequest) {
        if (bookQuery.getName() == null && bookQuery.getPublicationDate() == null) {
            bookRepo.findAll(pageRequest);
        }

        return bookRepo.findAll(getBookQuery(bookQuery), pageRequest);
    }

    private Specification<Book> getBookQuery(BookSearchDto bookQuery) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            if (bookQuery.getName() != null) {
                predicateList.add(criteriaBuilder.equal(root.get("name"), bookQuery.getName()));
            }
            if (bookQuery.getPublicationDate() != null) {
                predicateList.add(criteriaBuilder.equal(root.get("publicationDate"), bookQuery.getPublicationDate()));
            }

            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        };
    }

    private Page<BookInfoDto> convertEntityPageToDtoPage(Page<Book> bookPage) {
        return bookPage.map(BookInfoDto::of);
    }

    private PageRequest getPageRequestByParameters(int page, int size) {
        int DEFAULT_PAGE_SIZE = 5;

        return PageRequest.of(
                page,
                size == 0 ? DEFAULT_PAGE_SIZE : size,
                Sort.by(Sort.Direction.DESC, "id")
        );
    }

    @Override
    @Transactional
    public void updateBook(Long id, BookCreateDto updatedBook) {
        Book bookToUpdate = getBookByIdOrThrowNotFound(id);
        fillBookDataFromDto(updatedBook, bookToUpdate);
        bookRepo.save(bookToUpdate);
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepo.existsById(id))
            throw new NotFoundException("Cannot delete book with id %d. Book doesn't exist".formatted(id));

        bookRepo.deleteById(id);
    }
}
