package hr.maps.goalmanagment.dtos;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import hr.maps.goalmanagment.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
	private String commentText;
	private String writerFirstName;
	private String writerLastName;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = Constants.DEFAULT_TIMEZONE)
	private Date commentDate;

}
