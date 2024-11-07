package com.kitchensink.service;

import com.kitchensink.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MemberRegistrationService {

    public Member register(Member member);
    public List<Member> getMembers();

    public Optional<Member> getMemberById(Long id);

    public Member checkEmail(String email);
}
