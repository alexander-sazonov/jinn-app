package com.example.jynn.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jynn.R;
import com.example.jynn.model.Wish;
import com.example.jynn.viewmodel.AddWishViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddWishFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddWishFragment extends Fragment {

    AddWishViewModel addWishViewModel;
    EditText titleEditText;
    EditText descriptionEditText;
    Button addBtn;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddWishFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddWishFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddWishFragment newInstance(String param1, String param2) {
        AddWishFragment fragment = new AddWishFragment();
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

        addWishViewModel = new ViewModelProvider(this).get(AddWishViewModel.class);
        addWishViewModel.getWishAddedLiveData().observe(this,wishAdded -> {
            if(wishAdded){
                Toast.makeText(getContext(), "Wish successfully added", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(getView()).navigate(R.id.action_addWishFragment_to_navigation_wishesFragment);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_wish, container, false);
        titleEditText = view.findViewById(R.id.fragment_add_wish_title);
        descriptionEditText = view.findViewById(R.id.fragment_add_wish_description);
        addBtn = view.findViewById(R.id.fragment_add_wish_btn);
        addBtn.setOnClickListener(view1 -> {
            String title = titleEditText.getText().toString().trim();
            String description = descriptionEditText.getText().toString().trim();
            if(TextUtils.isEmpty(title)){
                titleEditText.setError("Title can not be empty");
            }
            if(TextUtils.isEmpty(description)){
                descriptionEditText.setError("Description can not be empty ");
            }
            addWishViewModel.sendData(title,description);

        });

        return view;
    }
}