import java.awt.Desktop;
import java.net.URI;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LinkShortenerService service = new LinkShortenerService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Регистрация нового пользователя...");
        User user = service.registerUser();
        System.out.println("Ваш UUID: " + user.getId());

        while (true) {
            System.out.println("\nВыберите действие:");
            System.out.println("1. Создать короткую ссылку");
            System.out.println("2. Перейти по короткой ссылке");
            System.out.println("3. Показать мои ссылки");
            System.out.println("4. Выход");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.println("Введите длинный URL:");
                    String longUrl = scanner.nextLine();
                    String shortUrl = service.createShortLink(longUrl, user.getId());
                    System.out.println("Короткая ссылка: " + shortUrl);
                }
                case 2 -> {
                    System.out.println("Введите короткую ссылку:");
                    String shortUrl = scanner.nextLine();
                    String result = service.getOriginalLink(shortUrl);

                    if (result.equals("Ссылка не найдена") || result.equals("Лимит переходов исчерпан") || result.equals("Время жизни ссылки истекло")) {
                        System.out.println("Ошибка: " + result);
                    } else {
                        try {
                            Desktop.getDesktop().browse(new URI(result));
                            System.out.println("Открытие ссылки: " + result);
                        } catch (Exception e) {
                            System.out.println("Ошибка при открытии ссылки: " + e.getMessage());
                        }
                    }
                }
                case 3 -> service.showUserLinks(user.getId());
                case 4 -> {
                    System.out.println("Выход из программы.");
                    return;
                }
                default -> System.out.println("Неверный выбор, попробуйте снова.");
            }
        }
    }
}
