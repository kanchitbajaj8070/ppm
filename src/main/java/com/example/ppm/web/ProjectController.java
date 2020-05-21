package com.example.ppm.web;

import com.example.ppm.domain.Project;
import com.example.ppm.repository.ProjectRepository;
import com.example.ppm.service.ProjectService;
import com.example.ppm.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ValidationService validationService;
    @PutMapping("/api/project")
    public ResponseEntity<?> updateProject ( @Valid @RequestBody Project project,BindingResult result)
    {
        ResponseEntity<Map<String,String> >errorMap= validationService.performValidation(result);
        if( errorMap!=null)
            return errorMap;
        projectService.saveOrUpdateProject(project);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }
@PostMapping("/api/project")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result)
{
    //if( result.hasErrors()){  /*  This is refactored into a new service of validation
    /*Map<String,String> errorMap= new HashMap<>();
            for( FieldError error:result.getFieldErrors())
                errorMap.put( error.getField(),error.getDefaultMessage());
            return new ResponseEntity<Map<String,String>>(errorMap,HttpStatus.BAD_REQUEST);*/
        //return new ResponseEntity<List<FieldError>>( result.getFieldErrors(),HttpStatus.BAD_REQUEST );
        /*return new ResponseEntity<String>("Invalid object",HttpStatus.BAD_REQUEST);*/
    /*
    * we want error of this form
    "description": "Please enter a valid description",
    "projectName": "Please enter a project name",
    "projectIdentifier": " Please enter a project Identifier of 4 or 5 words only"
    * */
    ResponseEntity<Map<String,String> >errorMap= validationService.performValidation(result);
    if( errorMap!=null)
        return errorMap;
    /* the unique property of the project identifier still returns error .this happens because the constraint is at
    * database level . The error occured when we were trying to insert a record in the database and not at the time
    * of checking the values of request Body*/
    projectService.saveOrUpdateProject(project);
    return new ResponseEntity<>(project, HttpStatus.CREATED);
}
@GetMapping("/api/project/all")
public List<Project> getAllProjects()
{
    return projectService.getAllProjects();
}
    @GetMapping("/api/project/{projectId}")
    public Project getProjectByIdentifier(@PathVariable String projectId)
    {
        return projectService.getProjectByIdentifier(projectId);
    }
    @DeleteMapping("/api/project/{projectId}")
    public ResponseEntity<?> deleteProjectByIdentifier( @PathVariable String projectId)
    {
        projectService.deleteProjectByIdentifer(projectId.toUpperCase());
return new ResponseEntity<String>("PROJECT WITH "+projectId+" DELETED",HttpStatus.OK);
    }
}
