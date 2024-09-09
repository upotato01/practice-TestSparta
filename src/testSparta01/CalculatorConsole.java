package testSparta01;

import java.util.Scanner;
import java.util.function.BiFunction;

public class CalculatorConsole {

    /**
        1단계: 요구사항 정의 및 설계
        --------------------------------------------

        1. 요구사항 정의:
           - 기본 사칙연산 기능 필요: 덧셈, 뺄셈, 곱셈, 나눗셈.
           - 사용자 인터페이스 방식: 콘솔 기반.
           - 예외 처리:
               - 0으로 나누기 오류 처리: 나눗셈에서 분모가 0일 경우 오류 메시지 출력.
               - 유효하지 않은 연산자 처리: 사용자가 유효하지 않은 연산자를 입력한 경우 오류 메시지 출력.
           - 사용자 입력 반복 처리: 사용자가 계산을 계속할지 종료할지를(`"exit"`) 결정할 수 있도록 해야 함.
           - Enum을 사용하여 사칙연산 정의.
           - 제네릭을 사용하여 숫자 입력 시 다양한 타입 처리.
           - 람다식을 통해 연산 로직을 간단하게 구현.
           - 연산 수행 시 발생 가능한 예외를 처리함.

        2. 설계:
           - 주요 클래스는 `Operation` Enum으로 설계.
           - 절차적 프로그래밍 방식 대신 `Enum`과 `BiFunction`을 사용하여 연산자 및 로직을 캡슐화.
           - 기능 분해:
               - 첫 번째 숫자 입력 받기: 사용자로부터 첫 번째 숫자를 입력받고 유효성 검사.
               - 연산자 입력 받기: Enum을 통해 사칙연산 기호를 처리.
               - 두 번째 숫자 입력 받기: 사용자로부터 두 번째 숫자를 입력받고 유효성 검사.
               - 사칙연산 수행하기: 입력된 연산자에 따라 Enum의 람다식을 사용해 덧셈, 뺄셈, 곱셈, 나눗셈을 수행함.
               - 예외 처리:
                   - 0으로 나누기 오류 처리: 나눗셈 연산에서 분모가 0일 경우 적절한 오류 메시지를 출력함.
                   - 유효하지 않은 연산자 처리: Enum을 사용해 유효하지 않은 연산자를 입력한 경우 오류 메시지를 출력함.
               - 결과 출력하기: 연산 결과를 콘솔에 출력함.
           - 데이터 흐름:
               - 입력: 사용자로부터 첫 번째 숫자, 연산자, 두 번째 숫자를 순서대로 입력받음.
               - 처리: Enum과 람다식을 사용하여 입력된 데이터를 바탕으로 사칙연산을 수행함.
               - 출력: 연산 결과를 콘솔에 출력함.
           - 반복 처리:
               - 사용자가 계산을 계속하고 싶다면 반복문을 통해 계속 입력을 받음.
               - 사용자가 `"exit"`을 입력하면 프로그램을 종료함.
    **/



    // Enum을 사용하여 사칙연산 정의
    public enum Operation {
        ADD("+", (a, b) -> a + b),
        SUBTRACT("-", (a, b) -> a - b),
        MULTIPLY("*", (a, b) -> a * b),
        DIVIDE("/", (a, b) -> {
            if (b == 0) throw new ArithmeticException("0으로 나눌 수 없습니다.");
            return a / b;
        });

        private final String symbol;
        private final BiFunction<Double, Double, Double> operation;

        Operation(String symbol, BiFunction<Double, Double, Double> operation) {
            this.symbol = symbol;
            this.operation = operation;
        }

        public Double apply(Double a, Double b) {
            return operation.apply(a, b);
        }

        public static Operation fromString(String symbol) {
            for (Operation op : Operation.values()) {
                if (op.symbol.equals(symbol)) {
                    return op;
                }
            }
            return null; // 유효하지 않은 연산자일 경우 null 반환
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // 첫 번째 숫자 입력받기
            Double num1 = getInput(scanner, "첫 번째 숫자를 입력하세요 (또는 'exit'로 종료): ");
            if (num1 == null) break;

            // 연산자 입력받기
            System.out.print("연산자를 입력하세요 (+, -, *, /): ");
            String operatorInput = scanner.next();
            Operation operation = Operation.fromString(operatorInput);

            // 유효하지 않은 연산자 처리
            if (operation == null) {
                System.out.println("오류: 유효하지 않은 연산자입니다.");
                continue;
            }

            // 두 번째 숫자 입력받기
            Double num2 = getInput(scanner, "두 번째 숫자를 입력하세요: ");
            if (num2 == null) break;

            try {
                // 연산 수행 및 결과 출력
                Double result = operation.apply(num1, num2);
                System.out.println("결과: " + result);
            } catch (ArithmeticException e) {
                System.out.println(e.getMessage());
            }

            // 계산 반복 여부 확인
            System.out.println("계산을 계속하려면 'y'를 입력하고, 종료하려면 'exit'를 입력하세요.");
            String input = scanner.next();
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("계산기를 종료합니다.");
                break;
            }
        }

        scanner.close();
    }

    // 제네릭을 사용하여 입력을 받아 유효한 숫자를 반환, 'exit' 입력 시 null 반환
    private static Double getInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.next();
            if (input.equalsIgnoreCase("exit")) {
                return null; // 종료 신호
            }

            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("유효하지 않은 숫자입니다. 다시 입력하세요.");
            }
        }
    }
}
