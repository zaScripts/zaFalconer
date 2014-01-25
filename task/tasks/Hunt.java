package task.tasks;

import java.util.concurrent.Callable;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.powerbot.script.util.Random;
import org.powerbot.script.wrappers.Npc;

import task.Task;

public class Hunt extends Task
{

	public Hunt(MethodContext arg0) 
	{
		super(arg0);
		
	}

	@Override
	public boolean activate()
	{
		return ctx.players.local().getAnimation() == -1;
	}

	@Override
	public void execute() 
	{
		
		final Npc kebbit = ctx.npcs.select().id(5098).nearest().poll();
		if(kebbit.isOnScreen())
		{
			kebbit.interact("Catch");
			Condition.wait(new Callable <Boolean>()
					{

						@Override
						public Boolean call() throws Exception 
						{
							return !kebbit.isValid();
						}
						
					},Random.nextInt(400, 500),5);
			
		}else // not on the screen
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
						
					},Random.nextInt(400, 500),5);
		}
		
		
	}

}
