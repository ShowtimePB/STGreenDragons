package ShowtimeScripts.dead.STGreenDragons.task.deathrun;

import ShowtimeScripts.dead.STGreenDragons.main.STGreenDragons;
import ShowtimeScripts.dead.STGreenDragons.main.Task;
import ShowtimeScripts.dead.STGreenDragons.misc.DataContainer;
import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Equipment;
import org.powerbot.script.rt4.Game;

public class DeathTeleport extends Task{
	
	public DeathTeleport(ClientContext ctx, DataContainer data){
		super(ctx, data);
		this.setData(data);
	}
	
	
	Tile topLeftLumbridgeBank = new Tile(3206, 3224, 2);
	Tile bottomRightLumbridgeBank = new Tile(3211, 3214, 2);
	Area lumbridgeBankArea = new Area(topLeftLumbridgeBank, bottomRightLumbridgeBank);
	
	@Override
	public boolean activate(){
		if(lumbridgeBankArea.contains(ctx.players.local().tile())) return true;
		return false;
	}
	
	@Override
	public void execute(){
		
		if(ctx.bank.opened()){
			ctx.bank.close();
		}
		
		
		STGreenDragons.status = "Resetting Location";
		
		if(data.hasGloryInventory(ctx, data)){
			ctx.inventory.select().id(data.gloryIDInventory(ctx, data)).poll().interact("Wear");
		}
		
		if(data.correctEquip(ctx, data)){
			ctx.game.tab(Game.Tab.EQUIPMENT);
			ctx.equipment.itemAt(Equipment.Slot.NECK).interact("Edgeville");
			Condition.wait(() -> !lumbridgeBankArea.contains(ctx.players.local().tile()));
			ctx.game.tab(Game.Tab.INVENTORY);
		}
		
		for(int i : data.getItemIDArray()){
			
			if(i == data.getPrimaryWeaponID()){
				ctx.inventory.select().id(i).poll().interact("Wield");
				Condition.wait(() -> ctx.inventory.select().id(i).count() == 0);
				
			}else{
				
				ctx.inventory.select().id(i).poll().interact("Wear");
				Condition.wait(() -> ctx.inventory.select().id(i).count() == 0);
			}
		}
		
		
	}
	
	private DataContainer data;
	
	public DataContainer getData(){
		return data;
	}
	
	public void setData(DataContainer data){
		this.data = data;
	}
}
