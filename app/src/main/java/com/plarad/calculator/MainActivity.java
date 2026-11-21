package com.plarad.calculator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    private EditText maxBar;
    private EditText instruction;
    private EditText maxKn;

    private TextView result;
    private TextView history;

    private Button calculate;
    private Button openD4K;
    private Button openD3K;
    private Button openITH;

    private final StringBuilder calculationHistory =
            new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        maxBar = findViewById(R.id.maxBar);
        instruction = findViewById(R.id.instruction);
        maxKn = findViewById(R.id.maxKn);

        result = findViewById(R.id.result);
        history = findViewById(R.id.history);

        calculate = findViewById(R.id.calculate);

        openD4K = findViewById(R.id.openD4K);
        openD3K = findViewById(R.id.openD3K);
        openITH = findViewById(R.id.openITH);

        openD4K.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            PDFActivity.class
                    );

            intent.putExtra(
                    "pdfFile",
                    "D4K Screw Connections.pdf"
            );

            startActivity(intent);
        });

        openD3K.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            PDFActivity.class
                    );

            intent.putExtra(
                    "pdfFile",
                    "D3K Screw Connections.pdf"
            );

            startActivity(intent);
        });

        openITH.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            PDFActivity.class
                    );

            intent.putExtra(
                    "pdfFile",
                    "ITH Tork Chart.pdf"
            );

            startActivity(intent);
        });

        calculate.setOnClickListener(v -> {

            hideKeyboard();

            try {

                double maxBarValue =
                        Double.parseDouble(
                                maxBar.getText().toString()
                        );

                double instructionValue =
                        Double.parseDouble(
                                instruction.getText().toString()
                        );

                double maxKnValue =
                        Double.parseDouble(
                                maxKn.getText().toString()
                        );

                if (maxKnValue == 0) {

                    result.setText(
                            "MAX KN - NM 0 olamaz"
                    );

                    result.setTextColor(
                            0xFFFFFFFF
                    );

                    return;
                }

                double calculatedBar =
                        (maxBarValue * instructionValue)
                                / maxKnValue;

                result.setText(
                        String.format(
                                "%.2f BAR",
                                calculatedBar
                        )
                );

                calculationHistory.insert(
                        0,
                        String.format(
                                "%.2f BAR\n",
                                calculatedBar
                        )
                );

                history.setText(
                        calculationHistory.toString()
                );

                if (calculatedBar < 500) {

                    result.setTextColor(
                            0xFFFFA500
                    );

                } else if (calculatedBar < 1000) {

                    result.setTextColor(
                            0xFF00FF88
                    );

                } else {

                    result.setTextColor(
                            0xFFFF4444
                    );
                }

            } catch (Exception e) {

                result.setText(
                        "Gecersiz deger"
                );

                result.setTextColor(
                        0xFFFFFFFF
                );
            }
        });
    }

    private void hideKeyboard() {

        View view = getCurrentFocus();

        if (view != null) {

            InputMethodManager imm =
                    (InputMethodManager)
                            getSystemService(
                                    Context.INPUT_METHOD_SERVICE
                            );

            imm.hideSoftInputFromWindow(
                    view.getWindowToken(),
                    0
            );
        }
    }
}
