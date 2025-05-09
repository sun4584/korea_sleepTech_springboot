package com.example.korea_sleepTech_springboot.service.implementations;

import com.example.korea_sleepTech_springboot.common.ResponseMessage;
import com.example.korea_sleepTech_springboot.dto.request.CommentCreateRequestDto;
import com.example.korea_sleepTech_springboot.dto.request.CommentUpdateRequestDto;
import com.example.korea_sleepTech_springboot.dto.response.CommentResponseDto;
import com.example.korea_sleepTech_springboot.dto.response.ResponseDto;
import com.example.korea_sleepTech_springboot.entity.D_Comment;
import com.example.korea_sleepTech_springboot.entity.D_Post;
import com.example.korea_sleepTech_springboot.repository.CommentRepository;
import com.example.korea_sleepTech_springboot.repository.PostRepository;
import com.example.korea_sleepTech_springboot.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    @Transactional(readOnly = false) // readOnly 속성: 읽기 전용 트랜잭션 설정 여부 (기본값 false)
    /*
     * @Transactional
     * : Spring Framework에서 제공하는 트랜젝션 관리 애너테이션
     * : DB의 일관성, 무결성, 원자성 등을 보장
     * - Spring의 AOP(관점 지향 프로그래밍)를 활용하여
     *   메서드의 시작과 종료 시점에 트랜잭션을 시작하고, 종료 시점에 자동으로 커밋하거나 롤백
     *
     * >> 메서드가 정상적으로 실행되면 commit(), 예외가 발생하면 rollback()
     *
     * cf) 조회(Read)의 경우 내부 로직에서 변경 작업이 감지되면 예외가 발생하여 rollback() 처리
     * */
    public ResponseDto<CommentResponseDto> createComment(Long postId, CommentCreateRequestDto dto) {
        CommentResponseDto responseDto = null;

        // Post가 존재하는지 확인
        D_Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_POST + postId));

        // cf) D_Post의 addComment()를 호출하지 않고, 직접적으로 post를 설정하여 양방향 관계를 수동으로 설정
        // 새로운 Comment 생성
        D_Comment newComment = D_Comment.builder()
                .content(dto.getContent())
                .commenter(dto.getCommenter())
                .build();

        post.addComment(newComment); // D_Comment가 D_Post에 추가되고 동시에 post 필드가 설정

        D_Comment savedComment = commentRepository.save(newComment);

//        responseDto = CommentResponseDto.builder()
//                .id(savedComment.getId())
//                .postId(savedComment.getPost().getId())
//                .content(savedComment.getContent())
//                .commenter(savedComment.getCommenter())
//                .build();

//        responseDto = new CommentResponseDto.Builder("필수 값) 내용 입력", "필수 값) 작성자")
//                .build();

        responseDto = new CommentResponseDto.Builder(savedComment.getContent(), savedComment.getCommenter())
                .id(savedComment.getId())
                .postId(savedComment.getPost().getId())
                .build();

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, responseDto);
    }

    @Override
    @Transactional
    // 1번 게시물, 3번 댓글 수정
    public ResponseDto<CommentResponseDto> updateComment(Long postId, Long commentId, CommentUpdateRequestDto dto) {
        CommentResponseDto responseDto = null;

        D_Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_COMMENT + commentId));

        if (!comment.getPost().getId().equals(postId)) {
            // 2번 게시물, 3번 댓글 수정
            throw new IllegalArgumentException("Comment does not belong to the specified Post");
        }

        comment.setContent(dto.getContent());
        D_Comment updatedComment = commentRepository.save(comment);

        responseDto = CommentResponseDto.builder()
                .id(updatedComment.getId())
                .postId(updatedComment.getPost().getId())
                .content(updatedComment.getContent())
                .commenter(updatedComment.getCommenter())
                .build();

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, responseDto);
    }

    @Override
    @Transactional
    public ResponseDto<Void> deleteComment(Long postId, Long commentId) {

        D_Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_COMMENT + commentId));

        if (!comment.getPost().getId().equals(postId)) {
            throw new IllegalArgumentException("Comment does not belong to the specified Post");
        }

        // == 연관 관계를 해제 == //
        comment.getPost().removeComment(comment);

        // == DB에서 삭제 == //
        commentRepository.delete(comment);

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, null);
    }
}