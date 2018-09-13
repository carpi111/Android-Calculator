package com.chapman.dev.vincecarpino.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MyActivity extends Activity {

    private TextView textView;
    private Boolean functionActive = false;
    private ArrayList<String> inputList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator);

        Button zeroButton      = findViewById(R.id.zero);
        Button oneButton       = findViewById(R.id.one);
        Button twoButton       = findViewById(R.id.two);
        Button threeButton     = findViewById(R.id.three);
        Button fourButton      = findViewById(R.id.four);
        Button fiveButton      = findViewById(R.id.five);
        Button sixButton       = findViewById(R.id.six);
        Button sevenButton     = findViewById(R.id.seven);
        Button eightButton     = findViewById(R.id.eight);
        Button nineButton      = findViewById(R.id.nine);
        Button addButton       = findViewById(R.id.add);
        Button subtractButton  = findViewById(R.id.subtract);
        Button multiplyButton  = findViewById(R.id.multiply);
        Button divideButton    = findViewById(R.id.divide);
        Button clearButton     = findViewById(R.id.clear_all);
        Button backspaceButton = findViewById(R.id.backspace);
        Button equalsButton    = findViewById(R.id.equals);
        textView               = findViewById(R.id.mainTextView);

        setNumberButtonClickListener(zeroButton,  0);
        setNumberButtonClickListener(oneButton,   1);
        setNumberButtonClickListener(twoButton,   2);
        setNumberButtonClickListener(threeButton, 3);
        setNumberButtonClickListener(fourButton,  4);
        setNumberButtonClickListener(fiveButton,  5);
        setNumberButtonClickListener(sixButton,   6);
        setNumberButtonClickListener(sevenButton, 7);
        setNumberButtonClickListener(eightButton, 8);
        setNumberButtonClickListener(nineButton,  9);

        setFunctionButtonClickListener(addButton,      "+");
        setFunctionButtonClickListener(subtractButton, "-");
        setFunctionButtonClickListener(multiplyButton, "x");
        setFunctionButtonClickListener(divideButton,   "/");
        setFunctionButtonClickListener(equalsButton,   "=");

        textView.setText("0");

        setClearButtonClickListener(clearButton);

        setBackspaceButtonClickListener(backspaceButton);

        setEqualsButtonClickListener(equalsButton);
    }

    private void setEqualsButtonClickListener(Button equalsButton) {
        equalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] allInputArray = textView.getText().toString().split(" ");

                ArrayList<String> allInputList = new ArrayList<>();

                inputList.addAll(Arrays.asList(allInputArray));

                textView.setText(doMathOnList(allInputList));
            }
        });
    }

    private void setBackspaceButtonClickListener(Button backspaceButton) {
        backspaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentText = textView.getText().toString();

                if (currentText.length() == 1) {
                    textView.setText("0");
                } else {
                    if (functionActive) {
                        textView.setText(currentText.subSequence(0, currentText.length() - 3));
                        functionActive = false;
                    } else if (textView.getText().equals("UNDEFINED")) {
                        textView.setText("0");
                    } else {
                        textView.setText(currentText.subSequence(0, currentText.length() - 1));
                    }
                }
            }
        });
    }

    private void setClearButtonClickListener(Button clearButton) {
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("0");
            }
        });
    }

    String doMathOnList(ArrayList<String> inputList) {

        if (inputList.size() < 3) {
            return inputList.get(0);
        } else if (inputList.get(1).equals("/") && inputList.get(2).equals("0")) {
            return "UNDEFINED";
        }

        String item1 = inputList.get(0);
        String item2 = inputList.get(1);
        String item3 = inputList.get(2);
        int answerInt = 0;
        String answerString;

        switch (item2) {
            case "+":
                answerInt = Integer.parseInt(item1) + Integer.parseInt(item3);
                break;
            case "-":
                answerInt = Integer.parseInt(item1) - Integer.parseInt(item3);
                break;
            case "x":
                answerInt = Integer.parseInt(item1) * Integer.parseInt(item3);
                break;
            case "/":
                answerInt = Integer.parseInt(item1) / Integer.parseInt(item3);
                break;
            default:break;
        }

        answerString = Integer.toString(answerInt);
        inputList.set(0, answerString);
        inputList.remove(1);
        inputList.remove(1);

        if (inputList.size() >= 3) {
            doMathOnList(inputList);
        }

        return inputList.get(0);
    }

    void setNumberButtonClickListener(Button button, final Integer value) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                functionActive = false;

                if (textView.getText() == "0") {
                    if (value != 0) {
                        textView.setText(value.toString());
                    }
                } else {
                    textView.setText(textView.getText() + value.toString());
                }
            }
        });
    }

    void setFunctionButtonClickListener(Button button, final String function) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textView.getText() != "0" && !functionActive) {
                    textView.setText(textView.getText() + " " + function + " ");
                }

                functionActive = true;
            }
        });
    }
}
