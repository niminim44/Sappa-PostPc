package com.postpc.nimrod.sappa_postpc.search;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.postpc.nimrod.sappa_postpc.R;
import com.postpc.nimrod.sappa_postpc.models.CategorySearchModel;
import com.postpc.nimrod.sappa_postpc.preferences.Preferences;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.postpc.nimrod.sappa_postpc.preferences.Preferences.PREFS_NAME;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements SearchContract.View{


    private SearchContract.Presenter presenter;

    @BindView(R.id.categories_recycler_view)
    RecyclerView categoriesRecyclerView;

    @BindView(R.id.search_edit_text)
    EditText searchEditText;
    private CategoriesAdapter adapter;


    public SearchFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, v);
        presenter = new SearchPresenter(this,
                new Preferences(Objects.requireNonNull(getContext()).getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)),
                EventBus.getDefault());
        presenter.init();
        return v;
    }

    @OnClick(R.id.search_button)
    public void onSearchClicked(){
        presenter.onSearchClicked();
    }

    @Override
    public void initRecyclerView(List<CategorySearchModel> categoriesInfo) {
        adapter = new CategoriesAdapter(categoriesInfo);
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        categoriesRecyclerView.setAdapter(adapter);
    }

    @Override
    public String getSearchEditTextInput() {
        return searchEditText.getText().toString();
    }

    @Override
    public List<CategorySearchModel> getCategoriesToSearch() {
        return adapter.getItems();
    }

    @Override
    public void callOnBackPressed() {
        (Objects.requireNonNull(getActivity())).onBackPressed();
    }

    @Override
    public void initSearchEditText(String freeTextFilter) {
        searchEditText.setText(freeTextFilter);
        searchEditText.setOnEditorActionListener(((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager imm = (InputMethodManager)textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                return true;
            }
            return false;
        }));
    }
}
