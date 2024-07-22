package com.shuaibu.controller;

import com.shuaibu.dto.SchoolClassDto;
import com.shuaibu.mapper.SchoolClassMapper;
import com.shuaibu.mapper.UserMapper;
import com.shuaibu.model.UserModel;
import com.shuaibu.repository.SchoolClassRepository;
import com.shuaibu.repository.StaffRepository;
import com.shuaibu.repository.UserRepository;
import com.shuaibu.service.*;
import com.shuaibu.service.impl.StaffImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.shuaibu.dto.StaffDto;
import com.shuaibu.model.StaffModel;

import jakarta.validation.Valid;

import java.util.Arrays;
import java.util.Collections;

@SuppressWarnings("ALL")
@Controller
@RequestMapping("/staffs")
@PreAuthorize("hasRole('ADMIN')")
public class StaffController {

    private static final Logger logger = LoggerFactory.getLogger(StaffController.class);
    private final StaffService staffService;
    private final SubjectService subjectService;
    private final SchoolClassService schoolClassService;
    private final RoleService roleService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final StaffRepository staffRepository;

    public StaffController(StaffService staffService, SubjectService subjectService, SchoolClassService schoolClassService, RoleService roleService, UserService userService, UserRepository userRepository, SchoolClassRepository schoolClassRepository, StaffRepository staffRepository) {
        this.staffService = staffService;
        this.subjectService = subjectService;
        this.schoolClassService = schoolClassService;
        this.roleService = roleService;
        this.userService = userService;
        this.userRepository = userRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.staffRepository = staffRepository;
    }

    

    @GetMapping
    public String listStaffs(Model model) {
        model.addAttribute("classModels", schoolClassService.getAllSchoolClass());
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("staff", new StaffModel());
        model.addAttribute("staffs", staffRepository.findAll());
        model.addAttribute("subjects", subjectService.getAllSubjects());
        model.addAttribute("gender", Arrays.asList("Male", "Female"));
        model.addAttribute("isActive", Arrays.asList("True", "False"));
        return "staffs/list";
    }

    @PostMapping
    public String saveListStaff(@Valid @ModelAttribute("staff") StaffDto staff, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("classModels", schoolClassService.getAllSchoolClass());
            model.addAttribute("subjects", subjectService.getAllSubjects());
            model.addAttribute("roles", roleService.getAllRoles());
            model.addAttribute("gender", Arrays.asList("Male", "Female"));
            model.addAttribute("isActive", Arrays.asList("True", "False"));

            return "staffs/list";
        }

        staffService.saveOrUpdateStaff(staff);
        return "redirect:/staffs";
    }

    @GetMapping("/new")
    public String createStaffForm(Model model) {
        model.addAttribute("classModels", schoolClassService.getAllSchoolClass());
        model.addAttribute("staff", new StaffModel());
        model.addAttribute("subjects", subjectService.getAllSubjects());
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("gender", Arrays.asList("Male", "Female"));
        model.addAttribute("isActive", Arrays.asList("True", "False"));
        
        return "staffs/new";
    }

    @PostMapping("/create")
    public String saveStaff(@Valid @ModelAttribute("staff") StaffDto staff, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("classModels", schoolClassService.getAllSchoolClass());
            model.addAttribute("staff", staff);
            model.addAttribute("subjects", subjectService.getAllSubjects());
            model.addAttribute("roles", roleService.getAllRoles());
            model.addAttribute("gender", Arrays.asList("Male", "Female"));
            model.addAttribute("isActive", Arrays.asList("True", "False"));

            return "staffs/new";
        }
        
        staffService.saveOrUpdateStaff(staff);
        return "redirect:/staffs";
    }

    @GetMapping("/edit/{id}")
    public String updateStaffForm(@PathVariable Long id, Model model) {
        StaffDto staff = staffService.getStaffById(id);

        model.addAttribute("classModels", schoolClassService.getAllSchoolClass());
        model.addAttribute("subjects", subjectService.getAllSubjects());
        model.addAttribute("staff", staff);
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("gender", Arrays.asList("Male", "Female"));
        model.addAttribute("isActive", Arrays.asList("True", "False"));

        return "staffs/edit";
    }

    @PostMapping("/update/{id}")
    public String updateStaff(@PathVariable Long id,
                                @Valid @ModelAttribute("staff") StaffDto staff, 
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("classModels", schoolClassService.getAllSchoolClass());
            model.addAttribute("subjects", subjectService.getAllSubjects());
            model.addAttribute("staff", staff);
            model.addAttribute("roles", roleService.getAllRoles());
            model.addAttribute("gender", Arrays.asList("Male", "Female"));
            model.addAttribute("isActive", Arrays.asList("True", "False"));

            return "staffs/edit";
        }

        staff.setId(id);
        staffService.saveOrUpdateStaff(staff);
        logger.info("About to redirect.....");
        return "redirect:/staffs";
    }

    @GetMapping("/delete/{id}")
    public String deleteStaff(@PathVariable Long id) {
        staffService.deleteStaff(id);
        return "redirect:/staffs";
    }
}
