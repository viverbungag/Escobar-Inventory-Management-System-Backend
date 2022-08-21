package com.exe.escobar.IMSBackend.Transaction;

import com.exe.escobar.IMSBackend.Employee.Employee;
import com.exe.escobar.IMSBackend.Employee.EmployeeDao;
import com.exe.escobar.IMSBackend.Employee.Exceptions.EmployeeNotFoundException;
import com.exe.escobar.IMSBackend.Pagination.PaginationDto;
import com.exe.escobar.IMSBackend.Print.PrintTransactionReport;
import com.exe.escobar.IMSBackend.Supplier.Exceptions.SupplierNotFoundException;
import com.exe.escobar.IMSBackend.Supplier.Supplier;
import com.exe.escobar.IMSBackend.Supplier.SupplierDao;
import com.exe.escobar.IMSBackend.Supply.Exceptions.SupplyNotFoundException;
import com.exe.escobar.IMSBackend.Supply.Supply;
import com.exe.escobar.IMSBackend.Supply.SupplyDao;
import com.exe.escobar.IMSBackend.Transaction.Exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransactionService {

    @Autowired
    @Qualifier("transaction_jdbc_mysql")
    TransactionDao transactionRepository;

    @Autowired
    @Qualifier("supplier_mysql")
    SupplierDao supplierRepository;

    @Autowired
    @Qualifier("supply_mysql")
    SupplyDao supplyRepository;

    @Autowired
    @Qualifier("employee_mysql")
    EmployeeDao employeeRepository;

    @Autowired
    PrintTransactionReport printTransactionReport;


    private TransactionDto convertEntityToDto(Transaction transaction){
        return new TransactionDto(
                transaction.getTransactionId(),
                String.format("%s, %s", transaction.getTransactBy().getLastName(), transaction.getTransactBy().getFirstName()),
                transaction.getTransactionDate(),
                transaction.getSupplier().getSupplierName(),
                transaction.getSupplyQuantity(),
                transaction.getSupply().getSupplyName(),
                transaction.getSupply().getUnitOfMeasurement().getUnitOfMeasurementAbbreviation(),
                transaction.getPricePerUnit(),
                transaction.getExpiryDate(),
                transaction.getTransactionType()
        );
    }

    private Sort getSortingMethod(Boolean isAscending, Sort sort){
        if (isAscending){
            return sort.ascending();
        }
        return sort.descending();
    }

    private Sort getSortingValue(String sortedBy){

        switch(sortedBy){
            case "Transact By":
                return Sort.by("employee.last_name");

            case "Transaction Date":
                return Sort.by("transaction_date");

            case "Supplier":
                return Sort.by("supplier.supplier_name");

            case "Quantity":
                return Sort.by("transaction_supply_quantity");

            case "Supply":
                return Sort.by("supply.supply_name");

            case "Expiry Date":
                return Sort.by("expiry_date");

            case "None":
                return Sort.by("transaction_id");

            default:
                return Sort.unsorted();
        }
    }

    private Pageable initializePageable(PaginationDto paginationDto){
        int pageNo = paginationDto.getPageNo();
        int pageSize = paginationDto.getPageSize();
        Boolean isAscending = paginationDto.getIsAscending();
        String sortedBy = paginationDto.getSortedBy();

        Sort sort = getSortingValue(sortedBy);
        Sort finalSort = getSortingMethod(isAscending, sort);

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, finalSort);

        return pageable;
    }

    private Map<String, Object> initializeTransactionWithPageDetails(Page<Transaction> transactionPage, PaginationDto paginationDto){
        Integer pageNo = paginationDto.getPageNo();
        Integer totalPages = transactionPage.getTotalPages();
        Long totalCount = transactionPage.getTotalElements();

        Map<String, Object> transactionWithPageDetails = new HashMap<>();

        if (pageNo < 1 || pageNo > totalPages){
            transactionWithPageDetails.put("contents", new ArrayList<>());
            transactionWithPageDetails.put("totalPages", 0);
            transactionWithPageDetails.put("totalCount", 0);
            return transactionWithPageDetails;
        }

        transactionWithPageDetails.put("contents",
                transactionPage
                        .getContent()
                        .stream()
                        .map((Transaction transaction)-> convertEntityToDto(transaction))
                        .collect(Collectors.toList()));

        transactionWithPageDetails.put("totalPages", totalPages);
        transactionWithPageDetails.put("totalCount", totalCount);

        return transactionWithPageDetails;
    }

    public Map<String, Object> getAllPagedTransactions(TransactionFiltersPaginationDto transactionFiltersPaginationDto){

        PaginationDto paginationDto = new PaginationDto(transactionFiltersPaginationDto.getPageNo(),
                transactionFiltersPaginationDto.getPageSize(),
                transactionFiltersPaginationDto.getSortedBy(),
                transactionFiltersPaginationDto.getIsAscending());



        Pageable pageable = initializePageable(paginationDto);
        Page<Transaction> transactionPage = transactionRepository
                .getAllPagedTransactions(pageable, transactionFiltersPaginationDto);
//        Page<Transaction> transactionPage = null;

        return initializeTransactionWithPageDetails(transactionPage, paginationDto);
    }

    public void stockInTransaction(TransactionDto transactionDto){
        String[] transactionSplit  = transactionDto.getTransactByName().split(", ");
        String transactByLastName = transactionSplit[0];
        String transactByFirstName = transactionSplit[1];
        LocalDateTime transactionDate = transactionDto.getTransactionDate();
        Double quantity = transactionDto.getSupplyQuantity();
        Double pricePerUnit = transactionDto.getPricePerUnit();
        LocalDateTime expiryDate = transactionDto.getExpiryDate();
        TransactionType transactionType = transactionDto.getTransactionType();

        if (transactionDate == null){
            throw new TransactionDateIsNullException();
        }

        if (pricePerUnit == null){
            throw new PricePerUnitIsNullException();
        }

        if (quantity == null){
            throw new QuantityIsNullException();
        }

        if (quantity < 1){
            throw new QuantityIsLessThanOneException();
        }

        if (pricePerUnit < 1){
            throw new PricePerUnitIsLessThanZeroException();
        }

        Employee transactBy = employeeRepository
                .getEmployeeByFirstAndLastName(transactByFirstName, transactByLastName)
                .orElseThrow(() -> new EmployeeNotFoundException(transactByFirstName, transactByLastName));

        Supplier supplier = supplierRepository
                .getSupplierByName(transactionDto.getSupplierName())
                .orElseThrow(()-> new SupplierNotFoundException(transactionDto.getSupplierName()));

        Supply supply = supplyRepository
                .getSupplyByName(transactionDto.getSupplyName())
                .orElseThrow(() -> new SupplyNotFoundException(transactionDto.getSupplyName()));

        supply.setSupplyQuantity(supply.getSupplyQuantity() + quantity);

        transactionRepository.insertTransaction(
                transactBy.getEmployeeId(),
                transactionDate,
                supplier.getSupplierId(),
                quantity,
                supply.getSupplyId(),
                pricePerUnit,
                expiryDate,
                transactionType.toString());
    }

    public void stockOutTransaction(TransactionDto transactionDto){
        String[] transactionSplit  = transactionDto.getTransactByName().split(", ");
        String transactByLastName = transactionSplit[0];
        String transactByFirstName = transactionSplit[1];
        LocalDateTime transactionDate = transactionDto.getTransactionDate();
        Double quantity = transactionDto.getSupplyQuantity();
        Double pricePerUnit = transactionDto.getPricePerUnit();
        LocalDateTime expiryDate = transactionDto.getExpiryDate();
        TransactionType transactionType = transactionDto.getTransactionType();

        Employee transactBy = employeeRepository
                .getEmployeeByFirstAndLastName(transactByFirstName, transactByLastName)
                .orElseThrow(() -> new EmployeeNotFoundException(transactByFirstName, transactByLastName));

        Supplier supplier = supplierRepository
                .getSupplierByName(transactionDto.getSupplierName())
                .orElseThrow(()-> new SupplierNotFoundException(transactionDto.getSupplierName()));

        Supply supply = supplyRepository
                .getSupplyByName(transactionDto.getSupplyName())
                .orElseThrow(() -> new SupplyNotFoundException(transactionDto.getSupplyName()));

        if (quantity == null){
            throw new QuantityIsNullException();
        }

        if (quantity < 1){
            throw new QuantityIsLessThanOneException();
        }

        Double newQuantity = supply.getSupplyQuantity() - quantity;

        if (newQuantity < 0){
            throw new SupplyQuantityIsLessThanZeroException();
        }

        supply.setSupplyQuantity(newQuantity);

        transactionRepository.insertTransaction(
                transactBy.getEmployeeId(),
                transactionDate,
                supplier.getSupplierId(),
                quantity,
                supply.getSupplyId(),
                pricePerUnit,
                expiryDate,
                transactionType.toString());

    }

    public void printTransaction(TransactionPrintDetailsDto transactionPrintDetailsDto){

        printTransactionReport.print(transactionPrintDetailsDto.getTransactions(), transactionPrintDetailsDto.getAccountFullName());
    }

}
