package com.kitchensink.utils;

import com.kitchensink.model.Member;
import com.kitchensink.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;


public class DataLoader {

    @Autowired
    private MemberRepository memberRepository;

    private void loadMembers() {
        memberRepository.save(new Member(0L, "John Smith", "john.smith@mailinator.com", "2125551212"));
    }
}
