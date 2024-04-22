package com.company.primenumber;


import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class PrimeNumberGenerator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int start =0;
        int end =0;
        //to check if user providing valid integer or not
        while (true) {
            try {
                System.out.print("Enter starting number: ");
                start = scanner.nextInt();
                break; // Break the loop if inputis a valid integer
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next();
            }
        }

        while (true) {
            try {
                System.out.print("Enter ending number: ");
                end = scanner.nextInt();
                break; // Break the loop if input is a valid integer
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, Please enter a valid integer.");
                scanner.next();
            }
        }
        System.out.println("Select prime number generation strategy:");
        System.out.println("1. Sieve of Eratosthenes");
        System.out.println("2. sieve Of Atkin");
        System.out.println("3. Trial Division");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        List<Integer> primes = new ArrayList<>();
        switch (choice) {
            case 1:
                primes = sieveOfEratosthenes(start, end);
                System.out.println("Its time complexity is ~ O(n*log(log(n)))");

                break;
            case 2:
                primes = sieveOfAtkin(start, end);
                System.out.println("Its time complexity is ~ O(n/log(log(n)))");
                break;
            case 3:
                primes = trialDivision(start, end);
                System.out.println("Its time complexity is ~ O(sqrt(n)) ");
                break;
            default:
                System.out.println("Invalid choice.");
        }

        System.out.println("Prime numbers between " + start + " and " + end + ": ");
        for (int prime : primes) {
            System.out.print(prime + " ");
        }
    }

    //has second best timecomplexity amongs all three

    private static List<Integer> sieveOfEratosthenes(int start, int end) {
        boolean[] primes = new boolean[end + 1];
        List<Integer> result = new ArrayList<>();
        for (int p = 2; p * p <= end; p++) {
            if (!primes[p]) {
                for (int i = p * p; i <= end; i += p) {
                    primes[i] = true;
                }
            }
        }
        for (int i = Math.max(2, start); i <= end; i++) {
            if (!primes[i]) {
                result.add(i);
            }
        }
        return result;
    }

    private static List<Integer> trialDivision(int start, int end) {
        List<Integer> result = new ArrayList<>();
        for (int i = Math.max(2, start); i <= end; i++) {
            if (isPrime(i)) {
                result.add(i);
            }
        }
        return result;
    }

    private static boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }


    //has best timecomplexity amongs all three
    public static List<Integer> sieveOfAtkin(int start, int end) {
        List<Integer> primes = new ArrayList<>();

        if (start <= 2) {
            primes.add(2);
        }
        if (start <= 3) {
            primes.add(3);
        }

        boolean[] sieve = new boolean[end + 1];
        for (int i = 1; i * i <= end; i++) {
            for (int j = 1; j * j <= end; j++) {
                int n = (4 * i * i) + (j * j);
                if (n <= end && (n % 12 == 1 || n % 12 == 5)) {
                    sieve[n] ^= true;
                }
                n = (3 * i * i) + (j * j);
                if (n <= end && n % 12 == 7) {
                    sieve[n] ^= true;
                }
                n = (3 * i * i) - (j * j);
                if (i > j && n <= end && n % 12 == 11)    {
                    sieve[n] ^= true;
                }
            }
        }

        for (int i = 5; i * i <= end; i++)   {
            if (sieve[i]) {
                for (int j = i * i; j <= end; j += i * i)  {
                    sieve[j] = false;
                }
            }
        }
        for (int i = start; i <= end; i++) {
            if (sieve[i]) {
                primes.add(i);
            }
        }

        return primes;
    }
}
