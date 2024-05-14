package sion.bestRoom.feign.response;

import lombok.Getter;
import lombok.Setter;
import sion.bestRoom.dto.NaverArticleDTO;

import java.util.List;

@Getter
@Setter
public class NaverResponse {
    private Boolean isMoreData;
    private List<NaverArticleDTO> articleList;
    private Long mapExposedCount;
    private Boolean nonMapExposedIncluded;
}
