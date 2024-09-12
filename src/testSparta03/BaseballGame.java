package testSparta03;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BaseballGame extends JFrame {
    private JTextArea resultArea;
    private JButton[] numberButtons;
    private JButton submitButton, restartButton, deleteButton, exitButton;
    private BaseballGameLogic gameLogic;
    private StringBuilder userInput;

    public BaseballGame() {
        setTitle("숫자 야구 게임");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 게임 로직 객체 생성
        gameLogic = new BaseballGameLogic();
        userInput = new StringBuilder();

        // 상단에 결과 표시 영역
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.BOLD, 16));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.NORTH);

        // 숫자 버튼 패널
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 3, 10, 10));

        numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setFont(new Font("Arial", Font.BOLD, 20));
            buttonPanel.add(numberButtons[i]);
            int finalI = i;
            numberButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (userInput.length() < 3) {
                        if (userInput.indexOf(String.valueOf(finalI)) == -1) { // 중복 입력 방지
                            userInput.append(finalI);
                            updateUserInputDisplay();
                        } else {
                            resultArea.append("중복된 숫자는 입력할 수 없습니다.\n");
                        }
                    } else {
                        resultArea.append("3개의 숫자만 입력 가능합니다.\n");
                    }
                }
            });
        }

        submitButton = new JButton("제출");
        restartButton = new JButton("다시 시작");
        deleteButton = new JButton("삭제");
        exitButton = new JButton("종료");

        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        restartButton.setFont(new Font("Arial", Font.BOLD, 16));
        deleteButton.setFont(new Font("Arial", Font.BOLD, 16));
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));

        buttonPanel.add(submitButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(restartButton);
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.CENTER);

        // 제출 버튼 이벤트
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (userInput.length() == 3) {
                        int[] userNumbers = new int[3];
                        for (int i = 0; i < 3; i++) {
                            userNumbers[i] = Character.getNumericValue(userInput.charAt(i));
                        }
                        String result = gameLogic.checkResult(userNumbers);
                        resultArea.append(result + "\n");
                        userInput.setLength(0); // 입력 초기화
                        updateUserInputDisplay();
                    } else {
                        resultArea.append("숫자 3개를 입력하세요.\n");
                    }
                } catch (Exception ex) {
                    resultArea.append("오류가 발생했습니다. 다시 시도해주세요.\n");
                }
            }
        });

        // 삭제 버튼 이벤트
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userInput.length() > 0) {
                    userInput.deleteCharAt(userInput.length() - 1);
                    updateUserInputDisplay();
                }
            }
        });

        // 다시 시작 버튼 이벤트
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameLogic.resetGame();
                resultArea.setText("게임이 다시 시작되었습니다.\n");
                userInput.setLength(0);
                updateUserInputDisplay();
            }
        });

        // 종료 버튼 이벤트
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(null,
                        "정말로 게임을 종료하시겠습니까?", "종료 확인",
                        JOptionPane.YES_NO_OPTION);

                if (confirmed == JOptionPane.YES_OPTION) {
                    dispose(); // 창 닫기
                }
            }
        });

        setVisible(true);
    }

    private void updateUserInputDisplay() {
        resultArea.append("입력된 숫자: " + userInput.toString() + "\n");
    }

    public static void main(String[] args) {
        new BaseballGame();
    }
}