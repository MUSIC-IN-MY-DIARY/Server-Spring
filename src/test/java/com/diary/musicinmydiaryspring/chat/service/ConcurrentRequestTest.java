package com.diary.musicinmydiaryspring.chat.service;

import com.diary.musicinmydiaryspring.chat.dto.ChatRequestDto;
import com.diary.musicinmydiaryspring.diary.entity.Diary;
import com.diary.musicinmydiaryspring.diary.repository.DiaryRepository;
import com.diary.musicinmydiaryspring.member.entity.Member;
import com.diary.musicinmydiaryspring.member.repsitory.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@Slf4j
class ConcurrentRequestTest {

    @Autowired
    private ChatService chatService;

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void concurrentRequestTest() throws InterruptedException {
        // 랜덤 이메일 생성
        String randomEmail = "tester" + System.currentTimeMillis() + "@test.com";
        
        Member testMember = Member.builder()
                .email(randomEmail)
                .password("1234")
                .nickname("test")
                .build();
        memberRepository.save(testMember);

        int[] userCounts = {1, 5, 10, 50};

        for (int userCount : userCounts) {
            log.info("=== {}명 동시 요청 테스트 시작 ===", userCount);

            ExecutorService executorService = Executors.newFixedThreadPool(userCount);
            CountDownLatch latch = new CountDownLatch(userCount);
            List<Long> responseTimes = new ArrayList<>();

            // Member 설정이 포함된 Diary 생성 및 저장
            final Diary testDiary = diaryRepository.save(createTestDiary(testMember));

            long testStartTime = System.currentTimeMillis();

            // 동시 요청 실행
            for (int i = 0; i < userCount; i++) {
                executorService.execute(() -> {
                    try {
                        long startTime = System.currentTimeMillis();
                        chatService.requestAndSaveChatRecommendSongs(
                            ChatRequestDto.builder()
                                .diaryContent("테스트 내용")
                                .build(),
                            testDiary.getId()
                        );
                        long endTime = System.currentTimeMillis();

                        synchronized (responseTimes) {
                            responseTimes.add(endTime - startTime);
                        }
                    } finally {
                        latch.countDown();
                    }
                });
            }

            // 모든 요청 완료 대기 (30초 타임아웃)
            boolean completed = latch.await(30, TimeUnit.SECONDS);
            long totalTime = System.currentTimeMillis() - testStartTime;

            // 결과 출력
            if (completed) {
                double avgResponseTime = responseTimes.stream()
                        .mapToLong(Long::longValue)
                        .average()
                        .orElse(0.0);

                log.info("평균 응답 시간: {}ms", String.format("%.2f", avgResponseTime));
                log.info("총 소요 시간: {}ms", totalTime);
                log.info("성공한 요청 수: {}/{}", responseTimes.size(), userCount);
                log.info("=====================================");
            } else {
                log.error("일부 요청이 30초 내에 완료되지 않았습니다.");
            }

            executorService.shutdown();
            Thread.sleep(5000); // 다음 테스트 전 5초 대기
        }
    }

    private Diary createTestDiary(Member member) {
        return Diary.builder()
                .content("테스트 일기 내용입니다.")
                .createdAt(LocalDateTime.now())
                .member(member)
                .build();
    }
}