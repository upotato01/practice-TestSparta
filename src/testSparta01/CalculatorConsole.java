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

        // 연산 결과를 저장
        setResults(result);
        return result;
    }

    public LinkedList<Integer> getResults() {
        return results;
    }

    public void setResults(int result) {
        if (results.size() == 10) {
            // 10개가 넘으면 가장 오래된 값(첫 번째 값)을 삭제
            results.removeFirst();
        }
        results.add(result);
    }

    public void removeResult() {
        if (!results.isEmpty()) {
            results.removeFirst();
        }
    }

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

    public void printHistoryWithDeleteCheck() {
        LinkedList<Integer> resultsList = getResults(); // getResults()를 사용하여 연산 결과 리스트 가져옴

        if (resultsList.isEmpty()) {
            System.out.println("저장된 연산 결과가 없습니다.");
        } else {
            System.out.println("저장된 연산 기록:");
            for (int result : resultsList) {
                System.out.println(result);
            }

            // 첫 번째 결과 삭제 여부 확인
            System.out.println("첫 번째 연산 결과를 삭제하시겠습니까? (yes/no)");
            Scanner sc = new Scanner(System.in);
            String userResponse = sc.next();

            if (userResponse.equalsIgnoreCase("yes")) {
                int initialSize = resultsList.size(); // 삭제 전 리스트 크기 저장
                removeResult(); // 첫 번째 결과 삭제

                if (resultsList.size() < initialSize) {
                    System.out.println("삭제되었습니다.");
                } else {
                    System.out.println("아직 삭제되지 않았습니다.");
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CalculatorConsole calculator = new CalculatorConsole();
        String continueCalc;

        do {
            System.out.println("계산을 시작하려면 '1', 기록을 보려면 '2', 삭제 확인과 함께 기록을 보려면 '3', 종료하려면 'exit'을 입력하세요:");
            String command = sc.next();

            if (command.equalsIgnoreCase("1")) {

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

            } else if (command.equalsIgnoreCase("2")) {
                calculator.printHistory();

            } else if (command.equalsIgnoreCase("3")) {
                calculator.printHistoryWithDeleteCheck();

            } else if (command.equalsIgnoreCase("exit")) {
                break;

            } else {
                System.out.println("잘못된 명령입니다. 계산을 다시 시작하려면 '1', 기록을 보려면 '2', 삭제 확인과 함께 기록을 보려면 '3', 종료하려면 'exit'을 입력하세요:");
            }

            System.out.println("계속하시겠습니까? (yes를 입력해주세요 exit 입력 시 종료)");
            continueCalc = sc.next();

        } while (!continueCalc.equalsIgnoreCase("exit"));

        sc.close();
    }
}