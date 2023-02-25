package com.mjsasha.orchestrator.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Lesson {

    private Integer id;

    @NotBlank
    private String name;
    @Schema(description = "If not specified, the lesson name is used")
    private String folderName;
    private String description;
}
