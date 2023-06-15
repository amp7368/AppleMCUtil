package apple.mc.utilities.player.chat;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public interface SendMessage {
    SendMessage instance = new SendMessage() {
    };

    static SendMessage get() {
        return instance;
    }

    default void green(CommandSender player, String message, Object... args) {
        sendMessage(player, ChatColor.GREEN, message, args);
    }

    default void red(CommandSender player, String message, Object... args) {
        sendMessage(player, ChatColor.RED, message, args);
    }

    default void aqua(CommandSender player, String message, Object... args) {
        sendMessage(player, ChatColor.AQUA, message, args);
    }

    private void sendMessage(CommandSender player, ChatColor color, String message, Object[] args) {
        player.sendMessage(color + String.format(message, args));
    }
}
