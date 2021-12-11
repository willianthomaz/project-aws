package com.willian.thomaz.projectaws.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

@Service
public class AwsService {

    @Value("${amazonProperties.bucketName}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3client;

    public void uploadFile(MultipartFile multipartFile, String folder) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        String fileName = multipartFile.getOriginalFilename();
        uploadFileTos3bucket(fileName, folder, file);
    }

    private void uploadFileTos3bucket(String fileName, String folder, File file) {
        if(folder == null || folder.equals("")){
            s3client.putObject(new PutObjectRequest(bucketName, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
        }else {
            s3client.putObject(new PutObjectRequest(bucketName + "/" + folder, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
        }
    }

    public URL linkS3(String fileName, String folder) {
        return s3client.getUrl(bucketName, folder + "/" + fileName);
    }
}
