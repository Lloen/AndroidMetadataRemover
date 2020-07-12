package com.example.metadataremover;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private static final int FILE_SELECT_CODE = 0;

    private List<FileData> chosenFiles;
    private FileAdapter fileAdapter;

    private RecyclerView recyclerView;
    private Button btnChooseFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chosenFiles = new ArrayList<>();

        btnChooseFiles = (Button) findViewById(R.id.btnChooseFiles);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        fileAdapter = new FileAdapter(chosenFiles);
        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(fileAdapter);

        btnChooseFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileIntent();
            }
        });
    }


    private void showFileIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
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
                                Uri uri = clipData.getItemAt(i).getUri();
                                FileData fileData = createFileData(uri);
                                chosenFiles.add(fileData);
                            }
                        } else {
                            Uri uri = data.getData();
                            FileData fileData = createFileData(uri);
                            chosenFiles.add(fileData);
                        }
                    }
                }
                break;
        }
        fileAdapter.notifyDataSetChanged();
        super.onActivityResult(requestCode, resultCode, data);
    }

    private FileData createFileData(Uri uri) {
        File file = new File(uri.getPath());
        FileData fileData = new FileData(file);
        Bitmap bitmapThumbnail = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(file.getAbsolutePath()), 64, 64);
        Drawable drawableThumbnail = new BitmapDrawable(getResources(), bitmapThumbnail);

        fileData.setThumbnail(drawableThumbnail);

        return fileData;
    }
}