package be.iccbxl.pid.reservationsSpringboot.controller;


import be.iccbxl.pid.reservationsSpringboot.model.Show;
import be.iccbxl.pid.reservationsSpringboot.service.ShowService;
import com.rometools.rome.feed.synd.*;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedOutput;
import org.hibernate.annotations.View;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/shows")
public class RssFeedController {

    private static final Logger logger = LoggerFactory.getLogger(RssFeedController.class);

    @Autowired
    private ShowService showService;

    @GetMapping("/feed.rss")
    public ResponseEntity<SyndFeed> getRssFeed() {
        logger.debug("Generating RSS feed");

        SyndFeed feed = new SyndFeedImpl();
        feed.setTitle("Flux RSS des spectacles");
        feed.setLink("/shows");
        feed.setDescription("Derniers spectacles disponibles");

        List<SyndEntry> entries = showService.getAll().stream()
                .map(this::createSyndEntry)
                .collect(Collectors.toList());
        feed.setEntries(entries);


        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_RSS_XML)
                .body(feed);
    }

    private SyndEntry createSyndEntry(Show show) {
        logger.debug("Creating RSS entry for show: " + show.getTitle());
        SyndEntry entry = new SyndEntryImpl();
        entry.setTitle(show.getTitle());
        entry.setLink("/shows/" + show.getId());

        if (show.getDate() != null) { // Vérifiez que la date n'est pas null
            if (show.getDate() instanceof LocalDateTime) {
                // Conversion de LocalDateTime en Date
                entry.setPublishedDate(Date.from(show.getDate().atZone(ZoneId.systemDefault()).toInstant()));

            } else {
                // Date par défaut si show.getDate() est null
                entry.setPublishedDate(new Date());
            }

        }

        SyndContent description = new SyndContentImpl();
        description.setType("text/plain");
        description.setValue(show.getDescription());
        entry.setDescription(description);
        return entry;
    }

}
