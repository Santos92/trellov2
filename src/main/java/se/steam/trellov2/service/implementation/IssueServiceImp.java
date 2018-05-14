package se.steam.trellov2.service.implementation;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import se.steam.trellov2.model.Issue;
import se.steam.trellov2.repository.IssueRepository;
import se.steam.trellov2.repository.TaskRepository;
import se.steam.trellov2.repository.model.IssueEntity;
import se.steam.trellov2.repository.model.parse.ModelParser;
import se.steam.trellov2.resource.parameter.PagingInput;
import se.steam.trellov2.service.IssueService;
import se.steam.trellov2.service.business.Logic;
import se.steam.trellov2.service.exception.DataNotFoundException;

import java.util.UUID;

@Service
final class IssueServiceImp implements IssueService {

    private final IssueRepository issueRepository;
    private final TaskRepository taskRepository;
    private final Logic logic;

    private IssueServiceImp(IssueRepository issueRepository, TaskRepository taskRepository, Logic logic) {
        this.issueRepository = issueRepository;
        this.taskRepository = taskRepository;
        this.logic = logic;
    }

    @Override
    public Issue save(UUID taskId, Issue issue) {
        return ModelParser.fromIssueEntity(issueRepository
                .save(ModelParser.toIssueEntity(issue.assignId())
                        .setTaskEnitity(taskRepository.findById(taskId)
                                .orElseThrow(() -> new DataNotFoundException("task not found")))));
    }

    @Override
    public void update(Issue issue) {
        issueRepository.save(issueRepository.findById(issue.getId())
                .map(issueEntity -> new IssueEntity(issue.getId(), issue.getDescription()))
                .orElseThrow(() -> new DataNotFoundException("Issue not found")));
    }

    @Override
    public void delete(UUID issueId) {
        issueRepository.delete(issueRepository.findById(issueId)
                .orElseThrow(() -> new DataNotFoundException("Issue not found")));
    }

    @Override
    public Page<Issue> getPage(PagingInput pagingInput) {
        return null;
    }


}
