package testSparta03;

import java.util.ArrayList;
import java.util.Collections;

public class BaseballGameLogic {
    private int[] randomNumbers;

    public BaseballGameLogic() {
        resetGame();
    }

    // 랜덤한 3개의 숫자를 생성하는 메소드
    public void resetGame() {
        randomNumbers = generateRandomNumbers();
    }

    private int[] generateRandomNumbers() {
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        return new int[]{numbers.get(0), numbers.get(1), numbers.get(2)};
    }

    // 결과를 확인하는 메소드
    public String checkResult(int[] userNumbers) {
        int strikes = 0;
        int balls = 0;

        for (int i = 0; i < 3; i++) {
            if (userNumbers[i] == randomNumbers[i]) {
                strikes++;
            } else if (contains(randomNumbers, userNumbers[i])) {
                balls++;
            }
        }

        if (strikes == 3) {
            return "정답입니다! 3 스트라이크로 게임이 끝났습니다!";
        } else {
            return "스트라이크: " + strikes + ", 볼: " + balls;
        }
    }

    // 배열에 특정 숫자가 포함되어 있는지 확인하는 메소드
    private boolean contains(int[] arr, int num) {
        for (int value : arr) {
            if (value == num) {
                return true;
            }
        }
        return false;
    }
}