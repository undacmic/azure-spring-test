package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/lendings")
public class LendingController {

    private final LendingRepository lendingRepository;

    public LendingController(LendingRepository lendingRepository) { this.lendingRepository = lendingRepository; }

    @GetMapping("/")
    public Iterable<Lending> getLendings() {
        String userIDs = "";
        //Iterable<Lending> queryResult = lendingRepository.findAll();
        //Iterator<Lending> iterator = queryResult.iterator();
        //while(iterator.hasNext()) {
            //userIDs += iterator.next().getPerson().getID() +":" +iterator.next().getBookObject().getID() +".";
            //System.out.print(userIDs);
        //}
        //List<Lending> result = lendingRepository.findAll().stream().toList();
        return lendingRepository.findAll();
    }

}
