package com.example.datarecorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author Mika Lammi
 * @version 1.0
 * The purpose of this simple application is to collect accelerometer and GPS data and store it to a csv-file. This
 * application is a part of Ficonic’s recruitment process for software developers to get an understanding of the
 * Android skills of the candidates. The application shall be developed as a native Android app.
 */
public class MainActivity extends AppCompatActivity{

    private RecordData dataRecording;
    private Button recordButton;
    private TextView textViewData;
    private TextView textViewInstructions;


    private final String INSTRUCTION_START = "Press to start recording accelerometer and GPS data";
    private final String INSTRUCTION_STOP = "Recording accelerometer and GPS data...";
    private final String RECORD_START = "start";
    private final String RECORD_STOP = "stop";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Request permissions from the user
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 123);

        textViewData = (TextView) this.findViewById(R.id.textViewData);
        textViewData.setText("");

        textViewInstructions = (TextView) this.findViewById(R.id.textViewInstructions);
        textViewInstructions.setText(INSTRUCTION_START);

        recordButton = findViewById(R.id.recordButton);
        recordButton.setText(RECORD_START);

        dataRecording = new RecordData(this, textViewData);

        recordButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Button b = (Button) v;
                if (b.getText().equals(RECORD_STOP)) {
                    dataRecording.stopRecording();
                    textViewInstructions.setText(INSTRUCTION_START);
                    b.setText(RECORD_START);
                } else {
                    dataRecording.startRecording();
                    textViewInstructions.setText(INSTRUCTION_STOP);
                    b.setText(RECORD_STOP);
                }
            }
        });



    }

    /**
     * If permissions ACCESS_FINE_LOCATION, WRITE_EXTERNAL_STORAGE or READ_EXTERNAL_STORAGE are not granted, close the application.
     * @param requestCode permission request code
     * @param permissions permissions
     * @param grantResults permission results
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 123) {
            for (int result : grantResults) {
                if (result == PackageManager.PERMISSION_DENIED) {
                    closeNow();
                }

            }
        }
    }

    /**
     * Closes application
     */
    private void closeNow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN  ) {
            this.finishAffinity();
        }
        else {
            this.finish();
        }
    }


}
