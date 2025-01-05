package org.zerok.club.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerok.club.dto.ClubAuthMemberDTO;

@Slf4j
@Controller
@RequestMapping("/sample")
public class SampleController {

    @GetMapping("/all")
    public void exAll() {
      log.info(" ### exAll ###");
    }

    @GetMapping("/member")
    public void exMember() {
        log.info(" ### exMember ###");
    }

    @GetMapping("/admin")
    public void exAdmin(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO) {
        log.info(" ### exAdmin ###");
        log.info("clubAuthMemberDTO : {}", clubAuthMemberDTO);
    }

}
