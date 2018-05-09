package se.steam.trellov2.service.implementation;

import org.springframework.stereotype.Service;
import se.steam.trellov2.model.Issue;
import se.steam.trellov2.repository.IssueRepository;
import se.steam.trellov2.service.IssueService;

import java.util.List;
import java.util.UUID;

@Service
final class IssueServiceImp implements IssueService {

    private final IssueRepository issueRepository;

    private IssueServiceImp(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    @Override
    public Issue save(UUID taskId, Issue issue) {
        return null;
    }

    @Override
    public void update(Issue entity) {

    }

}