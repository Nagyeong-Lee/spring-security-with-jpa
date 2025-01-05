package org.zerok.club.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.zerok.club.entity.ClubMember;

import java.util.Optional;

public interface ClubMemberRepository extends JpaRepository<ClubMember, String> {

    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<ClubMember> findByEmailAndFromSocial(String email, boolean fromSocial);
}
