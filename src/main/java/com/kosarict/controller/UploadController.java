package com.kosarict.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;

/**
 * Created by Ali-Pc on 12/23/2016.
 */
@Controller
public class UploadController {
    @Autowired
    private ServletContext servletContext;

    @RequestMapping(value = "/upload/api/uploadAttachment", method = RequestMethod.POST)
    public
    @ResponseBody
    String uploadAttachment(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();

            String uniqFileName = UUID.randomUUID().toString()+fileName;

            File rootDir = new File(servletContext.getRealPath("/static/attachment"));

            if (!rootDir.exists())
                rootDir.mkdirs();


            if (!"".equalsIgnoreCase(fileName)) {
                file.transferTo(new File(rootDir.getAbsolutePath() + File.separator + uniqFileName));
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("fileName",uniqFileName);
            return String.valueOf(jsonObject);
        } catch (IOException e) {
            return String.valueOf(false);
        }
    }
}
