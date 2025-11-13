package com.example.aiprojectfinal.dto;

import com.example.aiprojectfinal.client.MovieClient;

import java.util.List;

public record TmdbResponse(List<MovieClient.TmdbMovie> results) {}
