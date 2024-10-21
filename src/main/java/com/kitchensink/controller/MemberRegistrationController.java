package com.kitchensink.controller;


import com.kitchensink.common.KitchensinkConstant;
import com.kitchensink.exceptions.MemberAlreadyExistException;
import com.kitchensink.exceptions.MemberNotFoundException;
import com.kitchensink.model.Member;
import com.kitchensink.service.MemberRegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/")
@RequestScope
public class MemberRegistrationController {

    private final MemberRegistrationService memberRegistrationService;

    @Autowired
    public MemberRegistrationController(MemberRegistrationService memberRegistrationService){
        this.memberRegistrationService = memberRegistrationService;

    }

   /* @GetMapping("members")
    public Page<Member> getMembers(Pageable pageable)  {
         return memberRegistrationService.getAllMembers(pageable);
    }*/
    @GetMapping("members")
    public List<Member> getMembers(){
        return  memberRegistrationService.getMembers();
    }


    @PostMapping("/members")
    public ResponseEntity<?> saveMember(@RequestBody @Valid Member member) {
       try {

           Member savedUser = memberRegistrationService.register(member);
           return ResponseEntity.ok(savedUser);
       }catch (MemberAlreadyExistException mae){
           return  ResponseEntity.status(HttpStatus.CONFLICT).body(KitchensinkConstant.MEM_ALREADY_EXIST);
       }

    }

    @GetMapping("/members/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        Optional<Member> optionalMember = memberRegistrationService.getMemberById(id);
        return optionalMember.map(member -> new ResponseEntity<>(member, HttpStatus.OK)).orElseThrow(() ->new MemberNotFoundException(KitchensinkConstant.MEM_NOT_FOUND));
    }



}
