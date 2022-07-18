package com.exe.escobar.IMSBackend.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class SupplierService {

    @Autowired
    @Qualifier("supplier_mysql")
    SupplierDao supplierRepository;

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.getAllSuppliers();
    }
}
