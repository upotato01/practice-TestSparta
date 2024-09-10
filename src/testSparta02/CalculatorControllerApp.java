package testSparta02;

import java.util.LinkedList;
import java.util.Queue;

/**
 * CalculatorController 클래스는 사칙연산을 수행하고,
 * 연산 결과를 저장하며, 결과 이력을 관리합니다.
 */
public class CalculatorControllerApp {
    // 계산 결과를 저장하는 큐 (FIFO 방식 사용)
    private Queue<Double> results = new LinkedList<>();
    private static final int MAX_RESULTS = 10; // 저장할 최대 결과 수

    /**
     * 주어진 연산자에 따라 사칙연산을 수행하고 결과를 반환합니다.
     *
     * @param num1     첫 번째 숫자.
     * @param num2     두 번째 숫자.
     * @param operator 연산자 (+, -, *, /).
     * @return 연산 결과, 오류가 발생하면 Double.NaN 반환.
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
                    System.err.println("오류: 0으로 나눌 수 없습니다.");
                    return Double.NaN; // 0으로 나눌 경우 NaN 반환
                }
                break;
            default:
                System.err.println("오류: 잘못된 연산자입니다.");
                return Double.NaN; // 잘못된 연산자인 경우 NaN 반환
        }

        // 결과를 저장하고 큐 크기를 관리합니다.
        addResult(result);
        return result;
    }

    /**
     * 결과를 큐에 추가하고 큐의 크기 제한을 관리합니다.
     *
     * @param result 추가할 결과.
     */
    private void addResult(double result) {
        if (results.size() >= MAX_RESULTS) {
            results.poll(); // 큐가 가득 찼을 경우 가장 오래된 결과 삭제
        }
        results.add(result);
    }

    public double getLastResult() {
        return results.isEmpty() ? Double.NaN : results.peek();
    }
}