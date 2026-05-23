package com.challenge.workout.Controller;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.workout.DTO.MemberDTO;
import com.challenge.workout.DTO.RoomDTO;
import com.challenge.workout.Service.MemberService;
import com.challenge.workout.Service.RoomService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class RoomController {

    private final MemberService memberService;
    private final RoomService roomService;

    @PostMapping(value = "/createRoom", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createRoom(
        Authentication authentication,
        @ModelAttribute RoomDTO dto) {
            System.out.println("1");
        try {
            String userId = authentication.getName();
            MemberDTO member = memberService.getInfo(userId);
            roomService.createRoom(dto, member.getMemberNo());
            return ResponseEntity.ok(Map.of("message", "success"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("방 생성에 실패했습니다.");
        }
    }

}
