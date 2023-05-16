package com.example.peanutfriends_0505.service;

import com.example.peanutfriends_0505.domain.Basket;
import com.example.peanutfriends_0505.repository.BasketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasketService {

    private final BasketRepository basketRepository;


    public Basket addBasket(Basket basket) {
        Basket saveBasket = basketRepository.save(basket);
        return saveBasket;
    }
}
