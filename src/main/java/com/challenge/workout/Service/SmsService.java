package com.challenge.workout.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SmsService {

    private final StringRedisTemplate redisTemplate;

    @Value("${coolsms.api.key}")
    private String apiKey;

    @Value("${coolsms.api.secret}")
    private String apiSecret;

    @Value("${coolsms.from.number}")
    private String fromNumber;

    private DefaultMessageService messageService;

    @PostConstruct
    public void init() {
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");
    }

    public void sendVerificationCode(String phone) {
        String code = String.format("%06d", new Random().nextInt(1000000));

        Message message = new Message();
        message.setFrom(fromNumber);
        message.setTo(phone);
        message.setText("[MOTI] 인증번호: " + code);

        messageService.sendOne(new SingleMessageSendingRequest(message));

        redisTemplate.opsForValue().set("sms:" + phone, code, 5, TimeUnit.MINUTES);
    }

    public boolean verifyCode(String phone, String code) {
        String saved = redisTemplate.opsForValue().get("sms:" + phone);
        if (saved == null || !saved.equals(code)) return false;
        redisTemplate.delete("sms:" + phone);
        return true;
    }
}
