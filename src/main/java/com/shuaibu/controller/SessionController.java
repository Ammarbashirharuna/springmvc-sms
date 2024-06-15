package com.shuaibu.controller;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.shuaibu.dto.SessionDto;
import com.shuaibu.model.SessionModel;
import com.shuaibu.service.SessionService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/sessions")
public class SessionController {
    
    private SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
    public String listSessions(Model model) {
        model.addAttribute("sessions", sessionService.getAllSessions());
        return "sessions/list";
    }

    @GetMapping("/new")
    public String createSessionForm(Model model) {
        model.addAttribute("session", new SessionModel());
        return "sessions/new";
    }

    @PostMapping("/create")
    public String saveSession(@Valid @ModelAttribute("session") SessionDto session, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("session", session);
            return "sessions/new";
        }
        sessionService.saveSession(session);
        return "redirect:/sessions";
    }

    @GetMapping("/edit/{id}")
    public String updateSessionForm(@PathVariable UUID id, Model model) {
        SessionDto academicSession = sessionService.getSessionById(id);
        model.addAttribute("academicSession", academicSession);
        return "sessions/edit";
    }

    @PostMapping("/update/{id}")
    public String updateSession(@PathVariable UUID id,
                                @Valid @ModelAttribute("session") SessionDto session, 
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("session", session);
            return "sessions/edit";
        }
        session.setId(id);
        sessionService.updateSession(session);
        return "redirect:/sessions";
    }

    @GetMapping("/delete/{id}")
    public String deleteSession(@PathVariable UUID id) {
        sessionService.deleteSession(id);
        return "redirect:/sessions";
    }
}
