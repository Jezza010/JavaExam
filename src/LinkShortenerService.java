import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Random;

public class LinkShortenerService {
    private final HashMap<String, Link> linkMap = new HashMap<>();
    private final HashMap<String, User> userMap = new HashMap<>();
    private final String baseUrl = "http://short.ly/";

    public User registerUser() {
        User user = new User();
        userMap.put(user.getId(), user);
        return user;
    }

    public String createShortLink(String longUrl, String userId) {
        User user = userMap.get(userId);
        if (user == null) {
            throw new IllegalArgumentException("Пользователь не найден.");
        }

        String shortCode = generateRandomCode();
        String shortUrl = baseUrl + shortCode;

        linkMap.put(shortUrl, new Link(longUrl, 5));
        user.addLink(shortUrl);

        return shortUrl;
    }

    public String getOriginalLink(String shortUrl) {
        Link link = linkMap.get(shortUrl);

        if (link == null) {
            System.out.println("Уведомление: Ссылка не найдена.");
            return "Ссылка не найдена";
        }

        if (Duration.between(link.getCreationTime(), LocalDateTime.now()).toHours() >= 24) {
            linkMap.remove(shortUrl);
            System.out.println("Уведомление: Время жизни ссылки истекло.");
            return "Время жизни ссылки истекло";
        }

        if (link.getClickLimit() <= link.getClickCount()) {
            System.out.println("Уведомление: Лимит переходов исчерпан.");
            return "Лимит переходов исчерпан";
        }

        link.incrementClickCount();
        return link.getOriginalUrl();
    }

    public void showUserLinks(String userId) {
        User user = userMap.get(userId);
        if (user == null) {
            System.out.println("Пользователь не найден.");
            return;
        }

        System.out.println("Ссылки пользователя:");
        for (String link : user.getLinks()) {
            System.out.println(link);
        }
    }

    private String generateRandomCode() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            code.append(chars.charAt(random.nextInt(chars.length())));
        }

        return code.toString();
    }
}
