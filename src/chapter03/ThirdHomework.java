package chapter03;

import java.util.Scanner;

public class ThirdHomework {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calculator calculator = new Calculator(null); // 초기화 시에는 null로 설정

        while (true) {
            System.out.print("첫 번째 숫자를 입력하세요 (또는 'exit'로 종료): ");
            String input = scanner.next();
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("계산기를 종료합니다.");
                break;
            }

            double num1 = parseInputToDouble(input);
            if (Double.isNaN(num1)) {
                System.out.println("유효하지 않은 숫자입니다. 다시 시도하세요.");
                continue;
            }

            System.out.print("연산자를 입력하세요 (+, -, *, /): ");
            char operator = scanner.next().charAt(0);

            // 사용자가 입력한 연산자에 따라 연산 클래스 설정
            switch (operator) {
                case '+':
                    calculator.setOperation(new AddOperation());
                    break;
                case '-':
                    calculator.setOperation(new SubstractOperation());
                    break;
                case '*':
                    calculator.setOperation(new MultiplyOperation());
                    break;
                case '/':
                    calculator.setOperation(new DivideOperation());
                    break;
                default:
                    System.out.println("오류: 유효하지 않은 연산자입니다.");
                    continue;
            }

            System.out.print("두 번째 숫자를 입력하세요: ");
            input = scanner.next();
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("계산기를 종료합니다.");
                break;
            }

            double num2 = parseInputToDouble(input);
            if (Double.isNaN(num2)) {
                System.out.println("유효하지 않은 숫자입니다. 다시 시도하세요.");
                continue;
            }

            try {
                // 주입된 연산 클래스를 통해 연산 수행
                double result = calculator.calculate(num1, num2);
                System.out.println("결과: " + result);
            } catch (ArithmeticException e) {
                System.out.println(e.getMessage());
            }

            System.out.println("계산을 계속하려면 아무 키나 누르고, 종료하려면 'exit'을 입력하세요.");
            input = scanner.next();
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("계산기를 종료합니다.");
                break;
            }
        }

        scanner.close();
    }

    // 입력 값을 double로 변환하는 메서드
    private static double parseInputToDouble(String input) {
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return Double.NaN;
        }
    }
}
