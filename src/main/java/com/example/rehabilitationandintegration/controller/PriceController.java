package com.example.rehabilitationandintegration.controller;

import com.example.rehabilitationandintegration.enums.Status;
import com.example.rehabilitationandintegration.model.PriceDto;
import com.example.rehabilitationandintegration.model.request.ActiveInactiveDto;
import com.example.rehabilitationandintegration.service.PriceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/price")
@RequiredArgsConstructor
public class PriceController {
    private final PriceService priceService;

    @GetMapping("/all")
    public ResponseEntity<Page<PriceDto>> all(Pageable pageable, @RequestParam Status status){
        return ResponseEntity.status(HttpStatus.OK).body(priceService.all(pageable, status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PriceDto> get(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(priceService.get(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody @Valid PriceDto priceDto){
        priceService.create(priceDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid PriceDto priceDto){
        priceService.update(id, priceDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

//    @DeleteMapping("/{id}/delete")
//    public void delete(@PathVariable Long id){
//        priceService.delete(id);
//    }

    @PatchMapping("/{id}/changeStatus")
    public ResponseEntity<Void> changeStatus(@PathVariable Long id,
                                             @RequestBody @Valid ActiveInactiveDto activeInactiveDto){
        priceService.changeStatus(id, activeInactiveDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/therapy/{id}")
    public ResponseEntity<Page<PriceDto>> therapyPrices(@PathVariable Long id, Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(priceService.byTherapy(id, pageable));
    }
}
