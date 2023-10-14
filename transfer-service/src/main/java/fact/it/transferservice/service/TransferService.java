package fact.it.transferservice.service;

import fact.it.transferservice.dto.TransferRequest;
import fact.it.transferservice.dto.TransferResponse;
import fact.it.transferservice.model.Club;
import fact.it.transferservice.model.Transfer;
import fact.it.transferservice.repository.TransferRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransferService {
    private final TransferRepository transferRepository;

    public void placeTransfer(TransferRequest transferRequest) {
        Transfer transfer = new Transfer();
        transfer.setTransferNumber(UUID.randomUUID().toString());
        transfer.setTransferDate(LocalDate.now());

        Club newClub = new Club();
        transferRequest.getNewClubDto().setName(newClub.getName());

        Club previousClub = new Club();
        transferRequest.getPreviousClubDto().setName(newClub.getName());

        transferRepository.save(transfer);
    }

    public List<TransferResponse> getAllTransfers() {
        List<Transfer> transfers = transferRepository.findAll();

        return transfers.stream()
                .map(transfer -> new TransferResponse(
                        transfer.getTransferNumber(),
                        transfer.getTransferDate(),
                        transfer.getNewClub(),
                        transfer.getPreviousClub()
                ))
                .collect(Collectors.toList());
    }
}
