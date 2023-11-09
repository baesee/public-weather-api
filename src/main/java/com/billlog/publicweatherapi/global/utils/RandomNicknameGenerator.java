package com.billlog.publicweatherapi.global.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomNicknameGenerator {
    public String getRandomNickname() {
        final String[] adjectives = {
                "Happy", "Sunny", "Lucky", "Cool", "Funny", "Crazy", "Awesome", "Gentle", "Brave", "Super","Dry","Enormous"
        };
        final String[] nouns = {
                "Cat", "Dog", "Tiger", "Dragon", "Lion", "Bear", "Eagle", "Phoenix", "Fox", "Wolf","Crocodile","Watermelon","GuineaPig"
        };

        // Random 객체 생성
        Random random = new Random();

        // 무작위 형용사와 명사 선택
        String adjective = adjectives[random.nextInt(adjectives.length)];
        String noun = nouns[random.nextInt(nouns.length)];

        // 무작위 닉네임 생성
        return adjective + noun;
    }


}
