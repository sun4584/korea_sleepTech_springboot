package com.example.korea_sleepTech_springboot.service.implementations;

import com.example.korea_sleepTech_springboot.common.ResponseMessage;
import com.example.korea_sleepTech_springboot.dto.response.ResponseDto;
import com.example.korea_sleepTech_springboot.dto.user.request.UserUpdateRequestDto;
import com.example.korea_sleepTech_springboot.dto.user.response.GetUserResponseDto;
import com.example.korea_sleepTech_springboot.entity.User;
import com.example.korea_sleepTech_springboot.repository.UserRepository;
import com.example.korea_sleepTech_springboot.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 1) 회원 정보 조회
    @Override
    public ResponseDto<GetUserResponseDto> getUserInfo(String userEmail) {

        User user = userRepository.findByEmail(userEmail).orElse(null);

        if (user == null) {
            return ResponseDto.setFailed(ResponseMessage.NOT_EXISTS_USER);
        }

        GetUserResponseDto data = new GetUserResponseDto(user);

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
    }

    @Override
    public ResponseDto<GetUserResponseDto> updateUserInfo(String userEmail, UserUpdateRequestDto dto) {

//        User user = userRepository.findByEmail(userEmail).orElse(null);
//
//        if (user == null) {
//            return ResponseDto.setFailed(ResponseMessage.NOT_EXISTS_USER);
//        }

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_USER));


        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            // 비밀번호 정상
            if (!dto.getPassword().equals(dto.getConfirmPassword())) {
                // 비밀번호 확인과 일치하지 않음
//                return ResponseDto.setFailed(ResponseMessage.NOT_MATCH_PASSWORD);
                throw new IllegalArgumentException(ResponseMessage.NOT_MATCH_PASSWORD);
            }

            // 비밀번호 정상 + 일치
            String encodedPassword = bCryptPasswordEncoder.encode(dto.getPassword());
            user.setPassword(encodedPassword);
        }

        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        GetUserResponseDto data = new GetUserResponseDto(user);
        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
    }

    @Override
    public ResponseDto<Void> deleteUser(String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_USER));

        userRepository.delete(user);
        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, null);
    }
}