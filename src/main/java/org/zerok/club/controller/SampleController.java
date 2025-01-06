package org.zerok.club.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerok.club.dto.ClubAuthMemberDTO;

@Slf4j
@Controller
@RequestMapping("/sample")
public class SampleController {

    @PreAuthorize("permitAll()")
    @GetMapping("/all")
    public void exAll() {
      log.info(" ### exAll ###");
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/member")
    public void exMember() {
        log.info(" ### exMember ###");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public void exAdmin(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO) {
        log.info(" ### exAdmin ###");
        log.info("clubAuthMemberDTO : {}", clubAuthMemberDTO);
    }

    //email이 user95@zerok.org인 유저만 접근 가능
    @PreAuthorize("#clubAuthMemberDTO != null && #clubAuthMemberDTO.username eq \"user95@zerok.org\"")
    @GetMapping("/exOnly")
    public void exOnly(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO) {
        log.info(" ### exOnly ###");
    }

}
