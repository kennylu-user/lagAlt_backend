package no.accelerate.lagalt_backend.models.dto.user;

import lombok.Data;

@Data
public class UserUpdateDTO {
    private int id;
    private String f_name;
    private String l_name;
    private String description;
    private boolean hidden;
}
