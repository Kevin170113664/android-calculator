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
        return removeDotFromInteger(calculateResult(splitInput(input)));
    }

    @NonNull
    private String removeDotFromInteger(String result) {
        if (result.substring(result.length() - 2, result.length()).equals(".0")) {
            result = result.substring(0, result.length() - 2);
        }
        return result;
    }

    private String calculateResult(String[] inputArray) {
        String result;
        switch (inputArray[1]) {
            case "+":
                result = String.format("%s", Double.parseDouble(inputArray[0]) + Double.parseDouble(inputArray[2]));
                break;
            case "-":
                result = String.format("%s", Double.parseDouble(inputArray[0]) - Double.parseDouble(inputArray[2]));
                break;
            case "×":
                result = String.format("%s", Double.parseDouble(inputArray[0]) * Double.parseDouble(inputArray[2]));
                break;
            case "÷":
                if (Double.parseDouble(inputArray[2]) == 0.0) {
                    result = "Error";
                } else {
                    result = String.format("%s", Double.parseDouble(inputArray[0]) / Double.parseDouble(inputArray[2]));
                }
                break;
            default:
                result = "0";
        }
        return result;
    }

    private String[] splitInput(String input) {
        String[] inputArray = {"", "", ""};
        int i = 0;
        for (Character c : input.toCharArray()) {
            if (c.toString().matches("[0-9]|[\\.]")) {
                inputArray[i] += c;
            } else {
                inputArray[1] += c;
                i = 2;
            }
        }
        return inputArray;
    }

    private String deleteLastChar(String resultText) {
        if (resultText.length() != 0) {
            return resultText.substring(0, resultText.length() - 1);
        } else {
            return resultText;
        }
    }
}