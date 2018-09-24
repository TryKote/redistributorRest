package com.zaycev.redistributor.rest;

import com.zaycev.redistributor.core.Redistributor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Собственно, hello-world реализация rest сервиса
 */
@RequestMapping("/rest")
@RestController
public class RestService {
    private static final Logger log = LoggerFactory.getLogger(RestService.class.getName());
    private Redistributor redistributor;

    @RequestMapping(value = "/do", params = {"i"})
    public List<Integer> doIt(@RequestParam(name = "i") List<Integer> inputData) {
        log.info("Received data: " + inputData);
        return redistributor.doIt(inputData);
    }

    @Autowired
    public void setRedistributor(Redistributor redistributor) {
        this.redistributor = redistributor;
    }
}
