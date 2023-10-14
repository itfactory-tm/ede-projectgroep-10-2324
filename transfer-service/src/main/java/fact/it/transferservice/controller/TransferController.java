package fact.it.transferservice.controller;

import fact.it.transferservice.dto.TransferRequest;
import fact.it.transferservice.dto.TransferResponse;
import fact.it.transferservice.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transfer")
@RequiredArgsConstructor
public class TransferController {
    private final TransferService transferService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeTransfer(@RequestBody TransferRequest transferRequest) {
        transferService.placeTransfer(transferRequest);
        return "Transfer completed successfully";
    }

    @GetMapping
    public List<TransferResponse> getAllTransfers() {
        return transferService.getAllTransfers();
    }
}
