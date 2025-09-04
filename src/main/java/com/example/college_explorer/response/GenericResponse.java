// File: com.example.college_explorer.response.GenericResponse.java
package com.example.college_explorer.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenericResponse<T> {
    private String message;
    private T data;
}
