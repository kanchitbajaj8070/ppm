package com.example.ppm;
import javax.persistence.*;
import java.util.Date;
@Entity
@Table
public class Project {
    @Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;

 private  String projectName;
 private String projectIdentifier;//Never use project_id
    // because when using foreign keys it creates problems
 private String description;
 private Date start_date;
 private Date end_date;
 private Date created_at;
 private Date updated_at;

    public Project() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectIdentifier() {
        return projectIdentifier;
    }

    public void setProjectIdentifier(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    /*   The @PrePresist annotate model methods to indicate that the method should be called before the entity is
           inserted (persisted) into the database.
           The @PreUpdate is used to configure a pre-update callback for the entity model, i.e., it is used to annotate methods in models
            to indicate an operation that should be triggered before an entity has been updated in the database.
            */
    @PrePersist
    public void onCreate()
    {
        this.created_at=new Date();
    }
    @PreUpdate
    public void onUpdate()
    {
        this.updated_at=new Date();
    }


}
