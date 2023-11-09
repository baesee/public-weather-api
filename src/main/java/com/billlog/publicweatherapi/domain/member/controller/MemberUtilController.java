package com.billlog.publicweatherapi.domain.member.controller;

import com.billlog.publicweatherapi.domain.member.service.MemberUtilService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원관련 유틸")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/billlog-api/api")
public class MemberUtilController {
    private final MemberUtilService memberUtilService;
    @GetMapping(value = "/v1/member/util/duplicate-id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "아이디 중복확인 성공", content = @Content(schema = @Schema(implementation = Boolean.class, description = "true : 중복 , false : 미중복"))),
            @ApiResponse(responseCode = "4XX,5XX", description = "아이디 중복확인 실패", content = @Content(schema = @Schema(implementation = Void.class))),
    })
    @Operation(summary = "아이디 중복 확인", description = "아이디 중복 확인")
    public ResponseEntity<ApiResponseDTO> checkEmailIdDuplicate(@RequestParam("memberId") String memberId) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.SUCCESS(memberUtilService.isDuplicateId(memberId)));
    }

    @GetMapping(value = "/v1/member/util/duplicate-nickname")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "닉네임 중복확인 성공", content = @Content(schema = @Schema(implementation = Boolean.class, description = "true : 중복 , false : 미중복"))),
            @ApiResponse(responseCode = "4XX,5XX", description = "닉네임 중복확인 실패", content = @Content(schema = @Schema(implementation = Void.class))),
    })
    @Operation(summary = "닉네임 중복 확인", description = "닉네임 중복 확인")
    public ResponseEntity<ApiResponseDTO> checkNicknameDuplicate(@RequestParam("nickName") String nickName) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.SUCCESS(memberUtilService.isDuplicateNickname(nickName)));
    }
}
