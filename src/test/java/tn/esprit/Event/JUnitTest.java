

// JUnitTest Class
package tn.esprit.Event;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

        import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.entities.Logistics;
import tn.esprit.eventsproject.repositories.EventRepository;
import tn.esprit.eventsproject.repositories.LogisticsRepository;
import tn.esprit.eventsproject.services.EventServicesImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class JUnitTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private LogisticsRepository logisticsRepository;

    @InjectMocks
    private EventServicesImpl eventServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddAffectLog() {
        Logistics logistics = new Logistics();
        logistics.setDescription("Chairs");

        Event event = new Event();
        event.setDescription("Conference");

        when(eventRepository.findByDescription("Conference")).thenReturn(event);
        when(logisticsRepository.save(logistics)).thenReturn(logistics);

        Logistics savedLogistics = eventServices.addAffectLog(logistics, "Conference");

        assertNotNull(savedLogistics);
        verify(eventRepository, times(1)).findByDescription("Conference");
        verify(logisticsRepository, times(1)).save(logistics);
    }

    @Test
    void testGetLogisticsDates() {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);

        List<Event> events = new ArrayList<>();
        Event event = new Event();
        events.add(event);

        when(eventRepository.findByDateDebutBetween(startDate, endDate)).thenReturn(events);

        List<Logistics> logisticsList = eventServices.getLogisticsDates(startDate, endDate);

        assertNotNull(logisticsList);
        verify(eventRepository, times(1)).findByDateDebutBetween(startDate, endDate);
    }
}
