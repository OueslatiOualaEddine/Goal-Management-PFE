package hr.maps.goalmanagment.dtos;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDto<T> {

  private List<T> items;
  private long count;
}
