package com.example.aiprojectfinal.dto;

public record TmdbMovie(
        long id,
        String title,
        String overview,
        double vote_average,
        String poster_path)
{}
