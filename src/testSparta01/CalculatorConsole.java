package testSparta01;

import java.util.LinkedList;
import java.util.Scanner;

public class CalculatorConsole {
    // 연산 결과를 저장하는 컬렉션 (최대 10개의 값만 저장)
    private LinkedList<Integer> results = new LinkedList<>();

    public int calculate(int num1, int num2, char operator) {
        int result = 0;

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
                if (num2 == 0) {
                    throw new ArithmeticException("나눗셈 연산에서 분모(두번째 정수)에 0이 입력될 수 없습니다.");
                }
                result = num1 / num2;
                break;
            default:
                throw new IllegalArgumentException("잘못된 연산 기호입니다.");
        }

        setResults(result);
        return result;
    }

    // Getter 메서드: 연산 결과 리스트를 가져오는 메서드
    public LinkedList<Integer> getResults() {
        return results;
    }

    // Setter 메서드: 연산 결과 리스트에 값을 추가하는 메서드 (최대 10개까지만 유지)
    public void setResults(int result) {
        if (results.size() == 10) {
            // 10개가 넘으면 가장 오래된 값(첫 번째 값)을 삭제
            results.removeFirst();
        }
        results.add(result);
    }

    // 첫 번째 연산 결과를 삭제하는 메서드
    public void removeResult() {
        if (!results.isEmpty()) {
            results.removeFirst();
        }
    }

    // 연산 기록을 출력하는 메서드 (history 명령 처리)
    public void printHistory() {
        if (results.isEmpty()) {
            System.out.println("저장된 연산 결과가 없습니다.");
        } else {
            System.out.println("저장된 연산 기록:");
            for (int result : results) {
                System.out.println(result);
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CalculatorConsole calculator = new CalculatorConsole();
        String continueCalc;

        do {
            System.out.println("계산을 시작하려면 'start', 기록을 보려면 'history', 종료하려면 'exit'을 입력하세요:");
            String command = sc.next();

            if (command.equalsIgnoreCase("start")) {

                System.out.print("첫 번째 숫자를 입력하세요: ");
                int num1 = sc.nextInt();


                System.out.print("사칙연산 기호를 입력하세요 ( +, -, *, / ): ");
                char operator = sc.next().charAt(0);


                System.out.print("두 번째 숫자를 입력하세요: ");
                int num2 = sc.nextInt();


                try {

                    int result = calculator.calculate(num1, num2, operator);
                    System.out.println("결과: " + result);

                } catch (ArithmeticException | IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }

                System.out.println("현재까지 저장된 연산 결과들: " + calculator.getResults());

                System.out.println("첫 번째 연산 결과를 삭제하시겠습니까? (yes/no)");
                String removeResult = sc.next();
                if (removeResult.equalsIgnoreCase("yes")) {
                    calculator.removeResult();
                    System.out.println("첫 번째 결과가 삭제되었습니다.");
                }

            } else if (command.equalsIgnoreCase("history")) {
                calculator.printHistory();

            } else if (command.equalsIgnoreCase("exit")) {
                break;

            } else {
                System.out.println("잘못된 명령입니다. 'start', 'history', 또는 'exit'을 입력하세요.");
            }

            System.out.println("계속하시겠습니까? yes를 입력해주세요 (exit 입력 시 종료)");
            continueCalc = sc.next();

        } while (!continueCalc.equalsIgnoreCase("exit"));

        sc.close();
    }
}
