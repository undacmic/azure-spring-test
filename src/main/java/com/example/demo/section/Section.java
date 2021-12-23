package com.example.demo.section;

import com.example.demo.role.Role;

import javax.persistence.*;

@Entity
@Table(name = "sections")
public class Section {

    public Section() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    private String sectionName;

    private int maxNumberOfShelfsPerSection;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public int getMaxNumberOfShelfsPerSection() {
        return maxNumberOfShelfsPerSection;
    }

    public void setMaxNumberOfShelfsPerSection(int maxNumberOfShelfsPerSection) {
        this.maxNumberOfShelfsPerSection = maxNumberOfShelfsPerSection;
    }
    @Override
    public boolean equals(Object o) {
        if(this == o){
            return true;
        }
        if(!(o instanceof Section)) {
            return false;
        }
        return ID != null && ID.equals(((Section)o).ID);
    }
}
