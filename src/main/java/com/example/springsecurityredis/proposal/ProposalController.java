package com.example.springsecurityredis.proposal;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ProposalController {

    private final ProposalEntityRepository proposalEntityRepository;

    @PostMapping("/common/proposal")
    public ProposalEntity registryProposal(@Valid @RequestBody ProposalEntity proposalEntity){
        return proposalEntityRepository.save(proposalEntity);
    }
}
