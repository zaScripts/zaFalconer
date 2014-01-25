package task.tasks;

import java.util.concurrent.Callable;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.powerbot.script.util.Random;
import org.powerbot.script.wrappers.Npc;

import task.Task;
import zafalconry.ZaFalconry;

public class Hunt extends Task
{

	public Hunt(MethodContext arg0) 
	{
		super(arg0);
		
	}

	@Override
	public boolean activate()
	{
		if( ctx.players.local().getAnimation() == -1 && ZaFalconry.status !="Retrieving")
			return true;
		return false;
	}

	@Override
	public void execute() 
	{
		ZaFalconry.status = "Hunting";
		final Npc kebbit = ctx.npcs.select().id(ZaFalconry.kebbitID).nearest().poll();
		if(kebbit.isOnScreen())
		{
			kebbit.interact("Catch");
			Condition.wait(new Callable <Boolean>()
			{

				@Override
				public Boolean call() throws Exception 
				{
					return kebbit.getId() ==ZaFalconry.falconID; // turned into a falcon
				}
					
			},Random.nextInt(400,600),5);
			
			if(kebbit.getId()==ZaFalconry.kebbitID) //still a kebbit
			{
				return;
			}else
			{
				ZaFalconry.status = "Retrieving";
			}

			
			
		}
			
		if(!kebbit.isOnScreen())
		{
			ctx.camera.turnTo(kebbit.getLocation());
			if(kebbit.isOnScreen())
				return;
			ctx.movement.stepTowards(kebbit.getLocation());
				Condition.wait(new Callable <Boolean>()
				{

					@Override
					public Boolean call() throws Exception 
					{
						return ctx.players.local().getAnimation() ==-1;
					}
						
				},Random.nextInt(200, 400),5);
		}
						
	}
		
		
}


