package com.example.asterisk.xmastoys;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asterisk.xmastoys.model.Toy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

public class ManageToyActivity extends AppCompatActivity {
    static final int GALLERY = 0;
    static final int CAMERA = 1;

    private EditText newToyName;
    private EditText newToyYear;
    private EditText newToyStory;
    private ImageView newToyImage;
    private ImageButton addPhoto;

    private Bitmap bitmap;
    private String path;
    private Toy newToy = new Toy();
    private String userId;
    private int dbMessage;
    private int dbErrorMessage;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private CollectionReference dbToyCollection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_toy);

        // Get Firebase auth instance
        FirebaseAuth auth = FirebaseAuth.getInstance();
        // Get current user
        FirebaseUser user = auth.getCurrentUser();

        if (user == null) {
            startActivity(new Intent(ManageToyActivity.this, LoginChoiceActivity.class));
            finish();
        } else {
            userId = user.getUid();
            // Create reference to collection of toys for current user
            dbToyCollection = db.collection("users").document(userId).collection("toyCollection");
        }

        // Set Toolbar title
        Toolbar myToolbar = findViewById(R.id.my_add_toolbar);
        if (myToolbar != null) {
            // If there is an information about toy, then we need to show Edit toy title
            if (getIntent().getExtras() != null) {
                myToolbar.setTitle(R.string.edit_toy);
                // If there is not such an information, then we need to show Add toy title
            } else {
                myToolbar.setTitle(R.string.add_toy);
            }
        }

        // Set Views
        newToyName = findViewById(R.id.name_edit_text_view);
        newToyYear = findViewById(R.id.year_edit_text_view);
        newToyStory = findViewById(R.id.story_edit_text_view);
        newToyImage = findViewById(R.id.default_picture_image_view);
        addPhoto = findViewById(R.id.camera_image_button);

        // Show info about toy that is ready for editing
        if (getIntent().getExtras() != null) {
            newToy = (Toy) getIntent().getExtras().get("toy");
            assert newToy != null;

            newToyName.setText(newToy.getmToyName());
            newToyYear.setText(newToy.getmYear());
            newToyStory.setText(newToy.getmStory());

            int toyPictureId = newToy.getmImageResourceId();
            if (toyPictureId != 0) {
                newToyImage.setImageResource(toyPictureId);
            } else {
                path = newToy.getmPath();
                if (!TextUtils.isEmpty(path)) {
                    StorageReference toypicturesRef = storage.getReference(path);
                    Log.i("GLIDE", toypicturesRef.toString());
                    GlideApp.with(this)
                            .load(toypicturesRef)
                            .into(newToyImage);
                }
                addPhoto.setBackgroundColor(Color.TRANSPARENT);
            }
        }

        // Logic for addPhoto button
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });

        // Logic for addToy button
        FloatingActionButton fabAddToy = findViewById(R.id.done_toy_fab);
        fabAddToy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get current information from TextViews
                String toyName = newToyName.getText().toString().trim();
                String toyYear = newToyYear.getText().toString().trim();
                String toyStory = newToyStory.getText().toString().trim();

                if (validateInput(toyName, toyYear)) {
                    newToy.setmToyName(toyName);
                    newToy.setmYear(toyYear);

                    if (!TextUtils.isEmpty(toyStory)) {
                        newToy.setmStory(toyStory);
                    }

                    // If bitmap != null, then user has changed the image
                    if (bitmap != null) {
                        // If newToy.getmPath() != null, then this toy had corresponding image in Storage
                        if (newToy.getmPath() != null) {
                            // Delete old image from Storage
                            deleteImage(newToy.getmPath());
                            Log.i("DELETE_IMAGE", "Toy image " + newToy.getmPath() + " had been deleted from Firestore Storage");
                        }
                        // Save new image in Storage
                        saveImage(bitmap);
                        Log.i("ADD_IMAGE", "Toy image " + newToy.getmPath() + " had been added to Firestore Storage");
                        newToy.setmPath(path);
                        newToy.setmImageResourceId(0);
                    } else {
                        // If newToy.getmPath() == null, then this toy do not has custom image,
                        // we need to set default image
                        if (newToy.getmPath() == null) {
                            newToy.setmImageResourceId(R.drawable.toy);
                        }
                    }

                    // Create mDocumentId for toy, if it does not exists in DB
                    if (newToy.getmDocumentId() == null) {
                        String documentId = UUID.randomUUID().toString();
                        newToy.setmDocumentId(documentId);
                        dbMessage = R.string.toy_added;
                        dbErrorMessage = R.string.toy_not_added;
                    } else {
                        dbMessage = R.string.toy_updated;
                        dbErrorMessage = R.string.toy_not_updated;
                    }

                    // Writing newToy into DB
                    dbToyCollection.document(newToy.getmDocumentId()).set(newToy).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Task completed successfully
                                Log.i("WRITE_TO_DB", dbMessage + ": " + task.getException());
                                Toast.makeText(ManageToyActivity.this, dbMessage, Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ManageToyActivity.this, MainActivity.class));
                            } else {
                                // Task failed with an exception
                                Log.e("WRITE_TO_DB", dbErrorMessage + ": " + task.getException());
                                Toast.makeText(ManageToyActivity.this, dbErrorMessage, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private boolean validateInput(String toyName, String toyYear) {
        // Check if name of the toy is empty
        if (TextUtils.isEmpty(toyName)) {
            Toast.makeText(ManageToyActivity.this, "You should provide the name of the toy!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        // Check if year is empty
        if (TextUtils.isEmpty(toyYear)) {
            Toast.makeText(ManageToyActivity.this, "You should provide the year for the toy!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        // Check if year length is not 4 symbols
        if (toyYear.length() != 4) {
            Toast.makeText(this, R.string.year_length, Toast.LENGTH_LONG).show();
            return false;
        }

        // Check if year length is not a digit
        if (toyYear.matches("\\D") || toyYear.contains(".")) {
            Toast.makeText(ManageToyActivity.this, "Year should contain only numbers",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle(R.string.add_photo);
        pictureDialog.setItems(R.array.photo_choice, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case GALLERY:
                        choosePhotoFromGallery();
                        break;
                    case CAMERA:
                        takePhotoFromCamera();
                        break;
                }
            }
        });
        pictureDialog.show();
    }

    private void choosePhotoFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    newToyImage.setImageBitmap(bitmap);
                    addPhoto.setBackgroundColor(Color.TRANSPARENT);
                } catch (IOException e) {
                    Log.e("ADD_TOY_FROM_GALLERY", e.getMessage());
                    Toast.makeText(ManageToyActivity.this, R.string.failed_take_picture_from_gallery,
                            Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            if (data.getExtras() != null) {
                bitmap = (Bitmap) data.getExtras().get("data");
                newToyImage.setImageBitmap(bitmap);
                addPhoto.setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }

    private void saveImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        newToyImage.setDrawingCacheEnabled(false);
        byte[] data = baos.toByteArray();

        path = "toypictures/" + userId + "/" + UUID.randomUUID() + ".png";
        StorageReference toypicturesRef = storage.getReference(path);

        final UploadTask uploadTask = toypicturesRef.putBytes(data);
        uploadTask.addOnSuccessListener(ManageToyActivity.this,
                new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.i("ADD_TOY_SAVE_IMAGE", getString(R.string.image_saved) + " " + path);
                    }
                });
    }

    private void deleteImage(String path) {
        StorageReference toypicturesRef = storage.getReference();
        StorageReference toyToDelete = toypicturesRef.child(path);

        toyToDelete.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i("ADD_TOY_DELETE_IMAGE", getString(R.string.image_deleted));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("ADD_TOY_DELETE_IMAGE", getString(R.string.image_not_deleted) + ": " + exception.getMessage());
            }
        });
    }
}