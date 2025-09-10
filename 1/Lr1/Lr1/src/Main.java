import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Клас Fibonacci генерує ряд чисел Фібоначчі та визначає прості числа серед них.
 * Містить методи для отримання всього ряду та окремо простих чисел.
 *
 * @author Dmytro Daniuk
 * @version 1.0
 */
class Fibonacci {
    /** Кількість чисел Фібоначчі для генерації */
    private int n;

    /** Список для збереження згенерованих чисел Фібоначчі */
    private List<Long> series;

    /**
     * Конструктор класу Fibonacci.
     * Генерує ряд чисел Фібоначчі заданої довжини.
     *
     * @param n кількість чисел для генерації
     */
    public Fibonacci(int n) {
        this.n = n;
        this.series = new ArrayList<>();
        generateFibonacci();
    }

    /**
     * Повертає список усіх згенерованих чисел Фібоначчі.
     *
     * @return список Long чисел Фібоначчі
     */
    public List<Long> getSeries() {
        return series;
    }

    /**
     * Генерує ряд чисел Фібоначчі та зберігає його у списку series.
     * Використовується внутрішньо, не доступний ззовні.
     */
    private void generateFibonacci() {
        if (n <= 0) return;

        series.add(0L);
        if (n == 1) return;

        series.add(1L);
        for (int i = 2; i < n; i++)
        {
            long next = series.get(i - 1) + series.get(i - 2);
            series.add(next);
        }
    }

    /**
     * Повертає список простих чисел серед згенерованого ряду Фібоначчі.
     *
     * @return список Long простих чисел
     */
    public List<Long> getPrimes()
    {
        List<Long> primes = new ArrayList<>();
        for (long num : series)
        {
            if (isPrime(num))
            {
                primes.add(num);
            }
        }
        return primes;
    }

    /**
     * Перевіряє, чи є число простим.
     *
     * @param num число для перевірки
     * @return true, якщо число просте, false інакше
     */
    private boolean isPrime(long num)
    {
        if (num < 2) return false;
        for (long i = 2; i <= Math.sqrt(num); i++)
        {
            if (num % i == 0) return false;
        }
        return true;
    }
}

/**
 * Головний клас програми.
 * Зчитує від користувача кількість чисел Фібоначчі для генерації,
 * виводить весь ряд та прості числа серед нього.
 */
public class Main {
    /**
     * Точка входу в програму.
     *
     * @param args аргументи командного рядка (не використовуються)
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter how many Fibonacci numbers to generate: ");
        int n = sc.nextInt();

        Fibonacci fibonacci = new Fibonacci(n);

        System.out.println("Fibonacci series: " + fibonacci.getSeries());
        System.out.println("Primes in series: " + fibonacci.getPrimes());
    }
}
