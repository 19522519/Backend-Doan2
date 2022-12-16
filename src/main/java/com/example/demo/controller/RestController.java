package com.example.demo.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.service.RevenueService;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @Autowired
    RevenueService revenueService;

    @GetMapping(path = "/revenue", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRevenue() {
        List<Integer> revenueInt = new ArrayList<>();
        revenueInt.add(revenueService.getRevenueByJan());
        revenueInt.add(revenueService.getRevenueByFeb());
        revenueInt.add(revenueService.getRevenueByMar());
        revenueInt.add(revenueService.getRevenueByApr());
        revenueInt.add(revenueService.getRevenueByMay());
        revenueInt.add(revenueService.getRevenueByJun());
        revenueInt.add(revenueService.getRevenueByJul());
        revenueInt.add(revenueService.getRevenueByAug());
        revenueInt.add(revenueService.getRevenueBySep());
        revenueInt.add(revenueService.getRevenueByOct());
        revenueInt.add(revenueService.getRevenueByNov());
        revenueInt.add(revenueService.getRevenueByDec());

        Map<String, Integer> collectionRevenue = new LinkedHashMap<>();
        collectionRevenue.put("Jan", revenueService.getRevenueByJan());
        collectionRevenue.put("Feb", revenueService.getRevenueByFeb());
        collectionRevenue.put("Mar", revenueService.getRevenueByMar());
        collectionRevenue.put("Apr", revenueService.getRevenueByApr());
        collectionRevenue.put("May", revenueService.getRevenueByMay());
        collectionRevenue.put("Jun", revenueService.getRevenueByJun());
        collectionRevenue.put("Jul", revenueService.getRevenueByJul());
        collectionRevenue.put("Aug", revenueService.getRevenueByAug());
        collectionRevenue.put("Sep", revenueService.getRevenueBySep());
        collectionRevenue.put("Oct", revenueService.getRevenueByOct());
        collectionRevenue.put("Nov", revenueService.getRevenueByNov());
        collectionRevenue.put("Dec", revenueService.getRevenueByDec());

        return ResponseEntity.status(HttpStatus.OK).body(collectionRevenue);
    }
}
