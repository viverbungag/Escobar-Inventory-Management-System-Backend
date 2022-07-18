package com.exe.escobar.IMSBackend.Supplier;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierDao {

    List<Supplier> getAllSuppliers();
}
