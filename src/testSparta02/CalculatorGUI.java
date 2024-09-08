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
               - 최대 결과 기록 수 제한 기능: 계산 결과 이력을 최대 10개까지 저장.
               - 기록된 연산 결과를 저장 및 관리하는 큐(queue) 기능 추가.
               - 연산이 수행된 후 결과가 자동으로 저장되며, 큐의 크기가 10을 초과할 경우 가장 오래된 결과를 삭제.
           - 예외 처리:
               - 0으로 나누기 오류 처리.
               - 잘못된 연산자 입력 처리.
               - 연산 중 잘못된 입력이나 빈 입력 값에 대한 오류 처리.
               - 잘못된 연산 후 오류 메시지("Error")를 출력하여 사용자에게 피드백 제공.

        2. 설계:
           - 주요 클래스: CalculatorGUI, CalculatorControllerApp
               - CalculatorGUI: 사용자 인터페이스를 담당하고, 이벤트 처리 및 결과 출력을 관리.
               - CalculatorControllerApp: 실제 연산을 수행하고 결과를 관리하는 로직을 구현.
               - JFrame: 메인 창 구성.
               - JTextField: 입력 및 결과 출력.
               - JPanel: 버튼 배치 및 레이아웃 관리.
               - JButton: 각 계산 기능에 대한 버튼.
           - 기능 분해:
               - 숫자 및 연산자 버튼 처리 (actionPerformed 메서드에서 처리).
               - 사칙연산 수행: `CalculatorControllerApp` 클래스를 통해 수행.
               - 연산 수행 후 결과 큐에 저장 및 이력 관리 (10개까지 저장).
               - 결과 출력 및 예외 처리: GUI에서 사용자가 잘못된 입력을 했을 때 "Error" 메시지 출력.
               - 연산 결과 저장 및 삭제 기능 추가: `CalculatorControllerApp` 클래스를 통해 관리.
           - 데이터 흐름:
               - 입력: 사용자로부터 숫자 및 연산자를 입력받음.
               - 처리: 입력된 데이터를 바탕으로 연산을 수행하고, 결과를 `CalculatorControllerApp`에 저장.
               - 출력: 연산 결과를 텍스트 필드에 출력하며, 예외 발생 시 오류 메시지를 표시함.
               - 결과 이력: 계산된 결과는 큐에 저장되며, 최대 10개의 결과만 관리하고 이전 결과는 자동 삭제됨.

    */


    // JFrame과 관련된 GUI 구성 요소 선언
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
    private CalculatorControllerApp calculator = new CalculatorControllerApp(); // 연산 처리용 컨트롤러

    // GUI 창 설정 및 컴포넌트 초기화
    public CalculatorGUI() {
        setupFrame();
        setupTextField();
        setupPanel();
        frame.setVisible(true);
    }

    /**
     * JFrame 초기 설정
     */
    private void setupFrame() {
        frame = new JFrame("계산기");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);
    }

    /**
     * JTextField 초기 설정 (결과 출력)
     */
    private void setupTextField() {
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(230, 50));
        textField.setEditable(false);
        textField.setFont(new Font("Arial", Font.PLAIN, 24));
        textField.setHorizontalAlignment(JTextField.RIGHT);
        frame.add(textField, BorderLayout.NORTH);
    }

    /**
     * JPanel에 버튼들을 배치
     */
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

    /**
     * 버튼 클릭에 따른 동작 처리
     */
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

    /**
     * 숫자나 소수점 버튼 클릭 여부 확인
     */
    private boolean isNumberOrDot(String command) {
        return command.charAt(0) >= '0' && command.charAt(0) <= '9' || command.equals(".");
    }

    /**
     * 숫자 입력 처리
     */
    private void handleNumberInput(String command) {
        if (isOperatorClicked) {
            textField.setText("");
            isOperatorClicked = false;
        }
        textField.setText(textField.getText() + command);
    }

    /**
     * 모든 입력 초기화
     */
    private void clearAll() {
        textField.setText("");
        num1 = 0;
        num2 = 0;
        operator = "";
    }

    /**
     * 마지막 입력 삭제 (Backspace)
     */
    private void handleBackspace() {
        String currentText = textField.getText();
        if (!currentText.isEmpty()) {
            textField.setText(currentText.substring(0, currentText.length() - 1));
        }
    }

    /**
     * 연산자 클릭 시 처리
     */
    private void handleOperator(String command) {
        try {
            if (textField.getText().isEmpty()) {
                textField.setText("Error");
                return;
            }

            if (!operator.isEmpty() && !isOperatorClicked) {
                // 연산자 연속 입력 시 이전 연산 수행
                num2 = Double.parseDouble(textField.getText());
                num1 = calculator.calculate(num1, num2, operator);
                textField.setText(String.valueOf(num1));
            } else if (isOperatorClicked) {
                // 연산자 연속 클릭 시
                operator = command;
                return;
            } else {
                num1 = Double.parseDouble(textField.getText());
                textField.setText("");
            }

            operator = command;
            isOperatorClicked = true;
        } catch (NumberFormatException ex) {
            textField.setText("Error");
        }
    }

    /**
     * "=" 버튼 클릭 시 연산 수행
     */
    private void handleEqualOperation() {
        if (operator.isEmpty() || textField.getText().isEmpty()) {
            textField.setText("Error");
            return;
        }

        try {
            num2 = Double.parseDouble(textField.getText());

            double result = calculator.calculate(num1, num2, operator);

            if (!Double.isNaN(result)) {
                textField.setText(String.valueOf(result));
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

    public static void main(String[] args) {
        new CalculatorGUI();
    }
}