package com.example.tapptic.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tapptic.R;
import com.example.tapptic.databinding.NumberLightRowBinding;
import com.example.tapptic.model.NumberLight;
import com.example.tapptic.viewModel.FragmentListViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NumbersAdapter extends RecyclerView.Adapter<NumbersAdapter.NumberViewHolder> {

    private FragmentListViewModel fragmentListViewModel;
    List<NumberLight> numberList;
    private NumberLightRowBinding binding;
    private boolean isTabletLandMode;
    private int lastSelectedItem = 0;
    private int currentSelectedItem;

    public NumbersAdapter(FragmentListViewModel fragmentListViewModel, List<NumberLight> numberList, boolean isTabletLandMode, int initialSelectedItem) {
        this.fragmentListViewModel = fragmentListViewModel;
        this.numberList = numberList;
        this.isTabletLandMode = isTabletLandMode;
        this.currentSelectedItem = initialSelectedItem;
    }

    @NonNull
    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.number_light_row, parent, false);
        return new NumberViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NumberViewHolder holder, int position) {
        final NumberLight number = numberList.get(position);
        Picasso.get().load(number.getImagePath()).into(holder.imageView);
        holder.textView.setText(number.getName());
        boolean isSelected = (position == currentSelectedItem && isTabletLandMode);
        holder.layout.setSelected(isSelected);
        holder.itemView.setOnClickListener(view -> {
        lastSelectedItem = currentSelectedItem;
        currentSelectedItem = position;
        notifyItemChanged(lastSelectedItem);
        notifyItemChanged(currentSelectedItem);
        fragmentListViewModel.actionOnClickItem(view, number.getName(), isTabletLandMode);
    });
    }

    @Override
    public int getItemCount() {
        return numberList.size();
    }

    class NumberViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        ConstraintLayout layout;

        public NumberViewHolder(NumberLightRowBinding binding) {
            super(binding.getRoot());

            imageView = binding.imageViewItem;
            textView = binding.textViewItem;
            layout = binding.rowLayout;
        }
    }
}