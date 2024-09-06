package testSparta02;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorGUI implements ActionListener {

    /*
        1단계: 요구사항 정의 및 설계
        --------------------------------------------

        1. 요구사항 정의:
           - 기본 사칙연산 기능 필요: 덧셈, 뺄셈, 곱셈, 나눗셈.
           - 사용자 인터페이스 방식: GUI 기반 (Swing 사용).
           - 추가 기능:
               - 소수점 입력 기능.
               - 결과 초기화 기능 (C 버튼).
               - 마지막 입력 삭제 기능 (← 버튼).
           - 예외 처리:
               - 0으로 나누기 오류 처리.
               - 연산 중 잘못된 입력 처리.

        2. 설계:
           - 주요 클래스: Calculator01
               - JFrame: 메인 창 구성.
               - JTextField: 입력 및 결과 출력.
               - JPanel: 버튼 배치 및 레이아웃 관리.
               - JButton: 각 계산 기능에 대한 버튼.
           - 기능 분해:
               - 숫자 및 연산자 버튼 처리 (actionPerformed 메서드에서 처리).
               - 사칙연산 수행: `CalculatorControllerApp` 클래스를 통해 수행.
               - 결과 출력 및 예외 처리.
               - 연산 결과 저장 및 삭제 기능 추가: `CalculatorControllerApp` 클래스를 통해 관리.
           - 데이터 흐름:
               - 입력: 사용자로부터 숫자 및 연산자를 입력받음.
               - 처리: 입력된 데이터를 바탕으로 연산을 수행하고 결과를 `CalculatorControllerApp`에 저장.
               - 출력: 연산 결과를 텍스트 필드에 출력하며, 예외 발생 시 오류 메시지를 표시함.
    */



    private JFrame frame;
    private JTextField textField;
    private JPanel panel;
    private String[] buttons = {
            "7", "8", "9", "/",  // 첫 번째 행: 숫자 7, 8, 9와 나눗셈 버튼
            "4", "5", "6", "*",  // 두 번째 행: 숫자 4, 5, 6과 곱셈 버튼
            "1", "2", "3", "-",  // 세 번째 행: 숫자 1, 2, 3과 뺄셈 버튼
            "0", ".", "=", "+",  // 네 번째 행: 숫자 0, 소수점, 등호, 덧셈 버튼
            " ", " ", "←", "C"   // 다섯 번째 행: 빈 공간, 마지막 입력 삭제, 초기화 버튼
    };
    private JButton[] button = new JButton[buttons.length];  // 버튼 배열의 길이에 맞게 설정
    private String operator = "";
    private double num1 = 0, num2 = 0;
    private boolean isOperatorClicked = false;
    private testSparta02.CalculatorControllerApp calculator = new testSparta02.CalculatorControllerApp();  // CalculatorControllerApp 인스턴스 생성

    public CalculatorGUI() {
        // JFrame 초기화 및 기본 설정
        frame = new JFrame("계산기");
        textField = new JTextField();
        panel = new JPanel();

        // JTextField 설정: 결과와 입력을 표시하는 텍스트 필드
        textField.setPreferredSize(new Dimension(230, 50));
        textField.setEditable(false);
        textField.setFont(new Font("Arial", Font.PLAIN, 24));
        textField.setHorizontalAlignment(JTextField.RIGHT);

        // JPanel 설정: 5행 4열의 그리드 레이아웃을 사용
        panel.setLayout(new GridLayout(5, 4, 10, 10)); // 5행 4열 그리드 레이아웃

        // 버튼을 JPanel에 추가
        for (int i = 0; i < buttons.length; i++) {
            if (!buttons[i].trim().isEmpty()) {  // 빈 문자열이 아닌 경우
                button[i] = new JButton(buttons[i]);
                button[i].setFont(new Font("Arial", Font.BOLD, 20));
                button[i].addActionListener(this);
                panel.add(button[i]);
            } else {
                panel.add(new JLabel());  // 빈 문자열의 경우 빈 레이블 추가
            }
        }

        // JFrame에 텍스트 필드와 패널 추가
        frame.add(textField, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);  // 프레임 크기 설정
        frame.setVisible(true);   // 프레임을 화면에 표시
    }

    public static void main(String[] args) {
        new CalculatorGUI();  // 계산기 생성 및 실행
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        // 숫자와 소수점 입력 처리
        if (command.charAt(0) >= '0' && command.charAt(0) <= '9' || command.equals(".")) {
            if (isOperatorClicked) {
                textField.setText("");  // 연산자가 클릭되었을 경우 텍스트 필드를 초기화
                isOperatorClicked = false;
            }
            textField.setText(textField.getText() + command);
        }
        // 초기화 버튼 "C" 처리
        else if (command.equals("C")) {
            textField.setText("");
            num1 = 0;
            num2 = 0;
            operator = "";
        }
        // 마지막 입력 삭제 버튼 "←" 처리
        else if (command.equals("←")) {
            String currentText = textField.getText();
            if (!currentText.isEmpty()) {
                textField.setText(currentText.substring(0, currentText.length() - 1));
            }
        }
        // 등호 "=" 버튼 처리: 연산 수행
        else if (command.equals("=")) {
            if (!operator.isEmpty()) {
                num2 = Double.parseDouble(textField.getText());
                double result = calculator.calculate(num1, num2, operator);

                if (!Double.isNaN(result)) {
                    textField.setText(String.valueOf(result));
                }
                operator = "";
            }
        }
        // 연산자 버튼 처리: +, -, *, /
        else {
            if (!operator.isEmpty() && !textField.getText().isEmpty()) {
                num2 = Double.parseDouble(textField.getText());
                double intermediateResult = calculator.calculate(num1, num2, operator);
                num1 = intermediateResult; // 계속해서 계산을 이어가기 위해 업데이트
            } else {
                num1 = Double.parseDouble(textField.getText());
            }
            operator = command;
            isOperatorClicked = true;
        }
    }
}