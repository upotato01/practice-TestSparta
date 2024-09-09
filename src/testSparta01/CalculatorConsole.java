package testSparta01;

import java.util.*;

public class CalculatorConsole {
    private final ArrayList<Double> result = new ArrayList<>();
    private String operation;
    private int num1;
    private int num2;
    private int error;

    // 결과값 반환
    public double getResult() {
        return result.isEmpty() || error == 1 ? 0 : result.get(result.size() - 1);
    }

    // 연산 셋팅
    public void setCalc(String operation, int num1, int num2) {
        this.operation = operation;
        this.num1 = num1;
        this.num2 = num2;
        calculate();
    }

    // 값 삭제
    public void delete(int set, double num) {
        boolean success;
        if (!result.contains(num)) {
            System.out.println("해당 값은 존재하지 않습니다.");
            return;
        }

        if (set == 1) { // 중복값 포함 삭제
            success = result.removeAll(Collections.singleton(num));
        } else { // 중복값 미포함 삭제
            success = result.remove(Double.valueOf(num));
        }

        if (success) {
            System.out.println("정상적으로 삭제되었습니다. 현재 저장된 값: " + record());
        }
    }

    // 저장된 값 확인
    public ArrayList<Double> record() {
        return result;
    }

    // 연산 수행
    private void calculate() {
        error = 0;
        switch (operation) {
            case "+":
                result.add((double) num1 + num2);
                break;
            case "-":
                result.add((double) num1 - num2);
                break;
            case "*":
                result.add((double) num1 * num2);
                break;
            case "/":
                if (num2 == 0) {
                    System.out.println("0으로 나눌 수 없습니다.");
                    error = 1;
                } else {
                    result.add((double) num1 / num2);
                }
                break;
            default:
                System.out.println("잘못된 연산 기호입니다.");
                error = 1;
        }
    }

    public static void main(String[] args) {
        CalculatorConsole calc = new CalculatorConsole();
        Scanner sc = new Scanner(System.in);
        boolean checking = true;

        while (checking) {
            System.out.print("연산기호를 입력해주세요 (+, -, *, /): ");
            String operation = sc.nextLine();

            int num1 = getInputValue(sc, "첫 번째 값을 입력해주세요: ");
            if (num1 < 0) continue;

            int num2 = getInputValue(sc, "두 번째 값을 입력해주세요: ");
            if (num2 <= 0) continue;

            calc.setCalc(operation, num1, num2);

            System.out.println("결과: " + calc.getResult());

            System.out.print("계속 진행(1) / 종료(2) / 삭제(3) 번호 입력: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    break; // 계속 진행
                case "2":
                    checking = false; // 종료
                    break;
                case "3":
                    System.out.println("현재 저장된 값: " + calc.record());

                    System.out.print("삭제할 값을 입력하세요: ");
                    double delnum = Double.parseDouble(sc.nextLine());

                    System.out.print("중복값 포함(1) / 미포함(2) 번호 입력: ");
                    int setOption = Integer.parseInt(sc.nextLine());

                    calc.delete(setOption, delnum);
                    break;
                default:
                    System.out.println("잘못 입력하셨습니다. 처음으로 돌아갑니다.");
            }
        }

        sc.close(); // 리소스 해제
    }

    // 사용자 입력을 받는 헬퍼 메서드
    private static int getInputValue(Scanner sc, String prompt) {
        System.out.print(prompt);
        try {
            int value = Integer.parseInt(sc.nextLine());
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
