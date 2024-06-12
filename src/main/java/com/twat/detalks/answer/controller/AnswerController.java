package com.twat.detalks.answer.controller;

import com.twat.detalks.answer.dto.AnswerCreateDto;
import com.twat.detalks.answer.entity.AnswerEntity;
import com.twat.detalks.answer.service.AnswerService;
import com.twat.detalks.oauth2.dto.CustomUserDetail;
import com.twat.detalks.question.dto.ResErrorDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/questions")
public class AnswerController {
    @Autowired
    private AnswerService answerService;

    // 답변 생성
    // POST /api/questions/{questionId}/answers
    @PostMapping("/{questionId}/answers")
    public ResponseEntity<?> createAnswer(
            @AuthenticationPrincipal CustomUserDetail user,
            @PathVariable Long questionId,
             @RequestBody AnswerCreateDto answerCreateDto) {
        String memberIdx = user.getUserIdx();
        try {
            AnswerEntity newAnswer = answerService.createAnswer(memberIdx, questionId, answerCreateDto);
            return ResponseEntity.ok(newAnswer);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ResErrorDto.builder().error(e.getMessage()).build()
            );
        }
    }

    // 답변 수정
    // PATCH /api/questions/answers/{answerId}
    @PatchMapping("/answers/{answerId}")
    public ResponseEntity<?> updateAnswer(
            @AuthenticationPrincipal CustomUserDetail user,
            @PathVariable Long answerId,
            @Valid @RequestBody AnswerCreateDto answerCreateDto) {
        String memberIdx = user.getUserIdx();
        try {
            AnswerEntity updatedAnswer = answerService.updateAnswer(memberIdx, answerId, answerCreateDto);
            return ResponseEntity.ok(updatedAnswer);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ResErrorDto.builder().error(e.getMessage()).build()
            );
        }
    }

    // 답변 삭제
    // DELETE /api/questions/answers/{answerId}
    @DeleteMapping("/answers/{answerId}")
    public ResponseEntity<?> deleteAnswer(
            @AuthenticationPrincipal CustomUserDetail user,
            @PathVariable Long answerId) {
        String memberIdx = user.getUserIdx();
        try {
            answerService.deleteAnswer(memberIdx, answerId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ResErrorDto.builder().error(e.getMessage()).build()
            );
        }
    }

    // 답변 채택
    // POST /api/questions/{questionId}/{answerId}/select
    @PostMapping("/{questionId}/{answerId}/select")
    public ResponseEntity<?> selectAnswer(
            @PathVariable Long questionId,
            @PathVariable Long answerId,
            @AuthenticationPrincipal CustomUserDetail user) {
        String memberIdx = user.getUserIdx();
        try {
            answerService.selectAnswer(questionId, answerId, Long.parseLong(memberIdx));
            String msg = "답변을 채택하였습니다.";
            return ResponseEntity.ok(msg);
        } catch (Exception e) {
            log.error("Error selecting answer: {}", e.getMessage());
            return ResponseEntity.badRequest().body(
                    ResErrorDto.builder().error(e.getMessage()).build()
            );
        }
    }
}
