package com.billlog.publicweatherapi.domain.member.controller;

import com.billlog.publicweatherapi.domain.member.dto.request.MemberDto;
import com.billlog.publicweatherapi.domain.member.service.MemberJoinService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "회원가입")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/billlog-api/api")
public class MemberJoinController {
    private final MemberJoinService memberJoinService;
    @PostMapping(value = "/v1/member/signup")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입(Only 소셜) 성공", content = @Content(schema = @Schema(implementation = void.class))),
            @ApiResponse(responseCode = "4XX,5XX", description = "회원가입(Only 소셜) 실패", content = @Content(schema = @Schema(implementation = Void.class))),
    })
    @Operation(summary = "회원가입(Only 소셜)", description = "회원가입(Only 소셜)")
    public ResponseEntity<ApiResponseDTO> memberSignUp(@Valid @RequestBody MemberDto.JoinRequest dto) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.SUCCESS(memberJoinService.socialSignup(dto)));
    }
}
