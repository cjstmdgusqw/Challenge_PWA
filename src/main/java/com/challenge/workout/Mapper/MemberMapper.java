package com.challenge.workout.Mapper;

import org.apache.ibatis.annotations.Mapper;

import com.challenge.workout.DTO.MemberDTO;

@Mapper
public interface MemberMapper {
    void insertMember(MemberDTO member);
    MemberDTO findById(String id);
    void updateMember(MemberDTO member);
}
