package com.postpc.nimrod.sappa_postpc.main.settings;

import android.animation.ObjectAnimator;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;
import com.postpc.nimrod.sappa_postpc.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsRecyclerViewAdapter extends RecyclerView.Adapter<SettingsRecyclerViewAdapter.ViewHolder>{


    private SparseBooleanArray expandState = new SparseBooleanArray();
    private final List<SettingModel> data;

    SettingsRecyclerViewAdapter(final List<SettingModel> data) {
        this.data = data;
        for (int i = 0; i < data.size(); i++) {
            expandState.append(i, false);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expandable_settings_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.setData(data.get(position), expandState.get(position), position);
    }

    private void onClickButton(final ExpandableLayout expandableLayout) {
        expandableLayout.toggle();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.setting_title_text_view)
        TextView textView;

        @BindView(R.id.setting_color_view)
        View colorView;

        @BindView(R.id.button)
        ConstraintLayout buttonLayout;

        @BindView(R.id.expandableLayout)
        ExpandableLinearLayout expandableLayout;

        @BindView(R.id.item_layout)
        CardView itemLayout;

        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        void setData(SettingModel item, boolean expandState, int position) {
            setIsRecyclable(false);
            expandableLayout.setInRecyclerView(true);
            expandableLayout.setInterpolator(item.getInterpolator());
            expandableLayout.setExpanded(expandState);
            expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
                @Override
                public void onPreOpen() {
                    createRotateAnimator(buttonLayout, 0f, 180f).start();
                    SettingsRecyclerViewAdapter.this.expandState.put(position, true);
                }

                @Override
                public void onPreClose() {
                    createRotateAnimator(buttonLayout, 180f, 0f).start();
                    SettingsRecyclerViewAdapter.this.expandState.put(position, false);
                }
            });
            buttonLayout.setRotation(SettingsRecyclerViewAdapter.this.expandState.get(position) ? 180f : 0f);
            itemLayout.setOnClickListener(v -> onClickButton(expandableLayout));
            textView.setText(itemView.getContext().getResources().getString(item.getTitleResourceId()));
            colorView.setBackgroundColor(itemView.getContext().getResources().getColor(item.getColorResourceId()));
        }
    }

    private ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }

}
