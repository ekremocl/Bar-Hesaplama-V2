package com.plarad.calculator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText maxBar;
    private MaterialAutoCompleteTextView instruction;
    private TextView instructionDetails;
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

        instructionDetails =
                findViewById(R.id.instructionDetails);

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
                        return option != null
                                && !option.isHeader
                                && option.selectable;
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

                        row.setSingleLine(false);
                        row.setEllipsize(null);
                        row.setTextSize(
                                TypedValue.COMPLEX_UNIT_SP,
                                isHeader ? 14 : 13
                        );
                        float density =
                                getResources().getDisplayMetrics().density;
                        int verticalPadding = (int) (8 * density);
                        int horizontalPadding = (int) (16 * density);
                        row.setPadding(
                                horizontalPadding,
                                verticalPadding,
                                horizontalPadding,
                                verticalPadding
                        );
                        row.setTypeface(
                                null,
                                isHeader ? Typeface.BOLD : Typeface.NORMAL
                        );
                        if (isHeader) {
                            row.setTextColor(0xFF1D3557);
                        } else if (option != null && !option.selectable) {
                            row.setTextColor(0xFF757575);
                        } else {
                            row.setTextColor(0xFF172033);
                        }
                        row.setBackgroundColor(
                                isHeader ? 0xFFE5EDF8 : Color.WHITE
                        );
                        return row;
                    }
                };

        adapter.setDropDownViewResource(R.layout.torque_option_dropdown_item);
        instruction.setAdapter(adapter);
        instruction.setThreshold(0);

        instruction.setOnItemClickListener((parent, view, position, id) -> {
            InstructionOption option =
                    (InstructionOption) parent.getItemAtPosition(position);

            if (option.isHeader) {
                return;
            }

            if (!option.selectable) {
                return;
            }

            instruction.setText(option.numericValue, false);
            instructionDetails.setText(
                    "Source: " + option.sourceName
                            + "\nSection: " + option.section
                            + "\nDesignation: " + option.designation
                            + " | Thread: " + option.thread
                            + " | Grade: " + option.grade
                            + " | Wrench size (A/F): " + option.wrenchSize
                            + " | Tightening torque: "
                            + option.tighteningTorque
            );
            instructionDetails.setVisibility(View.VISIBLE);
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
                instructionDetails.setText("");
                instructionDetails.setVisibility(View.GONE);
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
        String currentSection = null;
        for (String encodedRow : getResources().getStringArray(valuesResource)) {
            String[] fields = encodedRow.split("\\|", -1);
            if (fields.length != 8) {
                throw new IllegalStateException(
                        "Invalid torque option row: " + encodedRow
                );
            }

            String section = fields[0];
            if (!section.equals(currentSection)) {
                options.add(
                        InstructionOption.header(sourceName, section)
                );
                currentSection = section;
            }

            options.add(
                    InstructionOption.value(
                            sourceName,
                            fields[0],
                            fields[1],
                            fields[2],
                            fields[3],
                            fields[4],
                            fields[5],
                            fields[6],
                            Boolean.parseBoolean(fields[7])
                    )
            );
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
        private final String designation;
        private final String thread;
        private final String grade;
        private final String wrenchSize;
        private final String tighteningTorque;
        private final String numericValue;
        private final boolean isHeader;
        private final boolean selectable;

        private InstructionOption(
                String sourceName,
                String section,
                String designation,
                String thread,
                String grade,
                String wrenchSize,
                String tighteningTorque,
                String numericValue,
                boolean isHeader,
                boolean selectable
        ) {
            this.sourceName = sourceName;
            this.section = section;
            this.designation = designation;
            this.thread = thread;
            this.grade = grade;
            this.wrenchSize = wrenchSize;
            this.tighteningTorque = tighteningTorque;
            this.numericValue = numericValue;
            this.isHeader = isHeader;
            this.selectable = selectable;
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
                    "",
                    "",
                    "",
                    "",
                    true,
                    false
            );
        }

        private static InstructionOption value(
                String sourceName,
                String section,
                String designation,
                String thread,
                String grade,
                String wrenchSize,
                String tighteningTorque,
                String numericValue,
                boolean selectable
        ) {
            return new InstructionOption(
                    sourceName,
                    section,
                    designation,
                    thread,
                    grade,
                    wrenchSize,
                    tighteningTorque,
                    numericValue,
                    false,
                    selectable
            );
        }

        @Override
        public String toString() {
            if (isHeader) {
                return sourceName + " • " + section;
            }
            return tighteningTorque + " • Designation: " + designation
                    + "\nThread: " + thread
                    + " | Grade: " + grade
                    + "\nWrench size (A/F): " + wrenchSize
                    + " | Tightening torque: " + tighteningTorque;
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
