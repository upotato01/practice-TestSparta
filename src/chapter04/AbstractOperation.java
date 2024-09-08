package chapter04;

// 추상 클래스
abstract class AbstractOperation {
    public abstract double operate(double num1, double num2);
}

// 더하기 연산 클래스
class AddOperation extends chapter04.AbstractOperation {
    @Override
    public double operate(double num1, double num2) {
        return num1 + num2;
    }
}

// 빼기 연산 클래스
class SubstractOperation extends chapter04.AbstractOperation {
    @Override
    public double operate(double num1, double num2) {
        return num1 - num2;
    }
}

// 곱하기 연산 클래스
class MultiplyOperation extends chapter04.AbstractOperation {
    @Override
    public double operate(double num1, double num2) {
        return num1 * num2;
    }
}

// 나누기 연산 클래스
class DivideOperation extends chapter04.AbstractOperation {
    @Override
    public double operate(double num1, double num2) {
        if (num2 == 0) {
            throw new ArithmeticException("오류: 0으로 나눌 수 없습니다.");
        }
        return num1 / num2;
    }
}
