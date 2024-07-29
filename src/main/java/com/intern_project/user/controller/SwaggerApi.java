package com.intern_project.user.controller;

import com.intern_project.user.domain.*;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

public interface SwaggerApi {

    @Operation(summary = "Register a new user group")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully registered",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @PostMapping("/register")
    void register(@RequestBody UserGroup userGroup);

    @Operation(summary = "Login with email and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully logged in",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "401", description = "Invalid login credentials",
                    content = @Content) })
    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody LoginRequest loginRequest);

    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created user",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "401", description = "Invalid token or UserGroup not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @PostMapping("/createUser")
    ResponseEntity<String> createUser(@RequestBody User user, @RequestAttribute("userinfo") UserInfo userInfo);

    @Operation(summary = "Get user list by group ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user list",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content) })
    @GetMapping("/userList")
    ResponseEntity<List<User>> getUserList(UserInfo userInfo);

    @Operation(summary = "Change user and generate a new token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully changed user",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }) })
    @PostMapping("/changeUser")
    ResponseEntity<String> changeUser(@RequestBody ChangeUserRequest changeUserRequest);
}

//스웨거 작성