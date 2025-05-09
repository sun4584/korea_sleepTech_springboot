package com.example.korea_sleepTech_springboot.service.implementations;

import com.example.korea_sleepTech_springboot.common.ResponseMessage;
import com.example.korea_sleepTech_springboot.dto.request.PostCreateRequestDto;
import com.example.korea_sleepTech_springboot.dto.request.PostUpdateRequestDto;
import com.example.korea_sleepTech_springboot.dto.response.*;
import com.example.korea_sleepTech_springboot.entity.D_Post;
import com.example.korea_sleepTech_springboot.repository.PostRepository;
import com.example.korea_sleepTech_springboot.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    @Transactional
    public ResponseDto<PostDetailResponseDto> createPost(PostCreateRequestDto dto) {
        PostDetailResponseDto responseDto = null;

        D_Post newPost = D_Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .author(dto.getAuthor())
                .build();

        D_Post saved = postRepository.save(newPost);

        responseDto = PostDetailResponseDto.builder()
                .id(saved.getId())
                .title(saved.getTitle())
                .content(saved.getContent())
                .author(saved.getAuthor())
                .build();

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, responseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<PostDetailResponseDto> getPostById(Long id) {
        PostDetailResponseDto responseDto = null;

        D_Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_POST + id));

        List<CommentResponseDto> comments = post.getComments().stream()
                .map(comment -> CommentResponseDto.builder()
                        .id(comment.getId())
                        .postId(comment.getPost().getId())
                        .content(comment.getContent())
                        .commenter(comment.getCommenter())
                        .build())
                .collect(Collectors.toList());

        responseDto = PostDetailResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .comments(comments) // comment 테이블에서 데이터를 찾아 CommentResponseDto 형식으로 변환
                .build();

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, responseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<List<PostListResponseDto>> getAllPosts() {
        List<PostListResponseDto> responseDtos = null;

        List<D_Post> posts = postRepository.findAll();

        responseDtos = posts.stream()
                .map(post -> PostListResponseDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .author(post.getAuthor())
                        .build())
                .collect(Collectors.toList());

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, responseDtos);
    }

    @Override
    @Transactional
    public ResponseDto<PostDetailResponseDto> updatePost(Long id, PostUpdateRequestDto dto) {
        PostDetailResponseDto responseDto = null;

        D_Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_POST + id));

        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());

        D_Post updatedPost = postRepository.save(post);

        responseDto = PostDetailResponseDto.builder()
                .id(updatedPost.getId())
                .title(updatedPost.getTitle())
                .content(updatedPost.getContent())
                .author(updatedPost.getAuthor())
                .comments(updatedPost.getComments().stream()
                        .map(comment -> CommentResponseDto.builder()
                                .id(comment.getId())
                                .postId(comment.getPost().getId())
                                .content(comment.getContent())
                                .commenter(comment.getCommenter())
                                .build())
                        .collect(Collectors.toList())
                )
                .build();

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, responseDto);
    }

    @Override
    public ResponseDto<Void> deletePost(Long id) {
//        if (!postRepository.existsById(id)) {
//            // .existsById(PK값)
//            // : 존재하면 true, 존재하지 않으면 false 반환
//            throw new EntityNotFoundException(ResponseMessage.NOT_EXISTS_POST + id);
//        }

        D_Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_POST + id));

        // cf) 게시물 삭제 이전에 모든 댓글에 대해 관계 해제
        // 인스턴스 메서드 참조
        // 기본) 인스턴스명.메서드명();
        //          forEach(post -> post.removeComment());
        post.getComments().forEach(post::removeComment);

        postRepository.deleteById(id);

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, null);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<List<PostListResponseDto>> getPostsByAuthor(String author) {
        List<PostListResponseDto> responseDtos = null;

        List<D_Post> posts = postRepository.findByAuthor(author);

        responseDtos = posts.stream()
                .map(post -> PostListResponseDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .author(post.getAuthor())
                        .build())
                .collect(Collectors.toList());

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, responseDtos);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<List<PostListResponseDto>> searchPostsByTitle(String keyword) {
        List<PostListResponseDto> responseDtos = null;

        List<D_Post> posts = postRepository.findByTitleIgnoreCaseContaining(keyword);

        responseDtos = posts.stream()
                .map(post -> PostListResponseDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .author(post.getAuthor())
                        .build())
                .collect(Collectors.toList());

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, responseDtos);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<List<PostWithCommentCountResponseDto>> getTop5PostsByComments() {
        List<PostWithCommentCountResponseDto> responseDtos = null;

        List<Object[]> results = postRepository.findTop5ByOrderByCommentsSizeDesc();

        responseDtos = results.stream()
                .map(row -> PostWithCommentCountResponseDto.builder()
                        // JPA 데이터 반환이 Object[] 타입
                        // : 내부의 데이터는 Object로 명시 >> 각 타입으로 명시적 형 변환(강제 형 변환)
                        .id( ((Number) row[0]).longValue() )
                        .title((String) row[1])
                        .content((String) row[2])
                        .author((String) row[3])
                        .commentCount( ((Number) row[4]).intValue() )
                        .build())
                .collect(Collectors.toList());

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, responseDtos);
    }
}