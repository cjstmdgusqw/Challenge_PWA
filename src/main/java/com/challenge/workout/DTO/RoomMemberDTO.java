package com.challenge.workout.DTO;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomMemberDTO {
    private Long id;
    private Long roomId;
    private Long memberId;

    private String role;
    private LocalDateTime joinedAt;
}
