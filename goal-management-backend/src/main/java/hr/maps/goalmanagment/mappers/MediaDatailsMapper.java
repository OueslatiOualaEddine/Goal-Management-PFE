package hr.maps.goalmanagment.mappers;

import org.springframework.stereotype.Service;

import hr.maps.goalmanagment.dtos.MediaDetails;
import hr.maps.goalmanagment.persistence.entities.Media;
import hr.maps.goalmanagment.persistence.repositories.MediaRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class MediaDatailsMapper {
  private final MediaRepository mediaRepository;

  public MediaDetails toMediaDetails(Media media) {
     if(media!=null){
       return new MediaDetails(media.getUuid(), media.getMediaUrl(),media.getMediaLabel(),media.getOriginalName());
     }else return null;
  }
  public Media toMedia(MediaDetails media) {
    return mediaRepository.findByUuid(media.getMediaUuid());
  }

}
