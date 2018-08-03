package com.postpc.nimrod.sappa_postpc.search;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.postpc.nimrod.sappa_postpc.R;
import com.postpc.nimrod.sappa_postpc.models.CategorySearchModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder>{

    private List<CategorySearchModel> items;

    public CategoriesAdapter(List<CategorySearchModel> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.choose_category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(items.get(position), position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<CategorySearchModel> getItems() {
        return items;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.yes_icon)
        ImageView yesIcon;

        @BindView(R.id.no_icon)
        ImageView noIcon;

        @BindView(R.id.category_name_text_view)
        TextView categoryName;

        private int position;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setData(CategorySearchModel categorySearchModel, int position) {
            this.position = position;
            categoryName.setText(categorySearchModel.getName());
            if(categorySearchModel.isSelected()){
                markAsSelected();
            }
            else{
                markAsUnSelected();
            }
        }

        private void markAsUnSelected() {
            items.get(position).setSelected(false);
            noIcon.setImageResource(R.drawable.no_on);
            yesIcon.setImageResource(R.drawable.yes_off);
        }

        private void markAsSelected() {
            items.get(position).setSelected(true);
            noIcon.setImageResource(R.drawable.no_off);
            yesIcon.setImageResource(R.drawable.yes_on);
        }

        @OnClick(R.id.yes_icon)
        public void onYesClicked(){
            markAsSelected();
        }

        @OnClick(R.id.no_icon)
        public void onNoClicked(){
            markAsUnSelected();
        }
    }
}
