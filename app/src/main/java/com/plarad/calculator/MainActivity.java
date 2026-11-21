package com.plarad.calculator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    private EditText maxBar;
    private MaterialAutoCompleteTextView instruction;
    private TextInputLayout instructionLayout;
    private EditText maxKn;

    private TextView result;
    private TextView history;

    private Button calculate;
    private Button clearHistory;
    private Button menuButton;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private final ArrayList<String> historyList =
            new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        drawerLayout =
                findViewById(R.id.drawerLayout);

        navigationView =
                findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);

        menuButton =
                findViewById(R.id.menuButton);

        maxBar =
                findViewById(R.id.maxBar);

        instruction =
                findViewById(R.id.instruction);

        instructionLayout =
                findViewById(R.id.instructionLayout);

        maxKn =
                findViewById(R.id.maxKn);

        result =
                findViewById(R.id.result);

        history =
                findViewById(R.id.history);

        calculate =
                findViewById(R.id.calculate);

        clearHistory =
                findViewById(R.id.clearHistory);

        setupInstructionDropdown();

        menuButton.setOnClickListener(v ->
                drawerLayout.openDrawer(Gravity.LEFT)
        );

        navigationView.setNavigationItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_d4k) {

                openPdf(
                        "D4K Screw Connections.pdf"
                );

            } else if (id == R.id.nav_d3k) {

                openPdf(
                        "D3K Screw Connections.pdf"
                );

            } else if (id == R.id.nav_ith) {

                openPdf(
                        "ITH Tork Chart.pdf"
                );

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

                double maxBarValue =
                        parseNumber(maxBar);

                double instructionValue =
                        parseNumber(instruction);

                double maxKnValue =
                        parseNumber(maxKn);

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
                        (maxBarValue
                                * instructionValue)
                                / maxKnValue;

                result.setText(
                        String.format(
                                "%.2f BAR",
                                calculatedBar
                        )
                );

                historyList.add(
                        0,
                        String.format(
                                "%.2f BAR",
                                calculatedBar
                        )
                );

                while (historyList.size() > 5) {

                    historyList.remove(
                            historyList.size() - 1
                    );
                }

                StringBuilder historyText =
                        new StringBuilder();

                for (String item : historyList) {

                    historyText
                            .append(item)
                            .append("\n");
                }

                history.setText(
                        historyText.toString()
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

    private void setupInstructionDropdown() {
        ArrayList<InstructionOption> options =
                new ArrayList<>();

        addInstructionOptions(
                options,
                "D4K Screw Connections.pdf",
                R.array.d4k_instruction_options
        );

        addInstructionOptions(
                options,
                "D3K Screw Connections.pdf",
                R.array.d3k_instruction_options
        );

        ArrayAdapter<InstructionOption> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_dropdown_item_1line,
                        options
                ) {
                    @Override
                    public boolean areAllItemsEnabled() {
                        return false;
                    }

                    @Override
                    public boolean isEnabled(int position) {
                        InstructionOption option = getItem(position);
                        return option != null && !option.isHeader;
                    }

                    @Override
                    public View getDropDownView(
                            int position,
                            View convertView,
                            ViewGroup parent
                    ) {
                        TextView row = (TextView) super.getDropDownView(
                                position,
                                convertView,
                                parent
                        );
                        InstructionOption option = getItem(position);
                        boolean isHeader =
                                option != null && option.isHeader;

                        row.setTypeface(
                                null,
                                isHeader ? Typeface.BOLD : Typeface.NORMAL
                        );
                        row.setTextColor(
                                isHeader ? 0xFF1D3557 : 0xFF172033
                        );
                        row.setBackgroundColor(
                                isHeader ? 0xFFE5EDF8 : Color.WHITE
                        );
                        return row;
                    }
                };

        instruction.setAdapter(adapter);
        instruction.setThreshold(0);

        instruction.setOnItemClickListener((parent, view, position, id) -> {
            InstructionOption option =
                    (InstructionOption) parent.getItemAtPosition(position);

            if (option.isHeader) {
                return;
            }

            instruction.setText(option.value, false);
            instructionLayout.setHelperText(
                    "Kaynak: " + option.sourceName
                            + " • Bölüm: " + option.section
                            + " • Vida: " + option.fastener
            );
        });

        instruction.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s,
                    int start,
                    int count,
                    int after
            ) {
            }

            @Override
            public void onTextChanged(
                    CharSequence s,
                    int start,
                    int before,
                    int count
            ) {
                instructionLayout.setHelperText(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void addInstructionOptions(
            ArrayList<InstructionOption> options,
            String sourceName,
            int valuesResource
    ) {
        for (String encodedSection : getResources().getStringArray(valuesResource)) {
            String[] sectionParts = encodedSection.split("\\|", 2);
            if (sectionParts.length != 2) {
                throw new IllegalStateException(
                        "Invalid torque option section: " + encodedSection
                );
            }

            String section = sectionParts[0];
            options.add(
                    InstructionOption.header(sourceName, section)
            );

            HashSet<String> sectionOptions = new HashSet<>();
            for (String encodedOption : sectionParts[1].split(",")) {
                String[] optionParts = encodedOption.split("@", 2);
                if (optionParts.length != 2) {
                    throw new IllegalStateException(
                            "Invalid torque option: " + encodedOption
                    );
                }

                if (sectionOptions.add(encodedOption)) {
                    options.add(
                            InstructionOption.value(
                                    sourceName,
                                    section,
                                    optionParts[0],
                                    optionParts[1]
                            )
                    );
                }
            }
        }
    }

    private double parseNumber(EditText input) {
        return Double.parseDouble(
                input.getText()
                        .toString()
                        .trim()
                        .replace(',', '.')
        );
    }

    private static class InstructionOption {
        private final String sourceName;
        private final String section;
        private final String fastener;
        private final String value;
        private final boolean isHeader;

        private InstructionOption(
                String sourceName,
                String section,
                String fastener,
                String value,
                boolean isHeader
        ) {
            this.sourceName = sourceName;
            this.section = section;
            this.fastener = fastener;
            this.value = value;
            this.isHeader = isHeader;
        }

        private static InstructionOption header(
                String sourceName,
                String section
        ) {
            return new InstructionOption(
                    sourceName,
                    section,
                    "",
                    "",
                    true
            );
        }

        private static InstructionOption value(
                String sourceName,
                String section,
                String value,
                String fastener
        ) {
            return new InstructionOption(
                    sourceName,
                    section,
                    fastener,
                    value,
                    false
            );
        }

        @Override
        public String toString() {
            if (isHeader) {
                return sourceName + " • " + section;
            }
            return value + " Nm tork — " + fastener;
        }
    }

    private void openPdf(String pdfName) {

        Intent intent =
                new Intent(
                        MainActivity.this,
                        PDFActivity.class
                );

        intent.putExtra(
                "pdfFile",
                pdfName
        );

        startActivity(intent);
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
