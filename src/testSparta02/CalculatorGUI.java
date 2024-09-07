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
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            " ", " ", "←", "C"
    };
    private JButton[] button = new JButton[buttons.length];
    private String operator = "";
    private double num1 = 0, num2 = 0;
    private boolean isOperatorClicked = false;
    private CalculatorControllerApp calculator = new CalculatorControllerApp();

    public CalculatorGUI() {
        setupFrame();
        setupTextField();
        setupPanel();
        frame.setVisible(true);
    }

    private void setupFrame() {
        frame = new JFrame("계산기");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);
    }

    private void setupTextField() {
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(230, 50));
        textField.setEditable(false);
        textField.setFont(new Font("Arial", Font.PLAIN, 24));
        textField.setHorizontalAlignment(JTextField.RIGHT);
        frame.add(textField, BorderLayout.NORTH);
    }

    private void setupPanel() {
        panel = new JPanel(new GridLayout(5, 4, 10, 10));

        for (int i = 0; i < buttons.length; i++) {
            if (!buttons[i].trim().isEmpty()) {
                button[i] = new JButton(buttons[i]);
                button[i].setFont(new Font("Arial", Font.BOLD, 20));
                button[i].addActionListener(this);
                panel.add(button[i]);
            } else {
                panel.add(new JLabel());
            }
        }
        frame.add(panel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (isNumberOrDot(command)) {
            handleNumberInput(command);
        } else if (command.equals("C")) {
            clearAll();
        } else if (command.equals("←")) {
            handleBackspace();
        } else if (command.equals("=")) {
            handleEqualOperation();
        } else {
            handleOperator(command);
        }
    }

    private boolean isNumberOrDot(String command) {
        return command.charAt(0) >= '0' && command.charAt(0) <= '9' || command.equals(".");
    }

    private void handleNumberInput(String command) {
        if (isOperatorClicked) {
            textField.setText("");
            isOperatorClicked = false;
        }
        textField.setText(textField.getText() + command);
    }

    private void clearAll() {
        textField.setText("");
        num1 = 0;
        num2 = 0;
        operator = "";
    }

    private void handleBackspace() {
        String currentText = textField.getText();
        if (!currentText.isEmpty()) {
            textField.setText(currentText.substring(0, currentText.length() - 1));
        }
    }

    private void handleEqualOperation() {
        if (operator.isEmpty() || textField.getText().isEmpty()) {
            // 연산자가 없거나 숫자가 입력되지 않았을 때
            textField.setText("Error");
            return;
        }

        try {
            // num2 값을 설정
            num2 = Double.parseDouble(textField.getText());

            if (Double.isNaN(num2)) {
                textField.setText("Error");
                return;
            }

            // 연산 수행
            double result = calculator.calculate(num1, num2, operator);

            if (!Double.isNaN(result)) {
                textField.setText(String.valueOf(result));
                // 결과를 출력한 후 상태 초기화
                operator = "";
                num2 = 0;
                isOperatorClicked = false;
            } else {
                textField.setText("Error");
            }
        } catch (NumberFormatException ex) {
            textField.setText("Error");
        }
    }

    private void handleOperator(String command) {
        try {
            // 현재 입력된 값이 없는데 연산자가 눌렸을 때
            if (textField.getText().isEmpty()) {
                textField.setText("Error");
                return;
            }

            if (!operator.isEmpty() && !isOperatorClicked) {
                // 기존 연산자가 설정되어 있고 새 연산자가 눌릴 때
                num2 = Double.parseDouble(textField.getText());
                num1 = calculator.calculate(num1, num2, operator);
                textField.setText(String.valueOf(num1)); // 결과를 텍스트 필드에 표시
            } else if (isOperatorClicked) {
                // 연산자가 연속으로 눌렸을 때
                operator = command;
                textField.setText(textField.getText() + " " + operator + " ");
                return;
            } else {
                // 새 연산자를 입력할 때
                num1 = Double.parseDouble(textField.getText());
                textField.setText(textField.getText() + " " + command + " ");
            }

            operator = command;
            isOperatorClicked = true;
        } catch (NumberFormatException ex) {
            textField.setText("Error");
        }
    }




    public static void main(String[] args) {
        new CalculatorGUI();
    }
}