package com.billlog.publicweatherapi.domain.member.controller;

import com.billlog.publicweatherapi.domain.member.dto.request.MemberDto;
import com.billlog.publicweatherapi.domain.member.service.RefreshTokenService;
import com.billlog.publicweatherapi.global.common.reponse.ApiResponseDTO;
import com.billlog.publicweatherapi.global.jwt.JwtTokenInfoVo;
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

@Tag(name = "토큰 재발행")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/billlog-api/api")
public class RefreshToeknInfoController {
    private final RefreshTokenService refreshTokenService;

    @PostMapping(value = "/v1/reissue/token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "토큰 재발행(ReIssue) 성공", content = @Content(schema = @Schema(implementation = JwtTokenInfoVo.class))),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다.", content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다.", content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "4XX,5XX", description = "토큰 재발행(ReIssue) 실패", content = @Content(schema = @Schema(implementation = Void.class))),
    })
    @Operation(summary = "토큰 재발행(ReIssue)", description = "토큰 재발행(ReIssue)")
    public ResponseEntity<ApiResponseDTO> reissue(@Valid @RequestBody MemberDto.reissueTokenRequest dto) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.SUCCESS(refreshTokenService.reissue(dto)));
    }
}
