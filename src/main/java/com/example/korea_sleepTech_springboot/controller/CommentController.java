package com.example.korea_sleepTech_springboot.controller;

import com.example.korea_sleepTech_springboot.common.ApiMappingPattern;
import com.example.korea_sleepTech_springboot.dto.request.CommentCreateRequestDto;
import com.example.korea_sleepTech_springboot.dto.request.CommentUpdateRequestDto;
import com.example.korea_sleepTech_springboot.dto.response.CommentResponseDto;
import com.example.korea_sleepTech_springboot.dto.response.ResponseDto;
import com.example.korea_sleepTech_springboot.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiMappingPattern.COMMENT_API)
@RequiredArgsConstructor
public class CommentController {
    // 댓글
    // : CUD

    private final CommentService commentService;

    // 1) 댓글 생성
    @PostMapping
    public ResponseEntity<ResponseDto<CommentResponseDto>> createComment(
            @PathVariable Long postId,
            @Valid @RequestBody CommentCreateRequestDto dto
    ) {
        ResponseDto<CommentResponseDto> response = commentService.createComment(postId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 2) 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<ResponseDto<CommentResponseDto>> updateComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentUpdateRequestDto dto
    ) {
        ResponseDto<CommentResponseDto> response = commentService.updateComment(postId, commentId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 3) 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ResponseDto<Void>> deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId
    ) {
        ResponseDto<Void> response = commentService.deleteComment(postId, commentId);
        return ResponseEntity.noContent().build();
    }
}