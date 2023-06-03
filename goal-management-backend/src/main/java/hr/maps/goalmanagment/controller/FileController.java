package hr.maps.goalmanagment.controller;


import hr.maps.goalmanagment.enumeration.MediaContext;
import hr.maps.goalmanagment.persistence.entities.Media;
import hr.maps.goalmanagment.persistence.entities.User;
import hr.maps.goalmanagment.service.MediaService;
import hr.maps.goalmanagment.service.UserService;
import hr.maps.goalmanagment.service.files.DBFileStorageService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/file")
@RestController
@RequiredArgsConstructor
public class FileController {

  private final DBFileStorageService dBFileStorageService;
  private final MediaService mediaService;

  private final UserService userService;
  @CrossOrigin /* ("http://localhost:4200/") ("https://fr.hrmaps.eu.com/") */
  @GetMapping("/downloadFile/{fileName:.+}")
  public ResponseEntity<Resource> downloadFile(@PathVariable String fileName,
      HttpServletRequest request)
      throws Exception {
    Resource resource = dBFileStorageService.loadFileAsResource(fileName);

    String contentType = null;
    try {
      contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
    } catch (IOException ex) {

    }

    // Fallback to the default content type if type could not be determined
    if (contentType == null) {
      contentType = "application/octet-stream";
    }

    return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + resource.getFilename() + "\"")
        .body(resource);
  }


  @CrossOrigin /* ("http://localhost:4200/") ("https://fr.hrmaps.eu.com/") */
  @RequestMapping(path = "/download-file", method = RequestMethod.GET)
  public ResponseEntity<Resource> download(@RequestParam("filename") String filepath) throws IOException {
    Path path =dBFileStorageService.getAbsolutePath(filepath);
    ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .body(resource);
  }

  @CrossOrigin /* ("http://localhost:4200/") ("https://fr.hrmaps.eu.com/") */
  @GetMapping("delete-user-photo")
  public void deleteUserPhoto(@RequestParam("user-uuid") UUID userUuid,@RequestParam("context") MediaContext context) {
    User user = userService.getUserByUUID(userUuid);
    if (!user.getMedias().isEmpty()) {
      if (context == MediaContext.PICTURE_PROFIL ) {
        Optional<Media> picture = user.getMedias().stream()
            .filter(userMedia -> userMedia.getMediaContext() == context)
            .findFirst();
        Media pictureToDelete = picture.isPresent() ? picture.get() : null;
        if (pictureToDelete != null) {
          user.getMedias().remove(pictureToDelete);
        }
      }

    }
    userService.saveOrUpdateUser(user);
  }

  }
