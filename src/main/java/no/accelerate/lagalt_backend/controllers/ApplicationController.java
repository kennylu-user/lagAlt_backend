package no.accelerate.lagalt_backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import no.accelerate.lagalt_backend.mappers.ApplicationMapper;
import no.accelerate.lagalt_backend.models.Application;
import no.accelerate.lagalt_backend.models.Project;
import no.accelerate.lagalt_backend.models.dto.application.ApplicationDTO;
import no.accelerate.lagalt_backend.models.dto.application.ApplicationPostDTO;
import no.accelerate.lagalt_backend.models.dto.application.ApplicationUpdateDTO;
import no.accelerate.lagalt_backend.models.dto.project.ProjectPostDTO;
import no.accelerate.lagalt_backend.services.application.ApplicationService;
import no.accelerate.lagalt_backend.utils.error.ApiErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Set;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "api/v1/application")
public class ApplicationController {

    private final ApplicationMapper applicationMapper;
    private final ApplicationService applicationService;

    public ApplicationController(ApplicationMapper applicationMapper, ApplicationService applicationService) {
        this.applicationMapper = applicationMapper;
        this.applicationService = applicationService;
    }

    @Operation(summary = "Get all applications")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Application.class)) }),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content ={ @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Application not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @GetMapping
    public ResponseEntity getAll() {
        return ResponseEntity.ok(applicationMapper.applicationToApplicationDto(applicationService.findAll()));
    }

    @Operation(summary = "Adds new application")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204",
                    description = "Application was successfully added",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Application.class)) }),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Application was not found with supplied ID",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @PostMapping
    public ResponseEntity add(@RequestBody ApplicationPostDTO applicationDto) {
        Application p1 = applicationService.add(applicationMapper.applicationPostDtoToApplication(applicationDto));
        URI location = URI.create("application/" + p1.getId());
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Get an application by id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204",
                    description = "Success",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Application.class)) }),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Application not found with supplied ID",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @GetMapping("{id}")
    public ResponseEntity getById(@PathVariable int id) {
        return ResponseEntity.ok(applicationMapper.applicationToApplicationDto(applicationService.findById(id)));
    }

    @Operation(summary = "Updates an application")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204",
                    description = "Application is successfully updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Application.class)) }),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Application was not found with supplied ID",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @PutMapping("{id}")
    public ResponseEntity update(@RequestBody ApplicationUpdateDTO applicationDTO, @PathVariable int id) {
        // Validates if body is correct
        if(id != applicationDTO.getId())
            return ResponseEntity.badRequest().build();
        applicationService.update(applicationMapper.applicationUpdateDtoToApplication(applicationDTO));
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Deletes an existing application by id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204",
                    description = "Application is successfully deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Application.class)) }),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Application was not found with supplied ID",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable int id) {
        applicationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
