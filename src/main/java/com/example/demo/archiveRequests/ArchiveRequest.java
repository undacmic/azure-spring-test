package com.example.demo.archiveRequests;

import com.example.demo.archive.Archive;
import com.example.demo.person.Person;
import com.example.demo.shelf.Shelf;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "archiveRequests")
public class ArchiveRequest {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long ID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idUser")
    @JsonManagedReference
    //@JsonIdentityInfo(
    //generator = ObjectIdGenerators.PropertyGenerator.class,
    //property = "id")
    private Person person;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idArchive")
    private Archive archive;

    private String requestType;

    private String requestStatus;

    private LocalDate requestTimestamp;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Archive getArchive() {
        return archive;
    }

    public void setArchive(Archive archive) {
        this.archive = archive;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public LocalDate getRequestTimestamp() {
        return requestTimestamp;
    }

    public void setRequestTimestamp(LocalDate requestTimestamp) {
        this.requestTimestamp = requestTimestamp;
    }
    @Override
    public boolean equals(Object o) {
        if(this == o){
            return true;
        }
        if(!(o instanceof ArchiveRequest)) {
            return false;
        }
        return ID != null && ID.equals(((ArchiveRequest)o).ID);
    }
}
