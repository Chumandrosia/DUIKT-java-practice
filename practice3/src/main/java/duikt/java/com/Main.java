package duikt.java.com;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calculator calculator = new Calculator();

        try {
            System.out.print("Enter the first number: ");
            double num1 = scanner.nextDouble();

            System.out.print("Enter the operation (+, -, *, /, sqrt): ");
            String operation = scanner.next();

            double num2 = 0;
            if (!operation.equals("sqrt")) {
                System.out.print("Enter the second number: ");
                num2 = scanner.nextDouble();
            }

            double result = 0;

            switch (operation) {
                case "+":
                    result = calculator.add(num1, num2);
                    break;
                case "-":
                    result = calculator.subtract(num1, num2);
                    break;
                case "*":
                    result = calculator.multiply(num1, num2);
                    break;
                case "/":
                    result = calculator.divide(num1, num2);
                    break;
                case "sqrt":
                    result = calculator.sqrt(num1);
                    break;
                default:
                    System.out.println("Unknown operation. Terminating.");
                    return;
            }

            System.out.println("Result: " + result);

        } catch (InputMismatchException e) {
            System.err.println("Input error: You entered not a number. Please enter only numbers.");

        } catch (ArithmeticException e) {
            System.err.println("Arithmetic error: " + e.getMessage());

        } catch (InvalidInputException e) {
            System.err.println("Input data error: " + e.getMessage());

        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());

        } finally {
            System.out.println("\n===== Request processing completed. =====");
            scanner.close();
        }
    }
}