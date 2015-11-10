package structure;

import android.graphics.Bitmap;

public class Book {

    private final String title;
    private final String description;
    private final String ID;
    private final String author;
    private final String criterion;
    private final String genre;
    private final String date;
    private final String userCategory;
    private final Bitmap image;

    public static class BookBuilder{
        private String title;
        private String description;
        private String ID;
        private String author;
        private String criterion;
        private String genre;
        private String date;
        private String userCategory;
        private Bitmap image;

        public BookBuilder(){
        }
        public BookBuilder title(String s){
            title=s;
            return this;
        }
        public BookBuilder userCategory(String s){
            userCategory=s;
            return this;
        }
        public BookBuilder genre(String s){
            genre=s;
            return this;
        }
        public BookBuilder criterion(String s){
            criterion=s;
            return this;
        }
        public BookBuilder ID(String s){
            ID=s;
            return this;
        }
        public BookBuilder description(String s){
            description=s;
            return this;
        }
        public BookBuilder author(String s){
            author=s;
            return this;
        }
        public BookBuilder date(String s)
        {
            date = s;
            return this;
        }
        public BookBuilder image(Bitmap s){
            image=s;
            return this;
        }
        public Book build(){
            return new Book(this);
        }
    }


    private Book(BookBuilder builder){

        title = builder.title;
        description = builder.description;
        ID = builder.ID;
        author = builder.author;
        criterion = builder.criterion;
        genre = builder.genre;
        date = builder.date;
        userCategory = builder.userCategory;
        image=builder.image;
    }
}

