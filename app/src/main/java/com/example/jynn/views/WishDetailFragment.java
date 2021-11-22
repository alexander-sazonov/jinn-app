package com.example.jynn.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jynn.R;
import com.example.jynn.model.Wish;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WishDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WishDetailFragment extends Fragment {

    private TextView wishTitleTextView;
    private TextView wishContentTextView;
    private EditText wishCommentEditText;
    private Button btn;
    private Wish wish;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WishDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WishDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WishDetailFragment newInstance(String param1, String param2) {
        WishDetailFragment fragment = new WishDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                wish = (Wish) result.get("bundleKey");
            }
        });*/
        if (getArguments() != null) {
           wish = getArguments().getParcelable("bundleKey");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wish_detail, container, false);
        wishTitleTextView = view.findViewById(R.id.fragment_detail_title);
        wishContentTextView = view.findViewById(R.id.fragment_detail_content);
        wishCommentEditText = view.findViewById(R.id.fragment_detail_comment);
        wishTitleTextView.setText(wish.getTitle());
        wishContentTextView.setText(wish.getDescription());
        btn = view.findViewById(R.id.fragment_detail_btn);
        btn.setOnClickListener(view1 -> {

        });
        return view;
    }
}