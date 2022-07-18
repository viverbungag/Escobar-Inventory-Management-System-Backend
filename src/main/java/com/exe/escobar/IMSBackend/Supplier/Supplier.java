package com.exe.escobar.IMSBackend.Supplier;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "supplier")
public class Supplier {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "supplier_id")
    private Long supplierId;

    @Column(name = "supplier_name")
    private String supplierName;

    @Column(name = "supplier_address")
    private String supplierAddress;

    @Column(name = "supplier_contact_number")
    private String supplierContactNumber;

    @Column(name = "supplier_contact_person")
    private String supplierContactPerson;
}
