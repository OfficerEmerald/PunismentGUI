package me.chiller.punishmentgui.data;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import me.chiller.punishmentgui.core.Resources;

/**
 * Created by Ethan Zeigler on 7/7/2015 for PunismentGUI.
 */
public class Infraction implements ConfigurationSerializable, Comparable<Infraction>
{
	private PunishType type;
	private String reason;
	private Long date;
	private String givenBy;
	private String removedBy;
	private boolean active;
	
	public Infraction(PunishType type, String reason, long date, String givenBy)
	{
		this.type = type;
		this.reason = reason;
		this.date = date;
		this.givenBy = givenBy;
		this.removedBy = "";
		this.active = type != PunishType.WARN;
	}
	
	public Infraction(Map<String, Object> map)
	{
		type = PunishType.valueOf((String) map.get("type"));
		reason = (String) map.get("reason");
		givenBy = (String) map.get("given_by");
		removedBy = (String) map.get("removed_by");
		active = (Boolean) map.get("active");
	}
	
	public Long getDate()
	{
		return date;
	}
	
	public String getDateString()
	{
		return Resources.getFormattedDate(getDate());
	}
	
	public String getReason()
	{
		return reason;
	}
	
	public PunishType getType()
	{
		return type;
	}
	
	public String getGivenBy()
	{
		return givenBy;
	}
	
	public String getRemovedBy()
	{
		return removedBy;
	}
	
	public void setRemovedBy(String player)
	{
		this.removedBy = player;
	}
	
	public void setDate(Long date)
	{
		this.date = date;
	}
	
	public boolean isActive()
	{
		return active;
	}
	
	public void setActive(boolean active)
	{
		this.active = active;
	}

	public Map<String, Object> serialize()
	{
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("type", type.name());
		map.put("reason", reason);
		map.put("given_by", givenBy);
		map.put("removed_by", removedBy);
		map.put("active", active);
		
		return map;
	}

	public int compareTo(Infraction other)
	{
		return getDate().compareTo(other.getDate());
	}
	
}
