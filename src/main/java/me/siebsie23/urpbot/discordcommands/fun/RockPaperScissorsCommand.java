package me.siebsie23.urpbot.discordcommands.fun;

import me.siebsie23.urpbot.main.Main;
import org.bukkit.scheduler.BukkitRunnable;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.Random;

public class RockPaperScissorsCommand implements MessageCreateListener {

    private Main plugin;

    public RockPaperScissorsCommand(Main plugin) {
        this.plugin = plugin;
    }

    public void onMessageCreate(MessageCreateEvent event) {

        if(!event.getMessageAuthor().isUser())
            return;

        if(event.getMessageContent().startsWith("!rps")) {
            String[] args = event.getMessageContent().split(" ");
            if(args.length == 2) {
                String input = args[1];
                if(input.equalsIgnoreCase("rock") || input.equalsIgnoreCase("paper") || input.equalsIgnoreCase("scissors")) {
                    event.getChannel().sendMessage(RPS(input));
                }else {
                    event.getChannel().sendMessage(":x: Please use !rps <rock, paper, scissors> :x:");
                }
            }else {
                event.getChannel().sendMessage(":x: Please use !rps <rock, paper, scissors> :x:");
            }
        }
    }


    private String RPS(String input) {
        int rock = 1, paper = 2, scissors = 3;
        int in = 1;
        Random r = new Random();
        int random = r.nextInt(3-1+1)+1;
        if(input.equalsIgnoreCase("rock"))
            in = 1;
        if(input.equalsIgnoreCase("paper"))
            in = 2;
        if(input.equalsIgnoreCase("scissors"))
            in = 3;

        if(random == rock) {
            if(in == rock) {
                return ":rock: Rock vs. Rock, **ITS A TIE!**";
            }else if(in == paper) {
                return ":rock: Rock vs. Paper, **YOU WIN!**";
            }else {
                return ":rock: Rock vs. Scissors, **YOU LOSE!**";
            }
        }else if(random == paper) {
            if(in == rock) {
                return ":newspaper: Paper vs. Rock, **YOU LOSE!**";
            }else if(in == paper) {
                return ":newspaper: Paper vs. Paper, **ITS A TIE!**";
            }else {
                return ":newspaper: Paper vs. Scissors, **YOU WIN!**";
            }
        }else if(random == scissors) {
            if(in == rock) {
                return ":scissors: Scissors vs. Rock, **YOU WIN!**";
            }else if(in == paper) {
                return ":scissors: Scissors vs. Paper, **YOU LOSE!**";
            }else {
                return ":scissors: Scissors vs. Scissors, **ITS A TIE!**";
            }
        }else {
            return ":x: Something went wrong, please try again later or contact a crew member. :x:";
        }
    }



}
