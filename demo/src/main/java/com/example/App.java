package com.example;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        CacheLoader<String, String> loader;

        loader = new CacheLoader<String, String>() {
            @Override
            public String load(String key) {
                return key.toUpperCase();
                // gør at value til en key bliver key'en sat til uppercase
            }
        };

        LoadingCache<String, String> cache;
        // sætter max størrelsen til 3
        cache = CacheBuilder.newBuilder().maximumSize(3).build(loader);
        cache.getUnchecked("anders");
        cache.getUnchecked("rasmus");
        cache.getUnchecked("marcus");
        cache.getUnchecked("hans");
        cache.getUnchecked("lise");
        cache.getUnchecked("anna");
        cache.getUnchecked("anne");
        System.out.println(cache.asMap());
        // vil kun printe de sidste 3 - Altså {lise=LISE,anna=ANNA,anne=ANNE}, da
        // størelsen er sat til max 3 og den første der kom ind er også den første der
        // ryger, derfor er de 3 sidst tilføjet tilbage

        LoadingCache<String, String> cache1;
        // sætter at de bliver slettet efter 2millisekunder
        cache1 = CacheBuilder.newBuilder().expireAfterAccess(2, TimeUnit.MILLISECONDS).build(loader);
        cache1.getUnchecked("anders");
        cache1.getUnchecked("lise");
        cache1.getUnchecked("anna");
        cache1.getUnchecked("anne");

        try {
            Thread.sleep(300); // Her tilføjes en pause på 300 millisekunder (0.3 sekunder)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        cache1.getUnchecked("rasmus");
        cache1.getUnchecked("marcus");
        cache1.getUnchecked("hans");
        System.out.println(cache1.asMap());
        // her vil kun {marcus=MARCUS, rasmus=RASMUS, hans=HANS} blive vist, da de
        // forskellige key/value har en levetid på 2milisekunder

        // få fat i en value
        try {
            String val = cache.get("anna");
            System.out.println(val);
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
