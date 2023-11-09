package com.billlog.publicweatherapi.global.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
public abstract class BaseTimeEntity {

    @CreatedDate
    @Column(name ="create_date", updatable =false)
    @Schema(description = "최초 등록일")
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(name ="update_date")
    @Schema(description = "최종 수정일")
    private LocalDateTime updateDate;

}