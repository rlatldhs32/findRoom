package sion.bestRoom.elasticsearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import sion.bestRoom.model.OneRoom;

public interface OneRoomSearchRepository extends ElasticsearchRepository<OneRoom, Long> {
}
