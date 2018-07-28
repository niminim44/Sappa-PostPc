package com.postpc.nimrod.sappa_postpc.main.newpost;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.postpc.nimrod.sappa_postpc.R;
import com.postpc.nimrod.sappa_postpc.models.NewPostModel;
import com.postpc.nimrod.sappa_postpc.preferences.Preferences;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


//TODO - maybe this fragment should become also the 'edit my post' fragment.
//TODO - so when it started - maybe it'll get some intent with post position in 'My Posts' array (of even the post object if possible)
//TODO - to display current post details, so the user updates it and saves by simply uploading it again,
//TODO - we might save the original image path in post so we don't have to re-uplosd the image if it wasn't changed, just update the details.

//TODO - you'll probably want to move the logic to NewPostPresenter. I'm just fighting to get things to work ^-^
/**
 * The new post fragment.
 */
public class NewPostFragment extends Fragment implements View.OnClickListener {

    private static int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;     // whatever...

    private StorageReference mStorageRef;   // Firebase Storage Reference.
    private FirebaseDatabase database;      // Firebase Realtime DB instance.
    private DatabaseReference myRef;        // Reference to the location to write into.
    FusedLocationProviderClient mFusedLocationClient;   // Location provider object.

    // Store location coordinates.
    double latitude;
    double longitude;

    //TODO - I don't remember why I didn't use ButterKnife like you showed me. I think I was fighting the fragment and forgot about it.
    // Declare views variables.
    Button publishBtn;
    Button cameraBtn;
    Button galleryBtn;
    View v;
    ImageView imageView;
    EditText titleTextView;
    EditText descriptionTextView;
    EditText nameTextView;
    EditText phoneTextView;
    Uri imageUri;
    String key;


    public NewPostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate fragment layout.
        v = inflater.inflate(R.layout.fragment_new_post, container, false);

        // Get Firebase "Realtime DB" and "Storage" references.
        mStorageRef = FirebaseStorage.getInstance().getReference(); // Initialize reference to Storage.
        database = FirebaseDatabase.getInstance();  // Retrieve an instance of the DB.
        myRef = database.getReference("posts"); // Reference the 'posts'.

        // Initialize views variables (find views in layout).
        imageView = v.findViewById(R.id.image_view);
        titleTextView = v.findViewById(R.id.title_text_view);
        descriptionTextView = v.findViewById(R.id.description_text_view);
        nameTextView = v.findViewById(R.id.name_text_view);
        phoneTextView = v.findViewById(R.id.phone_text_view);

        // Add functionality to buttons.
        publishBtn = v.findViewById(R.id.publish_btn);
        cameraBtn = v.findViewById(R.id.camera_btn);
        galleryBtn = v.findViewById(R.id.gallery_btn);
        publishBtn.setOnClickListener(this);
        cameraBtn.setOnClickListener(this);
        galleryBtn.setOnClickListener(this);

        // Initialize location provider.
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        // Return inflated fragment view.
        return v;
    }

    @Override
    public void onClick(View v) {

        // Publishing a post is done by pushing new node to Realtime DB, getting the unique
        // node key and uploading the image to Firebase Storage. On upload complete we get
        // the image URL and are safe to write the post details into the previously pushed DB node.
        if (v == publishBtn) {

            // Get additional info for post.
            Preferences preferences = new Preferences(requireContext().getSharedPreferences(Preferences.PREFS_NAME, MODE_PRIVATE));
            String userId = preferences.getUserId();
            getDeviceLocation();    //TODO - this method should probably become a separate module becouse we need current location in nearby posts filtering when getting data.

            // Use push() to create a post unique key in the node containing posts.
            key = myRef.push().getKey();

            // Upload photo to storage using a Task.
            final StorageReference ref = mStorageRef.child("images/" + key +".jpg");    // Set image name for storage.
            UploadTask uploadTask = ref.putFile(imageUri);

            //TODO - use irlTask to display progress bar somewhere.
            Task<Uri> urlTask = uploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    // Throw task.getException();
                    CharSequence text = "Failed image upload to storage task";
                    Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
                }

                // Continue with the task to get the download URL
                return ref.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();

                    CharSequence text = "Image uploaded to storage";
                    Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();

                    // Add post to DB.
                    NewPostModel newPost = new NewPostModel(downloadUri.toString(),
                            titleTextView.getText().toString(),
                            descriptionTextView.getText().toString(),
                            latitude,
                            longitude,
                            userId,
                            nameTextView.getText().toString(),
                            Long.parseLong(phoneTextView.getText().toString()), "default");

                    // Write a message to the database.
                    myRef.child(key).setValue(newPost);

                    text = "Published successfully:}";
                    Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();

                } else {
                    // Handle failures.
                    CharSequence text = "Failed image upload to storage";
                    Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
                }
            });


        } else if (v == galleryBtn) {
            openGallery();

            //TODO - I haven't fixed the camera bug yet, camera doesn't save the image taken, probably permissions issue.
        } else if (v == cameraBtn) {
            openCamera();
        }

    }

    // Opens gallery to pick an image.
    private void openGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , 0);
    }

    // Opens camera to take a picture.
    private void openCamera() {
        Intent takePicture  = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture , 1);
    }

    //TODO - the camera bug I've mentioned before and the image view should be filled with the picked picture, currently it's just looks ridiculous:)
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {

        CharSequence text = "Processing result";
        Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();

        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageUri = selectedImage;
                    imageView.setImageURI(selectedImage);
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageUri = selectedImage;
                    imageView.setImageURI(selectedImage);
                }
                break;
        }
    }

    //TODO - as I mentioned above, I need the user location in NearbyPresenter to filter the nearby posts, so this method should probably a module on it's own to use everywhere we need location.
    // Coppied this from somewhere, didn't went much into it, just got it to work.
    private void getDeviceLocation() {
        if (ContextCompat.checkSelfPermission(requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            //TODO - should we show some explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.

                            if (location != null) {
                                // Logic to handle location object
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    });
        }
    }

}
