package ua.goit.notes.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.goit.notes.security.JwtTokenRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class MainController {

    private UserServiceImpl service;
    private JwtTokenRepository tokenRepository;

    @Autowired
    public void setService(UserServiceImpl service) {
        this.service = service;
    }

    @Autowired
    public void setTokenRepository(JwtTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @PostMapping("/register")
    public void register(@Valid @RequestBody UserDto user, BindingResult bindingResult,
                         Model model) {

        if (service.getByName(user.getName()) != null) {
            model.addAttribute("message", Collections.singletonList("User exists!"));
        } else if (!bindingResult.hasErrors()) {
            service.create(user);
        }
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ModelAndView onConstraintValidationException(ConstraintViolationException e, Model model) {
        System.out.println("exception");
        model.addAttribute("message", e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList()));
        return new ModelAndView("/register");
    }



    @GetMapping("/login")
    public CsrfToken login(HttpServletRequest request) {

        UserDto user = service.getByName(request.getHeader("name"));

        if (user == null) {
            return null;
        }

        return tokenRepository.generateToken(request, user);
    }

    @GetMapping("/login-error")
    public String loginError(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        List<String> errorMessage = new ArrayList<>();

        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session
                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (ex != null) {
                errorMessage.add("Wrong login or password");//ex.getMessage());
            }
        }

        model.addAttribute("message", errorMessage);
        return "login";
    }
}
