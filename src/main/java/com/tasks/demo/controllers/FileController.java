package com.tasks.demo.controllers;


import com.tasks.demo.models.Document;
import com.tasks.demo.models.Task;
import com.tasks.demo.repositories.FileRepository;
import com.tasks.demo.repositories.TaskRepository;
import com.tasks.demo.repositories.UserRepository;
import com.tasks.demo.services.RegistrationService;
import com.tasks.demo.services.TaskDetailService;
import com.tasks.demo.util.PersonValidator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class FileController {

    private final TaskDetailService taskDetailService;
    private final RegistrationService registrationService;
    private final PersonValidator personValidator;


    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileRepository fileRepository;

    public FileController(TaskDetailService taskDetailService, RegistrationService registrationService, PersonValidator personValidator) {
        this.taskDetailService = taskDetailService;
        this.registrationService = registrationService;
        this.personValidator = personValidator;
    }


    @PostMapping("/upload")
    public String uploadFile(@ModelAttribute("file") MultipartFile file, @ModelAttribute("id") int id, HttpServletRequest request) {
        Task task = taskDetailService.get(id);
        String referer = request.getHeader("Referer");
        if (!file.isEmpty()) {
            try {
                String fileName = file.getOriginalFilename();
                String fileExtension = getFileExtension(fileName);
                String uploadDir = "C:\\Users\\NikitaPC\\Desktop\\demo\\src\\main\\resources\\static\\images"; // Укажите путь к папке на сервере, где будут храниться документы
                File uploadPath = new File(uploadDir);
                if (!uploadPath.exists()) {
                    uploadPath.mkdirs();
                }
                String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
                File savedFile = new File(uploadDir + File.separator + uniqueFileName);
                file.transferTo(savedFile);
                Document document = new Document();
                document.setFileName(uniqueFileName);
                document.setFileExtension(fileExtension);
                document.setFilePath(savedFile.getAbsolutePath());
                document.setTask(task);
                fileRepository.save(document);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:" + referer;
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }


}
