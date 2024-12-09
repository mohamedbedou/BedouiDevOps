package tn.esprit.eventsproject.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Participant implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idPart;
    String nom;
    String prenom;
    @Enumerated(EnumType.STRING)
    Tache tache;
    @ManyToMany
    Set<Event> events;
    public Set<Event> getEvents() {
        return this.events; // assuming 'events' is a member variable
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public static int getIdPart() {
        return getIdPart(); // assuming 'idPart' is the ID field
    }



}
