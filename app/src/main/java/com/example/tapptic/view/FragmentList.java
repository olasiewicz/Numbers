package com.example.tapptic.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.databinding.DataBindingUtil;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tapptic.R;
import com.example.tapptic.databinding.FragmentItemListBinding;

import com.example.tapptic.viewModel.FragmentListViewModel;
import com.squareup.picasso.Picasso;

public class FragmentList extends Fragment {

    private MainActivity mainActivity;
    private NumbersAdapter adapter;
    private FragmentItemListBinding binding;
    private LinearLayout progressBarLayout;
    private Button progressBarButton;
    private RecyclerView recyclerView;
    private int initialSelectedItem = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_item_list, container, false);
        binding.includeList.recyclerView.setHasFixedSize(true);
        binding.includeList.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBarLayout = binding.includeList.progressBarLayout;
        progressBarButton = binding.includeList.progressBarButton;
        recyclerView = binding.includeList.recyclerView;
        mainActivity = (MainActivity) getActivity();

        final FragmentListViewModel fragmentListViewModel = ViewModelProviders.of(this).get(FragmentListViewModel.class);

        fragmentListViewModel.getNumbersList().observe(this, numbersList -> {

        adapter = new NumbersAdapter(fragmentListViewModel, numbersList, mainActivity.isTabletLandMode(), initialSelectedItem);
        binding.includeList.recyclerView.setAdapter(adapter);
        progressBarLayout.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);

    });

        fragmentListViewModel.getTextValue().observe(this, s -> {
        progressBarLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        progressBarButton.setOnClickListener(view -> fragmentListViewModel.getNumbersList());
    });

        fragmentListViewModel.getDetails().observe(this, numberLightDetails -> {
        if (mainActivity.isTabletLandMode()) {

            binding.fragmentListDetailsName.setText(numberLightDetails.getName());
            Picasso.get().load(numberLightDetails.getImagePath()).into(binding.fragmentListDetailsImage);
            binding.fragmentListDetailsDescription.setText(numberLightDetails.getDescription());

            int item  = Integer.valueOf(numberLightDetails.getName()) -1;
            initialSelectedItem = item;

        }

        progressBarLayout.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    });


        return binding.getRoot();
    }
}
