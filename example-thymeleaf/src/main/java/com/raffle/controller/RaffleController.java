package com.raffle.controller;

import com.raffle.model.Player;
import com.raffle.service.RaffleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RaffleController {

    public static final String RAFFLE_VIEW = "raffle";
    public static final String PLAY_VIEW = "play";

    @Autowired
    private RaffleService raffleService;

    @GetMapping("/")
    public String getRafflePage(Model model) {
        return RAFFLE_VIEW;
    }

    @PostMapping("/")
    public String addPlayer(Model model, @Valid Player player, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return RAFFLE_VIEW;
        }
        raffleService.addPlayer(player);
        model.addAttribute("player", new Player());
        return RAFFLE_VIEW;
    }

    @GetMapping("/play")
    public String play(Model model) {
        Player winner = raffleService.play();
        if (winner != null) {
            model.addAttribute("winner", winner);
        } else {
            return RAFFLE_VIEW;
        }

        return PLAY_VIEW;
    }

    @PostMapping("/delete/{index}")
    public String delete(Model model, @PathVariable Integer index) {
        raffleService.deletePlayer(index);
        return RAFFLE_VIEW;
    }

    @PostMapping("/clear")
    public String clear(Model model) {
        raffleService.clear();
        return RAFFLE_VIEW;
    }

    @ModelAttribute("player")
    public Player getPlayer() {
        return new Player();
    }

    @ModelAttribute("listOfPlayers")
    public List<Player> getListOfPlayers() {
        return raffleService.getListOfPlayers();
    }
}
