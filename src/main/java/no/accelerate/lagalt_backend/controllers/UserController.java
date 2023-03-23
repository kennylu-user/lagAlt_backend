package no.accelerate.lagalt_backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import no.accelerate.lagalt_backend.mappers.*;
import no.accelerate.lagalt_backend.models.Application;
import no.accelerate.lagalt_backend.models.User;
import no.accelerate.lagalt_backend.models.dto.application.ApplicationPostDTO;
import no.accelerate.lagalt_backend.models.dto.user.UserDTO;
import no.accelerate.lagalt_backend.models.dto.user.UserPostDTO;
import no.accelerate.lagalt_backend.models.dto.user.UserUpdateDTO;
import no.accelerate.lagalt_backend.services.application.ApplicationService;
import no.accelerate.lagalt_backend.services.project.ProjectService;
import no.accelerate.lagalt_backend.services.user.UserService;
import no.accelerate.lagalt_backend.utils.error.ApiErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {
    private final UserService userService;
    private final ProjectService projectService;
    private final ProjectMapper projectMapper;
    private final ApplicationMapper applicationMapper;
    private final SkillMapper skillMapper;
    private final CommentMapper commentMapper;
    private final UserMapper userMapper;
    private final ApplicationService applicationService;

    public UserController(UserService userService, ProjectService projectService, ProjectMapper projectMapper, ApplicationMapper applicationMapper, SkillMapper skillMapper, CommentMapper commentMapper, UserMapper userMapper, ApplicationService applicationService) {
        this.userService = userService;
        this.projectService = projectService;
        this.projectMapper = projectMapper;
        this.applicationMapper = applicationMapper;
        this.skillMapper = skillMapper;
        this.commentMapper = commentMapper;
        this.userMapper = userMapper;
        this.applicationService = applicationService;
    }
    @Operation(summary = "Get all users")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content ={ @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Users not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @GetMapping
    public ResponseEntity getAll() {
        return ResponseEntity.ok(userMapper.userToUserDto(userService.findAll()));
    }

    @Operation(summary = "Adds new user")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201",
                    description = "User was successfully added",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "User was not found with supplied ID",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @PostMapping
    public ResponseEntity add(@RequestBody UserPostDTO userDto) {
        User user = userService.add(userMapper.userPostDtoToUser(userDto));
        URI location = URI.create("users/" + user.getId());
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Get a user by id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "User not found with supplied ID",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @GetMapping("{id}")
    public ResponseEntity getById(@PathVariable String id) {
        return ResponseEntity.ok(userMapper.userToUserDto(userService.findById(id)));
    }
    @Operation(summary = "Updates a user")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204",
                    description = "User is successfully updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "User was not found with supplied ID",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @PutMapping("{id}")
    public ResponseEntity update(@RequestBody UserUpdateDTO userDTO, @PathVariable String id) {
        // Validates if body is correct
        if (!id.equals(userDTO.getId()))
            return ResponseEntity.badRequest().build();

        userService.update(userMapper.userUpdateDtoToUser(userDTO));
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Deletes an existing user by id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204",
                    description = "User is successfully deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "User was not found with supplied ID",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable String id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all projectsOwned in user")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully retrieved all projects owned in user",
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
    @GetMapping("{id}/getProjectsOwned")
    public ResponseEntity getAllProjectsOwned(@PathVariable String id) {
        return ResponseEntity.ok(projectMapper.projectToProjectDto(userService.findAllProjectsOwned(id)));
    }

    @Operation(summary = "Get all projectsParticipated in user")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully retrieved all projects participated in user",
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
    @GetMapping("{id}/getProjectParticipated")
    public ResponseEntity getAllProjectsParticipated(@PathVariable String id) {
        return ResponseEntity.ok(projectMapper.projectToProjectDto(userService.findAllProjectsParticipated(id)));
    }
    @Operation(summary = "Get all user applications in user")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully retrieved all applications in user",
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
    @GetMapping("{id}/getProjectApplications")
    public ResponseEntity getAllUserApplications(@PathVariable String id) {
        return ResponseEntity.ok(applicationMapper.applicationToApplicationDto(userService.findAllUserApplications(id)));
    }
    @Operation(summary = "Add user application to project")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201",
                    description = "Project successfully applied to",
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
    @PostMapping("applications")
    public ResponseEntity updateMembers(@RequestBody ApplicationPostDTO applicationDTO){
        Application application = applicationService.add(applicationMapper.applicationPostDtoToApplication(applicationDTO));
        userService.applyToProject(applicationMapper.applicationPostDtoToApplication(applicationDTO));
        URI location = URI.create("application/" + application.getId());
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Get all skills in user")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully retrieved all skills in user",
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
    @GetMapping("{id}/getAllSkills")
    public ResponseEntity getAllSkills(@PathVariable String id) {
        return ResponseEntity.ok(skillMapper.skillToSkillDto(userService.findAllSkills(id)));
    }
    @Operation(summary = "Get all comments in user")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200",
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
    public ResponseEntity getAllComments(@PathVariable String id) {
        return ResponseEntity.ok(commentMapper.commentToCommentDTO(userService.findAllComments(id)));
    }


}
