package se.steam.trellov2.repository.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
public final class IssueEntity extends AbstractEntity {

    private final String description;

    @ManyToOne
    @JoinColumn(name = "taskEntity", nullable = false)
    private final TaskEntity taskEntity;

    IssueEntity() {
        this.taskEntity =null;
        this.description=null;
    }

    public IssueEntity(UUID id, String description) {
        super(id);
        this.description = description;
        this.taskEntity = null;
    }

    private IssueEntity(UUID id, String description, TaskEntity taskEntity) {
        super(id);
        this.description = description;
        this.taskEntity = taskEntity;
    }

    public String getDescription() {
        return description;
    }

    public IssueEntity setTaskEnitity(TaskEntity taskEnitity){
        return new IssueEntity(getId(), getDescription(), taskEntity);
    }
}
