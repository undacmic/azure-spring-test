package com.example.demo.shelf;

import com.example.demo.role.Role;
import com.example.demo.section.Section;

import javax.persistence.*;

@Entity
@Table(name = "shelfs")
public class Shelf {

    public Shelf() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    private int maxNumberOfBooksPerShelf;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bookSection")
    private Section section;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public int getMaxNumberOfBooksPerShelf() {
        return maxNumberOfBooksPerShelf;
    }

    public void setMaxNumberOfBooksPerShelf(int maxNumberOfBooksPerShelf) {
        this.maxNumberOfBooksPerShelf = maxNumberOfBooksPerShelf;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }
    @Override
    public boolean equals(Object o) {
        if(this == o){
            return true;
        }
        if(!(o instanceof Shelf)) {
            return false;
        }
        return ID != null && ID.equals(((Shelf)o).ID);
    }
}
