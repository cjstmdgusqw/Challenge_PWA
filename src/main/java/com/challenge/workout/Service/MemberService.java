package com.challenge.workout.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.challenge.workout.DTO.MemberDTO;
import com.challenge.workout.JWT.JwtUtil;
import com.challenge.workout.Mapper.MemberMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void signup(MemberDTO member) {
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberMapper.insertMember(member);
    }

    public String login(MemberDTO req){
        MemberDTO member = memberMapper.findById(req.getId());
        if(member == null) return null;
        if(!passwordEncoder.matches(req.getPassword(), member.getPassword())) return null;
        return jwtUtil.createToken(member.getId());
    }

    public MemberDTO getInfo(String id){
        MemberDTO member =  memberMapper.findById(id);
        return member;
    }

    public void updateProfile(String id, String name, String phone, MultipartFile imageFile) throws IOException {
        MemberDTO member = new MemberDTO();
        member.setId(id);
        member.setName(name);
        member.setPhone(phone);

        if (imageFile != null && !imageFile.isEmpty()) {
            String uploadDir = "uploads/profile/";
            Files.createDirectories(Paths.get(uploadDir));
            String filename = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            Path savePath = Paths.get(uploadDir + filename);
            Files.write(savePath, imageFile.getBytes());
            member.setProfileImagePath(uploadDir + filename);
        }

        memberMapper.updateMember(member);
    }
}
