package com.challenge.workout.Controller;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.challenge.workout.DTO.MemberDTO;
import com.challenge.workout.Service.MemberService;
import com.challenge.workout.Service.SmsService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final SmsService smsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberDTO req){
        String token = memberService.login(req);
        if(token == null){
           return ResponseEntity.status(401).body("아이디 또는 비밀번호가 틀렸습니다");
        }
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody MemberDTO req){
        memberService.signup(req);
        return ResponseEntity.ok(Map.of("message", "success"));
    }
 
    @PostMapping("/send-code")
    public ResponseEntity<?> sendCode(@RequestBody Map<String, String> body) {
        try {
            smsService.sendVerificationCode(body.get("phone"));
            return ResponseEntity.ok(Map.of("message", "success"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("문자 발송에 실패했습니다.");
        }
    }

    @PostMapping("/verify-code")
    public ResponseEntity<?> verifyCode(@RequestBody Map<String, String> body) {
        boolean valid = smsService.verifyCode(body.get("phone"), body.get("code"));
        if (!valid) return ResponseEntity.status(400).body("인증번호가 올바르지 않습니다.");
        return ResponseEntity.ok(Map.of("message", "success"));
    }

    @GetMapping("/mypage")
    public ResponseEntity<?> getMember(Authentication authentication){
        String userId = authentication.getName();
        MemberDTO member = memberService.getInfo(userId);
        String phone = member.getPhone();
        String name = member.getName();
        String profileImage = "";

        if(member.getProfileImagePath() != null){
            profileImage = member.getProfileImagePath();
        } else  {profileImage = ""; }
        
        return ResponseEntity.ok(Map.of(
            "id", userId,
            "name", name,
            "phone", phone,
            "profileImage", profileImage
        ));
    }

    @PutMapping(value = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProfile(
            Authentication authentication,
            @RequestParam("name") String name,
            @RequestParam("phone") String phone,
            @RequestParam(name = "profileImage", required = false) MultipartFile profileImage) {

        try {
            String userId = authentication.getName();
            memberService.updateProfile(userId, name, phone, profileImage);
            return ResponseEntity.ok(Map.of("message", "success"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("수정에 실패했습니다.");
        }
    }
}
