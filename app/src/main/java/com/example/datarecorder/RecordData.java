package com.example.datarecorder;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Instances of RecordData can record data into memory
 * Start recording: Create Data objects and store them in memory
 * Stop recording: Save data to .csv-file
 * Print latest data values to a texView
 *
 * @author Mika Lammi
 * @version 1.0
 *
 */
public class RecordData {

    //private long startTime;
    private GPSTracker gpsTracker;
    private AccelerometerTracker accelerometerTracker;
    private Context context;
    private TextView textView;
    private ArrayList<Data> dataList;

    private final String TAG = "MainActivity";
    private final int updateTimeInMillis = 500;

    private Handler timerHandler = new Handler();
    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {

            Data data = new Data(gpsTracker.getLocation(), accelerometerTracker.getValues());
            dataList.add(data);

            if (context instanceof MainActivity) {
                textView.setText(data.toString());
            }

            timerHandler.postDelayed(this, updateTimeInMillis);

        }
    };

    /**
     * Constructs an object
     * @param context application context
     * @param textView where to print latest data values
     */
    public RecordData(Context context, TextView textView) {

        this.context = context;
        dataList = new ArrayList<>();

        this.textView = textView;

        gpsTracker = new GPSTracker(context);
        accelerometerTracker = new AccelerometerTracker(context);


    }


    /**
     * Stop recording data and save it in a file.
     */
    public void stopRecording() {
        accelerometerTracker.stopListening();
        timerHandler.removeCallbacks(timerRunnable);
        saveToFile(context);


    }

    /**
     * Start recording data
     */
    public void startRecording() {

        //startTime = System.currentTimeMillis();
        accelerometerTracker.startListening();
        timerHandler.postDelayed(timerRunnable, 0);

    }

    /**
     * Save data to a .csv file.
     * @param context application context
     * @return true if succeeded
     */
    private boolean saveToFile(Context context) {


        final String fileName = "datarecorder.csv";
        StringBuilder sb = new StringBuilder();

        try {

            //File file = new File(Environment.getExternalStoragePublicDirectory(
            //        Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(), fileName);
            final String folder_name = "DataRecorder";
            File folder = new File(Environment.getExternalStorageDirectory(), folder_name);

            File file = new File(folder.getAbsolutePath(), fileName);

            if (!folder.exists()) {
                folder.mkdirs();
            }

            //Environment.
            //If file doesn't exist yet, place titles for csv table at the top of the file
            if (!file.exists()) {
                file.createNewFile();
                sb.append(Data.CSVTitles());
            }

            for (Data data : dataList) {
                sb.append(data.toCSVRow());
            }

            FileOutputStream fileOutputStream = new FileOutputStream(file,true);
            fileOutputStream.write((sb.toString()).getBytes());
            fileOutputStream.close();

            /*
            MediaScannerConnection.scanFile(context.getApplicationContext(), new String[] {Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()}, null, null);

            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(file));
            context.sendBroadcast(intent);
            */


            Toast.makeText(context, "Saved to file datarecorder.csv in DataRecorder folder", Toast.LENGTH_LONG).show();

            //Remove data from the memory
            this.dataList = null;
            dataList = new ArrayList<>();

            return true;
        }  catch(FileNotFoundException ex) {
            Log.d(TAG, ex.getMessage());
            Toast.makeText(context, "Failed to save to a file: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }  catch(IOException ex) {
            Log.d(TAG, ex.getMessage());
            Toast.makeText(context, "Failed to save to a file: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        return  false;
    }



    /*
    private void checkPermission(String permission, int requestId) {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(context,
                permission)
                != PackageManager.PERMISSION_GRANTED) {

            // No explanation needed; request the permission
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{permission},
                    requestId);

        }
    }*/

    /*
    private File getDownloadsStorageDir(String albumName) {
// Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), albumName);
        if (!file.mkdirs()) {
            Log.e(TAG, "Directory not created");
        }
        return file;
    }*/

}
