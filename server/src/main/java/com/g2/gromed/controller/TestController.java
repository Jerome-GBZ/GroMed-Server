package com.g2.gromed.controller;

import com.g2.gromed.aSUPPRIMER.InsertData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@CrossOrigin // NOSONAR
@RestController
public class TestController {

    @Autowired
    private InsertData insertData;

    @PostMapping("/poeple")
    public void poeple() {
        try {
            insertData.transformData();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
