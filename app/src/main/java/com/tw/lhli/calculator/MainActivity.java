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
    @Bind(R.id.button_plus) Button buttonPlus;
    @Bind(R.id.button_minus) Button buttonMinus;
    @Bind(R.id.button_multiply) Button buttonMultiply;
    @Bind(R.id.button_divide) Button buttonDivide;
    @Bind(R.id.button_dot) Button buttonDot;
    @Bind(R.id.button_equal) Button buttonEqual;
    @Bind(R.id.button_delete) Button buttonDelete;
    @Bind(R.id.button_clear) Button buttonClear;
    @Bind(R.id.button_left_bracket) Button buttonLeftBracket;
    @Bind(R.id.button_right_bracket) Button buttonRightBracket;

    @Bind(R.id.button_0) Button button0;
    @Bind(R.id.button_1) Button button1;
    @Bind(R.id.button_2) Button button2;
    @Bind(R.id.button_3) Button button3;
    @Bind(R.id.button_4) Button button4;
    @Bind(R.id.button_5) Button button5;
    @Bind(R.id.button_6) Button button6;
    @Bind(R.id.button_7) Button button7;
    @Bind(R.id.button_8) Button button8;
    @Bind(R.id.button_9) Button button9;

    @OnClick({R.id.button_plus, R.id.button_minus, R.id.button_multiply, R.id.button_divide,
              R.id.button_dot, R.id.button_left_bracket, R.id.button_right_bracket,
              R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4,
              R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9})
    public void showSymbolInResultText(View view) {
        resultText.setText(String.format("%s%s", resultText.getText(), ((Button)view).getText()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        resultText.setText(readResult());

        buttonEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultText.setText(calculateInput());
                saveResult();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultText.setText(deleteLastChar(resultText.getText().toString()));
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultText.setText("");
            }
        });
    }

    private void saveResult() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.last_result), resultText.getText().toString());
        editor.apply();
    }

    private String readResult() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String defaultValue = "";
        return sharedPref.getString(getString(R.string.last_result), defaultValue);
    }

    private String calculateInput() {
        String input = resultText.getText().toString();
        List<String> originOperationArray = splitInput(input);
        List<String> reversePolishNotation = transformToReversePolishNotation(originOperationArray);
        Double result = calculateReversePolishNotation(reversePolishNotation);
        return removeDotFromInteger((result == null ? "Error" : result).toString());
    }

    private Double calculateReversePolishNotation(List<String> reversePolishNotation) {
        Stack stack = new Stack();
        String result = "0";
        for (String operation : reversePolishNotation) {
            if (operation.matches(getString(R.string.reg_legal_number))) {
                stack.push(operation);
            } else {
                result = calculateTemp(stack.pop(), operation, stack.pop());
                if (result.equals("Error")) {
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
                    result = "Error";
                } else {
                    result = String.format("%s", Double.parseDouble(firstOperation) / Double.parseDouble(secondOperation));
                }
                break;
            default:
                result = "0";
        }
        return result;
    }

    private List<String> transformToReversePolishNotation(List<String> originOperationArray) {
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
            result = String.valueOf(Integer.parseInt(result));
        }
        return result;
    }

    private List<String> splitInput(String input) {
        String regex = "(?<=op)|(?=op)".replace("op", getString(R.string.reg_split));
        List<String> result = Arrays.asList(input.split(regex));
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