package com.example.peanutfriends_0505.service;

import com.example.peanutfriends_0505.domain.Basket;
import com.example.peanutfriends_0505.domain.BasketMember;
import com.example.peanutfriends_0505.domain.Member;
import com.example.peanutfriends_0505.repository.BasketRepository;
import com.example.peanutfriends_0505.status.BasketStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

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
                //시작 시간이 되면 RUNNING으로 상태 변환
                basket.setStatus("RUNNING");
                Long basketId = basket.getBasketId();
                //상태 변환된 basket 저장
                basketRepository.save(basket);
                //해당 basket에 있는 memberList
                List<BasketMember> basketMemberList = basketMemberService.findByBasketId(basketId);
                //랜덤 매칭을 위한 memberList
                ArrayList<Member> memberList = new ArrayList<>();
                for(BasketMember basketMember : basketMemberList){
                    if(basketMember.getIsPermit()){
                        memberList.add(basketMember.getMember());
                    }
                }
                //memberList에서 random으로 peanuFriend 할당
                for(BasketMember basketMember : basketMemberList){
                    if(basketMember.getIsPermit()) {
                        //memberList를 랜덤으로 섞음
                        Collections.shuffle(memberList);
                        //섞은 list에서 0번째 index
                        Member peanutFriend = memberList.get(0);
                        //뽑은 idx를 peanutFriend에 할당
                        basketMember.setPeanutFriend(peanutFriend);
                        //뽑은 idx는 삭제
                        memberList.remove(0);
                        System.out.println();
                    }
                }
            }
        }
    }

    public void deleteAll() {
        basketRepository.deleteAll();
    }
}
