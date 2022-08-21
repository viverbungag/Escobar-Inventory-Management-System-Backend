package com.exe.escobar.IMSBackend.Transaction;

import com.exe.escobar.IMSBackend.Pagination.PaginationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("api/v1/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;


    @PostMapping
    public Map<String, Object> getAllTransactions(@RequestBody TransactionFiltersPaginationDto transactionFiltersPaginationDto){
        return transactionService.getAllPagedTransactions(transactionFiltersPaginationDto);
    }

    @PostMapping("/stock-in")
    public void stockInTransaction(@RequestBody TransactionDto transactionDto){
        transactionService.stockInTransaction(transactionDto);
    }

    @PostMapping("/stock-out")
    public void stockOutTransaction(@RequestBody TransactionDto transactionDto){
        transactionService.stockOutTransaction(transactionDto);
    }
}
