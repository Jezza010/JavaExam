import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    private final String id;
    private final List<String> links;

    public User() {
        this.id = UUID.randomUUID().toString();
        this.links = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public List<String> getLinks() {
        return links;
    }

    public void addLink(String shortUrl) {
        links.add(shortUrl);
    }
}
