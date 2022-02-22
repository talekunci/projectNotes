package ua.goit.notes.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.goit.notes.security.JwtTokenRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class LoginController {

    @Autowired
    private JwtTokenRepository tokenRepository;
    @Autowired
    private UserServiceImpl service;

    @GetMapping("/login")
    public CsrfToken login(@Valid @RequestBody UserDto dto, BindingResult result,
                           HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (result.hasErrors()) {
            response.sendRedirect("/login-error");
            return null;
        } else {
            UserDto user = service.getByName(dto.getName());

            if (user == null || !user.getPassword().equals(dto.getPassword())) {
                response.sendRedirect("/login-error");
                return null;
            }
        }

        return tokenRepository.generateToken(request);
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
