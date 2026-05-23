package com.challenge.workout.DTO;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO {
    private Long memberNo;
    private String id;
    private String password;
    private String name;
    private String gender;
    private String phone;

    private LocalDateTime createdAt;
    private String profileImagePath;

    private Boolean userPhoneVerified;
}
