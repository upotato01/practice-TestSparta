package testSparta01;

import java.util.Scanner;

public class CalculatorConsole {
    public static void main(String[] args) {
        /*
        1단계: 요구사항 정의 및 설계
        --------------------------------------------

        1. 요구사항 정의:
           - 기본 사칙연산 기능 필요: 덧셈, 뺄셈, 곱셈, 나눗셈.
           - 사용자 인터페이스 방식: 콘솔 기반.
           - 예외 처리:
               - 0으로 나누기 오류 처리: 나눗셈에서 분모가 0일 경우 오류 메시지 출력.
           - 사용자 입력 반복 처리: 사용자가 계산을 계속 할지 종료할지를(exit) 결정할 수 있도록 해야 함.

        2. 설계:
           - 절차적 프로그래밍 방식으로 구현하며 클래스 설계는 생략.
           - 주요 기능은 main 메서드 내에서 구현함.
           - 기능 분해:
               - 첫 번째 숫자 입력 받기: 사용자로부터 첫 번째 숫자를 입력받고 유효성 검사.
               - 연산자 입력 받기: 사용자로부터 사칙연산 기호를 입력받음.
               - 두 번째 숫자 입력 받기: 사용자로부터 두 번째 숫자를 입력받고 유효성 검사.
               - 사칙연산 수행하기: 입력된 연산자에 따라 덧셈, 뺄셈, 곱셈, 나눗셈을 수행함.
               - 예외 처리:
                   - 0으로 나누기 오류 처리: 나눗셈 연산에서 분모가 0일 경우 적절한 오류 메시지를 출력함.
                   - 유효하지 않은 연산자 처리: 사용자가 유효하지 않은 연산자를 입력한 경우 오류 메시지를 출력함.
               - 결과 출력하기: 연산 결과를 콘솔에 출력함.
           - 데이터 흐름:
               - 입력: 사용자로부터 첫 번째 숫자, 연산자, 두 번째 숫자를 순서대로 입력받음.
               - 처리: 입력된 데이터를 바탕으로 사칙연산을 수행함.
               - 출력: 연산 결과를 콘솔에 출력함.
           - 반복 처리:
               - 사용자가 계산을 계속하고 싶다면 반복문을 통해 계속 입력을 받음.
               - 사용자가 `"exit"`을 입력하면 프로그램을 종료함.
        */

        Scanner scanner = new Scanner(System.in);
        String input;  // 사용자의 입력을 저장할 변수

        // 무한 반복 시작
        while (true) {
            // 사용자로부터 첫 번째 숫자를 입력받음
            System.out.print("첫 번째 숫자를 입력하세요 (또는 'exit'로 종료): ");
            input = scanner.next();

            // 사용자 입력이 'exit'일 경우 반복문 종료
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("계산기를 종료합니다.");
                break;  // 반복문 종료
            }

            double num1;
            // 입력된 값이 유효한 숫자인지 확인
            try {
                num1 = Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("유효하지 않은 숫자입니다. 다시 시도하세요.");
                continue;  // 다시 입력 받기
            }

            // 사용자로부터 연산자를 입력받음
            System.out.print("연산자를 입력하세요 (+, -, *, /): ");
            char operator = scanner.next().charAt(0);

            // 사용자로부터 두 번째 숫자를 입력받음
            System.out.print("두 번째 숫자를 입력하세요: ");
            input = scanner.next();

            // 사용자 입력이 'exit'일 경우 반복문 종료
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("계산기를 종료합니다.");
                break;  // 반복문 종료
            }

            double num2;
            // 입력된 값이 유효한 숫자인지 확인
            try {
                num2 = Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("유효하지 않은 숫자입니다. 다시 시도하세요.");
                continue;  // 다시 입력 받기
            }

            double result = 0;
            boolean validOperation = true;

            // 연산자에 따른 사칙연산 수행
            switch (operator) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                case '/':
                    // 나눗셈에서 분모가 0인지 확인
                    if (num2 != 0) {
                        result = num1 / num2;
                    } else {
                        System.out.println("오류: 0으로 나눌 수 없습니다.");
                        validOperation = false;
                    }
                    break;
                default:
                    // 유효하지 않은 연산자 처리
                    System.out.println("오류: 유효하지 않은 연산자입니다.");
                    validOperation = false;
                    break;
            }

            // 유효한 연산인 경우 결과를 출력
            if (validOperation) {
                System.out.println("결과: " + result);
            }

            // 반복 종료 여부 확인
            System.out.println("계산을 계속하려면 엔터를 누르고, 종료하려면 'exit'를 입력하세요.");
            input = scanner.next();
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("계산기를 종료합니다.");
                break;  // 반복문 종료
            }
        }

        // 자원 해제
        scanner.close();
    }
}
