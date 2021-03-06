package com.samirsayegh.marvelchars.view.main;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.orhanobut.logger.Logger;
import com.samirsayegh.marvelchars.R;
import com.samirsayegh.marvelchars.domain.entities.Hero;
import com.samirsayegh.marvelchars.presenter.mainPresenter.MainPresenter;
import com.samirsayegh.marvelchars.view.base.BaseActivity;
import com.samirsayegh.marvelchars.view.components.RecyclerScrollListener;
import com.samirsayegh.marvelchars.view.main.adapter.MainAdapter;
import com.samirsayegh.marvelchars.view.main.adapter.MainAdapterListener;

import java.util.List;

import butterknife.BindView;

/**
 * Created by yormirsamir.sayegh on 26/04/2017.
 */
public class MainActivity extends BaseActivity implements MainView, MainAdapterListener, TextWatcher {

    private final MainPresenter mainPresenter;
    private MainAdapter mainAdapter;
    private RecyclerScrollListener recyclerScrollListener;

    @BindView(R.id.editTextMainFind)
    EditText editText;
    @BindView(R.id.recyclerViewMain)
    RecyclerView recyclerView;

    public MainActivity() {
        layoutId = R.layout.activity_main;
        mainPresenter = new MainPresenter(this);
        mainAdapter = new MainAdapter();
        mainAdapter.setMainAdapterListener(this);
    }

    @Override
    protected void init() {
        mainPresenter.retrieveCharacters();
        setTitle(R.string.heroes);
        initRecyclerView();
        editText.addTextChangedListener(this);
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerScrollListener = new RecyclerScrollListener(linearLayoutManager, this);
        recyclerView.setAdapter(mainAdapter);
        recyclerView.addOnScrollListener(recyclerScrollListener);
    }

    @Override
    public void listLoaded(final List<Hero> heroList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainAdapter.setNewList(heroList);
                mainAdapter.notifyDataSetChanged();
                recyclerScrollListener.loaded();
            }
        });
    }

    @Override
    public void listUpdated(final List<Hero> heroList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainAdapter.setAddToList(heroList);
                mainAdapter.notifyDataSetChanged();
                recyclerScrollListener.loaded();

            }
        });
    }

    @Override
    public boolean isLoadingMoreItems() {
        return recyclerScrollListener.isLoading();
    }

    @Override
    public void stopLoading() {
        recyclerScrollListener.loaded();
    }

    @Override
    public void loadMoreElements() {
        mainPresenter.loadMoreElements();
    }

    @Override
    public void onClick(View v) {
        int position = recyclerView.getChildLayoutPosition(v);
        Hero hero = mainAdapter.getHero(position);
        Logger.i(hero.toString());
        navigateWithExtra(EXTRA_HERO, hero);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mainPresenter.retrieveCharactersStartingWith(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}
