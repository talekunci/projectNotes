package ua.goit.notes.users;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.Collections;
import java.util.stream.Collectors;

@AllArgsConstructor
@Validated
@RestController
public class RegistrationController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public void addUser(@Valid @RequestBody UserDto user, BindingResult bindingResult,
                          Model model) {

        if (service.getByName(user.getName()) != null) {
            model.addAttribute("message", Collections.singletonList("User exists!"));
        } else if (!bindingResult.hasErrors()) {
            service.create(user);
        }
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ModelAndView onConstraintValidationException(ConstraintViolationException e, Model model) {
        model.addAttribute("message", e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList()));
        return new ModelAndView("/register");
    }
}
