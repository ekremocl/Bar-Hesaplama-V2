package com.plarad.calculator;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class PDFActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        try {

            String pdfFile =
                    getIntent().getStringExtra(
                            "pdfFile"
                    );

            InputStream inputStream =
                    getAssets().open(pdfFile);

            File pdf =
                    new File(
                            getCacheDir(),
                            pdfFile
                    );

            FileOutputStream outputStream =
                    new FileOutputStream(pdf);

            byte[] buffer =
                    new byte[1024];

            int length;

            while ((length =
                    inputStream.read(buffer))
                    > 0) {

                outputStream.write(
                        buffer,
                        0,
                        length
                );
            }

            outputStream.close();
            inputStream.close();

            Uri uri =
                    FileProvider.getUriForFile(
                            this,
                            getPackageName()
                                    + ".provider",
                            pdf
                    );

            Intent intent =
                    new Intent(
                            Intent.ACTION_VIEW
                    );

            intent.setDataAndType(
                    uri,
                    "application/pdf"
            );

            intent.addFlags(
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
            );

            startActivity(intent);

            finish();

        } catch (Exception e) {

            e.printStackTrace();

            finish();
        }
    }
}
