package testSparta01;

import java.util.LinkedList;
import java.util.Scanner;

public class CalculatorConsole {
    private final LinkedList<Double> result = new LinkedList<>();
    private String operation;
    private int num1;
    private int num2;
    private int error;

    // 결과값 반환
    public double getResult() {
        return result.isEmpty() || error == 1 ? 0 : result.getLast();
    }

    // 연산 셋팅 및 계산 수행
    public void setCalc(String operation, int num1, int num2) {
        this.operation = operation;
        this.num1 = num1;
        this.num2 = num2;
        calculate();
    }

    // 가장 오래된 결과값 삭제
    public void deleteOldest() {
        if (!result.isEmpty()) {
            System.out.println("가장 오래된 값 " + result.removeFirst() + " 이(가) 삭제되었습니다.");
        } else {
            System.out.println("저장된 값이 없습니다.");
        }
    }

    // 저장된 결과값 확인
    public LinkedList<Double> record() {
        return result;
    }

    // 연산 수행 및 결과값 저장
    private void calculate() {
        error = 0;
        double sum = 0;
        switch (operation) {
            case "+":
                sum = num1 + num2;
                break;
            case "-":
                sum = num1 - num2;
                break;
            case "*":
                sum = num1 * num2;
                break;
            case "/":
                if (num2 == 0) {
                    System.out.println("0으로 나눌 수 없습니다.");
                    error = 1;
                    return;
                }
                sum = (double) num1 / num2;
                break;
            default:
                System.out.println("잘못된 연산 기호입니다.");
                error = 1;
                return;
        }
        addResult(sum);
    }

    // 결과값 저장 및 10개 초과 시 가장 오래된 값 삭제
    private void addResult(double resultValue) {
        if (result.size() >= 10) {
            deleteOldest();
        }
        result.add(resultValue);
    }

    // 메인 메서드
    public static void main(String[] args) {
        CalculatorConsole calc = new CalculatorConsole();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("종료하려면 exit, 계속하려면 아무 키나 입력:");
            if ("exit".equalsIgnoreCase(sc.next())) break;

            int num1 = getInputValue(sc, "첫 번째 값을 입력해주세요: ");
            if (num1 < 0) continue;

            System.out.print("연산기호를 입력해주세요 (+, -, *, /): ");
            String operation = sc.next();

            int num2 = getInputValue(sc, "두 번째 값을 입력해주세요: ");
            if (num2 <= 0) continue;

            calc.setCalc(operation, num1, num2);

            System.out.println("결과: " + calc.getResult());

            System.out.print("계속 진행(1) / 종료(2) / 삭제(3) 번호 입력: ");
            switch (sc.next()) {
                case "1":
                    break;
                case "2":
                    sc.close();
                    return;
                case "3":
                    calc.deleteOldest();
                    System.out.println("현재 저장된 값: " + calc.record());
                    break;
                default:
                    System.out.println("잘못 입력하셨습니다.");
            }
        }
    }

    // 사용자 입력을 받는 헬퍼 메서드
    private static int getInputValue(Scanner sc, String prompt) {
        System.out.print(prompt);
        try {
            int value = Integer.parseInt(sc.next());
            if (value < 0) {
                System.out.println("0 미만의 수는 입력할 수 없습니다.");
                return -1;
            }
            return value;
        } catch (NumberFormatException e) {
            System.out.println("유효한 숫자를 입력해주세요.");
            return -1;
        }
    }
}
