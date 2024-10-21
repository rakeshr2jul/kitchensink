package com.kitchensink.service.impl;



import com.kitchensink.common.KitchensinkConstant;
import com.kitchensink.exceptions.MemberAlreadyExistException;
import com.kitchensink.model.Member;
import com.kitchensink.repository.MemberRepository;
import com.kitchensink.service.MemberRegistrationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberRegistrationServiceImpl implements MemberRegistrationService {


    private final MemberRepository memberRepository;
    private final SequenceGeneratorServiceImpl sequenceGeneratorServiceImpl;

    public MemberRegistrationServiceImpl(MemberRepository memberRepository, SequenceGeneratorServiceImpl sequenceGeneratorServiceImpl){
        this.memberRepository = memberRepository;
        this.sequenceGeneratorServiceImpl = sequenceGeneratorServiceImpl;
    }


    public Member register(Member member)  {
        if(memberRepository.findByEmail(member.getEmail()).isPresent()){
            throw new MemberAlreadyExistException(KitchensinkConstant.MEM_ALREADY_EXIST);
        }
        member.setId(sequenceGeneratorServiceImpl.generateSequence(Member.SEQUENCE_NAME));
        return memberRepository.save(member);
    }

    public Page<Member> getAllMembers(Pageable pageable){
        return memberRepository.findAll(pageable);
    }

    public List<Member> getMembers(){
        return memberRepository.findAll();
    }
    public Optional<Member> getMemberById(Long id) {
        return memberRepository.findById(id);

    }

    public Optional<Member> checkEmail(String email){
        return  memberRepository.findByEmail(email);
    }
}
