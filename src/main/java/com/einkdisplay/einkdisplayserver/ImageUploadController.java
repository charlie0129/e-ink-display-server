package com.einkdisplay.einkdisplayserver;

import java.io.IOException;
import java.util.stream.Collectors;

import com.einkdisplay.einkdisplayserver.storage.StorageFileNotFoundException;
import com.einkdisplay.einkdisplayserver.storage.StorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//@Controller
//public class ImageUploadController {
//    private final StorageService storageService;
//
//    @Autowired
//    public ImageUploadController(StorageService storageService) {
//        this.storageService = storageService;
//    }
//
//    @GetMapping("/api/messages/{messageId}/images")
//    public String listUploadedFiles(Model model) throws IOException {
//
//        model.addAttribute("files", storageService.loadAll().map(
//                path -> MvcUriComponentsBuilder.fromMethodName(ImageUploadController.class,
//                                                               "serveFile", path.getFileName().toString()).build().toUri().toString())
//                                                  .collect(Collectors.toList()));
//
//        return "uploadForm";
//    }
//
//    @GetMapping("/api/messages/{messageId}/images/{filename:.+}")
//    @ResponseBody
//    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
//
//        Resource file = storageService.loadAsResource(filename);
//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
//                                          "attachment; filename=\"" + file.getFilename() + "\"").body(file);
//    }
//
//    @PostMapping("/api/messages/{messageId}/images")
//    public String handleFileUpload(@RequestParam("file") MultipartFile file,
//                                   @PathVariable("messageId") Long messageId,
//                                   RedirectAttributes redirectAttributes) {
//
//        storageService.store(file);
//        redirectAttributes.addFlashAttribute("message",
//                                             "You successfully uploaded " + file.getOriginalFilename() + "!");
//
//        return "redirect:"+"/api/messages/"+messageId+"/images";
//    }
//
//    @ExceptionHandler(StorageFileNotFoundException.class)
//    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
//        return ResponseEntity.notFound().build();
//    }
//}
