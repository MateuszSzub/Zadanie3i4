package pl.edu.wat.projektspring.service;

import org.springframework.stereotype.Service;
import pl.edu.wat.projektspring.entity.Tool;

import java.util.Random;

@Service
public class RatingService {
    public Integer getRating(Tool tool) {
        return new Random().nextInt(10); //TODO
    }
}