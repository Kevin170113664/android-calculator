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
    public void clickingButton_shouldShowInResultText() throws Exception {
        mainActivity.buttonPlus.performClick();
        mainActivity.buttonMinus.performClick();
        mainActivity.buttonMultiply.performClick();
        mainActivity.buttonDivide.performClick();
        assertEquals(mainActivity.resultText.getText().toString(), "+-×÷");
    }

    @Test
    public void clickingDeleteButton_shouldDeleteLastCharInResultText() {

    }
}