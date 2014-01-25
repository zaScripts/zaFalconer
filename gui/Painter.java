package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.concurrent.TimeUnit;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.methods.MethodProvider;
import org.powerbot.script.methods.Skills;

import zafalconry.ZaFalconry;

public class Painter extends MethodProvider
{
	public Painter(MethodContext arg0) 
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	private final Color paintColor = new Color(0,0,0,180);
	private final Font paintFont = new Font("Tahoma",Font.PLAIN,12);
	

	public void draw(Graphics g)
	{
		final int expGained = ctx.skills.getExperience(Skills.HUNTER) - ZaFalconry.startingExp;
		final int currentLevel = ctx.skills.getLevel(Skills.HUNTER);
		final int levelsGained = currentLevel - ZaFalconry.startingLevel;
		final long runTime = System.currentTimeMillis()-ZaFalconry.startingTime;
		final long expPerHour = (expGained * 3600000L)/runTime;
		
		g.setColor(paintColor);
		g.fillRect(0, 0, 400, 65);
		g.setColor(Color.WHITE);
		g.setFont(paintFont);
		g.drawString("Run Time: "+getRunTimeString(runTime),10,20);
		g.drawString("Experience: "+expGained,170,20);
		g.drawString("Status: "+ZaFalconry.status,270,20);
		g.drawString("Exp/Hour: "+expPerHour,10,40);
		g.drawString("Level: "+currentLevel+"("+levelsGained+")",170 ,40 );
		
	}
	
	private String getRunTimeString(long millis)
	{
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
        return hours+"h "+minutes+"m "+seconds+"s";
	}
}
