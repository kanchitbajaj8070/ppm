package com.example.ppm.web;

import com.example.ppm.domain.Backlog;
import com.example.ppm.domain.Project;
import com.example.ppm.domain.ProjectTask;
import com.example.ppm.repository.BacklogRepository;
import com.example.ppm.repository.ProjectTaskRepository;
import com.example.ppm.service.ProjectService;
import com.example.ppm.service.ProjectTaskService;
import com.example.ppm.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class BacklogController {
@Autowired
    private ProjectTaskService projectService;
@Autowired
private ValidationService validationService;
    @Autowired
    private BacklogRepository backlogRepository;

@PostMapping("/api/backlog/{backlogId}")
@CrossOrigin
public ResponseEntity<?>  addProjectTaskToBacklog(@Valid @RequestBody ProjectTask projectTask,
                                                  BindingResult result, Principal principal, @PathVariable String backlogId)
{
    ResponseEntity<Map<String,String>> errorMap= validationService.performValidation(result);
if( errorMap!=null)
    return errorMap;
ProjectTask res=projectService.addProjectTask(backlogId.toUpperCase(),projectTask,principal.getName());
return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.CREATED);
}
    @GetMapping("/api/backlog/{backlogId}")
    public ResponseEntity<?> getAllProjectTasksByProjectIdentifier(Principal principal, @PathVariable String backlogId)
    {
        List<ProjectTask> tasks= projectService.getAllTasksById(backlogId.toUpperCase(),principal.getName());
        return new ResponseEntity<List<ProjectTask>>(tasks,HttpStatus.OK);
    }

    @GetMapping("/api/backlog/{backlogId}/{ptId}")
    public ResponseEntity<?> getProjectTask(Principal principal,@PathVariable String backlogId,@PathVariable String ptId)
    {
        ProjectTask projectTask=projectService.findProjectTaskBySequence(backlogId,ptId,principal.getName().toLowerCase().toLowerCase());
return new ResponseEntity<ProjectTask>(projectTask,HttpStatus.OK);
    }

    @PutMapping("/api/backlog/{backlogId}")
    public ResponseEntity<?>  updateProjectTask(@Valid @RequestBody ProjectTask projectTask,
                                                    Principal principal,  BindingResult result, @PathVariable String backlogId)
    {
        ResponseEntity<Map<String,String>> errorMap= validationService.performValidation(result);
        if( errorMap!=null)
            return errorMap;
        ProjectTask res=projectService.updateByProjectSequence(projectTask,backlogId.toUpperCase(),principal.getName().toLowerCase());

        return new ResponseEntity<ProjectTask>(res, HttpStatus.CREATED);
    }
    @DeleteMapping("/api/backlog/{backlogId}/{ptId}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable String backlogId,@PathVariable String ptId,Principal principal)
    {
         projectService.deleteByProjectSequence(backlogId,ptId,principal.getName().toLowerCase());
        return new ResponseEntity<String>("Deleted succesfully",HttpStatus.OK);
    }
}
