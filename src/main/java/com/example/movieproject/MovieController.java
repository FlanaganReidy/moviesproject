package com.example.movieproject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MovieController {
    static final String API_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=be2a38521a7859c95e2d73c48786e4bb";



    @RequestMapping("/home")
    public String home(){
        return "home";
    }
    @RequestMapping("/medium-popular-long-name")
    public String mediumPopularLongName(Model model){
        List <Movie> movieList = getMovies();
       List<Movie> medPop = movieList.stream()
                .filter(e -> e.getPopularity()> 30 && e.getPopularity()< 80)
                .filter(e-> e.getTitle().length() >= 10)
                .collect(Collectors.toList());

        model.addAttribute("movieList", medPop);

        return "medium-popular-long-name";
    }
    @RequestMapping("/now-playing")
    public String nowPlaying(Model model){
        List <Movie> movieList = getMovies();
        model.addAttribute("movieList", movieList);

        return "now-playing";
    }
    public static List<Movie> getMovies(){

        RestTemplate restTemplate = new RestTemplate();
        ImdbResponse response =
                restTemplate.getForObject(API_URL, ImdbResponse.class);
        List<Movie> movieList = response.getResults();

        return movieList;

    }
}
