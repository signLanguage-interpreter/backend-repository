package signLanguage.web.servie;

import com.sun.xml.bind.v2.runtime.unmarshaller.Intercepter;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import signLanguage.web.domain.common.Position;
import signLanguage.web.domain.dto.CommentDto;
import signLanguage.web.domain.dto.ManagerDto;
import signLanguage.web.domain.dto.UploadImage;
import signLanguage.web.domain.entity.Comment;
import signLanguage.web.domain.entity.Interpreter;
import signLanguage.web.domain.entity.Member;
import signLanguage.web.domain.entity.ReceptionOrder;
import signLanguage.web.domain.repository.comment.CommentRepositoryInterface;
import signLanguage.web.domain.repository.manager.ManagerMemberRepositoryInterface;
import signLanguage.web.domain.repository.member.MemoryMemberRepository;
import signLanguage.web.domain.repository.order.OrderRepositoryInterface;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static signLanguage.web.domain.dto.CommentDto.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemoryMemberRepository memoryMemberRepository;
    private final CommentRepositoryInterface commentRepository;
    private final ManagerMemberRepositoryInterface memoryManagerMemberRepository;
    private final OrderRepositoryInterface orderRepository;

    @Transactional
    public Long join(Member member) {
        if (validation(member.getUsername())) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
        Long savedMemberId = memoryMemberRepository.save(member);
        return savedMemberId;
    }

    public boolean validation(String name) {
        Optional<Member> findMember = memoryMemberRepository.findByName(name);
        if (findMember.isPresent()) {
            return true;
        }
        return false;
    }

    public Member findById(Long id) {
        return memoryMemberRepository.findOne(id).get();
    }

//    public MemberBasicInfo printMemberBasicInfo(Long id) {
//        Member member = findById(id);
//        MemberBasicInfo memberBasicInfo = new MemberBasicInfo(member.getId(),
//                member.getUserNickName(),
//                member.getUsername(),
//                member.getEMail(),
//                member.getCellPhone());
//
//        return memberBasicInfo;
//    }

    @Transactional
    public void regComment(ReturnComment commentDto, Long id, String orderId) {
        Optional<Member> findMember = memoryMemberRepository.findOne(id);
        Optional<ReceptionOrder> findOrder = orderRepository.findOne(orderId);
        Comment comment = Comment.createComment(findMember.get(), findOrder.get(), commentDto.getContent());
        commentRepository.save(comment);
    }

    @Data
    public static class MemberBasicInfo {
        private Long id;
        private String userNickName;
        private String username;
        private String eMail;
        private String cellPhone;

        public MemberBasicInfo(Long id, String userNickName, String username, String eMail, String cellPhone) {
            this.id = id;
            this.userNickName = userNickName;
            this.username = username;
            this.eMail = eMail;
            this.cellPhone = cellPhone;
        }
    }

    @Transactional
    public void modifyMember(Long id, String eMail, String password, String cellPhone) {
        Member findMember = findById(id);
        findMember.setEMail(eMail);
        findMember.setPassword(password);
        findMember.setCellPhone(cellPhone);
    }
}