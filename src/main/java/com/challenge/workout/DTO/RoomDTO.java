package com.challenge.workout.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomDTO {
    private Long id;
    private String title;
    private String content;
    private String imagePath;

    private LocalDate startDate;
    private LocalDate endDate;

    private Long createdBy;
    private LocalDateTime createdAt;

    private MultipartFile roomImage; 
}
