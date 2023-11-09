package com.billlog.publicweatherapi.global.utils;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

public class QueryDslPagingUtil {
    /**
     * 결과 목록을 Slice 형태로 페이징 처리
     * @param pageable
     * @param results
     * @return
     * @param <T>
     */
    public static <T> Slice<T> sliceReturnByResults(Pageable pageable, List<T> results) {
        boolean hasNext = false;
        if (results.size() > pageable.getPageSize()) {
            hasNext = true;
            results.remove(pageable.getPageSize()); //더 가져온 데이터 삭제
        }
        return new SliceImpl(results, pageable, hasNext);
    }
}
