package com.postpc.nimrod.sappa_postpc.main.nearbypost;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.postpc.nimrod.sappa_postpc.R;
import com.postpc.nimrod.sappa_postpc.main.utils.UiUtils;
import com.postpc.nimrod.sappa_postpc.models.PostModel;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.postpc.nimrod.sappa_postpc.main.nearbypost.NearbyPostPresenter.CATEGORY;
import static com.postpc.nimrod.sappa_postpc.main.nearbypost.NearbyPostPresenter.DESCRIPTION;
import static com.postpc.nimrod.sappa_postpc.main.nearbypost.NearbyPostPresenter.EMAIL;
import static com.postpc.nimrod.sappa_postpc.main.nearbypost.NearbyPostPresenter.IMAGE_URL;
import static com.postpc.nimrod.sappa_postpc.main.nearbypost.NearbyPostPresenter.LATITUDE;
import static com.postpc.nimrod.sappa_postpc.main.nearbypost.NearbyPostPresenter.LONGITUDE;
import static com.postpc.nimrod.sappa_postpc.main.nearbypost.NearbyPostPresenter.PHONE;
import static com.postpc.nimrod.sappa_postpc.main.nearbypost.NearbyPostPresenter.POST_ID;
import static com.postpc.nimrod.sappa_postpc.main.nearbypost.NearbyPostPresenter.TITLE;
import static com.postpc.nimrod.sappa_postpc.main.nearbypost.NearbyPostPresenter.USER_ID;
import static com.postpc.nimrod.sappa_postpc.main.nearbypost.NearbyPostPresenter.USER_NAME;

/**
 * A simple {@link Fragment} subclass.
 */
public class NearbyPostFragment extends Fragment implements NearbyPostContract.View{


    @BindView(R.id.image_view)
    ImageView imageView;

    @BindView(R.id.title_text_view)
    TextView titleTextView;

    @BindView(R.id.distance_text_view)
    TextView distanceTextView;

    @BindView(R.id.description_text_view)
    TextView descriptionTextView;

    @BindView(R.id.category_text_view)
    TextView categoryTextView;


    public static NearbyPostFragment newInstance(PostModel nearbyPostModel){
        NearbyPostFragment myFragment = new NearbyPostFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, nearbyPostModel.getTitle());
        args.putString(DESCRIPTION, nearbyPostModel.getDescription());
        args.putString(IMAGE_URL, nearbyPostModel.getImageUrl());
        args.putString(CATEGORY, nearbyPostModel.getCategory());
        args.putString(USER_ID, nearbyPostModel.getUserID());
        args.putString(USER_NAME, nearbyPostModel.getUserName());
        args.putDouble(LATITUDE, nearbyPostModel.getLatitude());
        args.putDouble(LONGITUDE, nearbyPostModel.getLongitude());
        args.putString(PHONE, nearbyPostModel.getPhone());
        args.putString(EMAIL, nearbyPostModel.getEmail());
        args.putString(POST_ID, nearbyPostModel.getPostId());
        myFragment.setArguments(args);
        return myFragment;
    }

    private NearbyPostContract.Presenter presenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_nearby_post, container, false);
        ButterKnife.bind(this, v);
        presenter = new NearbyPostPresenter(this, new UiUtils());
        presenter.init();
        return v;
    }

    @OnClick(R.id.back_button)
    public void onBackButtonClicked(){
        presenter.backButtonClicked();
    }

    @Override
    public void callOnBackPressed() {
        Objects.requireNonNull(getActivity()).onBackPressed();
    }

    @Override
    public Bundle getPostArguments() {
        return getArguments();
    }

    @Override
    public void setImage(String imageUrl, int screenWidth, int screenHeight) {

        CircularProgressDrawable circularProgressBar = new CircularProgressDrawable(imageView.getContext());
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .apply(new RequestOptions().placeholder(circularProgressBar))
                .into(imageView);
    }

    @Override
    public void setTitle(String title) {
        titleTextView.setText(title);
    }

    @Override
    public void setDistance(String distance) {
        distanceTextView.setText(distance);
    }

    @Override
    public void setDescription(String description) {
        descriptionTextView.setText(description);
    }

    @Override
    public int getScreenHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    @Override
    public int getScreenWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    @Override
    public void setCategory(String category) {
        categoryTextView.setText(category);
    }

    @Override
    public void createAlertDialog(int titleResourceId, String[] items, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        pictureDialog.setTitle(getContext().getResources().getString(titleResourceId));
        pictureDialog.setItems(items, onClickListener);
        pictureDialog.show();
    }

    @Override
    public void callOwner(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phone));
        startActivity(intent);
    }

    @Override
    public void messageOwner(String title, String phone) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("smsto:" + phone));  // This ensures only SMS apps respond
        intent.putExtra("sms_body", "Hi! I saw your post " + "\"" + title + "\"" + " on Sappa, is it still available?");
        if (intent.resolveActivity(requireContext().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void whatsappOwner(String title, String phone) {
        try {
            String text = "Hi! I saw your post " + "\"" + title + "\"" + " on Sappa, is it still available?";// Replace with your message.
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + "972" + phone +"&text="+text));
            startActivity(intent);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void emailOwner(String title, String email) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + email)); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Is " + "\""+ title + "\"" + " from Sappa still available?");
        if (intent.resolveActivity(requireContext().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @OnClick(R.id.i_need_it_button)
    public void onNeedItClicked() {
        presenter.onNeedItClicked();
    }
}
