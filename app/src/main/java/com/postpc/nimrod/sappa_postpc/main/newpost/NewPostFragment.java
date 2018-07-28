package com.postpc.nimrod.sappa_postpc.main.newpost;


import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.postpc.nimrod.sappa_postpc.R;
import com.postpc.nimrod.sappa_postpc.main.utils.LocationUtils;
import com.postpc.nimrod.sappa_postpc.preferences.Preferences;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;


//TODO - maybe this fragment should become also the 'edit my post' fragment.
//TODO - so when it started - maybe it'll get some intent with post position in 'My Posts' array (of even the post object if possible)
//TODO - to display current post details, so the user updates it and saves by simply uploading it again,
//TODO - we might save the original image path in post so we don't have to re-uplosd the image if it wasn't changed, just update the details.

/**
 * The new post fragment.
 */
public class NewPostFragment extends Fragment implements NewPostContract.View{

    @BindView(R.id.category_button)
    ConstraintLayout buttonLayout;

    @BindView(R.id.category_view)
    CardView categoryView;

    @BindView(R.id.image_view)
    ImageView imageView;

    @BindView(R.id.title_edit_text)
    EditText titleEditText;

    @BindView(R.id.description_edit_text)
    EditText descriptionEditText;

    @BindView(R.id.expandableLayout)
    ExpandableLinearLayout expandableLayout;

    @BindViews({R.id.electronics_checkbox, R.id.furniture_checkbox, R.id.books_checkbox, R.id.clothing_checkbox, R.id.sports_checkbox, R.id.children_checkbox, R.id.other_checkbox})
    List<CheckBox> categoryCheckboxes;


    private String category = "general";
    private NewPostContract.Presenter presenter;


    public NewPostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_post_new, container, false);
        ButterKnife.bind(this, v);
        presenter = new NewPostPresenter(this,
                new Preferences(requireContext().getSharedPreferences(Preferences.PREFS_NAME, MODE_PRIVATE)),
                new LocationUtils(requireContext(), requireActivity()));
        presenter.init();
        return v;
    }

    @Override
    public void openGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , 0);
    }

    @Override
    public void openCamera() {
        Intent takePicture  = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture , 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        presenter.onActivityResult(requestCode, resultCode, imageReturnedIntent);
    }

    @Override
    public void initCategoryLayout(boolean categoryExpandedState) {
        expandableLayout.setInRecyclerView(false);
        expandableLayout.setInterpolator(Utils.createInterpolator(Utils.FAST_OUT_LINEAR_IN_INTERPOLATOR));
        expandableLayout.setExpanded(categoryExpandedState);
        expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                createRotateAnimator(buttonLayout, 0f, 180f).start();
                presenter.onCategoryPreOpen();
            }

            @Override
            public void onPreClose() {
                createRotateAnimator(buttonLayout, 180f, 0f).start();
                presenter.onCategoryPreClose();
            }
        });
        buttonLayout.setRotation(categoryExpandedState ? 180f : 0f);
        categoryView.setOnClickListener(view -> onClickButton(expandableLayout));
    }

    @Override
    public void showToast(int stringResourceId) {
        Toast.makeText(getActivity(), getResources().getString(stringResourceId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setImageUri(Uri imageUri) {
        imageView.setImageURI(imageUri);
    }

    @Override
    public String getTitle() {
        return titleEditText.getText().toString();
    }

    @Override
    public String getDescription() {
        return descriptionEditText.getText().toString();
    }

    @Override
    public String getContactName() {
        return ""; // TODO: 28/07/2018 implement this
    }

    @Override
    public String getContactPhone() {
        return "012345678"; // TODO: 28/07/2018 implement this
    }

    @Override
    public String getCategory() {
        return "electronics"; // TODO: 28/07/2018 implement this
    }

    @OnClick(R.id.publish_button)
    public void onPublishClicked(){
        presenter.onPublishClicked();
    }

    private void onClickButton(ExpandableLinearLayout expandableLayout) {
        expandableLayout.toggle();
    }

    private ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }
}
