package com.example.danielgoncalves.listafilmes;

import com.example.danielgoncalves.listafilmes.api.NetworkModule;
import com.example.danielgoncalves.listafilmes.ui.MainActivity;
import com.example.danielgoncalves.listafilmes.ui.SortingDialogFragment;
import com.example.danielgoncalves.listafilmes.ui.detail.MovieDetailActivity;
import com.example.danielgoncalves.listafilmes.ui.detail.MovieDetailFragment;
import com.example.danielgoncalves.listafilmes.ui.grid.MoviesGridFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface NetworkComponent {

    void inject(MoviesGridFragment moviesGridFragment);

    void inject(MainActivity mainActivity);

    void inject(SortingDialogFragment sortingDialogFragment);

    void inject(MovieDetailActivity movieDetailActivity);

    void inject(MovieDetailFragment movieDetailFragment);

}
