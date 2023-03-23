package no.accelerate.lagalt_backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import no.accelerate.lagalt_backend.mappers.ProjectMapper;
import no.accelerate.lagalt_backend.mappers.SkillMapper;
import no.accelerate.lagalt_backend.mappers.UserMapper;
import no.accelerate.lagalt_backend.models.Skill;
import no.accelerate.lagalt_backend.models.dto.project.ProjectDTO;
import no.accelerate.lagalt_backend.models.dto.skill.SkillPostDTO;
import no.accelerate.lagalt_backend.models.dto.skill.SkillUpdateDTO;
import no.accelerate.lagalt_backend.models.dto.user.UserDTO;
import no.accelerate.lagalt_backend.services.skill.SkillService;
import no.accelerate.lagalt_backend.utils.error.ApiErrorResponse;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "api/v1/skill")
public class SkillController {
    private final SkillService skillService;
    private final SkillMapper skillMapper;
    private final UserMapper userMapper;
    private final ProjectMapper projectMapper;

    public SkillController(SkillService skillService, SkillMapper skillMapper, UserMapper userMapper, ProjectMapper projectMapper) {
        this.skillService = skillService;
        this.skillMapper = skillMapper;
        this.userMapper = userMapper;
        this.projectMapper = projectMapper;
    }

    @Operation(summary = "Gets all skills")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Skill.class)) }),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content ={ @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Skill not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @GetMapping
    public ResponseEntity getAll() {
        return ResponseEntity.ok(skillMapper.skillToSkillDto(skillService.findAll()));
    }
    @Operation(summary = "Adds a new skill")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204",
                    description = "Skill was successfully added",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Skill.class)) }),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Skill was not found with supplied ID",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @PostMapping
    public ResponseEntity add(@RequestBody SkillPostDTO skillPostDto) {
        Skill s1 = skillService.add(skillMapper.skillPostDtoToSkill(skillPostDto));
        URI location = URI.create("skill/" + s1.getId());
        return ResponseEntity.created(location).build();
    }
    @Operation(summary = "Deletes an existing skill by id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204",
                    description = "Skill was successfully deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Skill.class)) }),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Skill was not found with supplied ID",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable int id) {
        skillService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Get a skill by id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204",
                    description = "Success",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Skill.class)) }),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Skill not found with supplied ID",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @GetMapping("{id}")
    public ResponseEntity getById(@PathVariable int id) {
        return ResponseEntity.ok(skillMapper.skillToSkillDto(skillService.findById(id)));
    }
    @Operation(summary = "Updates a skill")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204",
                    description = "Skill is successfully updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Skill.class)) }),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Skill was not found with supplied ID",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @PutMapping("{id}")
    public ResponseEntity update(@RequestBody SkillUpdateDTO skillUpdateDTO, @PathVariable int id) {
        // Validates if body is correct
        if(id != skillUpdateDTO.getId())
            return ResponseEntity.badRequest().build();
        skillService.update(skillMapper.skillUpdateDtoToSkill(skillUpdateDTO));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update the list of users that have a skill")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Success",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)) }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)) })
    })
    //Updates users that have a skill.
    @PutMapping("{id}/users")
    public ResponseEntity updateUsers(@PathVariable int id, @RequestBody String[] user_ids){
        skillService.updateUsers(id, user_ids);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get a list of all users that have a skill")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = {@Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserDTO.class)))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = @Content(
                            mediaType = "application/json",
                            schema=@Schema(implementation = ApiErrorResponse.class)
                    )),
            @ApiResponse(responseCode = "404",
                    description = "Not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema=@Schema(implementation = ApiErrorResponse.class)
                    )),
    })
    @GetMapping("{id}/users")
    public ResponseEntity getAllUsers(@PathVariable int id) {
        return ResponseEntity.ok(
                userMapper.userToUserDto(
                        skillService.findAllUsers(id)
                ));
    }

    @Operation(summary = "Update the list of projects that require a skill")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Success",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)) }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)) })
    })
    //Updates projects that require a skill.
    @PutMapping("{id}/projects")
    public ResponseEntity updateProjects(@PathVariable int id, @RequestBody int[] project_ids){
        skillService.updateProjects(id, project_ids);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get a list of all projects that require a skill")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = {@Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ProjectDTO.class)))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = @Content(
                            mediaType = "application/json",
                            schema=@Schema(implementation = ApiErrorResponse.class)
                    )),
            @ApiResponse(responseCode = "404",
                    description = "Not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema=@Schema(implementation = ApiErrorResponse.class)
                    )),
    })
    @GetMapping("{id}/projects")
    public ResponseEntity getAllProjects(@PathVariable int id) {
        return ResponseEntity.ok(
                projectMapper.projectToProjectDto(
                        skillService.findAllProjects(id)
                ));
    }

}
