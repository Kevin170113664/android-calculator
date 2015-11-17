package com.tw.lhli.calculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.result_text) EditText resultText;

    @OnClick({R.id.button_plus, R.id.button_minus, R.id.button_multiply, R.id.button_divide,
              R.id.button_dot, R.id.button_left_bracket, R.id.button_right_bracket,
              R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4,
              R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9})
    public void showSymbolInResultText(View view) {
        resultText.setText(String.format("%s%s", resultText.getText(), ((Button)view).getText()));
    }

    @OnClick(R.id.button_equal)
    public void calculate() {
        resultText.setText(calculateInput());
        saveResult();
    }

    @OnClick(R.id.button_delete)
    public void deleteFromResultText() {
        resultText.setText(deleteLastChar(resultText.getText().toString()));
    }

    @OnClick(R.id.button_clear)
    public void clearResultText() {
        resultText.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        resultText.setText(readResult());
    }

    protected void saveResult() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.last_result), resultText.getText().toString());
        editor.apply();
    }

    protected String readResult() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String defaultValue = "";
        return sharedPref.getString(getString(R.string.last_result), defaultValue);
    }

    private String calculateInput() {
        List<String> originOperationList = splitInput(resultText.getText().toString());
        List<String> reversePolishNotation = transformToReversePolishNotation(originOperationList);
        Double result = calculateReversePolishNotation(reversePolishNotation);
        return removeDotFromInteger((result == null ? getString(R.string.result_text_error) : result).toString());
    }

    protected Double calculateReversePolishNotation(List<String> reversePolishNotation) {
        Stack stack = new Stack();
        String result = "0";
        for (String operation : reversePolishNotation) {
            if (operation.matches(getString(R.string.reg_legal_number))) {
                stack.push(operation);
            } else {
                result = calculateTemp(stack.pop(), operation, stack.pop());
                if (result.equals(getString(R.string.result_text_error))) {
                    return null;
                } else {
                    stack.push(result);
                }
            }
        }
        return Double.parseDouble(result);
    }

    private String calculateTemp(String secondOperation, String symbol, String firstOperation) {
        String result;
        switch (symbol) {
            case "+":
                result = String.format("%s", Double.parseDouble(firstOperation) + Double.parseDouble(secondOperation));
                break;
            case "-":
                result = String.format("%s", Double.parseDouble(firstOperation) - Double.parseDouble(secondOperation));
                break;
            case "×":
                result = String.format("%s", Double.parseDouble(firstOperation) * Double.parseDouble(secondOperation));
                break;
            case "÷":
                if (Double.parseDouble(secondOperation) == 0.0) {
                    result = getString(R.string.result_text_error);
                } else {
                    result = String.format("%s", Double.parseDouble(firstOperation) / Double.parseDouble(secondOperation));
                }
                break;
            default:
                result = "0";
        }
        return result;
    }

    protected List<String> transformToReversePolishNotation(List<String> originOperationArray) {
        Stack stack = new Stack();
        List<String> reversePolishNotation = new ArrayList<>();
        for (String operation : originOperationArray) {
            if (operation.matches(getString(R.string.reg_legal_number))) {
                reversePolishNotation.add(operation);
            } else if (operation.matches(getString(R.string.reg_left_bracket))) {
                stack.push(operation);
            } else if (operation.matches(getString(R.string.reg_right_bracket))) {
                do {
                    reversePolishNotation.add(stack.pop());
                } while (!stack.getStackTop().equals("("));
                stack.pop();
            } else {
                if (Stack.priority(operation, stack.getStackTop())) {
                    stack.push(operation);
                } else {
                    while (!stack.isEmpty()) {
                        reversePolishNotation.add(stack.pop());
                        if (Stack.priority(operation, stack.getStackTop())) {
                            break;
                        }
                    }
                    stack.push(operation);
                }
            }
        }

        while (!stack.isEmpty()) {
            reversePolishNotation.add(stack.pop());
        }

        return reversePolishNotation;
    }

    @NonNull
    private String removeDotFromInteger(String result) {
        if (result.substring(result.length() - 2, result.length()).equals(".0")) {
            return result.substring(0, result.length() - 2);
        } else {
            return result;
        }
    }

    protected List<String> splitInput(String input) {
        String regex = "(?<=op)|(?=op)".replace("op", getString(R.string.reg_split));
        return removeEmptyElementsInArray(Arrays.asList(input.split(regex)));
    }

    @NonNull
    private List<String> removeEmptyElementsInArray(List<String> result) {
        if (result.get(0).equals("")) {
            result = result.subList(1, result.size());
        } else if (result.get(result.size() - 1).equals("")) {
            result = result.subList(0, result.size() - 1);
        }
        return result;
    }

    private String deleteLastChar(String resultText) {
        if (resultText.length() != 0) {
            return resultText.substring(0, resultText.length() - 1);
        } else {
            return resultText;
        }
    }
}