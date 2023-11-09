package com.billlog.publicweatherapi.domain.mypage.controller;

import com.billlog.publicweatherapi.domain.mypage.dto.request.MyInfoDto;
import com.billlog.publicweatherapi.domain.mypage.service.ChangeNicknameService;
import com.billlog.publicweatherapi.domain.mypage.service.ChangePasswordService;
import com.billlog.publicweatherapi.global.common.reponse.ApiResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "내 정보 관리")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/billlog-api/api")
public class MyPageController {
    private final ChangeNicknameService changeNicknameService;
    private final ChangePasswordService changePasswordService;
    @PutMapping(value = "/v1/my/nickname")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "닉네임 변경 성공", content = @Content(schema = @Schema(implementation = void.class))),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다.", content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다.", content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "4XX,5XX", description = "닉네임 변경 실패", content = @Content(schema = @Schema(implementation = Void.class))),
    })
    @Operation(summary = "닉네임 변경", description = "닉네임 변경")
    public ResponseEntity<ApiResponseDTO> changeNickname(@Valid @RequestBody MyInfoDto.nicknameChangeRequest dto) {
        changeNicknameService.setChangeNickname(dto);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.SUCCESS());
    }
    @PutMapping(value = "/v1/my/change-password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "패스워드 변경 성공", content = @Content(schema = @Schema(implementation = void.class))),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다.", content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다.", content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "4XX,5XX", description = "패스워드 변경 실패", content = @Content(schema = @Schema(implementation = Void.class))),
    })
    @Operation(summary = "패스워드 변경", description = "패스워드 변경")
    public ResponseEntity<ApiResponseDTO> changePassword(@Valid @RequestBody MyInfoDto.passwordChangeRequest dto) {
        changePasswordService.setChangePassword(dto);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.SUCCESS());
    }
}