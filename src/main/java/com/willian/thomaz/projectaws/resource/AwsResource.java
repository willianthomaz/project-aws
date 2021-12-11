package com.willian.thomaz.projectaws.resource;

import com.willian.thomaz.projectaws.service.AwsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/archive")
public class AwsResource {

    private AwsService service;

    @Autowired
    AwsResource(AwsService awsService){
        this.service = awsService;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public void uploadFile(@RequestPart(value = "file") MultipartFile file, String folder) throws IOException {
        service.uploadFile(file, folder);
    }

    @GetMapping
    @ResponseStatus(OK)
    public URL linkFileS3(@RequestParam String fileName, @RequestParam String folder) {
        return service.linkS3(fileName, folder);
    }
}
