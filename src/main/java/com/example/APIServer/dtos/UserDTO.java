package com.example.APIServer.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;

    @NonNull
    private String username;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;

    public UserDTO(String username, String email) {
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setId(Object id) {
        this.id = (Long) id;
    }

    public Long getId() {
        return this.id;
    }
}
