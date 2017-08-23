package com.mawic.homepage.controller.game;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BaseGameController {

    @RequestMapping("/game/{gameName}")
    public String gameIndex(@PathVariable String gameName) {
        return "game/" + gameName;
    }
}
