package com.raffle.repository;

import com.raffle.model.Player;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PlayerRepository {
    private List<Player> listOfPlayers = new ArrayList<>();

    public List<Player> findAll() {
        return listOfPlayers;
    }

    public void add(Player player) {
        listOfPlayers.add(player);
    }

    public void delete(Integer index) {
        listOfPlayers.remove(index.intValue());
    }

    public void deleteAll() {
        listOfPlayers.removeAll(listOfPlayers);
    }
}
