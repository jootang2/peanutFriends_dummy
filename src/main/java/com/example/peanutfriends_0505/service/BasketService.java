package com.example.peanutfriends_0505.service;

import com.example.peanutfriends_0505.domain.Basket;
import com.example.peanutfriends_0505.domain.BasketMember;
import com.example.peanutfriends_0505.repository.BasketRepository;
import com.example.peanutfriends_0505.status.BasketStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasketService {

    private final BasketRepository basketRepository;
    private final BasketMemberService basketMemberService;


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

    @Scheduled(cron = "0 * * * * *") // 매 분 0초마다 실행
    public void cronTest() {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");
        String NOW = formatter.format(now);
        List<Basket> basketList = basketRepository.findAll();
        for(Basket basket : basketList){
            if(basket.getStartDate().equals(NOW)){
                basket.setStatus(String.valueOf(BasketStatus.RUNNING));
                Long basketId = basket.getBasketId();
                List<BasketMember> basketMemberList = basketMemberService.findByBasketId(basketId);
                for(BasketMember basketMember : basketMemberList){
                    basketMember.getMember();
                }

                basketRepository.save(basket);

            }
        }

    }

    public void deleteAll() {
        basketRepository.deleteAll();
    }
}
