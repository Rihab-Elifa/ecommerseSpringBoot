package com.example.AppEcommerce.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;

@Data
@Entity
public class File {

    @Id

    private String id;

    private String name;

    private String type;

    //@JsonIgnore
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] bytes;


    public File(String name, String type, byte[] bytes) {
        this.name=name;
        this.type=type;
        this.bytes=bytes;
    }


}