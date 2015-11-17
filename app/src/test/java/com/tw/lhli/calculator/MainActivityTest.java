package com.tw.lhli.calculator;

import android.widget.Button;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import butterknife.Bind;
import butterknife.ButterKnife;

import static junit.framework.Assert.assertEquals;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18)
public class MainActivityTest {

    private MainActivity mainActivity;
    @Bind(R.id.result_text) EditText resultText;
    @Bind(R.id.button_clear) Button buttonClear;
    @Bind(R.id.button_delete) Button buttonDelete;

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

        buttonClear.performClick();
        mainActivity.findViewById(R.id.button_multiply).performClick();
        assertEquals("×", resultText.getText().toString());

        buttonClear.performClick();
        mainActivity.findViewById(R.id.button_divide).performClick();
        assertEquals("÷", resultText.getText().toString());

        buttonClear.performClick();
        mainActivity.findViewById(R.id.button_left_bracket).performClick();
        assertEquals("(", resultText.getText().toString());

        buttonClear.performClick();
        mainActivity.findViewById(R.id.button_right_bracket).performClick();
        assertEquals(")", resultText.getText().toString());

        buttonClear.performClick();
        mainActivity.findViewById(R.id.button_dot).performClick();
        assertEquals(".", resultText.getText().toString());
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
}