package com.example.anapo.user.application.account.controller;

import com.example.anapo.user.application.account.dto.AccountDto;
import com.example.anapo.user.application.account.service.AccountService;
import com.example.anapo.user.domain.account.entity.Account;
import jakarta.servlet.http.HttpSession;
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
        model.addAttribute("userCreateForm", new UserCreateForm());
        return "join"; // 회원가입 화면
    }

    @PostMapping("/join") // POST 요청으로 /user/join에 들어오면 실행
    public String join(
            AccountDto userCreateForm, // 폼 데이터를 UserCreateForm 객체로 받아 유효성 검증
            BindingResult bindingResult,          // 유효성 검증 결과를 담는 객체
            RedirectAttributes redirectAttributes // 리다이렉트 시 메시지 전달용 객체
    ) {

        // 1️. 폼 유효성 검증 오류 확인
        // @NotEmpty, @Size 등의 어노테이션 검증 실패 시 bindingResult.hasErrors()가 true
        if (bindingResult.hasErrors()) {
            return "join"; // 오류가 있으면 다시 회원가입 화면으로 돌아감
        }

        // 2️. 비밀번호 확인 체크
        // userPassword와 userPassword2가 일치하지 않으면 오류
        if (!userCreateForm.getUserPassword().equals(userCreateForm.getUserPassword2())) {
            // rejectValue: 특정 필드에 대한 오류 등록
            // 첫 번째 인자는 필드 이름, 두 번째 인자는 오류 코드, 세 번째 인자는 사용자 메시지
            bindingResult.rejectValue("userPassword2", "passwordMismatch", "비밀번호가 일치하지 않습니다.");
            return "join"; // 다시 회원가입 화면으로 돌아감
        }

        try {
            // 3️. 계정 생성 시도
            accountService.create(userCreateForm);

            // 4️. 회원가입 성공 메시지 전달
            // redirectAttributes.addFlashAttribute를 사용하면 리다이렉트 후에도 메시지 유지
            redirectAttributes.addFlashAttribute("message", "회원가입이 완료되었습니다. 로그인하세요.");

        } catch (DataIntegrityViolationException e) {
            // 5️. DB 유니크 제약 조건 위반 시 (예: 이미 존재하는 사용자명)
            // bindingResult.reject: 특정 필드가 아닌 글로벌 오류 등록
            bindingResult.reject("signupFailed", "이미 사용 중인 사용자명입니다.");
            return "join"; // 회원가입 화면으로 돌아감

        } catch (Exception e) {
            // 6️. 예상치 못한 예외 처리
            bindingResult.reject("signupFailed", "회원가입 중 오류가 발생했습니다.");
            return "join"; // 회원가입 화면으로 돌아감
        }

        // 7️. 성공 시 로그인 페이지로 리다이렉트
        return "redirect:/user/main_form";
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
            @RequestParam String name,          // 폼에서 전송된 'name' 파라미터 받음
            @RequestParam String userPassword,  // 폼에서 전송된 'userPassword' 파라미터 받음
            HttpSession session,                // 로그인 성공 시 세션에 사용자 정보 저장
            Model model                         // 로그인 실패 시 에러 메시지 전달
    ) {
        // 2-1️ 서비스 계층 호출로 사용자 인증
        Account user = accountService.login(name, userPassword);

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

            // 로그인 화면으로 이동
            return "login_form";
        }
    }

}