package com.raffle.service;

import com.raffle.model.Player;
import com.raffle.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class RaffleService {

    @Autowired
    private PlayerRepository playerRepository;

    public List<Player> getListOfPlayers() {
        return playerRepository.findAll();
    }

    public void addPlayer(Player player) {
        playerRepository.add(player);
    }

    public void deletePlayer(Integer index) {
        playerRepository.delete(index.intValue());
    }

    public Player play() {
        List<Player> listOfPlayers = playerRepository.findAll();
        if (listOfPlayers.size() > 0) {
            Random r = new Random();
            int index = r.ints(0, listOfPlayers.size()).findFirst().getAsInt();
            return listOfPlayers.get(index);
        }
        return null;
    }

    public void clear() {
        playerRepository.deleteAll();
    }
}
