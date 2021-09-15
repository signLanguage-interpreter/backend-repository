package signLanguage.web.servie;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import signLanguage.web.domain.common.Position;
import signLanguage.web.domain.common.UploadName;
import signLanguage.web.domain.dto.ManagerReturnDto;
import signLanguage.web.domain.dto.UploadImage;
import signLanguage.web.domain.entity.Interpreter;
import signLanguage.web.domain.entity.Member;
import signLanguage.web.domain.repository.manager.ManagerMemberRepositoryInterface;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManagerService {

    private final ManagerMemberRepositoryInterface managerMemberRepository;

    public ManagerReturnDto printInterpreter(Long id){
        Member member = managerMemberRepository.findMemberWithInterpreter(id).get();
        Interpreter interpreter = member.getInterpreter();
        if(validation(Optional.of(interpreter))){
            return null;
        }

        UploadName uploadName = interpreter.getUploadName();
        ManagerReturnDto managerReturnDto = new ManagerReturnDto(interpreter.getId(), interpreter.getPosition(), interpreter.getIntroduce(),uploadName);
        return managerReturnDto;
    }

    public boolean validation(Optional<Interpreter> findInterpreter){
        if(findInterpreter.isPresent()){
            return true;
        }
        return false;
    }

    @Transactional
    public void addInterpreter(String introduce, Position position, UploadImage uploadImage, Long id) {

        UploadName uploadName = new UploadName(uploadImage.getFileName(), uploadImage.getStoreFileName());
        Member member = managerMemberRepository.findMemberWithInterpreter(id).get();
        if(member.getInterpreter() == null){
            Interpreter interpreter = Interpreter.createInterpreter(introduce, position, uploadName, member);
            managerMemberRepository.save(interpreter);
        }
        modifyInterpreter(introduce, position, uploadName, member);
    }

    @Transactional
    public void modifyInterpreter(String introduce, Position position, UploadName uploadName, Member member) {
        Interpreter interpreter = member.getInterpreter();
        interpreter.setIntroduce(introduce);
        interpreter.setPosition(position);
        interpreter.setUploadName(uploadName);
    }
}
