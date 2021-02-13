package me.sachin.ConfigurationUtils;

import me.sachin.wanderin;
import me.sachin.utils.ColorUtils;

public class ConfigSection {


    private static String invalidDimensionMessage;
    private static String permissionMessage;


    public static String getInvalidDimensionMessage() {
        try {
            invalidDimensionMessage = ColorUtils.translateColorCodes(wanderin.getPlugin().getConfig().getString("Messages.invalidDimension"));
            if(invalidDimensionMessage == ""){
                return null;
            }
        } catch (Exception e) {
            invalidDimensionMessage = ColorUtils.translateColorCodes("&cCould not find structure in current dimension");
        }
        return invalidDimensionMessage;
    }

    public static String getPermissionMessage() {
        try {
            permissionMessage = ColorUtils.translateColorCodes(wanderin.getPlugin().getConfig().getString("Messages.permission"));
            if(permissionMessage == ""){
                return null;
            }
        } catch (Exception e) {
            permissionMessage = ColorUtils.translateColorCodes("&cYou dont have permission to execute the command");
        }
        return permissionMessage;
    }
}
