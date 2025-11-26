package com.example.anapo.user.application.account.controller;

import com.example.anapo.user.application.account.dto.AccountDto;
import com.example.anapo.user.application.account.service.AccountService;
import com.example.anapo.user.domain.account.entity.Account;
import com.example.anapo.user.exception.DuplicateUserIdException;
import com.example.anapo.user.exception.PasswordMismatchException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/join")
    public ResponseEntity<?> joinUser(@RequestBody AccountDto accountDto) {

        try {
            return ResponseEntity.ok(accountService.join(accountDto));
        } catch (DuplicateUserIdException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (PasswordMismatchException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // 로그인 화면 요청
    @GetMapping("/login")
    public String login() {
        // 로그인 화면 뷰 이름 반환
        // templates/login_form.html을 렌더링
        return "login_form";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(
            @RequestBody AccountDto accountDto,  // 폼에서 전송된 'userPassword' 파라미터 받음
            HttpSession session,                // 로그인 성공 시 세션에 사용자 정보 저장
            Model model                         // 로그인 실패 시 에러 메시지 전달
    ) {
        // 1. 서비스 계층 호출로 사용자 인증
        Account user = accountService.login(accountDto);

        if (user != null) {
            // 2. 로그인 성공 시
            // 세션에 로그인 정보 저장 (key: "loggedInUser")
            session.setAttribute("loggedInUser", user);

            // 로그인 성공 후 메인 페이지로 리다이렉트
            return "redirect:/user/main";

        } else {
            // 3. 로그인 실패 시
            // 모델에 에러 메시지 담아서 다시 로그인 화면 렌더링
            model.addAttribute("error", "아이디 또는 비밀번호가 잘못되었습니다.");

            // 입력한 아이디 유지
            model.addAttribute("userId", accountDto.getUserId());

            // 로그인 화면으로 이동
            return "login_form";
        }
    }

    // 로그인 후 메인 화면
    @GetMapping("/main")
    public String main(HttpSession session, Model model) {
        // 로그인한 사용자 정보 가져오기
        Account logged = (Account) session.getAttribute("loggedInUser");

        // 로그인 안되어 있을 때 처리
        if (logged == null) {
            return "redirect:/user/login";
        }

        // DB에서 최신 사용자 정보 다시 조회 (세션 데이터는 오래될 수 있음)
        Account freshUser = accountService.getUser(logged.getUserName());

        model.addAttribute("user", freshUser);
        return "main_page";
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/login";
    }
}