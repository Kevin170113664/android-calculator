package com.tw.lhli.calculator;

import android.widget.Button;
import android.widget.EditText;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18)
public class MainActivityTest {
    @Test
    public void clickingButton_shouldShowInResultText() throws Exception {
        MainActivity mainActivity = Robolectric.setupActivity(MainActivity.class);

        EditText resultText = (EditText) mainActivity.findViewById(R.id.result_text);
        Button buttonPlus = (Button) mainActivity.findViewById(R.id.button_plus);

        buttonPlus.performClick();
        assertEquals(resultText.getText().toString(), "+");
    }
}