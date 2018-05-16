package se.steam.trellov2.service.implementation;

import org.springframework.stereotype.Service;
import se.steam.trellov2.model.Team;
import se.steam.trellov2.repository.TeamRepository;
import se.steam.trellov2.repository.UserRepository;
import se.steam.trellov2.repository.model.parse.ModelParser;
import se.steam.trellov2.service.TeamService;
import se.steam.trellov2.service.business.Logic;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static se.steam.trellov2.repository.model.parse.ModelParser.fromTeamEntity;
import static se.steam.trellov2.repository.model.parse.ModelParser.toTeamEntity;

@Service
final class TeamServiceImp implements TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final Logic logic;

    private TeamServiceImp(TeamRepository teamRepository, UserRepository userRepository, Logic logic) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.logic = logic;
    }

    @Override
    public Team save(Team team) {
        return fromTeamEntity(teamRepository.save(toTeamEntity(team.assignId())));
    }

    @Override
    public Team get(UUID entityId) {
        return fromTeamEntity(logic.validateTeam(entityId));
    }

    @Override
    public void update(Team entity) {
        logic.validateTeam(entity.getId());
        teamRepository.save(toTeamEntity(entity));
    }

    @Override
    public void remove(UUID entityId) {
        teamRepository.save(logic.validateTeam(entityId).deactivate());
    }

    @Override
    public void addUserToTeam(UUID teamId, UUID userId) {
        userRepository.save(logic.checkUserTeamAvailability(logic.validateUser(userId))
                .setTeamEntity(logic.checkTeamMaxCap(logic.validateTeam(teamId))));
    }

    @Override
    public List<Team> getAll() {
        return teamRepository.findAll()
                .stream()
                .map(ModelParser::fromTeamEntity)
                .collect(Collectors.toList());
    }

}