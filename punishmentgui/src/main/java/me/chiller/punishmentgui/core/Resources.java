package me.chiller.punishmentgui.core;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ethan on 6/27/2015.
 */
public class Resources
{
	private static String DATE_FORMAT = "MM/dd/yyyy h:mm:ss a";
	
	public static enum Permission
	{
		PUNISH_USE("punish.use"),
		TEMP_MUTE("punish.temp_mute"),
		TEMP_BAN("punish.temp_ban"),
		PERM_MUTE("punish.perm_mute"),
		PERM_BAN("punish.perm_ban"),
		PROTECTED("punish.protected");
		
		private String perm;
		
		private Permission(String perm)
		{
			this.perm = perm;
		}
		
		public String toString()
		{
			return perm;
		}
	}
	
	public static enum Messages
	{
		MESSAGE_PREFIX("%message_prefix%", "&c&lPUNISH&r"),
		MESSAGE_SUFFIX("%message_suffix%", "&aUnfairly punished? Contact us on the forums!"),
		
		WARN(     "", "%message_prefix% &4You have been warned &6(%reason%)"),
		PERM_BAN( "", "%message_prefix% &4You have been permanantly banned &6(%reason%)"),
		PERM_MUTE("", "%message_prefix% &4You have been permanantly muted &6(%reason%)"),
		TEMP_BAN( "", "%message_prefix% &4You have been temporarily banned &6(%reason%)"),
		TEMP_MUTE("", "%message_prefix% &4You have been temporarily muted &6(%reason%)"),
		
		UNMUTE(    "", "%message_prefix% &aYou are no longer muted."),
		PERM_MUTED("", "%message_prefix% &4You are permanently muted."),
		TEMP_MUTED("", "%message_prefix% &4You are muted until &b%date%"),
		
		LOGIN_PERM_BAN("", "You have been permanently banned &6(%reason%)#n%message_suffix%"),
		LOGIN_TEMP_BAN("", "You have been banned until &b%date% &6(%reason%)#n%message_suffix%");
		
		static
		{
			for (Messages message : values())
			{
				message.value = ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString((message.name().contains("MESSAGE") ? "" : "messages.") + message.getKey(), message.def)).replace("#n", "\n");
			}
			
			for (Messages message : values())
			{
				for (Messages replacement : values())
				{
					if (replacement.getReplacement().isEmpty()) continue;
					
					message.value = message.value.replace(replacement.getReplacement(), replacement.value);
				}
			}
		}
		
		protected String replacement;
		protected String def;
		protected String value;
		
		private Messages(String replacement, String def)
		{
			this.replacement = replacement;
			this.def = def;
		}
		
		public String getKey()
		{
			return name().toLowerCase();
		}
		
		public String getReplacement()
		{
			return replacement;
		}
		
		public String toString()
		{
			return value;
		}
		
		public String replace(String replace, String replacement)
		{
			return toString().replace(replace, replacement);
		}
	}
	
	public static enum Times
	{
		BAN("ban_increment",   60),
		MUTE("mute_increment", 30);
		
		static
		{
			for (Times time : values())
			{
				time.time = Main.getInstance().getConfig().getInt("time." + time.key, time.time);
			}
		}
		
		protected String key;
		protected int time;
		
		private Times(String key, int time)
		{
			this.key = key;
			this.time = time;
		}
		
		public long getTime(int amount)
		{
			return System.currentTimeMillis() + time * (amount + 1) * 1000;
		}
	}
	
	public static String getFormattedDate(Long date)
	{
		return new SimpleDateFormat(DATE_FORMAT).format(new Date(date));
	}
	
	public static void sendMessage(String msg, CommandSender recipient, ChatColor startColor)
	{
		if (recipient != null) recipient.sendMessage(String.format("%s %s%s", Messages.MESSAGE_PREFIX, startColor, msg));
	}
	
	public static void sendMessage(String msg, CommandSender recipient)
	{
		if (recipient != null) recipient.sendMessage(msg);
	}
}
