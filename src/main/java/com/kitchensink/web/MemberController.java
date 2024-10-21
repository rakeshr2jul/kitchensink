package com.kitchensink.web;


import com.kitchensink.controller.MemberRegistrationController;
import com.kitchensink.model.Member;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MemberController {

    @Autowired
    private MemberRegistrationController memberRegistrationController;

    @GetMapping("members")
    public String listMembers(Model model) {
        List<Member> members = memberRegistrationController.getMembers();
        model.addAttribute("member", new Member());
        model.addAttribute("members", members);
        return "members";
    }

    @GetMapping("/members/{id}")
    @ResponseBody
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        return memberRegistrationController.getMemberById(id);
    }

    @GetMapping("/list")
    @ResponseBody
    public List<Member> getMemberList() {
        return memberRegistrationController.getMembers();

    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute Member member, BindingResult result,Model model){
        List<Member> members = memberRegistrationController.getMembers();
        if (result.hasErrors()) {
            model.addAttribute("members", members);
            return "members";
        }
        memberRegistrationController.saveMember(member);

        model.addAttribute("members", members);
        return "redirect:/members";
    }

}
