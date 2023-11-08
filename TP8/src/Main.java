import java.util.*;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.*;

public class Main {
    public static void searchWord(List<String> words) {
        System.out.println(words.stream()
                .collect(groupingBy(w -> w, counting())) // группировка слов по количеству их повторений
                .entrySet().stream()
                .collect(groupingBy(
                        Map.Entry::getValue, // группировка по количеству повторений
                        mapping(Map.Entry::getKey, toList()) // преобразование в список слов
                ))
                .entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getKey)) // нахождение группы с максимальным количеством повторений
                .map(Map.Entry::getValue) // извлечение списка слов с максимальным количеством повторений
                .orElse(Collections.emptyList())
                .stream().collect(groupingBy(String::length)) // группировка слов по их длине
                .entrySet().stream()
                .min(Comparator.comparingInt(Map.Entry::getKey)) // нахождение группы с минимальной длиной слов
                .map(Map.Entry::getValue) // извлечение списка слов с минимальной длиной
                .orElse(Collections.emptyList())
                .stream().collect(Collectors.joining(", ")));
    }
    public static void main(String[] args) {
        System.out.println(" —Задание №1");
        String[] words = {"Чайн", "Сков2", "Роза1", "Чайн", "Ночь", "Улица", "Сков", "Фонарь", "Аптека", "Роза", "Аптека"};
        searchWord(List.of(words));

//ПОФИКСИТЬ ВЫВОД
        System.out.println("=== 2 задание ===");
        List<Product> products = new ArrayList<>(Arrays.asList(
                new Product("Маршмелоу", 24, 150, Product.Country.CHINA),
                new Product("Вафли", 43, 79, Product.Country.INDIA),
                new Product("Бананы", 18, 90, Product.Country.INDIA),
                new Product("Молоко", 50, 75, Product.Country.RUSSIA),
                new Product("Кефир", 26, 67, Product.Country.RUSSIA),
                new Product("Вафли Золотые", 300, 999, Product.Country.INDIA),
                new Product("Сыр", 100, 250, Product.Country.RUSSIA),
                new Product("Семечки", 450, 3, Product.Country.RUSSIA),
                new Product("Ряженка", 5, 80, Product.Country.RUSSIA),
                new Product("Творог (Домашний)", 26, 67, Product.Country.RUSSIA),
                new Product("Разливной Борщ (Русский)", 15, 300, Product.Country.RUSSIA),
                new Product("Сало", 43, 50, Product.Country.RUSSIA),
                new Product("Мороженное", 500, 75, Product.Country.RUSSIA),
                new Product("Снежок", 80, 100, Product.Country.RUSSIA)

        ));
        printNExpensive(products, 3);
    }
    public static void printNExpensive(List<Product> products, int n) {
        System.out.println(products.stream()
                .filter(s->s.getCountry()== Product.Country.RUSSIA)
                .sorted(Comparator.comparing(Product::getQuantity)
                        .thenComparingInt(Product::getPrice).reversed())
                .limit(n)
                .sorted(Comparator.comparingInt(Product::getPrice).reversed())
                .map(Product::getName)
                .collect(joining(", ", n + " самых частых продуктов на складе: ", ";")));
    }
}