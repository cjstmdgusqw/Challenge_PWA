package com.challenge.workout.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.challenge.workout.DTO.RoomDTO;
import com.challenge.workout.DTO.RoomMemberDTO;
import com.challenge.workout.Mapper.RoomMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomMapper roomMapper;

    public void createRoom(RoomDTO dto, Long memberNo) throws IOException {
        System.out.println(memberNo);
        if (dto.getRoomImage() != null && !dto.getRoomImage().isEmpty()) {
            String uploadDir = "uploads/room/";
            Files.createDirectories(Paths.get(uploadDir));
            String filename = UUID.randomUUID() + "_" + dto.getRoomImage().getOriginalFilename();
            Path savePath = Paths.get(uploadDir + filename);
            Files.write(savePath, dto.getRoomImage().getBytes());
            dto.setImagePath(uploadDir + filename);
        }
        dto.setCreatedBy(memberNo);
        roomMapper.createRoom(dto);

        RoomMemberDTO roomMember = new RoomMemberDTO();
        roomMember.setRoomId(dto.getId());
        roomMember.setMemberId(memberNo);
        roomMember.setRole("OWNER");
        roomMapper.owerMember(roomMember);
    }
}
