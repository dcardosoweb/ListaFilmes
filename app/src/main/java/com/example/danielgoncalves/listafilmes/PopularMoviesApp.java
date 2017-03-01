package com.example.danielgoncalves.listafilmes;

import android.app.Application;

import com.example.danielgoncalves.listafilmes.api.NetworkModule;


public class PopularMoviesApp extends Application {

    private NetworkComponent networkComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        networkComponent = DaggerNetworkComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .build();
    }

    public NetworkComponent getNetworkComponent() {
        return networkComponent;
    }

}
