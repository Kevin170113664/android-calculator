package com.tw.lhli.calculator;

import android.app.Activity;
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

public class MainActivity extends AppCompatActivity {

    EditText resultText;

    Button buttonPlus;
    Button buttonMinus;
    Button buttonMultiply;
    Button buttonDivide;
    Button buttonDot;
    Button buttonEqual;
    Button buttonDelete;
    Button buttonClear;
    Button buttonLeftBracket;
    Button buttonRightBracket;

    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultText = (EditText) findViewById(R.id.result_text);
        resultText.setText(readResult());

        buttonPlus = (Button) findViewById(R.id.button_plus);
        buttonMinus = (Button) findViewById(R.id.button_minus);
        buttonMultiply = (Button) findViewById(R.id.button_multiply);
        buttonDivide = (Button) findViewById(R.id.button_divide);
        buttonDot = (Button) findViewById(R.id.button_dot);
        buttonEqual = (Button) findViewById(R.id.button_equal);
        buttonDelete = (Button) findViewById(R.id.button_delete);
        buttonClear = (Button) findViewById(R.id.button_clear);
        buttonLeftBracket = (Button) findViewById(R.id.button_left_bracket);
        buttonRightBracket = (Button) findViewById(R.id.button_right_bracket);

        button0 = (Button) findViewById(R.id.button_0);
        button1 = (Button) findViewById(R.id.button_1);
        button2 = (Button) findViewById(R.id.button_2);
        button3 = (Button) findViewById(R.id.button_3);
        button4 = (Button) findViewById(R.id.button_4);
        button5 = (Button) findViewById(R.id.button_5);
        button6 = (Button) findViewById(R.id.button_6);
        button7 = (Button) findViewById(R.id.button_7);
        button8 = (Button) findViewById(R.id.button_8);
        button9 = (Button) findViewById(R.id.button_9);

        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultText.setText(String.format("%s+", resultText.getText()));
            }
        });

        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultText.setText(String.format("%s-", resultText.getText()));
            }
        });

        buttonMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultText.setText(String.format("%s×", resultText.getText()));
            }
        });

        buttonDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultText.setText(String.format("%s÷", resultText.getText()));
            }
        });

        buttonDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultText.setText(String.format("%s.", resultText.getText()));
            }
        });

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

        buttonLeftBracket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultText.setText(String.format("%s(", resultText.getText()));
            }
        });

        buttonRightBracket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultText.setText(String.format("%s)", resultText.getText()));
            }
        });

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultText.setText(String.format("%s0", resultText.getText()));
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultText.setText(String.format("%s1", resultText.getText()));
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultText.setText(String.format("%s2", resultText.getText()));
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultText.setText(String.format("%s3", resultText.getText()));
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultText.setText(String.format("%s4", resultText.getText()));
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultText.setText(String.format("%s5", resultText.getText()));
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultText.setText(String.format("%s6", resultText.getText()));
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultText.setText(String.format("%s7", resultText.getText()));
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultText.setText(String.format("%s8", resultText.getText()));
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultText.setText(String.format("%s9", resultText.getText()));
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
            if (operation.matches("[0-9]+|[0-9]+\\.[0-9]+")) {
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
            if (operation.matches("[0-9]+|[0-9]+\\.[0-9]+")) {
                reversePolishNotation.add(operation);
            } else if (operation.matches("^[(]")) {
                stack.push(operation);
            } else if (operation.matches("^[)]")) {
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
            result = result.substring(0, result.length() - 2);
        }
        return result;
    }

    private List<String> splitInput(String input) {
        String regex = "(?<=op)|(?=op)".replace("op", "[-+×÷()]");
        List<String> result = Arrays.asList(input.split(regex));
        return result.subList(1, result.size());
    }

    private String deleteLastChar(String resultText) {
        if (resultText.length() != 0) {
            return resultText.substring(0, resultText.length() - 1);
        } else {
            return resultText;
        }
    }
}