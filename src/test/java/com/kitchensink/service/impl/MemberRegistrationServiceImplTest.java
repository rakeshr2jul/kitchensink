package com.kitchensink.service.impl;

import com.kitchensink.common.KitchensinkConstant;
import com.kitchensink.common.TestDataUtils;
import com.kitchensink.exceptions.MemberAlreadyExistException;
import com.kitchensink.model.Member;
import com.kitchensink.repository.MemberRepository;
import com.kitchensink.service.MemberRegistrationService;
import com.kitchensink.service.SequenceGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.kitchensink.common.TestDataUtils.getMember;
import static com.kitchensink.common.TestDataUtils.getMemberOptional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class MemberRegistrationServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private SequenceGeneratorServiceImpl sequenceGeneratorServiceImpl;

    @InjectMocks
    private MemberRegistrationServiceImpl memberRegistrationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister_WhenMemberAlreadyExists_ShouldThrowException() {
        Member member = new Member();
        member.setEmail("existing@example.com");
        when(memberRepository.findByEmail(member.getEmail())).thenReturn(new Member());
        MemberAlreadyExistException exception = assertThrows(MemberAlreadyExistException.class, () -> {
            memberRegistrationService.register(member);
        });
        assertEquals(KitchensinkConstant.MEM_ALREADY_EXIST, exception.getMessage());
    }

    @Test
    public void testRegister_WhenEmailIsNew_ShouldRegisterSuccessfully() {

        Member member = new Member();
        member.setEmail("new@example.com");

        when(memberRepository.findByEmail(member.getEmail())).thenReturn(null);
        when(sequenceGeneratorServiceImpl.generateSequence(Member.SEQUENCE_NAME)).thenReturn(1L);
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        Member registeredMember = memberRegistrationService.register(member);

        assertNotNull(registeredMember);
        assertEquals(1L, registeredMember.getId());
        verify(sequenceGeneratorServiceImpl).generateSequence(Member.SEQUENCE_NAME);
        verify(memberRepository).save(registeredMember);
    }

    @Test
    void getMembers() {

        when(memberRepository.findAll()).thenReturn(TestDataUtils.getAllMembers());
        List<Member> memberList =memberRegistrationService.getMembers();
        assertNotNull(memberList);
        assertEquals("test@test.com",memberList.stream().findFirst().get().getEmail());

    }

    @Test
    void getMemberById() {
        when(memberRepository.findById(any())).thenReturn(getMemberOptional());
        Optional<Member> newMember =memberRegistrationService.getMemberById(any());
        assertEquals("test@email.com",newMember.get().getEmail());

    }

    @Test
    void checkEmail() {
        when(memberRepository.findByEmail(anyString())).thenReturn(getMember());
       Member newMember= memberRegistrationService.checkEmail(anyString());
       assertEquals("test@email.com",newMember.getEmail());
    }
}