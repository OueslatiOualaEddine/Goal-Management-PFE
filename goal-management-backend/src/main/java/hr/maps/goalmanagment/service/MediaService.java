package hr.maps.goalmanagment.service;

import hr.maps.goalmanagment.enumeration.MediaContext;
import hr.maps.goalmanagment.persistence.entities.Media;
import hr.maps.goalmanagment.persistence.repositories.MediaRepository;
import hr.maps.goalmanagment.service.files.DBFileStorageService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MediaService {

  private final MediaRepository mediaRepository;

  private final DBFileStorageService dBFileStorageService;

  public List<Media> getAllMedias() {
    return this.mediaRepository.findAll();
  }

 public Media saveMedia(Media media) {
    return this.mediaRepository.save(media);
  }
  public Media findByUuid(UUID mediaUuid) {
    return this.mediaRepository.findByUuid(mediaUuid);
  }


  public void deleteMedia(Long id) {
    this.mediaRepository.deleteById(id);
  }

  public Media saveMedia(MultipartFile file, MediaContext context) throws Exception {

    String mediaName = dBFileStorageService.storeFile(file);
    String fileDownloadUri = "/file/downloadFile/" + mediaName;

    Media media = new Media();
    media.setMediaContext(context);
    media.setMediaUrl(fileDownloadUri);
    media.setMediaLabel(mediaName);
    media.setMediaSize(file.getSize());
    media.setMediaContentType(file.getContentType());
    media.setOriginalName(file.getOriginalFilename());
    media = mediaRepository.save(media);
    return media;
  }
}
