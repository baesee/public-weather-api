package com.billlog.publicweatherapi.global.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Schema(description = "페이징 처리가 된 목록 관련 DTO")
public class CommonListResultDto {
    /* 페이징이 있는 건들에 대한 공통 응답 VO */
    @Schema(description = "리스트(List) 형태 결과 데이터")
    private Object list;
    @Schema(description = "마지막 조회 글 목록")
    private boolean isLast;
    @Schema(description = "첫번째 조회 글 목록")
    private boolean isFirst;
    @Schema(description = "현재 페이지 번호")
    private int currentPageNumber;
    @Schema(description = "다음 조회 페이지 존재 여부")
    private boolean hasNext;

    @Builder
    public CommonListResultDto(Object list, boolean isLast, boolean isFirst, int currentPageNumber, boolean hasNext) {
        this.list = list;
        this.isLast = isLast;
        this.isFirst = isFirst;
        this.currentPageNumber = currentPageNumber;
        this.hasNext = hasNext;
    }
}
