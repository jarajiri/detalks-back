package com.twat.detalks.question.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResDto {
    private boolean result;
    private String msg;
    private Object data;
    private String status;
    private String errorType; // 에러 타입
    private String token;
}
