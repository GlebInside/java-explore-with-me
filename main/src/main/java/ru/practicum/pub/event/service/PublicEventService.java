package ru.practicum.pub.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.admin.events.EventMapper;
import ru.practicum.admin.events.model.Event;
import ru.practicum.admin.events.model.State;
import ru.practicum.dto.EventFullDto;
import ru.practicum.dto.EventShortDto;
import ru.practicum.exception.NotFoundException;
import ru.practicum.pub.event.dto.EventSort;
import ru.practicum.pub.event.storage.PublicEventRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

import static ru.practicum.pub.event.dto.EventSort.EVENT_DATE;
import static ru.practicum.pub.event.dto.EventSort.VIEWS;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PublicEventService {

    private final PublicEventRepository publicEventRepository;

    public Collection<EventShortDto> find(String text, State state, String[] strCategories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, boolean onlyAvailable, EventSort eventSort, int from, int size) {
        int[] categories = null;
        if (strCategories != null) {
            categories = Arrays.stream(strCategories).map(Integer::parseInt).mapToInt(i -> i).toArray();
        }
        if (rangeStart == null && rangeEnd == null) {
            rangeStart = LocalDateTime.now();
        }
        var events = publicEventRepository.find(
                state.ordinal(), onlyAvailable, text != null, text != null ? text.toLowerCase() : null, strCategories != null, categories, paid != null, paid, rangeStart != null, rangeStart, rangeEnd != null, rangeEnd, PageRequest.of(from, size)
        ).stream().map(EventMapper::toShortDto);
        if (eventSort != null) {
            if (eventSort == EVENT_DATE) {
                events = events.sorted(Comparator.comparing(EventShortDto::getEventDate));
            }
            if (eventSort == VIEWS) {
                events = events.sorted(Comparator.comparing(EventShortDto::getViews));
            }
        } else {
            events = events.sorted(Comparator.comparing(EventShortDto::getId).reversed());
        }
        var list = events.collect(Collectors.toList());
        return list;
    }

    public Event getById(int eventId) {
        return publicEventRepository.getReferenceById(eventId);
    }

    public EventFullDto getFullDtoById(int eventId) {
        if (!publicEventRepository.existsById(eventId)) {
            throw new NotFoundException("Event with eventId " + eventId + " was not found.");
        }
        var model = getById(eventId);
        if (model.getState() != State.PUBLISHED) {
            throw new NotFoundException("Event with eventId " + eventId + " was not found.");
        }
        return EventMapper.toFullDto(model);
    }

    public int countByCategoryId(int categoryId) {
        return publicEventRepository.countByCategoryId(categoryId);
    }

}
