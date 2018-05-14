package se.steam.trellov2.service.implementation;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import se.steam.trellov2.model.User;
import se.steam.trellov2.repository.TaskRepository;
import se.steam.trellov2.repository.TeamRepository;
import se.steam.trellov2.repository.UserRepository;
import se.steam.trellov2.repository.model.TaskEntity;
import se.steam.trellov2.repository.model.UserEntity;
import se.steam.trellov2.repository.model.parse.ModelParser;
import se.steam.trellov2.resource.parameter.PagingInput;
import se.steam.trellov2.resource.parameter.UserInput;
import se.steam.trellov2.service.UserService;
import se.steam.trellov2.service.business.Logic;
import se.steam.trellov2.service.exception.DataNotFoundException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static se.steam.trellov2.repository.model.parse.ModelParser.fromUserEntity;
import static se.steam.trellov2.repository.model.parse.ModelParser.toUserEntity;

@Service
final class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final TaskRepository taskRepository;
    private final Logic logic;

    private UserServiceImp(UserRepository userRepository, TeamRepository teamRepository, TaskRepository taskRepository, Logic logic) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.taskRepository = taskRepository;
        this.logic = logic;
    }

    @Override
    public User save(User user) {
        return fromUserEntity(userRepository.save(toUserEntity(logic.validateUsername(user).assignId())));
    }

    @Override
    public User get(UUID entityId) {
        return userRepository.findById(entityId)
                .map(ModelParser::fromUserEntity)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
    }

    @Override
    public void update(User user) {
        userRepository.findById(user.getId()).orElseThrow(() -> new DataNotFoundException("User not found"));
        userRepository.save(toUserEntity(user));
    }

    @Override
    public void remove(UUID id) {
        taskRepository.findByUserEntity(userRepository.save(userRepository.findById(id)
                .map(UserEntity::deactivate)
                .orElseThrow(() -> new DataNotFoundException("User not found"))))
        .stream().map(x -> x).forEach(taskRepository::save);
    }

    @Override
    public List<User> getByTeam(UUID teamId) {
        return userRepository.findByTeamEntity(teamRepository.findById(teamId)
                .orElseThrow(() -> new DataNotFoundException("Team not found")))
                .stream()
                .map(ModelParser::fromUserEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getWithAttributes(UserInput userInput) {
        return userRepository.findAll().stream()
                .filter((u) ->
                    u.isActive() &&
                    (u.getFirstName().contains(userInput.getFirstName()) &&
                    u.getLastName().contains(userInput.getLastName()) &&
                    u.getUsername().contains(userInput.getUsername())))
                .map(ModelParser::fromUserEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void addTaskToUser(UUID userId, UUID taskId) {
        UserEntity u = userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("User not found"));
        TaskEntity t = taskRepository.findById(taskId).orElseThrow(() -> new DataNotFoundException("User not found"));
        
//        taskRepository.save(taskRepository.findById(taskId)
//                .map(t -> t.setUserEntity(userRepository.findById(userId)
//                        .orElseThrow(RuntimeException::new)))
//                .orElseThrow(RuntimeException::new));
    }

    @Override
    public Page<User> getPage(PagingInput pagingInput) {
        return null;
    }
}