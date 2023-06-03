package com.example.peanutfriends_0505.service;

import com.example.peanutfriends_0505.domain.Basket;
import com.example.peanutfriends_0505.repository.BasketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasketService {

    private final BasketRepository basketRepository;


    public Basket addBasket(Basket basket) {
        Basket saveBasket = basketRepository.save(basket);
        return saveBasket;
    }

    public List<Basket> getBasketList() {
        List<Basket> baskets = basketRepository.findAll();
        return baskets;
    }

    public Basket findById(Long basketId) {
        Basket findBasket = basketRepository.findById(basketId).orElseThrow(() -> new IllegalArgumentException("잘못된 바구니정보 입니다."));
        return findBasket;
    }

    public void deleteAll() {
        basketRepository.deleteAll();
    }
}
