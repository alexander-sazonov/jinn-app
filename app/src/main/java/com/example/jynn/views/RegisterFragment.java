package com.example.jynn.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jynn.R;
import com.example.jynn.viewmodel.RegisterViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {
    private EditText nameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button registerButton;
    private RegisterViewModel registerViewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        registerViewModel.getUserLiveData().observe(this,firebaseUser -> {
            if(firebaseUser != null){
                Navigation.findNavController(getView()).navigate(R.id.action_registerFragment_to_loggedInFragment);
            }
        });
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        nameEditText = view.findViewById(R.id.fragment_register_name);
        emailEditText = view.findViewById(R.id.fragment_register_email);
        passwordEditText = view.findViewById(R.id.fragment_register_password);
        registerButton = view.findViewById(R.id.fragment_register_btn);
        registerButton.setOnClickListener(view1 -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String name = nameEditText.getText().toString();
            if(email.length()>0 && password.length()>0){
                registerViewModel.register(name, email, password);
            }else{
                Toast.makeText(getContext(),"Email and password must be entered",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}