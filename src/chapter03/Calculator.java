package chapter03;

public class Calculator {
    private AbstractOperation operation;

    // 연산 클래스를 주입받는 생성자
    public Calculator(AbstractOperation operation) {
        this.operation = operation;
    }

    // 연산 클래스를 변경할 수 있는 Setter 메서드
    public void setOperation(AbstractOperation operation) {
        this.operation = operation;
    }

    // 주입받은 연산 클래스를 사용하여 연산 수행
    public double calculate(double num1, double num2) {
        return operation.operate(num1, num2);
    }
}
