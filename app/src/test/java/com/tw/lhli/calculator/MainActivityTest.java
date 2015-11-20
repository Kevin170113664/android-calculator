package com.tw.lhli.calculator;

import android.widget.Button;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18)
public class MainActivityTest {

    private MainActivity mainActivity;
    @Bind(R.id.result_text) EditText resultText;
    @Bind(R.id.button_clear) Button buttonClear;
    @Bind(R.id.button_delete) Button buttonDelete;
    @Bind(R.id.button_equal) Button buttonEqual;

    @Before
    public void setUp() {
        mainActivity = Robolectric.setupActivity(MainActivity.class);
        ButterKnife.bind(this, mainActivity);
    }

    @Test
    public void clickingClearButton_shouldClearResultText() {
        resultText.setText("This is my first android app.");
        buttonClear.performClick();

        assertEquals("", resultText.getText().toString());
    }

    @Test
    public void clickingDeleteButton_shouldDeleteLastCharInResultText() {
        resultText.setText("Hey, Hey, Hey");
        buttonDelete.performClick();

        assertEquals("Hey, Hey, He", resultText.getText().toString());
    }

    @Test
    public void clickingDeleteButton_shouldNotDeleteAnythingWhenResultTextIsEmpty() {
        resultText.setText("");
        buttonDelete.performClick();

        assertEquals("", resultText.getText().toString());
    }

    @Test
    public void clickingOperatorButton_shouldShowInResultText() throws Exception {
        buttonClear.performClick();
        mainActivity.findViewById(R.id.button_plus).performClick();
        assertEquals("+", resultText.getText().toString());

        buttonClear.performClick();
        mainActivity.findViewById(R.id.button_minus).performClick();
        assertEquals("-", resultText.getText().toString());

        resultText.setText("0");
        mainActivity.findViewById(R.id.button_multiply).performClick();
        assertEquals("0×", resultText.getText().toString());

        resultText.setText("0");
        mainActivity.findViewById(R.id.button_divide).performClick();
        assertEquals("0÷", resultText.getText().toString());

        buttonClear.performClick();
        mainActivity.findViewById(R.id.button_left_bracket).performClick();
        assertEquals("(", resultText.getText().toString());

        resultText.setText("0");
        mainActivity.findViewById(R.id.button_right_bracket).performClick();
        assertEquals("0)", resultText.getText().toString());
    }

    @Test
    public void clickingNumberButton_shouldShowInResultText() {
        mainActivity.findViewById(R.id.button_0).performClick();
        mainActivity.findViewById(R.id.button_1).performClick();
        mainActivity.findViewById(R.id.button_2).performClick();
        mainActivity.findViewById(R.id.button_3).performClick();
        mainActivity.findViewById(R.id.button_4).performClick();
        mainActivity.findViewById(R.id.button_5).performClick();
        mainActivity.findViewById(R.id.button_6).performClick();
        mainActivity.findViewById(R.id.button_7).performClick();
        mainActivity.findViewById(R.id.button_8).performClick();
        mainActivity.findViewById(R.id.button_9).performClick();

        assertEquals("0123456789", resultText.getText().toString());
    }

    @Test
    public void clickingEqualButton_shouldShowCorrectAnswerWithSingleOperator() {
        resultText.setText("1+2");
        buttonEqual.performClick();
        assertEquals("3", resultText.getText().toString());

        resultText.setText("1-2");
        buttonEqual.performClick();
        assertEquals("-1", resultText.getText().toString());

        resultText.setText("88×1");
        buttonEqual.performClick();
        assertEquals("88", resultText.getText().toString());

        resultText.setText("100÷5");
        buttonEqual.performClick();
        assertEquals("20", resultText.getText().toString());
    }

    @Test
    public void shouldSplitInputFormulaCorrectly() {
        List<String> expectList = Arrays.asList("(", "3.4", "-", "2.4", ")", "÷", "(", "100", "-", "98", ")");
        assertEquals(expectList, mainActivity.splitInput("(3.4-2.4)÷(100-98)"));

        expectList = Arrays.asList("2.5", "-", "0.4", "×", "(", "250", "+", "88", ")");
        assertEquals(expectList, mainActivity.splitInput("2.5-0.4×(250+88)"));
    }

    @Test
    public void shouldTransformToReversePolishNotation() {
        List<String> originOperationList = Arrays.asList("(", "5.5", "-", "0.5", ")", "×", "9.5", "+", "380", "÷", "10");
        List<String> expectList = Arrays.asList("5.5", "0.5", "-", "9.5", "×", "380", "10", "÷", "+");

        assertEquals(expectList, mainActivity.transformToReversePolishNotation(originOperationList));
    }

    @Test
    public void shouldCalculateReversePolishNotationCorrectly() {
        List<String> reversePolishNotation = Arrays.asList("5.5", "0.5", "-", "9.5", "×", "380", "10", "÷", "+");
        Double expectResult = 85.5;

        assertEquals(expectResult, mainActivity.calculateReversePolishNotation(reversePolishNotation));
    }

    @Test
    public void shouldRetainResultWhenQuitApp() {
        resultText.setText("250.666");
        mainActivity.saveResult();
        resultText.setText("Start Dash!");

        assertEquals("250.666", mainActivity.readResult());
    }

    @Test
    public void shouldShowErrorWhenDividedZero() {
        resultText.setText("0÷0");
        buttonEqual.performClick();

        assertEquals("Error", resultText.getText().toString());
    }

    @Test
    public void clickingSymbolButton_shouldValidateInput() {
        buttonClear.performClick();
        assertFalse(mainActivity.validateClickingButton(")"));

        resultText.setText("0÷");
        assertFalse(mainActivity.validateClickingButton("+"));

        resultText.setText("-2÷1.23(");
        assertFalse(mainActivity.validateClickingButton("."));

        resultText.setText("123÷333");
        assertTrue(mainActivity.validateClickingButton("."));

        resultText.setText("0.1238+21");
        assertTrue(mainActivity.validateClickingButton("×"));

        resultText.setText("(");
        assertFalse(mainActivity.validateClickingButton("×"));
    }

    @Test
    public void clickingEqualButton_shouldValidateInput() {
        resultText.setText("1823.");
        assertFalse(mainActivity.validateFormula());

        resultText.setText("((((((((1238.12-2)-12)");
        assertFalse(mainActivity.validateFormula());
    }

    @Test
    public void clickingZeroButton_shouldNotAllowShowMoreThanOneZeroBeforeDot() {
        resultText.setText("0");
        mainActivity.findViewById(R.id.button_0).performClick();

        assertEquals("0", resultText.getText().toString());
    }

    @Test
    public void shouldTranslateNegativeNumber() {
        resultText.setText("-0.5×(-5)");

        assertEquals("0-0.5×(0-5)", mainActivity.translateNegativeNumber(resultText.getText().toString()));
    }

    @Test
    public void clickingDotButton_shouldAutocompleteSmartly() {
        buttonClear.performClick();
        mainActivity.findViewById(R.id.button_dot).performClick();
        assertEquals("0.", resultText.getText().toString());

        resultText.setText("()09");
        mainActivity.findViewById(R.id.button_dot).performClick();
        assertEquals("()09.", resultText.getText().toString());
    }

    @Test
    public void clickingEqualButton_shouldShowCorrectAnswerWithMultipleOperators() {
        resultText.setText("-5÷5+0.5÷5+(5+(-1.5))");
        buttonEqual.performClick();
        assertEquals("2.6", resultText.getText().toString());

        resultText.setText("(5×5)+0.5÷5+(5-5)");
        buttonEqual.performClick();
        assertEquals("25.1", resultText.getText().toString());

        resultText.setText("(14.5-2.5)÷(0.5+5.5)");
        buttonEqual.performClick();
        assertEquals("2", resultText.getText().toString());
    }
}