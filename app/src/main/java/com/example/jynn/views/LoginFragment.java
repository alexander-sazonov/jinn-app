package com.example.jynn.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.jynn.R;
import com.example.jynn.viewmodel.LoginViewModel;

public class LoginFragment extends Fragment {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button registerButton;
    private Button loginButton;
    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = new  ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.getUserLiveData().observe(this, firebaseUser -> {
            if(firebaseUser != null){
                Navigation.findNavController(getView()).navigate(R.id.action_loginRegisterFragment_to_loggedInFragment);
            }
        });
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login,container,false);
        emailEditText = view.findViewById(R.id.fragment_loginregister_email);
        passwordEditText = view.findViewById(R.id.fragment_loginregister_password);
        registerButton = view.findViewById(R.id.fragment_loginregister_register);
        loginButton = view.findViewById(R.id.fragment_loginregister_login);

        registerButton.setOnClickListener(view1 -> {
            Navigation.findNavController(getView()).navigate(R.id.action_loginRegisterFragment_to_registerFragment);



        });
        loginButton.setOnClickListener(view1 -> {

            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            if(email.length()>0 && password.length()>0){
                loginViewModel.login(email, password);
            }else{
                Toast.makeText(getContext(),"Email and password must be entered",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
