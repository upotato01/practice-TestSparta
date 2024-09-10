package testSparta02;

import java.util.LinkedList;
import java.util.Queue;

public class CalculatorControllerApp {
    // 계산 결과를 저장하는 큐 (FIFO 방식 사용)
    private Queue<Double> results = new LinkedList<>();
    private static final int MAX_RESULTS = 10; // 저장할 최대 결과 수

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
                    return Double.NaN;
                }
                break;
            default:
                System.err.println("오류: 잘못된 연산자입니다.");
                return Double.NaN;
        }

        addResult(result);
        return result;
    }

    private void addResult(double result) {
        if (results.size() >= MAX_RESULTS) {
            results.poll();
        }
        results.add(result);
    }

    public double getLastResult() {
        return results.isEmpty() ? Double.NaN : results.peek();
    }
}