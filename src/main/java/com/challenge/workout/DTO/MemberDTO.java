package com.challenge.workout.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO {
    private String Id;
    private String password;
    private String name;
    private String gender;
    private String phone;
    private String profileImagePath;
}
