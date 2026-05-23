package com.challenge.workout.Mapper;

import org.apache.ibatis.annotations.Mapper;

import com.challenge.workout.DTO.RoomDTO;
import com.challenge.workout.DTO.RoomMemberDTO;

@Mapper
public interface RoomMapper {
    void createRoom(RoomDTO roomlist);
    void owerMember(RoomMemberDTO roomMember);
}
