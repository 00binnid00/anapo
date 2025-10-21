package com.example.anapo.user.application.account.controller;

import com.example.anapo.user.application.account.dto.AccountDto;
import com.example.anapo.user.application.account.service.AccountService;
import com.example.anapo.user.domain.account.entity.Account;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/join")
    public String join(Model model) {
        model.addAttribute("accountDto", new AccountDto());
        return "join"; // 회원가입 화면
    }

    @PostMapping("/join")
        public String join(@ModelAttribute @Valid AccountDto accountDto,
                           BindingResult bindingResult,
                           Model model) {

            // 1. 사용자명 중복 체크
            if (accountService.existsByUserName(accountDto.getUserName())) {
                bindingResult.rejectValue("userName", "duplicate", "이미 사용 중인 사용자명입니다.");
                return "join";
            }
            // 2. 비밀번호 확인 체크
            if (!accountDto.getUserPassword().equals(accountDto.getUserPassword2())) {
                bindingResult.rejectValue("userPassword2", "error", "비밀번호가 일치하지 않습니다.");
            }

            // 유효성 오류 있으면 다시 가입 화면
            if (bindingResult.hasErrors()) {
                return "join";
            }

            // 3. 계정 생성
            try {
                accountService.create(accountDto);
            } catch (Exception e) {
                model.addAttribute("errorMessage", "회원가입 실패: " + e.getMessage());
                return "join";
            }

            // 4. 성공 시 로그인 페이지 이동
            return "redirect:/user/login";
        }

    // 1️. 로그인 화면 보여주기
    @GetMapping("/login")
    public String login() {
        // 로그인 화면 뷰 이름 반환
        // templates/login_form.html을 렌더링
        return "login_form";
    }

    // 2️. 로그인 처리
    @PostMapping("/login")
    public String login(
            @RequestParam String userId,          // 폼에서 전송된 'userId' 파라미터 받음
            @RequestParam String userPassword,  // 폼에서 전송된 'userPassword' 파라미터 받음
            HttpSession session,                // 로그인 성공 시 세션에 사용자 정보 저장
            Model model                         // 로그인 실패 시 에러 메시지 전달
    ) {
        // 2-1️ 서비스 계층 호출로 사용자 인증
        Account user = accountService.login(userId, userPassword);

        if (user != null) {
            // 2-2️ 로그인 성공 시
            // 세션에 로그인 정보 저장 (key: "loggedInUser")
            session.setAttribute("loggedInUser", user);

            // 로그인 성공 후 메인 페이지로 리다이렉트
            return "redirect:/user/main";

        } else {
            // 2-3️ 로그인 실패 시
            // 모델에 에러 메시지 담아서 다시 로그인 화면 렌더링
            model.addAttribute("error", "아이디 또는 비밀번호가 잘못되었습니다.");

            // 입력한 아이디 유지
            model.addAttribute("userId", userId);

            // 로그인 화면으로 이동
            return "login_form";
        }
    }

}