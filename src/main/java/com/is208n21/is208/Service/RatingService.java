package com.is208n21.is208.Service;



import com.is208n21.is208.Entity.Model.Rating;
import com.is208n21.is208.Repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class RatingService {
    @Autowired
    RatingRepository ratingRepository;

    public void save(Rating rating) {
        ratingRepository.save(rating);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void delete() {
        ratingRepository.deleteRatingByRating();
    }
}
