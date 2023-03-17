package no.accelerate.lagalt_backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import no.accelerate.lagalt_backend.mappers.*;
import no.accelerate.lagalt_backend.models.Project;
import no.accelerate.lagalt_backend.models.dto.project.ProjectDTO;
import no.accelerate.lagalt_backend.models.dto.project.ProjectPostDTO;
import no.accelerate.lagalt_backend.models.dto.project.ProjectUpdateDTO;
import no.accelerate.lagalt_backend.models.dto.user.UserDTO;
import no.accelerate.lagalt_backend.services.project.ProjectService;
import no.accelerate.lagalt_backend.utils.error.ApiErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

import java.net.URI;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "api/v1/project")
public class ProjectController {
    private final ProjectService projectService;
    private final ProjectMapper projectMapper;
    private final ApplicationMapper applicationMapper;
    private final UserMapper userMapper;
    private final SkillMapper skillMapper;
    private final CommentMapper commentMapper;

    public ProjectController(ProjectService projectService, ProjectMapper projectMapper, ApplicationMapper applicationMapper, UserMapper userMapper, SkillMapper skillMapper, CommentMapper commentMapper) {
        this.projectService = projectService;
        this.projectMapper = projectMapper;
        this.applicationMapper = applicationMapper;
        this.userMapper = userMapper;
        this.skillMapper = skillMapper;
        this.commentMapper = commentMapper;
    }

    @Operation(summary = "Get all projects")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Project.class)) }),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content ={ @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Project not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @GetMapping
    public ResponseEntity getAll() {
        return ResponseEntity.ok(projectMapper.projectToProjectDto(projectService.findAll()));
    }
    @Operation(summary = "Adds new project")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204",
                    description = "Project was successfully added",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Project.class)) }),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Project was not found with supplied ID",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @PostMapping
    public ResponseEntity add(@RequestBody ProjectPostDTO projectDto) {
        Project p1 = projectService.add(projectMapper.projectPostDtoToProject(projectDto));
        URI location = URI.create("project/" + p1.getId());
        return ResponseEntity.created(location).build();
    }
    @Operation(summary = "Deletes an existing project by id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204",
                    description = "Project was successfully deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Project.class)) }),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Project was not found with supplied ID",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable int id) {
        projectService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Get a project by id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204",
                    description = "Success",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Project.class)) }),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Project not found with supplied ID",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @GetMapping("{id}")
    public ResponseEntity getById(@PathVariable int id) {
        return ResponseEntity.ok(projectMapper.projectToProjectDto(projectService.findById(id)));
    }
    @Operation(summary = "Updates a project")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204",
                    description = "Project is successfully updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Project.class)) }),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Project was not found with supplied ID",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @PutMapping("{id}")
    public ResponseEntity update(@RequestBody ProjectUpdateDTO projectDTO, @PathVariable int id) {
        // Validates if body is correct
        if(id != projectDTO.getId())
            return ResponseEntity.badRequest().build();
        projectService.update(projectMapper.projectUpdateDtoToProject(projectDTO));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all applications in project")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204",
                    description = "Successfully retrieved all applications in project",
                    content = {@Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ProjectDTO.class))
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content ={ @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Project not found with supplied ID",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @GetMapping("{id}/projectApplications")
    public ResponseEntity getAllProjectApplications(@PathVariable int id) {
        return ResponseEntity.ok(applicationMapper.applicationToApplicationDto(projectService.findAllProjectApplications(id)));
    }
    @Operation(summary = "Get all members in project")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204",
                    description = "Successfully retrieved all members in project",
                    content = {@Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ProjectDTO.class))
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content ={ @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Project not found with supplied ID",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @GetMapping("{id}/projectMembers")
    public ResponseEntity getAllProjectMembers(@PathVariable int id) {
        return ResponseEntity.ok(userMapper.userToUserDto(projectService.findAllMembers(id)));
    }
    @Operation(summary = "Add existing users to project as members")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204",
                    description = "Project successfully updated",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @PutMapping("{id}/members")
    public ResponseEntity updateApplications(@PathVariable int id,@RequestBody Set<String> user_id){
        projectService.updateMembers(id,user_id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Approve deny applications")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204",
                    description = "Project successfully updated",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @PutMapping("{id}/applications")
    public ResponseEntity updateApplications(@PathVariable int id){
        projectService.test(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Remove existing users from project as members")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204",
                    description = "Project successfully updated",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @PutMapping("{id}/removeMembers")
    public ResponseEntity removeMember(@PathVariable int id,@RequestBody Set<String> user_id){
        projectService.removeMembersByIds(id,user_id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all required skills in project")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204",
                    description = "Successfully retrieved all skills in user",
                    content = {@Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Project.class))
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content ={ @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "User not found with supplied ID",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @GetMapping("{id}/getAllRequiredSkills")
    public ResponseEntity getAllSkills(@PathVariable int id) {
        return ResponseEntity.ok(skillMapper.skillToSkillDto(projectService.findAllSkills(id)));
    }
    @Operation(summary = "Get all comments in project")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204",
                    description = "Successfully retrieved all comments in user",
                    content = {@Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserDTO.class))
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content ={ @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "User not found with supplied ID",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @GetMapping("{id}/getAllComments")
    public ResponseEntity getAllComments(@PathVariable int id) {
        return ResponseEntity.ok(commentMapper.commentToCommentDTO(projectService.findAllComments(id)));
    }












}
