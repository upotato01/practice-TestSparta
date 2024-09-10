package testSparta01;

import java.util.*;

public class CalculatorConsole {
    private final ArrayList<Double> result = new ArrayList<>();
    private char operation;
    private int num1;
    private int num2;
    private int error;

    public double getResult() {
        return result.isEmpty() || error == 1 ? 0 : result.get(result.size() - 1);
    }

    public void setCalc(char operation, int num1, int num2) {
        this.operation = operation;
        this.num1 = num1;
        this.num2 = num2;
        calculate();
    }

    public void delete(int set, double num) {
        boolean success = false;
        if (!result.contains(num)) {
            System.out.println("해당 값은 존재하지 않습니다.");
            return;
        }

        if (set == 1) {
            success = result.removeAll(Collections.singleton(num));
        } else if (set == 2) {
            success = result.remove(Double.valueOf(num));
        } else {
            System.out.println("값을 잘못 입력했습니다.");
            return;
        }

        if (success) {
            System.out.println("정상적으로 삭제되었습니다. 현재 저장된 값: " + record());
        } else {
            System.out.println("삭제에 실패했습니다.");
        }
    }

    public ArrayList<Double> record() {
        return result;
    }

    private void calculate() {
        switch (operation) {
            case '+':
                result.add((double) num1 + num2);
                break;
            case '-':
                result.add((double) num1 - num2);
                break;
            case '*':
                result.add((double) num1 * num2);
                break;
            case '/':
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
                break;
        }
    }

    public static void main(String[] args) {
        CalculatorConsole calc = new CalculatorConsole();
        Scanner sc = new Scanner(System.in);
        boolean checking = true;

        while (checking) {
            int num1 = getInputValue(sc, "첫 번째 값을 입력해주세요: ");
            if (num1 < 0) continue;

            System.out.print("연산기호를 입력해주세요 (+, -, *, /): ");
            String operationStr = sc.nextLine().trim();

            if (operationStr.length() != 1 || !"+-*/".contains(operationStr)) {
                System.out.println("잘못된 연산 기호입니다. 다시 입력해주세요.");
                continue;
            }

            char operation = operationStr.charAt(0);

            int num2 = getInputValue(sc, "두 번째 값을 입력해주세요: ");
            if (num2 < 0) continue;

            calc.setCalc(operation, num1, num2);

            System.out.println("결과: " + calc.getResult());

            System.out.print("계속 진행(1) / 종료(2) / 삭제(3) 번호 입력: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    break;
                case "2":
                    checking = false;
                    break;
                case "3":
                    System.out.println("현재 저장된 값: " + calc.record());

                    System.out.print("삭제할 값을 입력하세요: ");
                    double delnum = Double.parseDouble(sc.nextLine().trim());

                    System.out.print("중복값 포함(1) / 미포함(2) 번호 입력: ");
                    int setOption = Integer.parseInt(sc.nextLine().trim());

                    calc.delete(setOption, delnum);
                    break;
                default:
                    System.out.println("잘못된 입력입니다. 처음으로 돌아갑니다.");
            }
        }

        sc.close();
    }

    private static int getInputValue(Scanner sc, String prompt) {
        System.out.print(prompt);
        try {
            int value = Integer.parseInt(sc.nextLine().trim());
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
