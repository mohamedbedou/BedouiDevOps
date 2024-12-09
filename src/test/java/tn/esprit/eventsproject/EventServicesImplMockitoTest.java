package tn.esprit.eventsproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.entities.Logistics;
import tn.esprit.eventsproject.entities.Participant;
import tn.esprit.eventsproject.entities.Tache;
import tn.esprit.eventsproject.repositories.EventRepository;
import tn.esprit.eventsproject.repositories.LogisticsRepository;
import tn.esprit.eventsproject.repositories.ParticipantRepository;
import tn.esprit.eventsproject.services.EventServicesImpl;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class EventServicesImplMockitoTest {





        @Mock
        private EventRepository eventRepository;

        @InjectMocks
        private EventServicesImpl eventService;

        @Test
        public void testCreateEvent() {
            // Arrange
            Event event = new Event();
            event.setName("Tech Conference");
            event.setLocation("Paris");

            when(eventRepository.save(any(Event.class))).thenReturn(event);

            // Act
            Event createdEvent = eventService.createEvent(event);

            // Assert
            assertNotNull(createdEvent);
            assertEquals("Tech Conference", createdEvent.getName());
            assertEquals("Paris", createdEvent.getLocation());
            verify(eventRepository, times(1)).save(event);
        }

        @Test
        public void testGetAllEvents() {
            // Arrange
            Event event1 = new Event();
            event1.setName("Event 1");
            event1.setLocation("Location 1");

            Event event2 = new Event();
            event2.setName("Event 2");
            event2.setLocation("Location 2");

            List<Event> events = Arrays.asList(event1, event2);

            when(eventRepository.findAll()).thenReturn(events);

            // Act
            List<Event> result = eventService.getAllEvents();

            // Assert
            assertEquals(2, result.size());
            assertEquals("Event 1", result.get(0).getName());
            assertEquals("Event 2", result.get(1).getName());
            verify(eventRepository, times(1)).findAll();
        }
    }
