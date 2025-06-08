package model;

import java.time.LocalDate;

public class LendingRecord {
    private String recordId;
    private Book book;
    private Member member;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public LendingRecord
            (String recordId,
             Book book,
             Member member,
             LocalDate issueDate,
             LocalDate dueDate){
        this.recordId = recordId;
        this.book = book;
        this.member = member;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = null;
    }
    public String getRecordId() { return recordId; }
    public Book getBook() { return book; }
    public Member getMember() { return member; }
    public LocalDate getIssueDate() { return issueDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }

    public void setReturnDate(LocalDate returnDate){
        this.returnDate = returnDate;
        book.setStatus(BookStatus.AVAILABLE);
    }

    public boolean isOverdue(){
        return LocalDate.now().isAfter(dueDate);
    }


    @Override
    public String toString() {
        return "LendingRecord{" +
                "recordId='" + recordId + '\'' +
                ", book=" + book +
                ", member=" + member +
                ", issueDate=" + issueDate +
                ", dueDate=" + dueDate +
                ", returnDate=" + returnDate +
                '}';
    }


}
