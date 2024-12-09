package tn.esprit.eventsproject.services;
import tn.esprit.eventsproject.config.PerformanceAspect;
import tn.esprit.eventsproject.entities.Tache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.entities.Logistics;
import tn.esprit.eventsproject.entities.Participant;
import tn.esprit.eventsproject.repositories.EventRepository;
import tn.esprit.eventsproject.repositories.LogisticsRepository;
import tn.esprit.eventsproject.repositories.ParticipantRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class EventServicesImpl implements IEventServices {

    private final EventRepository eventRepository;
    private final ParticipantRepository participantRepository;
    private final LogisticsRepository logisticsRepository;


    private static final Logger log = LoggerFactory.getLogger(PerformanceAspect.class);
    @Override
    public Participant addParticipant(Participant participant) {
        return participantRepository.save(participant);
    }

    @Override
    public Event addAffectEvenParticipant(Event event, int idParticipant) {
        Participant participant = participantRepository.findById(idParticipant).orElse(null);
        if (participant != null) {
            if (participant.getEvents() == null) {
                Set<Event> events = new HashSet<>();
                events.add(event);
                participant.setEvents(events);
            } else {
                participant.getEvents().add(event);
            }
            eventRepository.save(event);
        }
        return event;
    }

    @Override
    public Event addAffectEvenParticipant(Event event) {
        Set<Participant> participants = event.getParticipants();
        for (Participant participant : participants) {
            participant = participantRepository.findById(participant.getIdPart()).orElse(null);
            if (participant != null) {
                if (participant.getEvents() == null) {
                    Set<Event> events = new HashSet<>();
                    events.add(event);
                    participant.setEvents(events);
                } else {
                    participant.getEvents().add(event);
                }
            }
        }
        return eventRepository.save(event);
    }

    @Override
    public Logistics addAffectLog(Logistics logistics, String descriptionEvent) {
        Event event = eventRepository.findByDescription(descriptionEvent);
        if (event != null) {
            if (event.getLogistics() == null) {
                Set<Logistics> logisticsSet = new HashSet<>();
                logisticsSet.add(logistics);
                event.setLogistics(logisticsSet);
            } else {
                event.getLogistics().add(logistics);
            }
            eventRepository.save(event);
        }
        return logisticsRepository.save(logistics);
    }

    @Override
    public List<Logistics> getLogisticsDates(LocalDate dateDebut, LocalDate dateFin) {
        List<Event> events = eventRepository.findByDateDebutBetween(dateDebut, dateFin);
        List<Logistics> logisticsList = new ArrayList<>();
        for (Event event : events) {
            if (event.getLogistics() != null && !event.getLogistics().isEmpty()) {
                for (Logistics logistics : event.getLogistics()) {
                    if (logistics.isReserve()) {
                        logisticsList.add(logistics);
                    }
                }
            }
        }
        return logisticsList;
    }

    @Scheduled(cron = "0 * * * * *")
    @Override
    public void calculCout() {
        List<Event> events = eventRepository.findByParticipants_NomAndParticipants_PrenomAndParticipants_Tache("Tounsi", "Ahmed", Tache.ORGANISATEUR);
        for (Event event : events) {
            log.info("Calculating cost for event: " + event.getDescription());
            float somme = 0f;
            Set<Logistics> logisticsSet = event.getLogistics();
            for (Logistics logistics : logisticsSet) {
                if (logistics.isReserve()) {
                    somme += logistics.getPrixUnit() * logistics.getQuantite();
                }
            }
            event.setCout(somme);
            eventRepository.save(event);
            log.info("Cout de l'Event " + event.getDescription() + " est " + somme);
        }
    }
}
