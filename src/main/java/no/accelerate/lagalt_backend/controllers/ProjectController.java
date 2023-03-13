package no.accelerate.lagalt_backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import no.accelerate.lagalt_backend.mappers.ApplicationMapper;
import no.accelerate.lagalt_backend.mappers.ProjectMapper;
import no.accelerate.lagalt_backend.mappers.UserMapper;
import no.accelerate.lagalt_backend.models.Project;
import no.accelerate.lagalt_backend.models.dto.project.ProjectDTO;
import no.accelerate.lagalt_backend.models.dto.project.ProjectPostDTO;
import no.accelerate.lagalt_backend.models.dto.project.ProjectUpdateDTO;
import no.accelerate.lagalt_backend.services.project.ProjectService;
import no.accelerate.lagalt_backend.utils.error.ApiErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "api/v1/project")
public class ProjectController {
    private final ProjectService projectService;
    private final ProjectMapper projectMapper;
    private final ApplicationMapper applicationMapper;
    private final UserMapper userMapper;

    public ProjectController(ProjectService projectService, ProjectMapper projectMapper, ApplicationMapper applicationMapper, UserMapper userMapper) {
        this.projectService = projectService;
        this.projectMapper = projectMapper;
        this.applicationMapper = applicationMapper;
        this.userMapper = userMapper;
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
    @PutMapping("{id}/projectApplications")
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
    @PutMapping("{id}/projectMembers")
    public ResponseEntity getAllProjectMembers(@PathVariable int id) {
        return ResponseEntity.ok(userMapper.userToUserDto(projectService.findAllMembers(id)));
    }









}
