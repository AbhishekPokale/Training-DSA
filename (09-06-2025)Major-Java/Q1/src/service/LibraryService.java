package service;

import exception.BookNotAvailableException;
import exception.MemberNotFoundException;
import exception.OverdueBookException;
import model.Book;
import model.BookStatus;
import model.LendingRecord;
import model.Member;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class LibraryService {
    private final Map<String, Book> books = new HashMap<>();
    private final Map<String, Member> members = new HashMap<>();
    private final List<LendingRecord> lendingRecords = new ArrayList<>();

    public void addBook(Book book) {
        books.put(book.getBookId(), book);
    }

    public void removeBook(String bookId) {
        books.remove(bookId);
    }

    public void addMember(Member member) {
        members.put(member.getMemberId(), member);
    }

    public void removeMember(String memberId) {
        members.remove(memberId);
    }
    public List<Book> getAvailableBooks() {
        return books.values().stream()
                .filter(b -> b.getStatus() == BookStatus.AVAILABLE)
                .collect(Collectors.toList());
    }

    public List<Member> getBorrowingMembers() {
        return lendingRecords.stream()
                .filter(r -> r.getReturnDate() == null)
                .map(LendingRecord::getMember)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<LendingRecord> getOverdueRecords() {
        return lendingRecords.stream()
                .filter(r -> r.getReturnDate() == null && LocalDate.now().isAfter(r.getDueDate()))
                .collect(Collectors.toList());
    }


    public void issueBook(String bookId, String memberId, LocalDate issueDate, LocalDate dueDate)
            throws BookNotAvailableException, MemberNotFoundException, OverdueBookException {
        Book book = books.get(bookId);
        Member member = members.get(memberId);

        if (book == null || book.getStatus() == BookStatus.ISSUED) {
            throw new BookNotAvailableException("Book with ID " + bookId + " is not available.");
        }

        if (member == null) {
            throw new MemberNotFoundException("Member with ID " + memberId + " not found.");
        }

        boolean hasOverdue = lendingRecords.stream()
                .anyMatch(r -> r.getMember().getMemberId().equals(memberId) && r.isOverdue());

        if (hasOverdue) {
            throw new OverdueBookException("Member with ID " + memberId + " has overdue books.");
        }

        book.setStatus(BookStatus.ISSUED);
        String recordId = "R" + (lendingRecords.size() + 1);
        LendingRecord record = new LendingRecord(recordId, book, member, issueDate, dueDate);
        lendingRecords.add(record);
    }

    public void returnBook(String recordId, LocalDate returnDate) {
        for (LendingRecord r : lendingRecords) {
            if (r.getRecordId().equals(recordId) && r.getReturnDate() == null) {
                r.setReturnDate(returnDate);
                r.getBook().setStatus(BookStatus.AVAILABLE);
                break;
            }
        }
    }

    public List<Book> findBooksByAuthor(String author) {
        return books.values().stream()
                .filter(b -> b.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }

    public List<Book> findBooksByTitle(String title) {
        return books.values().stream()
                .filter(b -> b.getTitle().equalsIgnoreCase(title))
                .collect(Collectors.toList());
    }

    public List<Member> findMembers(String keyword) {
        return members.values().stream()
                .filter(m -> m.getName().equalsIgnoreCase(keyword) || m.getEmail().equalsIgnoreCase(keyword))
                .collect(Collectors.toList());
    }

    public List<Book> getBooksSortedByTitle() {
        return books.values().stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Book> getBooksSortedByAuthor() {
        return books.values().stream()
                .sorted(Comparator.comparing(Book::getAuthor))
                .collect(Collectors.toList());
    }

    public List<LendingRecord> getLendingRecords() {
        return lendingRecords;
    }
}