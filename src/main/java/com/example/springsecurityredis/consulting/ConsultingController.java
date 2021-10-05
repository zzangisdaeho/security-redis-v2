package com.example.springsecurityredis.consulting;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ConsultingController {

    private final ConsultingEntityRepository consultingEntityRepository;

    @PostMapping("/common/consulting")
    public ConsultingEntity registryConsulting(@Valid @RequestBody ConsultingEntity consultingEntity){
        return consultingEntityRepository.save(consultingEntity);
    }
}
