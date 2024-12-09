package tn.esprit.eventsproject.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int idEvent;
    String description;
    LocalDate dateDebut;
    LocalDate dateFin;
    float cout;

    @ManyToMany(mappedBy = "events")
    Set<Participant> participants;

    @OneToMany(fetch = FetchType.EAGER)
    Set<Logistics> logistics;

    public Set<Participant> getParticipants() {
        return this.participants;
    }

    public Set<Logistics> getLogistics() {
        return this.logistics;
    }

    public void setLogistics(Set<Logistics> logistics) {
        this.logistics = logistics;
    }

    public String getDescription() {
        return description;
    }
    public void setCout(float cout) {
        this.cout = cout;
    }


}
