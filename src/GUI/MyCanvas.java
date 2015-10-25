package GUI;

import Algorithm.MyAlgorithm;
import Algorithm.QuestionState;
import Algorithm.Subtraction;
import Calculator.CalculatorPrevention;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MyCanvas {


    private final int MAX_INCORRECT = 5;
    private final int QUESTION_GOAL = 60;
    private final String PASS = "Going Trinity";
    private final int INITIAL_MAX_RANDOM_RANGE = 5;
    private final int NUM_CHARS_ANSWER = 5;
    private final int calcTimer = 1000;

    private STATE s = STATE.IN_GAME;
    private CORRECTNESS correctnessFlag = CORRECTNESS.CORRECT;

    private CheckboxGroup percentIncDec = new CheckboxGroup();
    private Checkbox increaseCheckBox = new Checkbox("Increase", percentIncDec,
            false);
    private Checkbox decreaseCheckBox = new Checkbox("Decrease", percentIncDec,
            false);

    private Canvas canvas;
    private Frame mainFrame;
    private Panel controlPanel = new Panel();
    private Panel percentIncreaseDecreasePlaceHolder = new Panel();
    private Panel percentIncreaseDecreasePanel = new Panel();
    private JTextField ans = new JTextField(NUM_CHARS_ANSWER);
    private Panel answerPanel = new Panel();
    private int numQuestionSoFar = 0;
    private int maxRandomRange = INITIAL_MAX_RANDOM_RANGE;
    private int incorrectScore = 0;
    private String currQuestion = "";
    private String prevQuestion = "";
    private String correctness = "";
    private String answer = "";
    private String incorrectScoreText = "Incorrect: 0/5";
    private MyAlgorithm operation = new Subtraction();
    private CalculatorPrevention c = new CalculatorPrevention(this, calcTimer);
    public MyCanvas() {
        mainFrame = new Frame();
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setSize(750, 500);
        mainFrame.add(controlPanel, BorderLayout.CENTER);
        controlPanel.add(canvas = new MyMiniCanvas());
        InitialAnswerPanel();
        mainFrame.add(answerPanel, BorderLayout.SOUTH);
        mainFrame.setBackground(Color.WHITE);
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        mainFrame.setVisible(true);
        mainFrame.setResizable(false);

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // percentIncreaseDecreasePlaceHolder.setVisible(false);
        setUpQuestion();

    }

    private void InitialAnswerPanel() {
        // Answer Panel
        // Answer Box
        Panel answerPlaceHolder = new Panel(new FlowLayout());
        answerPlaceHolder.add(new JLabel("Answer:"));
        ans.setHorizontalAlignment(JTextField.CENTER);
        answerPlaceHolder.add(ans);
        answerPanel.add(answerPlaceHolder);
        // Increase/Decrease Radio Button
        percentIncreaseDecreasePlaceHolder.setLayout(new FlowLayout());
        percentIncreaseDecreasePanel.setLayout(new GridLayout(2, 1));
        percentIncreaseDecreasePanel.add(increaseCheckBox);
        JButton submit = new JButton("Submit");
        percentIncreaseDecreasePanel.add(decreaseCheckBox);
        percentIncreaseDecreasePlaceHolder.add(percentIncreaseDecreasePanel);
        percentIncreaseDecreasePlaceHolder.add(submit);
        submit.addActionListener(new MyActionListener());
        // answerPanel.add(percentIncreaseDecreasePlaceHolder);
        ans.addActionListener(new MyActionListener());
    }

    public void setUpQuestion() {
        operation = operation.instantiateQuestion();
        currQuestion = operation.generateQuestion(maxRandomRange) + " = ";
        // System.out.println(operation.getstate() == PERCENT_STATE.INC_DEC);
        setUpAnswerPanel(operation.getstate());
        System.out.println("Question state is " + operation.getstate());
        if (operation.getstate() == QuestionState.NO_CALC) {
            if (s != STATE.CAL_DETECTED) {
                s = STATE.CAL_WARNING_FLAG;
            }
            System.out.println("Timer started");
            c.startTimer();
        }
       
        ans.requestFocus();
        mainFrame.setVisible(true);
        // System.out.println(percentIncreaseDecreasePlaceHolder.isVisible());
        // System.out.println("---------");
        canvas.repaint();
    }

    private void setUpAnswerPanel(QuestionState gameState) {
        if (gameState == QuestionState.PERCENT_INC_DEC) {
            clearRadioSelectionButtons();
            answerPanel.add(percentIncreaseDecreasePlaceHolder);
            percentIncreaseDecreasePlaceHolder.setVisible(true);
        } else {
            answerPanel.remove(percentIncreaseDecreasePlaceHolder);
            percentIncreaseDecreasePlaceHolder.setVisible(false);
        }

    }

    private void clearRadioSelectionButtons() {
        percentIncDec.setSelectedCheckbox(null);
    }

    public STATE getGameState() {
        return s;
    }

    public void setGameState(STATE gameState) {
        STATE prevState = s;
        s = gameState;
        if (gameState == STATE.CAL_DETECTED) {

            incorrectScoreText = "Incorrect: "
                    + ((prevState == STATE.CAL_WARNING_FLAG) ? incorrectScore : ++incorrectScore)
                    + "/" + MAX_INCORRECT;
            if (incorrectScore >= MAX_INCORRECT) {
                numQuestionSoFar = 0;
                incorrectScore = 0;
                maxRandomRange = INITIAL_MAX_RANDOM_RANGE;
            }
            c.stop();
            setUpQuestion();
        }
    }

    /**
     * @author MSDK
     */
    private class MyMiniCanvas extends Canvas {

        /**
         *
         */
        // Padding space setup
        private static final long serialVersionUID = 1L;
        private static final int PADDING_SPACE = 10;
        private static final int SEPARATION_SPACE = PADDING_SPACE;

        public MyMiniCanvas() {
            setBackground(Color.WHITE);
            setSize(mainFrame.getSize());
        }

        public void paint(Graphics g) {
            // Set up Parameters
            setSize(mainFrame.getSize());
            Font font = new Font("default", Font.PLAIN, 20);
            Map<TextAttribute, Object> attributes = new HashMap<>();
            attributes.put(TextAttribute.TRACKING, 0.1);
            Font font2 = font.deriveFont(attributes);

            // Begin Drawing Components
            Graphics2D g2;
            g2 = (Graphics2D) g;

            // Write incorrect string on top right in red
            g2.setColor(Color.RED);
            g2.setFont(new Font("default", Font.BOLD, 16));
            g2.drawString(incorrectScoreText, mainFrame.getSize().width - 120,
                    20);

            // Write current score in top left corner in blue
            g2.setColor(Color.BLUE);
            g2.setFont(new Font("default", Font.BOLD, 16));
            g2.drawString("Score: " + Integer.toString(numQuestionSoFar) + "/"
                    + QUESTION_GOAL, 10, 20);

            // Write previous question with answer and correctness
            // and current question
            g2.setColor(Color.BLACK);

            g2.setFont(font2);
            printCurrentQuestion(g2, font2);
            printPrevQuestion(g2, font2);
        }

        /**
         * Based on the size of the frame and window, the current question will
         * be printed at the bottom panel of the frame below the imaginary half
         * height line
         *
         * @param g2D graphic element of the canvas
         * @param f   reference fonts
         */
        private void printCurrentQuestion(Graphics2D g2D, Font f) {
            if (!currQuestion.isEmpty()) {
                FontMetrics metrics = g2D.getFontMetrics(f);
                ArrayList<String> currQuestionElement = new ArrayList<>(
                        Arrays.asList(currQuestion.split("\n")));
                int yCoordinate = mainFrame.getSize().height / 2
                        + metrics.getHeight() + SEPARATION_SPACE
                        + PADDING_SPACE;
                int xCoordinate;
                for (String s : currQuestionElement) {
                    xCoordinate = (mainFrame.getSize().width / 2)
                            - (metrics.stringWidth(s) / 2) - PADDING_SPACE;
                    g2D.drawString(s, xCoordinate, yCoordinate);
                    yCoordinate += metrics.getHeight() + PADDING_SPACE;
                }
            }
        }

        /**
         * Based on the height of the window and canvas frame, the previous
         * question will be printed in the top half of the frame, above the
         * imaginary half height line
         *
         * @param g2D graphic element of the canvas
         * @param f   reference fonts
         */
        private void printPrevQuestion(Graphics2D g2D, Font f) {
            if (!prevQuestion.isEmpty()) {
                FontMetrics metrics = g2D.getFontMetrics(f);
                ArrayList<String> preQuestionElement = new ArrayList<>(
                        Arrays.asList(prevQuestion.split("\n")));
                int yCoordinate = mainFrame.getSize().height / 2
                        - preQuestionElement.size()
                        * (metrics.getHeight() + PADDING_SPACE)
                        - SEPARATION_SPACE;
                int xCoordinate = 0;
                for (String s : preQuestionElement) {
                    xCoordinate = (mainFrame.getSize().width / 2)
                            - (metrics.stringWidth(s) / 2) - PADDING_SPACE;
                    g2D.drawString(s, xCoordinate, yCoordinate);
                    yCoordinate += metrics.getHeight() + PADDING_SPACE;
                }
                int yCoordinateCorrectness = yCoordinate - metrics.getHeight()
                        - PADDING_SPACE;
                int xCoordinateForCorrectness = xCoordinate
                        + metrics.stringWidth(preQuestionElement
                        .get(preQuestionElement.size() - 1))
                        + PADDING_SPACE;
                g2D.setColor(correctnessFlag.getColor());
                g2D.drawString(correctness, xCoordinateForCorrectness,
                        yCoordinateCorrectness);
            }
        }

    }

    private class MyActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (s == STATE.END) {
                    System.exit(0);
                }

                if (operation.getstate() == QuestionState.PERCENT_INC_DEC
                        && !increaseCheckBox.getState()
                        && !decreaseCheckBox.getState())
                    ans.setText("null");
                double a = Double.parseDouble(ans.getText());
                if (operation.getstate() == QuestionState.PERCENT_INC_DEC
                        && decreaseCheckBox.getState())
                    a = -(Math.abs(a));
                answer = String.format("%.2f", a);
                prevQuestion = currQuestion + answer;
                if (operation.checkAnswer(a)) {
                    numQuestionSoFar++;
                    correctness = "CORRECT !!!";
                    correctnessFlag = CORRECTNESS.CORRECT;
                } else {
                    incorrectScore++;
                    correctness = "WRONG !!!";
                    correctnessFlag = CORRECTNESS.WRONG;
                    prevQuestion += " " + "("
                            + String.format("%.2f", operation.getAnswer())
                            + ")";
                }
                s = STATE.IN_GAME;
                c.stop();
                if (incorrectScore > MAX_INCORRECT) {
                    numQuestionSoFar = 0;
                    incorrectScore = 0;
                    maxRandomRange = INITIAL_MAX_RANDOM_RANGE;
                }

                if (numQuestionSoFar >= QUESTION_GOAL) {
                    currQuestion = PASS + " !!!!!";
                    s = STATE.END;
                }

                maxRandomRange += 2;
                /*
                 * operation new with switch statements for diff kind of
				 * question
				 */

                if (s != STATE.END) {
                    setUpQuestion();
                }

                incorrectScoreText = "Incorrect: " + incorrectScore + "/"
                        + MAX_INCORRECT;
                answer = "";
                canvas.repaint();
            } catch (Exception E) {
                if (ans.getText().equalsIgnoreCase(PASS)) {
                    currQuestion = PASS + " !!!!!";
                    prevQuestion = "Entered Password to Finish !!!";
                    s = STATE.END;
                }
                if (ans.getText().equalsIgnoreCase("V1")) {
                    percentIncreaseDecreasePlaceHolder.setVisible(true);
                    System.out.println("setVisible to true");
                }
                if (ans.getText().equalsIgnoreCase("V2")) {
                    percentIncreaseDecreasePlaceHolder.setVisible(false);
                    System.out.println("setVisible to false");
                }
                if (ans.getText().equalsIgnoreCase("")) {
                    System.out.println("empty answer");
                }
                canvas.repaint();
            }
            ans.setText("");
        }

    }

}
