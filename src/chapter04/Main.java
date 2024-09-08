package chapter04;

public class Main {
    public static void main(String[] args) {
        boolean calculateEnded = false;
        while (!calculateEnded) {
            try {
                calculateEnded = CalculatorApp.start();
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("알 수 없는 에러가 발생했습니다.");
            }
        }
    }
}
