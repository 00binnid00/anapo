package com.example.anapo.user.application.account.controller;

import com.example.anapo.user.application.account.dto.AccountDto;
import com.example.anapo.user.application.account.service.AccountService;
import com.example.anapo.user.domain.account.entity.Account;
import com.example.anapo.user.domain.account.repository.AccountRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

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

    @PostMapping("/join")
    public String join(@Valid UserCreateForm userCreateForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "join";
        }
        if (!userCreateForm.getUserPassword().equals(userCreateForm.getUserPassword2())) {
            bindingResult.rejectValue("password2", "passwordMismatch", "비밀번호가 일치하지 않습니다.");
            return "join";
        }
        try {
            AccountService.create(userCreateForm);
            model.addAttribute("message", "회원가입이 완료되었습니다. 로그인하세요.");
        } catch (DataIntegrityViolationException e) {
            bindingResult.reject("signupFailed", "이미 사용 중인 사용자명입니다.");
            return "join";
        } catch (Exception e) {
            bindingResult.reject("signupFailed", "회원가입 중 오류가 발생했습니다.");
            return "join";
        }
        return "redirect:/user/login"; // 성공 시 로그인 화면으로 리다이렉트
    }

    @GetMapping("/login")
    public String login() {
        return "login_form"; // 로그인 화면
    }

    @PostMapping("/login")
    public String login(@RequestParam String name, @RequestParam String userPassword, HttpSession session, Model model) {
        Account user = AccountService.login(name, userPassword);
        if (user != null) {
            session.setAttribute("loggedInUser", user);
            return "redirect:/user/main";
        } else {
            model.addAttribute("error", "아이디 또는 비밀번호가 잘못되었습니다.");
            return "login_form";
        }
    }

//    // 회원가입
//    @PostMapping("/join")
//    public Account joinUser(@RequestBody AccountDto accountDto) {
//        Account account = accountService.join(accountDto);
//        // null = 비어있음x -> 아이디가 이미 존재함
//        if (account == null) {
//            // account가 null이면
//            // throw -> 예의를 발생시키겠다
//            // new RuntimeException(...) -> 런타임 예외 객체 생성
//            throw new RuntimeException("이미 존재하는 아이디입니다."); // 실패 메시지 처리
//        }
//        return account;
//    }
//
//
//    // 로그인
//    @PostMapping("/login")
//    public Account loginUser(@RequestBody AccountDto accountDto) {
//        Account account = accountService.login(accountDto);
//        if (account == null) {
//            throw new RuntimeException("아이디 또는 비밀번호가 틀렸습니다."); // 실패 메시지 처리
//        }
//        return account;
//    }
}