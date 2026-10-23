package com.example.calc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML public TextField display;

    private DamnBigNumber a = DamnBigNumber.DBN_ZERO;
    private char operation = '+';
    private boolean newEntry = true;

    private String current() {
        String text = display.getText();
        return text.matches("-?\\d+") ? text : "0";
    }

    private void calculate() {
        DamnBigNumber b = new DamnBigNumber(current());
        DamnBigNumber c = b;
        try {
            switch (operation) {
                case '+':
                    c = DamnBigNumbers.add(a, b);
                    break;
                case '-':
                    c = DamnBigNumbers.subtract(a, b);
                    break;
                case '*':
                    c = DamnBigNumbers.multiply(a, b);
                    break;
                case '/':
                    c = DamnBigNumbers.divide(a, b);
                    break;
            }
            display.setText(c.getNumStr());
        } catch (ArithmeticException e) {
            display.setText("Hiba");
        }
    }

    private void handleOperator(char op) {
        if (!newEntry) {
            calculate();
        }
        a = new DamnBigNumber(current());
        operation = op;
        newEntry = true;
    }

    @FXML
    private void handleDigitClick(ActionEvent actionEvent) {
        String digit = ((Button) actionEvent.getSource()).getText();
        if (newEntry || display.getText().equals("0")) {
            display.setText(digit);
        } else {
            display.appendText(digit);
        }
        newEntry = false;
    }

    @FXML
    private void handleAddClick(ActionEvent actionEvent) {
        handleOperator('+');
    }

    @FXML
    private void handleSubtractClick(ActionEvent actionEvent) {
        handleOperator('-');
    }

    @FXML
    private void handleMultiplyClick(ActionEvent actionEvent) {
        handleOperator('*');
    }

    @FXML
    private void handleDivideClick(ActionEvent actionEvent) {
        handleOperator('/');
    }

    @FXML
    private void handleEqualsClick(ActionEvent actionEvent) {
        if (!newEntry) {
            calculate();
        }
        a = DamnBigNumber.DBN_ZERO;
        operation = '+';
        newEntry = true;
    }

    @FXML
    private void handleClearClick(ActionEvent actionEvent) {
        display.setText("0");
        a = DamnBigNumber.DBN_ZERO;
        operation = '+';
        newEntry = true;
    }
}
