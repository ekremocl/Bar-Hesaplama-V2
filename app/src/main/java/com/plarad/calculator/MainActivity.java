package com.plarad.calculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    private EditText maxBar;
    private EditText instruction;
    private EditText maxKn;

    private TextView result;

    private Button calculate;
    private Button openD4K;
    private Button openD3K;
    private Button openITH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        maxBar = findViewById(R.id.maxBar);
        instruction = findViewById(R.id.instruction);
        maxKn = findViewById(R.id.maxKn);

        result = findViewById(R.id.result);

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

            } catch (Exception e) {

                result.setText(
                        "Gecersiz deger"
                );
            }
        });
    }
}
