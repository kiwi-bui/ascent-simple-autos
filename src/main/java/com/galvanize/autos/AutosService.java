package com.galvanize.autos;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutosService {

    AutosRepository autosRepository;

    public AutosService(AutosRepository autosRepository) {
        this.autosRepository = autosRepository;
    }

    public AutosList getAutos() {
        List<Automobile> automobiles = autosRepository.findAll();

        if(!automobiles.isEmpty()) {
            return new AutosList(automobiles);
        }

        return new AutosList();
    }

    public AutosList getAutos(String color, String make) {
        List<Automobile> automobiles = autosRepository.findByColorContainsAndMakeContains(color, make);

        if(!automobiles.isEmpty()) {
            return new AutosList(automobiles);
        }

        return new AutosList();
    }

    public Automobile addAuto(Automobile automobile) {
        return autosRepository.save(automobile);
    }

    public Automobile getAuto(String vin) {
        return autosRepository.findByVin(vin).orElse(null);
    }

    public Automobile updateAuto(String vin, int price, Preowned preowned) {
        Optional<Automobile> oFoundAutomobile = autosRepository.findByVin(vin);

        if (oFoundAutomobile.isPresent()) {
            oFoundAutomobile.get().setPrice(price);
            oFoundAutomobile.get().setPreowned(preowned);
            return autosRepository.save(oFoundAutomobile.get());
        } else {
            return null;
        }
    }

    public void deleteAuto(String vin) {
        Optional<Automobile> oFoundAutomobile = autosRepository.findByVin(vin);

        if (oFoundAutomobile.isPresent()) {
            autosRepository.delete(oFoundAutomobile.get());
        } else {
            throw new InvalidAutoExcepton();
        }
    }
}
