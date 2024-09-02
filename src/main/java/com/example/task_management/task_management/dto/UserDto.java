package com.example.task_management.task_management.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String id;

    @NonNull
    private String userName;

    @NonNull
    private String password;
}
