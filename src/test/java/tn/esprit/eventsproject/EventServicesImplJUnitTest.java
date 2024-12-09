package tn.esprit.eventsproject;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.eventsproject.entities.Logistics;
import tn.esprit.eventsproject.services.EventServicesImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EventServicesImplJUnitTest {
    @Autowired
    private EventServicesImpl eventServices;
    @Test
    @Order(1)
    public void testGetLogisticsDates_ValidRange() {
        // Arrange
        LocalDate dateDebut = LocalDate.now().minusDays(1);
        LocalDate dateFin = LocalDate.now().plusDays(1);

        // Act
        List<Logistics> result = eventServices.getLogisticsDates(dateDebut, dateFin);

        // Assert
        assertNotNull(result, "The result should not be null");
        assertFalse(result.isEmpty(), "The result list should not be empty");
        result.forEach(logistics -> {
            assertTrue(logistics.isReserve(), "Each logistics item should be reserved");
        });
    }
    @Test
    @Order(2)
    public void testGetLogisticsDates_EmptyRange() {
        // Arrange
        LocalDate dateDebut = LocalDate.now().plusDays(10);
        LocalDate dateFin = LocalDate.now().plusDays(20);

        // Act
        List<Logistics> result = eventServices.getLogisticsDates(dateDebut, dateFin);

        // Assert
        assertNotNull(result, "The result should not be null");
        assertTrue(result.isEmpty(), "The result list should be empty for the given date range");
    }

    @Test
    @Order(3)
    public void testGetLogisticsDates_NullRange() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> eventServices.getLogisticsDates(null, null),
                "An exception should be thrown when the date range is null");
    }

}
