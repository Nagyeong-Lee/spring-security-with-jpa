package org.zerok.club.ClubMemberRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.zerok.club.entity.ClubMember;
import org.zerok.club.entity.ClubMemberRole;
import org.zerok.club.repository.ClubMemberRepository;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ClubMemberRepositoryTests {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ClubMemberRepository clubMemberRepository;

    @Test
    public void insertDummies() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            ClubMember clubMember = ClubMember.builder()
                    .email("user" + i + "@zerok.org")
                    .name("사용자" + i)
                    .password(bCryptPasswordEncoder.encode("1111"))
                    .fromSocial(false)
                    .build();

            clubMember.addMemberRole(ClubMemberRole.USER);

            if (i > 80) {
                clubMember.addMemberRole(ClubMemberRole.MANAGER);
            }

            if (i > 90) {
                clubMember.addMemberRole(ClubMemberRole.ADMIN);
            }

            clubMemberRepository.save(clubMember);
        });
    }

    @Test
    public void memberSelectTest() {
       Optional<ClubMember> clubMember = clubMemberRepository.findByEmailAndFromSocial("user100@zerok.org", false);
       if(clubMember.isPresent()) {
           System.out.println("clubMember.get() = " + clubMember.get());
       }
    }
}
