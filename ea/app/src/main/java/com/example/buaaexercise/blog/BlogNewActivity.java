package com.example.buaaexercise.blog;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.buaaexercise.MainActivity;
import com.example.buaaexercise.R;
import com.example.buaaexercise.backend.dbFunction.DBFunction;
import com.example.buaaexercise.blog.BlogPost;
import com.example.buaaexercise.blog.sensitive_detect.SensitiveWordFilter;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BlogNewActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText contentEditText;

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;

    private Uri imageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        titleEditText = findViewById(R.id.titleEditText);
        contentEditText = findViewById(R.id.contentEditText);

        Button publishButton = findViewById(R.id.publishButton);
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEditText.getText().toString();
                String content = contentEditText.getText().toString();

                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {
                    Toast.makeText(BlogNewActivity.this, "标题和内容不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    title = SensitiveWordFilter.Filter(title);
                    content = SensitiveWordFilter.Filter(content);

                    BlogPost newPost = new BlogPost(title, MainActivity.getCurrentUsername(),
                            new Date(), content, -1);
                    if (imageUri != null) {
                        // Save the image to internal storage and get the new URI
                        Uri savedImageUri = saveImageToInternalStorage(imageUri);
                        newPost.setImageID(savedImageUri.toString());
                    }
                    DBFunction.addBlogToDB(newPost);
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });

        Button chooseImageButton = findViewById(R.id.chooseImageButton);
        chooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            ImageView selectedImageView = findViewById(R.id.selectedImageView);
            selectedImageView.setImageURI(imageUri);
        }
    }

    private Uri saveImageToInternalStorage(Uri sourceUri) {
        try {
            // Get the content resolver for the app's internal storage
            ContentResolver resolver = getContentResolver();

            // Create a file in the internal storage directory
            File internalStorageDir = new File(getFilesDir(), "images");
            if (!internalStorageDir.exists()) {
                internalStorageDir.mkdirs();
            }

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File destinationFile = new File(internalStorageDir, imageFileName + ".jpg");

            // Copy the image to the internal storage
            try {
                InputStream inputStream = resolver.openInputStream(sourceUri);
                OutputStream outputStream = new FileOutputStream(destinationFile);

                byte[] buffer = new byte[8192];
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                inputStream.close();
                outputStream.close();

                // Notify the system that a new image has been added to the gallery
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, destinationFile.getAbsolutePath());
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                // Return the URI of the saved image
                return Uri.fromFile(destinationFile);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
