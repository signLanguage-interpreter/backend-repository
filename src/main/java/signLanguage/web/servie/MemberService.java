package signLanguage.web.servie;

import com.sun.xml.bind.v2.runtime.unmarshaller.Intercepter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import signLanguage.web.domain.common.Position;
import signLanguage.web.domain.dto.ManagerDto;
import signLanguage.web.domain.entity.Interpreter;
import signLanguage.web.domain.entity.Member;
import signLanguage.web.domain.repository.manager.ManagerMemberRepositoryInterface;
import signLanguage.web.domain.repository.member.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemoryMemberRepository memoryMemberRepository;
    private final ManagerMemberRepositoryInterface memoryManagerMemberRepository;

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
        Member member = memoryMemberRepository.findOne(id).get();//.orElseThrow(()->new IllegalStateException("null 입니다."));
        Interpreter interpreter = Interpreter.createAddInfo(position, introduce, imagePath, member);
        System.out.println(member.getInterpreter().getIntroduce());
        if(interpreter == null){
            throw new IllegalStateException("통역사가 저장되지 않았어요.");
        }
        Long interpreterId = memoryManagerMemberRepository.save(interpreter);
//        memoryMemberRepository.save(member);
        return interpreterId;
    }


    @Transactional
    public void modifyInterpreter(Long id,Position position, String introduce, String imagePath){
        Interpreter interpreter = memoryManagerMemberRepository.findOne(id).orElseThrow(()->new NullPointerException("null 입니다."));
        interpreter.setImagePath(imagePath);
        interpreter.setPosition(position);
        interpreter.setIntroduce(introduce);
    }

    @Transactional(readOnly = true)
    public List<Interpreter> printInterpreter(Long id){
        List<Interpreter> interpreterWithMember = memoryManagerMemberRepository.findInterpreterWithMember(id);
        if(interpreterWithMember.isEmpty()){
            throw new NullPointerException("null 값이 들어갔어요.");
        }
        return interpreterWithMember;
    }
}
