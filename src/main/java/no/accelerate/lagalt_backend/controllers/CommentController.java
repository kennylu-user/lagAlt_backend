package no.accelerate.lagalt_backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import no.accelerate.lagalt_backend.mappers.CommentMapper;
import no.accelerate.lagalt_backend.models.Comment;
import no.accelerate.lagalt_backend.models.Project;
import no.accelerate.lagalt_backend.models.dto.comment.CommentPostDTO;
import no.accelerate.lagalt_backend.models.dto.comment.CommentUpdateDTO;
import no.accelerate.lagalt_backend.models.dto.project.ProjectPostDTO;
import no.accelerate.lagalt_backend.models.dto.project.ProjectUpdateDTO;
import no.accelerate.lagalt_backend.models.dto.user.UserDTO;
import no.accelerate.lagalt_backend.services.comment.CommentService;
import no.accelerate.lagalt_backend.utils.error.ApiErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "api/v1/comment")
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    public CommentController(CommentService commentService, CommentMapper commentMapper) {
        this.commentService = commentService;
        this.commentMapper = commentMapper;
    }

    @Operation(summary = "Get all comments")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Comment.class)) }),
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
        return ResponseEntity.ok(commentMapper.commentToCommentDTO((commentService.findAll())));
    }
    @Operation(summary = "Adds new comment")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204",
                    description = "User was successfully added",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Comment.class)) }),
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
    public ResponseEntity add(@RequestBody CommentPostDTO commentPostDto) {
        Comment c1 = commentService.add(commentMapper.commentPostDtoToComment(commentPostDto));
        URI location = URI.create("comment/" + c1.getId());
        return ResponseEntity.created(location).build();
    }
    @Operation(summary = "Deletes an existing project by id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204",
                    description = "Project was successfully deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Comment.class)) }),
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
        commentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Get a project by id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204",
                    description = "Success",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Comment.class)) }),
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
        return ResponseEntity.ok(commentMapper.commentToCommentDTO(commentService.findById(id)));
    }
    @Operation(summary = "Updates a project")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204",
                    description = "Project is successfully updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Comment.class)) }),
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
    public ResponseEntity update(@RequestBody CommentUpdateDTO commentUpdateDTO, @PathVariable int id) {
        // Validates if body is correct
        if(id != commentUpdateDTO.getId())
            return ResponseEntity.badRequest().build();
        commentService.update(commentMapper.commentUpdateDtoToComment(commentUpdateDTO));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get a list of all characters that appear in a given movie")
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
    @GetMapping("{id}/replies")
    public ResponseEntity getAllReplies(@PathVariable int id) {
        return ResponseEntity.ok(
                commentMapper.commentToCommentDTO(
                        commentService.findAllReplies(id)
                ));
    }
}
