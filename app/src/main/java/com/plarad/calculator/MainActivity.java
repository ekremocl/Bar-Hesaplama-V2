package com.plarad.calculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText maxBar;
    private EditText instruction;
    private EditText maxKn;

    private TextView result;
    private TextView history;

    private Button calculate;
    private Button clearHistory;
    private Button menuButton;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private final ArrayList<String> historyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);

        menuButton = findViewById(R.id.menuButton);
        maxBar = findViewById(R.id.maxBar);
        instruction = findViewById(R.id.instruction);
        maxKn = findViewById(R.id.maxKn);

        result = findViewById(R.id.result);
        history = findViewById(R.id.history);
        calculate = findViewById(R.id.calculate);
        clearHistory = findViewById(R.id.clearHistory);

        menuButton.setOnClickListener(v ->
                drawerLayout.openDrawer(Gravity.LEFT)
        );

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_d4k) {
                openPdf("D4K Screw Connections.pdf");

            } else if (id == R.id.nav_d3k) {
                openPdf("D3K Screw Connections.pdf");

            } else if (id == R.id.nav_ith) {
                openPdf("ITH Tork Chart.pdf");

            } else if (id == R.id.nav_about) {
                Toast.makeText(
                        MainActivity.this,
                        "Hydraulic Pressure Calculator v1.0",
                        Toast.LENGTH_LONG
                ).show();
            }

            drawerLayout.closeDrawers();
            return true;
        });

        clearHistory.setOnClickListener(v -> {
            historyList.clear();
            history.setText("");
        });

        calculate.setOnClickListener(v -> {
            hideKeyboard();

            try {
                double maxBarValue = parseNumber(maxBar);
                double instructionValue = parseNumber(instruction);
                double maxKnValue = parseNumber(maxKn);

                if (maxKnValue == 0) {
                    result.setText("MAX KN - NM 0 olamaz");
                    result.setTextColor(0xFFFFFFFF);
                    return;
                }

                double calculatedBar =
                        (maxBarValue * instructionValue) / maxKnValue;

                result.setText(
                        String.format("%.2f BAR", calculatedBar)
                );

                historyList.add(
                        0,
                        String.format("%.2f BAR", calculatedBar)
                );

                while (historyList.size() > 5) {
                    historyList.remove(historyList.size() - 1);
                }

                StringBuilder historyText = new StringBuilder();

                for (String item : historyList) {
                    historyText.append(item).append("\n");
                }

                history.setText(historyText.toString());

                if (calculatedBar < 500) {
                    result.setTextColor(0xFFFFA500);

                } else if (calculatedBar < 1000) {
                    result.setTextColor(0xFF00FF88);

                } else {
                    result.setTextColor(0xFFFF4444);
                }

            } catch (Exception e) {
                result.setText("Gecersiz deger");
                result.setTextColor(0xFFFFFFFF);
            }
        });
    }

    private double parseNumber(EditText input) {
        String text = input.getText().toString().trim();

        if (text.isEmpty()) {
            throw new NumberFormatException("Empty input");
        }

        return Double.parseDouble(text.replace(',', '.'));
    }

    private void openPdf(String pdfName) {
        Intent intent = new Intent(
                MainActivity.this,
                PDFActivity.class
        );

        intent.putExtra("pdfFile", pdfName);
        startActivity(intent);
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();

        if (view != null) {
            InputMethodManager imm =
                    (InputMethodManager) getSystemService(
                            Context.INPUT_METHOD_SERVICE
                    );

            if (imm != null) {
                imm.hideSoftInputFromWindow(
                        view.getWindowToken(),
                        0
                );
            }
        }
    }
}