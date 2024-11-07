package com.kitchensink.common;

import com.kitchensink.model.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TestDataUtils {


    public static List<Member> getAllMembers(){
        List<Member> memberList = new ArrayList<>();
        Member member = new Member();
        member.setId(1L);
        member.setName("TestUser");
        member.setEmail("test@test.com");
        member.setPhoneNumber("1234567890");
        memberList.add(member);
       return memberList;

    }

    public static Optional<Member> getMemberOptional(){
        Member member = new Member();
       // member.setId(1L);
        member.setEmail("test@email.com");
        member.setName("Jack");
        member.setPhoneNumber("4443331111");
        return Optional.of(member);

    }

    public static Member getMember(){
        Member member = new Member();
        member.setId(1L);
        member.setEmail("test@email.com");
        member.setName("Jack");
        member.setPhoneNumber("4443331111");
        return member;

    }
}
