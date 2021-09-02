package signLanguage.web.servie;

import com.sun.xml.bind.v2.runtime.unmarshaller.Intercepter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import signLanguage.web.domain.common.Position;
import signLanguage.web.domain.entity.Interpreter;
import signLanguage.web.domain.entity.Member;
import signLanguage.web.domain.repository.member.MemoryMemberRepository;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemoryMemberRepository memoryMemberRepository;

    @Transactional
    public Long join(Member member){
        if(validation(member.getUsername())){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
        Long savedMemberId = memoryMemberRepository.save(member);
        return savedMemberId;
    }

    public boolean validation(String name){
        Optional<Member> findMember = memoryMemberRepository.findByName(name);
        if(findMember.isPresent()){
            return true;
        }
        return false;
    }

    public Member findById(Long id){
        return memoryMemberRepository.findOne(id).get();
    }

    @Transactional
    public Long addInterpreter(Long id, Position position, String introduce, String imagePath){
        Member member = memoryMemberRepository.findOne(id).orElseThrow(()->new IllegalStateException("null 입니다."));
        Interpreter interpreter = Interpreter.createAddInfo(position, introduce, imagePath, member);
        return interpreter.getId();
    }
}
