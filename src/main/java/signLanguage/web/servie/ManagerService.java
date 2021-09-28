package signLanguage.web.servie;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import signLanguage.web.domain.common.OrderStatus;
import signLanguage.web.domain.common.Position;
import signLanguage.web.domain.common.UploadName;
import signLanguage.web.domain.dto.ManagerReturnDto;
import signLanguage.web.domain.dto.UploadImage;
import signLanguage.web.domain.entity.Interpreter;
import signLanguage.web.domain.entity.Member;
import signLanguage.web.domain.entity.ReceptionOrder;
import signLanguage.web.domain.repository.manager.ManagerMemberRepositoryInterface;
import signLanguage.web.domain.repository.order.OrderRepositoryInterface;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManagerService {

    private final ManagerMemberRepositoryInterface managerMemberRepository;
    private final OrderRepositoryInterface orderRepository;

    public ManagerReturnDto printInterpreter(Long id){
        Member member = managerMemberRepository.findMemberWithInterpreter(id).get();
        Interpreter interpreter = member.getInterpreter();
        if(interpreter == null){
            System.out.println("null이떴습니다잡닺다ㅏㅂㅈ답ㅈ다ㅏㅂㅈ = ");
            return null;
        }

        if(validation(Optional.of(interpreter))){
            UploadName uploadName = interpreter.getUploadName();
            ManagerReturnDto managerReturnDto = new ManagerReturnDto(interpreter.getId(), interpreter.getPosition().getValue(), interpreter.getIntroduce(),uploadName);
            return managerReturnDto;
        }

        throw new NullPointerException();
    }

    public boolean validation(Optional<? extends Object> optional){
        if(optional.isPresent()){
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
            log.info("{}=======================================인터프리터가있습니다.=",interpreter);
            managerMemberRepository.save(interpreter);
            String introduce1 = member.getInterpreter().getIntroduce();
            log.info("{}",introduce1);
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

    @Transactional
    public boolean receiptReception(String orderId, Long memberId, OrderStatus status){
        if(status==OrderStatus.END){
            Optional<ReceptionOrder> findOrder = orderRepository.findOne(orderId);
            if(validation(findOrder)){
                log.info("{}바뀜===================================ㄱ야ㅕㅇㅇㅇㅇㅇ",status);
                findOrder.get().setStatus(status);
            }
        }

        Optional<ReceptionOrder> findOrder = orderRepository.findOne(orderId);
        Optional<Member> memberWithInterpreter = managerMemberRepository.findMemberWithInterpreter(memberId);
        if(validation(findOrder) && validation(memberWithInterpreter)){
            if(memberWithInterpreter.get().getInterpreter()==null){
                throw new NullPointerException("값이 존재하지 않습니다.");
            }
            findOrder.get().setInterpreter(memberWithInterpreter.get().getInterpreter());
            log.info("{}===================================ㄱ야ㅕㅇㅇㅇㅇㅇ",status);
            findOrder.get().setStatus(status);
            return true;
        }
        return false;
    }
}
