package com.example.springsecurityredis.join_company;

import com.example.springsecurityredis.config.security.entity.CompanyEntity;
import com.example.springsecurityredis.config.security.entity.TeamEntity;
import com.example.springsecurityredis.config.security.filter.FilterService;
import com.example.springsecurityredis.repository.CompanyEntityRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class JoinCompanyController {

    private final CompanyEntityRepository companyEntityRepository;

    private final JoinCompanyService joinCompanyService;

    private final JoinCompanyEntityRepository joinCompanyEntityRepository;
    
    private final ModelMapper modelMapper;

    private final FilterService filterService;

    @GetMapping("/user/teamList/{companySeq}")
    public TeamDto teamList(@PathVariable long companySeq) throws Exception {
        CompanyEntity findCompany = companyEntityRepository.findById(companySeq).orElseThrow(() -> new Exception("해당하는 조직이 없습니다"));
        List<TeamEntity> teams = findCompany.getTeams();

        TeamEntity teamEntity = teams.stream().filter(one -> (one.getUpperTeam() == null)).findAny().orElseThrow(() -> new Exception("최상위 조직이 없네???"));

        return teamConverter(teamEntity);
    }

    public TeamDto teamConverter(TeamEntity teamEntity){
        TeamDto teamMap = modelMapper.map(teamEntity, TeamDto.class);
        if(teamEntity.getLowerTeam().size() > 0 && teamEntity.getLowerTeam() != null){
            teamMap.setLowerTeam(teamEntity.getLowerTeam().stream().map(this::teamConverter).collect(Collectors.toList()));
        }

        return teamMap;
    }

    @PostMapping("/user/joinCompany")
    public JoinCompanyEntity joinCompany(@RequestBody JoinCompanyEntity joinCompanyEntity) {
        return joinCompanyService.joinCompany(joinCompanyEntity);
    }

    @GetMapping("/{companyDomain}/joinCompany")
    public List<JoinCompanyEntity> getJoinCompany(@PathVariable String companyDomain) throws Exception {
        long companySeq = filterService.getCompanySeqByDomain(companyDomain);

        return joinCompanyEntityRepository.findByCompanySeq(companySeq);
    }

//    @PostMapping("/")

}
