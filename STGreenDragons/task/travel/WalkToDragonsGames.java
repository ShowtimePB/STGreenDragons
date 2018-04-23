package ShowtimeScripts.dead.STGreenDragons.task.travel;

import ShowtimeScripts.dead.STGreenDragons.main.Task;
import ShowtimeScripts.dead.STGreenDragons.misc.DataContainer;
import org.powerbot.script.Area;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

public class WalkToDragonsGames extends Task{


	public WalkToDragonsGames(ClientContext ctx, DataContainer data){
		super(ctx, data);
		this.data = data;
	}

	Tile bottomLeftGreenDragon = new Tile(3134, 3690);
	Tile topRightGreenDragon = new Tile(3168, 3717);
	Area greenDragonArea = new Area(bottomLeftGreenDragon, topRightGreenDragon);

	private Tile lowerLeft = new Tile(1622, 3934);
	private Tile upperRight = new Tile(1642, 3948);
	private Area winterArea = new Area(lowerLeft, upperRight);

	@Override
	public boolean activate(){

		return false;
	}

	@Override
	public void execute(){

	}

	private DataContainer data;

	public DataContainer getData(){
		return data;
	}

	public void setData(DataContainer data){
		this.data = data;
	}
}
