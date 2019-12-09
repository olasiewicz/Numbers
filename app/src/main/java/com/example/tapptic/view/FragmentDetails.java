package com.example.tapptic.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.tapptic.R;
import com.example.tapptic.databinding.ItemDetailsBinding;
import com.example.tapptic.model.NumberLightDetails;
import com.example.tapptic.viewModel.FragmentDetailsViewModel;
import com.squareup.picasso.Picasso;

public class FragmentDetails extends Fragment {

    private ItemDetailsBinding binding;
    private MainActivity mainActivity;
    private LinearLayout progressBarLayout;
    private Button progressButton;
    private ConstraintLayout detailsLayout;
    private FragmentDetailsViewModel fragmentDetailsViewModel;
    private FragmentDetailsArgs args;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.item_details, container, false);
        progressBarLayout = binding.progressBarLayout;
        progressButton = binding.progressBarButton;
        detailsLayout = binding.detailsLayout;

        mainActivity = (MainActivity) getActivity();

        args = FragmentDetailsArgs.fromBundle(getArguments());

        if (savedInstanceState != null && mainActivity.isTabletLandMode()) {
            mainActivity.popNavigatorBackStack();
        }

        fragmentDetailsViewModel = ViewModelProviders.of(this).get(FragmentDetailsViewModel.class);


        fragmentDetailsViewModel.getText().observe(this, nrDetails -> {
        progressBarLayout.setVisibility(View.VISIBLE);
        progressButton.setOnClickListener(view -> loadData());
        detailsLayout.setVisibility(View.INVISIBLE);
        loadData();
    });


        loadData();

        return binding.getRoot();

    }

    private void loadData() {
        fragmentDetailsViewModel.getNumberDetails(args.getName() != null ? args.getName() : "1").observe(this, nrDetails -> {
        Picasso.get().load(nrDetails.getImagePath()).into(binding.imageViewDetails);
        binding.textViewDetails.setText(nrDetails.getDescription());
        binding.textViewName.setText(nrDetails.getName());

        detailsLayout.setVisibility(View.VISIBLE);
        progressBarLayout.setVisibility(View.INVISIBLE);

    });
    }
}
