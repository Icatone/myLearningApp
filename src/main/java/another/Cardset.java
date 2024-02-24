package another;

public class Cardset {
    public String id;
    public String name;
    public String description;
    public String author_login;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor_login() {
        return author_login;
    }

    public String getAuthor_nickname() {
        return author_nickname;
    }

    public String author_nickname;

    public Cardset(String id, String name, String description, String author_login,String author_nickname) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.author_login = author_login;
        this.author_nickname = author_nickname;
    }
}
