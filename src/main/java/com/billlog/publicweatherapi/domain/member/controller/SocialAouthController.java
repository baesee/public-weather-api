package com.billlog.publicweatherapi.domain.member.controller;

import com.billlog.publicweatherapi.domain.member.dto.request.SocialDto;
import com.billlog.publicweatherapi.domain.member.service.KaKaoAouthService;
import com.billlog.publicweatherapi.domain.member.service.SocialAouthService;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@Tag(name = "소셜(Kakao)인증")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/billlog-api/api")
public class SocialAouthController {
    private final SocialAouthService socialAouthService;
    private final KaKaoAouthService kaKaoAouthService;
    @PostMapping(value ="/v1/oauth2/{provider}/login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소셜 로그인 성공", content = @Content(schema = @Schema(implementation = Object.class, description = "기회원가입 사용자 : JWT, 미회원가입 사용자 : 사용자의 회원가입 필요 정보"))),
            @ApiResponse(responseCode = "4XX,5XX", description = "소셜 로그인 실패", content = @Content(schema = @Schema(implementation = Void.class))),
    })
    @Operation(summary = "소셜 제공자에서 받은 엑세스 토큰으로 로그인 처리", description = "소셜 제공자에서 받은 엑세스 토큰으로 로그인 처리")
    public ResponseEntity<ApiResponseDTO> socialLoginByAccessToken(@Schema(description = "제공자('kakao')") @PathVariable("provider") String provider, @RequestBody @Valid SocialDto.accessTokenRequest dto) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.SUCCESS(socialAouthService.getSocialAuthProcessByAccessToken(provider,dto.getAccessToken())));
    }

    // ========================================================================================================== //
    // ------------------------ 아래 코드는 프론트 화면이 없어 테스트를 위한 API입니다. ------------------------ //
    // ========================================================================================================== //
    @RequestMapping(value ="/v1/oauth2/code/{provider}", produces = "application/json", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소셜 CALLBACK 호출 성공", content = @Content(schema = @Schema(implementation = Object.class, description = "기회원가입 사용자 : JWT, 미회원가입 사용자 : 사용자의 회원가입 필요 정보"))),
            @ApiResponse(responseCode = "4XX,5XX", description = "소셜 CALLBACK 호출 실패", content = @Content(schema = @Schema(implementation = Void.class))),
    })
    @Operation(summary = "소셜 Callback Endpoint", description = "소셜 Callback Endpoint")
    public ResponseEntity<ApiResponseDTO> socialCallback(@Schema(description = "제공자('kakao','apple')") @PathVariable("provider") String provider, @RequestParam String code) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.SUCCESS(code));
    }

    @PostMapping(value ="/v1/oauth2/test")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소셜 로그인 성공", content = @Content(schema = @Schema(implementation = Object.class, description = "기회원가입 사용자 : JWT, 미회원가입 사용자 : 사용자의 회원가입 필요 정보"))),
            @ApiResponse(responseCode = "4XX,5XX", description = "소셜 로그인 실패", content = @Content(schema = @Schema(implementation = Void.class))),
    })
    @Operation(summary = "(카카오 로긴 화면 개발전까지 테스트용도)소셜 제공자에서 받은 엑세스 토큰으로 로그인 처리", description = "(카카오 로긴 화면 개발전까지 테스트용도)소셜 제공자에서 받은 엑세스 토큰으로 로그인 처리")
    public ResponseEntity<ApiResponseDTO> socialLoginByAccessToken(@RequestParam(value = "code", required = false) String code) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.SUCCESS(kaKaoAouthService.getKakaoAccessToken(code)));
    }

}