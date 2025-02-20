package com.twat.detalks.member.service;

import com.twat.detalks.answer.repository.AnswerRepositroy;
import com.twat.detalks.member.dto.MemberDeleteDto;
import com.twat.detalks.member.dto.MemberCreateDto;
import com.twat.detalks.member.dto.MemberUpdateDto;
import com.twat.detalks.member.entity.MemberEntity;
import com.twat.detalks.member.repository.MemberRepository;
import com.twat.detalks.member.utils.FileNameUtils;
import com.twat.detalks.member.vo.Social;
import com.twat.detalks.question.repository.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
@Transactional
public class MemberService {

    private final QuestionRepository questionRepository;
    private final AnswerRepositroy answerRepositroy;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Path fileStorageLocation;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final String PASSWORD_REGEX = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    @Autowired
    public MemberService(MemberRepository memberRepository,
                         QuestionRepository questionRepository,
                         AnswerRepositroy answerRepositroy,
                         BCryptPasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.fileStorageLocation = setFileStorageLocation();
        try {
            Files.createDirectories(this.fileStorageLocation); // 파일 저장 디렉토리가 없으면 생성
        } catch (Exception e) {
            throw new RuntimeException("디렉토리 생성 오류");
        }
        this.questionRepository = questionRepository;
        this.answerRepositroy = answerRepositroy;
    }

    // 운영체제에 따른 파일 저장 경로 설정 메서드
    private Path setFileStorageLocation() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return Paths.get("C:\\upload").toAbsolutePath().normalize();
        } else if (os.contains("mac")) {
            return Paths.get("/Users/jarajiri/upload").toAbsolutePath().normalize();
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            return Paths.get("/home/ubuntu/detalks/server/upload").toAbsolutePath().normalize();
        } else {
            throw new IllegalStateException("Unsupported OS: " + os);
        }
    }


    // 이메일 유효성 검사
    public void regexEmailCheck(final String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다.");
        }
    }

    // 이메일 중복체크
    public void duplicateEmailCheck(final String email) {
        boolean result = memberRepository.existsByMemberEmail(email);
        if (result) {
            throw new IllegalArgumentException("사용 불가능한 이메일 입니다.");
        }
    }

    // 이름 중복체크
    public void duplicateNameCheck(final String name) {
        boolean result = memberRepository.existsByMemberName(name);
        if (result) {
            throw new IllegalArgumentException("사용 불가능한 이름 입니다.");
        }
    }

    // 비밀번호 검증
    public void checkPassword(final String pwd, final String memberPwd) {
        boolean matches = passwordEncoder.matches(pwd, memberPwd);
        if (!matches) throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
    }

    // 비밀번호 유효성 검사
    public void regexPwdCheck(final String pwd) {
        Matcher matcher = PASSWORD_PATTERN.matcher(pwd);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("비밀번호 형식이 올바르지 않습니다.");
        }
    }

    // 회원가입
    public void saveMember(final MemberCreateDto memberDTO) {
        MemberEntity test = MemberEntity.builder()
            .memberEmail(memberDTO.getEmail())
            .memberPwd(passwordEncoder.encode(memberDTO.getPwd()))
            .memberName(memberDTO.getName())
            .memberImg("default" + (int) (Math.random() * 5 + 1) + ".png")
            .build();
        memberRepository.save(test);
    }

    // 회원정보조회(id)
    public MemberEntity findByMemberId(final String idx) throws NumberFormatException {
        Long memberId = Long.parseLong(idx);
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }

    // 로그인
    public MemberEntity getByCredentials(final String email, final String pwd) {
        // 유효성 검사
        regexEmailCheck(email);
        regexPwdCheck(pwd);
        // 이메일 조회
        MemberEntity loginMember = memberRepository.findByMemberEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        // 비밀번호 검증
        checkPassword(pwd, loginMember.getMemberPwd());
        // 계정 상태 확인
        if (!loginMember.getMemberState()) {
            throw new IllegalArgumentException("비활성화된 계정입니다.");
        }
        // 방문일자 수정
        MemberEntity updateVisited = loginMember.toBuilder()
            .memberVisited(LocalDateTime.now())
            .build();

        memberRepository.save(updateVisited);
        return updateVisited;
    }

    // 회원 정보 수정
    public void updateMember(final String idx, final MemberUpdateDto memberUpdateDto) {
        // 아이디로 회원 조회
        MemberEntity prevMember = findByMemberId(idx);

        // 회원 정보 업데이트
        MemberEntity updateMember = prevMember.toBuilder()
            .memberName(memberUpdateDto.getName())
            .memberSummary(memberUpdateDto.getSummary())
            .memberAbout(memberUpdateDto.getAbout())
            .memberUpdated(LocalDateTime.now())
            .build();

        memberRepository.save(updateMember);
    }

    // 회원 프로필 이미지 수정
    public void updateImg(final String idx, final MultipartFile img){
        // 아이디로 회원 조회
        MemberEntity prevMember = findByMemberId(idx);

        // 기존 db에 저장된 값 불러오기
        String dbFileName = prevMember.getMemberImg();

        // 프로필 이미지가 넘어왔을 경우
        if (img != null && !Objects.requireNonNull(img.getOriginalFilename()).isEmpty()) {
            try {
                // UUID 적용해서 파일이름 수정
                String UUIDFileName = FileNameUtils.fileNameConvert(img.getOriginalFilename());
                // 실제 저장되는 경로 설정
                String fullPath = this.fileStorageLocation.resolve(UUIDFileName).toString();


                // 파일 생성
                img.transferTo(new File(fullPath));
                dbFileName = UUIDFileName; // 업로드 된 이미지로 변경

            } catch (IOException e) {
                throw new RuntimeException("이미지 저장에 실패하였습니다.");
            }
        }

        // 회원 정보 업데이트
        MemberEntity updateMember = prevMember.toBuilder()
            .memberImg(dbFileName)
            .build();

        memberRepository.save(updateMember);
    }


    // 회원 탈퇴
    public void deleteMember(final String idx, final MemberDeleteDto memberDeleteDto) {
        // 아이디로 회원 조회
        MemberEntity existMember = findByMemberId(idx);
        // 일반 회원 검증
        if (!existMember.getMemberSocial().equals(Social.NONE)) {
            throw new IllegalArgumentException("일반 회원 전용 탈퇴 기능입니다.");
        }
        // 비밀번호 검증
        checkPassword(memberDeleteDto.getPwd(), existMember.getMemberPwd());
        // 논리 삭제
        MemberEntity deleteMember = existMember.toBuilder()
            .memberIsDeleted(true)
            .memberDeleted(LocalDateTime.now())
            .memberReason(memberDeleteDto.getReason())
            .build();

        memberRepository.save(deleteMember);
    }

    // 소셜회원 탈퇴
    public void deleteSocialMember(final String idx, final String reason) {
        // 아이디로 회원 조회
        MemberEntity existMember = findByMemberId(idx);
        // 소셜회원 검증
        if (existMember.getMemberSocial().equals(Social.NONE)) {
            throw new IllegalArgumentException("소셜 회원 전용 탈퇴 기능입니다.");
        }
        // 논리 삭제
        MemberEntity deleteMember = existMember.toBuilder()
            .memberIsDeleted(true)
            .memberDeleted(LocalDateTime.now())
            .memberReason(reason)
            .build();

        memberRepository.save(deleteMember);
    }

    // 회원 탈퇴 복구
    public void restoreMember(final String idx) {
        // 아이디로 회원 조회
        MemberEntity existMember = findByMemberId(idx);
        // 복구
        MemberEntity restoreMember = existMember.toBuilder()
            .memberIsDeleted(false)
            .memberDeleted(null)
            .memberReason(null)
            .build();
        memberRepository.save(restoreMember);
    }

    // 비밀번호 변경
    public void changePassword(final String idx, final String pwd, final String changePwd) {
        // 아이디로 회원 조회
        MemberEntity prevMember = findByMemberId(idx);
        // 소셜 회원 여부 조회
        if (prevMember.getMemberSocial() != Social.NONE) {
            throw new IllegalArgumentException("소셜 로그인 유저는 비밀번호를 변경할 수 없습니다.");
        }
        // 기존 비밀번호 검증
        checkPassword(pwd, prevMember.getMemberPwd());
        // 새 비밀번호 유효성 검사
        regexPwdCheck(changePwd);
        // 새 비밀번호로 변경
        MemberEntity updateMember = prevMember.toBuilder()
            .memberPwd(passwordEncoder.encode(changePwd))
            .build();

        memberRepository.save(updateMember);
    }

    // 바운티 설정
    public void setBounty(final String bounty, final String idx) {
        // 문자열 -> 정수형
        int intBounty = parseBounty(bounty);
        // 음수 체크
        validateBounty(intBounty);

        MemberEntity member = findByMemberId(idx);
        // 평판점수와 바운티 비교해서 1 미만이 아니면 변경
        updateMemberReputation(member, intBounty);
    }

    // 문자열 바운티 정수형 파싱 메서드
    private int parseBounty(String bounty) throws NumberFormatException {
        return Integer.parseInt(bounty);
    }

    // 바운티 음수 예외처리 메서드
    private void validateBounty(int bounty) {
        if (bounty < 0) {
            throw new IllegalArgumentException("바운티는 음수가 될 수 없습니다 :: " + bounty);
        }
    }

    // 바운티 적용
    private void updateMemberReputation(MemberEntity member, int bounty) {
        int updatedRep = member.getMemberRep() - bounty;
        // 평판은 1 미만으로 떨어질 수 없음
        if (updatedRep < 1 ) {
            throw new IllegalArgumentException("현재 평판 점수보다 높게 바운티를 설정할 수 없습니다.");
        }
        memberRepository.save(member.toBuilder().memberRep(updatedRep).build());
    }


    // 평판 증감
    // 평판 점수 증가표
    // 질문 / 답변 작성시 + 5 , action = "WRITE"
    // 답변 채택시 + 20 , action = "ACCEPTED"
    // 질문 / 답변 투표 UP + 10 , action = "VOTE_UP"
    // 질문 / 투표 DOWN - 5 , action = "VOTE_DOWN"
    // 평판은 1이하로 떨어지지 않음
    // 반환값 boolean
    public void actionMemberReputation(final String idx, final String action) {
        MemberEntity member = findByMemberId(idx);

        int currRep = member.getMemberRep();

        switch (action) {
            case "WRITE":
                currRep += 5;
                break;
            case "ACCEPTED":
                currRep += 20;
                break;
            case "VOTE_UP":
                currRep += 10;
                break;
            case "VOTE_DOWN":
                currRep -= 10;
                break;
            default:
                log.warn("알수없는 액션 타입 {}", action);
                throw new IllegalArgumentException("알 수 없는 액션 타입입니다.");
        }

        // 평판 점수 하한선
        if (currRep < 1) {
            currRep = 1;
        }

        memberRepository.save(member.toBuilder()
            .memberRep(currRep)
            .build());
    }

    // 회원이 작성한 질문수 반환 메서드
    public long getQuestionCount(final String idx) {
        MemberEntity member = findByMemberId(idx);
        return questionRepository.countAllByMembersEquals(member);
    }

    // 회원이 작성한 답변수 반환 메서드
    public long getAnswerCount(final String idx) {
        MemberEntity member = findByMemberId(idx);
        return answerRepositroy.countAllByMembersEquals(member);
    }

    // 회원 이메일로 조회 (비밀번호 찾기용)
    public MemberEntity findByMemberEmail(final String email) {
        MemberEntity member = memberRepository.findByMemberEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        if (!member.getMemberSocial().equals(Social.NONE)) {
            throw new IllegalArgumentException("소셜 회원은 비밀번호 찾기가 불가능 합니다.");
        }

        return member;
    }

    // 비밀번호 초기화
    public void initPassword(final MemberEntity member, final String pwd) {
        MemberEntity updateMember = member.toBuilder()
            .memberPwd(passwordEncoder.encode(pwd))
            .build();
        memberRepository.save(updateMember);
    }

    // 태그 조회
    public List<String> getTags(final String idx) throws NumberFormatException {
        Long memberIdx = Long.parseLong(idx);
        Pageable tags = PageRequest.of(0, 3);
        return questionRepository.findTagsByMemberId(memberIdx, tags);
    }
}
