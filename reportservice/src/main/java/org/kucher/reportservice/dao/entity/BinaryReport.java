package org.kucher.reportservice.dao.entity;


import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
public class BinaryReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] repArray;

    @OneToOne
    @JoinColumn(name = "report_uuid", referencedColumnName = "uuid")
    private Report report;

    public BinaryReport() {
    }


    public BinaryReport(byte[] array, Report report) {
        this.repArray = array;
        this.report = report;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getRepArray() {
        return repArray;
    }

    public void setRepArray(byte[] repArray) {
        this.repArray = repArray;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }
}
