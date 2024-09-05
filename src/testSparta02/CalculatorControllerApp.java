package testSparta02;

import java.util.LinkedList;
import java.util.Queue;

/**
 * CalculatorController 클래스는 사칙연산을 수행하고 연산 결과를 저장하며
 * 가장 오래된 연산 결과를 삭제하는 기능을 제공합니다.
 */
public class CalculatorControllerApp {
    // 연산 결과를 저장하는 컬렉션 타입 필드 (큐를 사용하여 FIFO 방식으로 저장)
    private Queue<Double> results = new LinkedList<>();

    /**
     * 사칙연산을 수행하고 결과를 반환하는 메서드.
     *
     * @param num1 첫 번째 숫자
     * @param num2 두 번째 숫자
     * @param operator 연산 기호 (+, -, *, /)
     * @return 연산 결과
     */
    public double calculate(double num1, double num2, String operator) {
        double result = 0;
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    System.out.println("Error: Division by zero.");
                    return Double.NaN; // 0으로 나누는 경우 NaN 반환
                }
                break;
            default:
                System.out.println("Error: Invalid operator.");
                return Double.NaN; // 잘못된 연산자인 경우 NaN 반환
        }

        // 연산 결과를 저장
        results.add(result);
        return result;
    }

    /**
     * 연산 결과를 저장하는 컬렉션의 가장 오래된 데이터를 삭제하는 메서드.
     */
    public void removeResult() {
        if (!results.isEmpty()) {
            results.poll(); // 가장 오래된 결과 삭제
        } else {
            System.out.println("No results to remove.");
        }
    }

    /**
     * 결과 컬렉션을 가져오는 Getter 메서드.
     *
     * @return 연산 결과 리스트
     */
    public Queue<Double> getResults() {
        return results;
    }
}
