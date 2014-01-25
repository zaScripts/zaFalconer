package task.tasks;

import org.powerbot.script.methods.Hud;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.wrappers.Item;

import task.Task;

public class DropItems extends Task
{

	public DropItems(MethodContext arg0) 
	{
		super(arg0);
		
	}

	@Override
	public boolean activate()
	{
		return ctx.backpack.select().count() >=27;
	}

	@Override
	public void execute() 
	{
		if(!ctx.hud.isOpen(Hud.Window.BACKPACK))
			ctx.hud.open(Hud.Window.BACKPACK);
		if(!ctx.hud.isVisible(Hud.Window.BACKPACK))
			ctx.hud.view(Hud.Window.BACKPACK);
		
		for(Item fur : ctx.backpack.select().id(10125))
			fur.interact("Drop");
		for(Item bone : ctx.backpack.select().id(526))
			bone.interact("Bury");
		
		ctx.hud.close(Hud.Window.BACKPACK);
		
	}

}
