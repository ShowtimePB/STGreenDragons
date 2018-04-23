package ShowtimeScripts.dead.STGreenDragons.main;

import ShowtimeScripts.dead.STGreenDragons.misc.DataContainer;
import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;

public abstract class Task extends ClientAccessor{

	public Task(ClientContext ctx, DataContainer data){
		super(ctx);
	}

	public abstract boolean activate();

	public abstract void execute();

}
