package model;

public class Book {
    private String bookId;
    private String title;
    private String author;
    private BookStatus status;
    public Book(String bookId, String title, String author){
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.status = BookStatus.AVAILABLE;
    }

    public String getBookId(){return bookId;}
    public String getTitle(){return title;}
    public String getAuthor() { return author; }

    public void setStatus(BookStatus status){
        this.status = status;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId='" + bookId + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", status=" + status +
                '}';
    }


    public Object getStatus() {
        return status;
    }

    public int compareTo(Book other) {
        return this.title.compareToIgnoreCase(other.title);
    }
}




