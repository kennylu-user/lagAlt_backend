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

    private final UserMapper userMapper;
    private final ApplicationService applicationService;

    public UserController(UserService userService, ProjectService projectService, ProjectMapper projectMapper, ApplicationMapper applicationMapper, UserMapper userMapper, ApplicationService applicationService) {
        this.userService = userService;
        this.projectService = projectService;
        this.projectMapper = projectMapper;
        this.applicationMapper = applicationMapper;
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
            @ApiResponse(responseCode = "204",
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
            @ApiResponse(responseCode = "204",
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
    public ResponseEntity getById(@PathVariable int id) {
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
    public ResponseEntity update(@RequestBody UserUpdateDTO userDTO, @PathVariable int id) {
        // Validates if body is correct
        if (id != userDTO.getId())
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
    public ResponseEntity delete(@PathVariable int id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all projectsOwned in user")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204",
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
    public ResponseEntity getAllProjectsOwned(@PathVariable int id) {
        return ResponseEntity.ok(projectMapper.projectToProjectDto(userService.findAllProjectsOwned(id)));
    }

    @Operation(summary = "Get all projectsParticipated in user")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204",
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
    public ResponseEntity getAllProjectsParticipated(@PathVariable int id) {
        return ResponseEntity.ok(projectMapper.projectToProjectDto(userService.findAllProjectsParticipated(id)));
    }
    @Operation(summary = "Get all user applications in user")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204",
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
    public ResponseEntity getAllUserApplications(@PathVariable int id) {
        return ResponseEntity.ok(applicationMapper.applicationToApplicationDto(userService.findAllUserApplications(id)));
    }
    @Operation(summary = "Add user application to project")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204",
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


}
