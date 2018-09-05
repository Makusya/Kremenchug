package com.example.sash.kremenchug;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Document;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static android.R.attr.data;
import static android.R.attr.delay;

public class Activity_massage extends AppCompatActivity implements View.OnClickListener {

    public static final String TOP_KEY = "Topic";
    public static final String TEXT_KEY = "Text";
    public static final String IMAGE_KEY = "Image";
    public static final String COOR_KEY = "Coordinate";
    public static final String TAG = "InspiringQuote";

    private Uri mImageUri;
    private Uri mImageUri1;
    private AutoCompleteTextView topView;
    //private ProgressBar progressBar;

    Button btninside4;
    Button btninside6;
    String mediaPath;
    ImageView imageView;
    TextView tv; //наши координаты

    final int Request_Code =1;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    StorageReference mStorageRef;
    DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_massage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initPermission();

        btninside6 = (Button) findViewById(R.id.button6);
        btninside4 = (Button) findViewById(R.id.button4);
        btninside4.setOnClickListener(this);
        imageView = (ImageView)findViewById(R.id.imageView);
        tv = (TextView) findViewById(R.id.textView10);
        //progressBar = (ProgressBar) findViewById(R.id.progressBar3);

        mStorageRef = FirebaseStorage.getInstance().getReference("images");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("inform");

        btninside6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 0);
            }
        });
        }

        public void saveSave(View view){
        upLoadFile();

        }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void upLoadFile(){
        if (mImageUri != null){
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));

            fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                          Toast.makeText(Activity_massage.this, "waiting...", Toast.LENGTH_LONG).show();

                          fileReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                              @Override
                              public void onComplete(@NonNull Task<Uri> task) {
                                    mImageUri1 = task.getResult();

                                  topView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView3);
                                  final AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
                                  final String topText = topView.getText().toString();
                                  final String textText = textView.getText().toString();
                                  String coorText = tv.getText().toString();
                                  String a = mImageUri1.toString();

                                  if (topText.isEmpty() ||  coorText.isEmpty()) {return;}

                                  Map<String, Object> user = new HashMap<>();
                                  user.put(TOP_KEY, topText);
                                  user.put(TEXT_KEY, textText);
                                  user.put(COOR_KEY, coorText);
                                  user.put(IMAGE_KEY, a);

                                  db.collection("inform")
                                          .add(user)
                                          .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                              @Override
                                              public void onSuccess(DocumentReference documentReference) {
                                                  //Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                                  Toast.makeText(Activity_massage.this, "upload successful!", Toast.LENGTH_LONG).show();
                                              }
                                          })
                                          .addOnFailureListener(new OnFailureListener() {
                                              @Override
                                              public void onFailure(@NonNull Exception e) {
                                                  //Log.w(TAG, "Error adding document", e);
                                                  Toast.makeText(Activity_massage.this, "Error: text isn't saved!", Toast.LENGTH_LONG).show();
                                              }
                                          });

                                  sendToMap();

                              }
                          });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Activity_massage.this, e.getMessage()+"errrror", Toast.LENGTH_LONG).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    });

        }else{
            Toast.makeText(this, "No file selected", Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void onClick(View view) {
        Intent intent;
        int id = view.getId();
        if (id == R.id.button4){
            intent = new Intent(this, Map_activity.class);
            startActivityForResult(intent, Request_Code);}
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            // When an Image is picked
            if (requestCode == 0 && resultCode == RESULT_OK && null != data && null != data.getData()) {

                // Get the Image from data
                Uri selectedImage = data.getData();
                mImageUri = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                // Set the Image in ImageView for Previewing the Media
                imageView.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();

            }
            else if (requestCode == 1 && resultCode == RESULT_OK && null != data){
                String coor = data.getStringExtra("coordinate");
                tv.setText(coor);
            }
            else {
                Toast.makeText(this, "You haven't picked Image/Video", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }

    public void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    private void sendToMap() {
        Intent loginIntent = new Intent(Activity_massage.this, Activity_massage.class);
        startActivity(loginIntent);
        finish();
    }





}
