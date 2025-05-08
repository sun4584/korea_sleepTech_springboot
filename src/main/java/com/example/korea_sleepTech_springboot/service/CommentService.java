package com.example.korea_sleepTech_springboot.service;

import com.example.korea_sleepTech_springboot.dto.request.CommentCreateRequestDto;
import com.example.korea_sleepTech_springboot.dto.request.CommentUpdateRequestDto;
import com.example.korea_sleepTech_springboot.dto.response.CommentResponseDto;
import com.example.korea_sleepTech_springboot.dto.response.ResponseDto;
import jakarta.validation.Valid;

public interface CommentService {
    ResponseDto<CommentResponseDto> createComment(Long postId, @Valid CommentCreateRequestDto dto);
    ResponseDto<CommentResponseDto> updateComment(Long postId, Long commentId, CommentUpdateRequestDto dto);
    ResponseDto<Void> deleteComment(Long postId, Long commentId);
}