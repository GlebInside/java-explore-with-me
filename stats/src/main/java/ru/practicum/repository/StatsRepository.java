package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.HitsCount;
import ru.practicum.model.Stats;

public interface StatsRepository extends JpaRepository<Stats, Integer> {
    //    Collection<Stats> findByTimestampBetweenAndUriIn(int start, int end, String[] uris);
    @Query("" +
            "select" +
            " count (s.id) as hitsCount" +
            ", s.app as app" +
            ", s.uri as uri" +
            " from Stats s where " +
            " s.timestamp between :start and :end" +
            " and s.uri = :uri" +
            " group by s.app, s.uri" +
            "" +
            "")
    HitsCount getHits(int start, int end, String uri);

}
