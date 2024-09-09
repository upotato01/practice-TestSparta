package chapter01;

import java.util.Scanner;

public class FirstHomework {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // 제목 입력 받기
        System.out.print("제목을 입력해주세요: ");
        String title = scanner.nextLine();

        // 평가 별점 입력 받기
        System.out.print("평가 별점을 1~5점 중 선택해주세요: ");
        double gradeGoal = scanner.nextDouble();

        // 개행 문자 처리
        scanner.nextLine(); // nextDouble 후 남아있는 개행 문자를 처리해줍니다.

        // 10줄의 문자열을 저장할 배열 초기화
        String[] lineNumber = new String[10];

        // 10줄의 입력을 배열에 저장
        for (int i = 0; i < 10; i++) {
            System.out.print((i + 1) + "번째 줄에 입력할 조리 방법을 타이핑해주세요: ");
            lineNumber[i] = scanner.nextLine();
        }

        // 제목 출력
        System.out.println("\n[ " + title + " ]");

        // 별점 출력
        System.out.println("별점 : " + gradeGoal + " (" + (gradeGoal * 20) + "%)");

        // 각 줄 출력
        for (int i = 0; i < 10; i++) {
            System.out.println((i + 1) + ". " + lineNumber[i]);
        }

        scanner.close();
    }
}
