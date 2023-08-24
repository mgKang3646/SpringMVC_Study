package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemberRepository {

    private static Map<Long,Member> store = new HashMap<>();
    private static long sequeence = 0L;

    public Member save(Member member){
        member.setId(++sequeence);
        log.info("save: member={}",member);
        store.put(member.getId(),member);
        return member;
    }

    public Member findById(Long id){
        return store.get(id);
    }

    public Optional<Member> findByLoginId(String loginId){ // Optional : 팀섹에 실패한 경우, Null을 반환하지 않기 위해서. ( NPE, NullPointerException 방지 )
    /*
        List<Member> all = findAll();
        for (Member m : all) {
            if(m.getLoginId().equals(loginId)){
                return Optional.of(m);
            }
        }
        return Optional.empty();
    */

        return findAll().stream()
                .filter(m-> m.getLoginId().equals(loginId)) // filter : 조건에 만족하는 member만 다음 파이프라인으로 이동
                .findFirst();
    }

    public List<Member> findAll(){
        return new ArrayList<>(store.values());
    }
}
