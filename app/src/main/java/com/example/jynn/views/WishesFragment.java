package com.example.jynn.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jynn.R;
import com.example.jynn.model.Wish;
import com.example.jynn.viewmodel.WishesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WishesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WishesFragment extends Fragment {

    private static String TAG = "WishesFragment";

    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private WishAdapter adapter;
    private WishesViewModel wishesViewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WishesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WishesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WishesFragment newInstance(String param1, String param2) {
        WishesFragment fragment = new WishesFragment();
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wishes, container, false);
        WishAdapter.OnWishClickListener onWishClickListener = new WishAdapter.OnWishClickListener() {
            @Override
            public void onWishClick(Wish wish) {
                //открыть новый фрагмент и передать туда wish
                Toast.makeText(getContext(),wish.getTitle(), Toast.LENGTH_SHORT).show();
                Bundle result = new Bundle();
                result.putParcelable("bundleKey",wish);
                //getParentFragmentManager().setFragmentResult("requestKey",result);
                Navigation.findNavController(getView()).navigate(R.id.action_wishesFragment_to_wishDetailFragment, result);
            }
        };
        adapter = new WishAdapter(onWishClickListener);
        recyclerView = view.findViewById(R.id.fragment_wishes_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        floatingActionButton = view.findViewById(R.id.fragment_wishes_add);
        floatingActionButton.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.action_wishesFragment_to_addWishFragment);
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        wishesViewModel = new ViewModelProvider(this).get(WishesViewModel.class);
        wishesViewModel.getWishesLiveData().observe(getViewLifecycleOwner(), wishes -> {
            adapter.setWishList(wishes);
        });
    }
}