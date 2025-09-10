import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Fibonacci {
    private int n;
    private List<Long> series;

    public Fibonacci(int n) {
        this.n = n;
        this.series = new ArrayList<>();
        generateFibonacci();
    }

    public List<Long> getSeries() {
        return series;
    }

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

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter how many Fibonacci numbers to generate: ");
        int n = sc.nextInt();

        Fibonacci fibonacci = new Fibonacci(n);

        System.out.println("Fibonacci series: " + fibonacci.getSeries());

        System.out.println("Primes in series: " + fibonacci.getPrimes());
    }
}
