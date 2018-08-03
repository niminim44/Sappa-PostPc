package com.postpc.nimrod.sappa_postpc.search;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.postpc.nimrod.sappa_postpc.R;
import com.postpc.nimrod.sappa_postpc.models.CategorySearchModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements SearchContract.View{


    private SearchContract.Presenter presenter;

    @BindView(R.id.categories_recycler_view)
    RecyclerView categoriesRecyclerView;

    @BindView(R.id.search_edit_text)
    EditText searchEditText;


    public SearchFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, v);
        presenter = new SearchPresenter(this);
        presenter.init();
        return v;
    }

    @OnClick
    public void onSearchClicked(){

    }

    @Override
    public void initRecyclerView(List<CategorySearchModel> categoriesInfo) {
        CategoriesAdapter adapter = new CategoriesAdapter(categoriesInfo);
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        categoriesRecyclerView.setAdapter(adapter);
    }
}
