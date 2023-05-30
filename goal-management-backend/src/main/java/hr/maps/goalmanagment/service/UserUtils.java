package hr.maps.goalmanagment.service;

import org.springframework.stereotype.Service;

import hr.maps.goalmanagment.dtos.MediaDetails;
import hr.maps.goalmanagment.enumeration.MediaContext;
import hr.maps.goalmanagment.mappers.MediaDatailsMapper;
import hr.maps.goalmanagment.persistence.entities.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserUtils {

	private final MediaDatailsMapper mediaDatailsMapper;

	public MediaDetails getPictureProfile(User user) {
		return mediaDatailsMapper.toMediaDetails(user.getMedias().stream()
				.filter(media -> media.getMediaContext() == MediaContext.PICTURE_PROFIL).findFirst().orElse(null));

	}
}
