package com.example.familymapclient;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.familymapclient.tasks.LoginTask;
import com.example.familymapclient.tasks.RegisterTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import requests.RegisterRequest;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button registerButton;
    private Button signInButton;
    private EditText serverHost;
    private EditText portHost;
    private EditText userName;
    private EditText password;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private RadioButton male;
    private RadioButton female;
    private String gender = "m";


    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MYFRAG", "This was actually created");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        userName = view.findViewById(R.id.usernameField);
        serverHost = view.findViewById(R.id.serverHostField);
        portHost = view.findViewById(R.id.serverPortField);
        password = view.findViewById(R.id.passwordField);
        firstName = view.findViewById(R.id.firstNameField);
        lastName = view.findViewById(R.id.lastNameField);
        email = view.findViewById(R.id.emailField);
        male = view.findViewById(R.id.maleField);
        female = view.findViewById(R.id.femaleField);

        userName.addTextChangedListener(mTextWatcher);
        serverHost.addTextChangedListener(mTextWatcher);
        portHost.addTextChangedListener(mTextWatcher);
        password.addTextChangedListener(mTextWatcher);
        firstName.addTextChangedListener(mTextWatcher);
        lastName.addTextChangedListener(mTextWatcher);
        email.addTextChangedListener(mTextWatcher);

        signInButton = view.findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //String usernameString = userName.getText().toString();
                Handler uiThreadMessageHandler = new Handler(){
                    @Override
                    public void handleMessage(Message message){
                        Bundle bundle = message.getData();
                        String message2 = bundle.getString("Message");
                        Toast.makeText(getActivity(), message2, Toast.LENGTH_LONG).show();
                    }
                };
                LoginTask task = new LoginTask(uiThreadMessageHandler, serverHost.getText().toString(),
                        portHost.getText().toString(), userName.getText().toString(),
                        password.getText().toString());
                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.submit(task);
            }
        });

        registerButton = view.findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Handler uiThreadMessageHandler = new Handler(){
                    @Override
                    public void handleMessage(Message message){
                        Bundle bundle = message.getData();
                        String message2 = bundle.getString("Message");
                        Toast.makeText(getActivity(), message2, Toast.LENGTH_LONG).show();
                    }
                };
                RegisterTask task = new RegisterTask(serverHost.getText().toString(), portHost.getText().toString(),
                        userName.getText().toString(), password.getText().toString(), email.getText().toString(), firstName.getText().toString(),
                        lastName.getText().toString(), gender, uiThreadMessageHandler);
                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.submit(task);
            }
        });

        male.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                gender = "m";
                Toast.makeText(getActivity(), gender, Toast.LENGTH_LONG).show();
            }
        });
        female.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                gender = "f";
                Toast.makeText(getActivity(), gender, Toast.LENGTH_LONG).show();
            }
        });
        checkFieldsForEmptyValues();
        return view;
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            checkFieldsForEmptyValues();
        }
    };
    void checkFieldsForEmptyValues(){


        String s1 = serverHost.getText().toString();
        String s2 = portHost.getText().toString();
        String s3 = userName.getText().toString();
        String s4 = password.getText().toString();
        String s5 = firstName.getText().toString();
        String s6 = lastName.getText().toString();
        String s7 = email.getText().toString();

        if(s1.equals("")|| s2.equals("")||s3.equals("")||s4.equals("")){
            signInButton.setEnabled(false);
        } else {
            signInButton.setEnabled(true);
        }
        if(s1.equals("")|| s2.equals("")||s3.equals("")||s4.equals("")||s5.equals("")||s6.equals("")
                ||gender.equals("")||s7.equals("")){
            registerButton.setEnabled(false);
        }else{
            registerButton.setEnabled(true);
        }
    }
}