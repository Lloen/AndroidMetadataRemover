package com.example.metadataremover;

import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private static final int FILE_SELECT_CODE = 0;
    List<Uri> chosenFiles = new ArrayList<>();
    private ContentResolver contentResolver = this.getContentResolver();
    private Button btnChooseFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.btnChooseFiles = (Button) findViewById(R.id.btnChooseFiles);

        btnChooseFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileIntent();
            }
        });
    }


    private void showFileIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");      //all files
        //intent.setType("image/jpeg");   //JPEG
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "No File Manger found.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    if (null != data) { // checking empty selection
                        if (null != data.getClipData()) { // checking multiple selection or not
                            ClipData clipData = data.getClipData();
                            for (int i = 0; i < clipData.getItemCount(); i++) {
                                chosenFiles.add(clipData.getItemAt(i).getUri());
                            }
                        } else {
                            chosenFiles.add(data.getData());
                        }
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}