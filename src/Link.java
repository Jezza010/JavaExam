import java.time.LocalDateTime;

public class Link {
    private final String originalUrl;
    private final LocalDateTime creationTime;
    private final int clickLimit;
    private int clickCount;

    public Link(String originalUrl, int clickLimit) {
        this.originalUrl = originalUrl;
        this.creationTime = LocalDateTime.now();
        this.clickLimit = clickLimit;
        this.clickCount = 0;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public int getClickLimit() {
        return clickLimit;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void incrementClickCount() {
        this.clickCount++;
    }
}
