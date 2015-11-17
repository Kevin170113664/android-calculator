package com.tw.lhli.calculator;

import android.widget.Button;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18)
public class MainActivityTest {

    private MainActivity mainActivity;

    @Before
    public void setUp() {
        mainActivity = Robolectric.setupActivity(MainActivity.class);
    }

    @Test
    public void clickingClearButton_shouldClearResultText() {
        mainActivity.resultText.setText("This is my first android app.");
        mainActivity.buttonClear.performClick();

        assertEquals("", mainActivity.resultText.getText().toString());
    }

    @Test
    public void clickingDeleteButton_shouldDeleteLastCharInResultText() {
        mainActivity.resultText.setText("Hey, Hey, Hey");
        mainActivity.buttonDelete.performClick();

        assertEquals("Hey, Hey, He", mainActivity.resultText.getText().toString());
    }

    @Test
    public void clickingDeleteButton_shouldNotDeleteAnythingWhenResultTextIsEmpty() {
        mainActivity.resultText.setText("");
        mainActivity.buttonDelete.performClick();

        assertEquals("", mainActivity.resultText.getText().toString());
    }

    @Test
    public void clickingOperatorButton_shouldShowInResultText() throws Exception {
        mainActivity.buttonClear.performClick();
        mainActivity.buttonPlus.performClick();
        assertEquals("+", mainActivity.resultText.getText().toString());

        mainActivity.buttonClear.performClick();
        mainActivity.buttonMinus.performClick();
        assertEquals("-", mainActivity.resultText.getText().toString());

        mainActivity.buttonClear.performClick();
        mainActivity.buttonMultiply.performClick();
        assertEquals("×", mainActivity.resultText.getText().toString());

        mainActivity.buttonClear.performClick();
        mainActivity.buttonDivide.performClick();
        assertEquals("÷", mainActivity.resultText.getText().toString());

        mainActivity.buttonClear.performClick();
        mainActivity.buttonLeftBracket.performClick();
        assertEquals("(", mainActivity.resultText.getText().toString());

        mainActivity.buttonClear.performClick();
        mainActivity.buttonRightBracket.performClick();
        assertEquals(")", mainActivity.resultText.getText().toString());

        mainActivity.buttonClear.performClick();
        mainActivity.buttonDot.performClick();
        assertEquals(".", mainActivity.resultText.getText().toString());
    }

    @Test
    public void clickingNumberButton_shouldShowInResultText() {
        mainActivity.button0.performClick();
        mainActivity.button1.performClick();
        mainActivity.button2.performClick();
        mainActivity.button3.performClick();
        mainActivity.button4.performClick();
        mainActivity.button5.performClick();
        mainActivity.button6.performClick();
        mainActivity.button7.performClick();
        mainActivity.button8.performClick();
        mainActivity.button9.performClick();

        assertEquals( "0123456789", mainActivity.resultText.getText().toString());
    }
}