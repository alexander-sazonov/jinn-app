package com.example.jynn.views;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.jynn.R;
import com.example.jynn.viewmodel.LoggedInViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileFragment extends Fragment {

    private TextView userEmailTextView;
    private TextView userNameTextView;
    private Button logOutButton;
    private ImageView userPhotoImageView;
    private LoggedInViewModel loggedInViewModel;

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            loggedInViewModel.uploadUserPhoto(result);
        }
    });

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoggedInFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserProfileFragment newInstance(String param1, String param2) {
        UserProfileFragment fragment = new UserProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        loggedInViewModel = new ViewModelProvider(this).get(LoggedInViewModel.class);
        loggedInViewModel.getUserLiveData().observe(this,firebaseUser -> {
            if(firebaseUser != null){
                userEmailTextView.setText(firebaseUser.getEmail());
                userNameTextView.setText(firebaseUser.getDisplayName());
                if(firebaseUser.getPhotoUrl()==null){
                    int recourseId = R.drawable.user_image;
                    userPhotoImageView.setImageResource(recourseId);
                    /*Uri imageUri = (new Uri.Builder())
                            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                            .authority(getResources().getResourcePackageName(recourseId))
                            .appendPath(getResources().getResourceTypeName(recourseId))
                            .appendPath(getResources().getResourceEntryName(recourseId))
                            .build();
                    loggedInViewModel.uploadUserPhoto(imageUri);*/
                }else{
                    Glide.with(this).load(firebaseUser.getPhotoUrl()).apply(RequestOptions.circleCropTransform()).into(userPhotoImageView);
                    //Glide.with(this).load(firebaseUser.getPhotoUrl()).into(userPhotoImageView);
                }

                logOutButton.setEnabled(true);
            }else{
                logOutButton.setEnabled(false);
            }

        });
        loggedInViewModel.getLoggedOutLiveData().observe(this, loggedOut -> {
            if(loggedOut){
                Toast.makeText(getContext(),"User Logged Out", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(getView()).navigate(R.id.action_loggedInFragment_to_loginRegisterFragment);

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        userEmailTextView = view.findViewById(R.id.fragment_profile_email);
        userNameTextView = view.findViewById(R.id.fragment_profile_name);
        userPhotoImageView = view.findViewById(R.id.fragment_profile_photo);
        userPhotoImageView.setOnClickListener(view1 -> {
            mGetContent.launch("image/*");
        });
        logOutButton = view.findViewById(R.id.fragment_loggedin_logOut);
        logOutButton.setOnClickListener(view1 -> {
            loggedInViewModel.logOut();
        });
        return view;

    }


}